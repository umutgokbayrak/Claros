package org.claros.intouch.calendar.controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.utility.Formatter;
import org.claros.intouch.calendar.models.CalendarDailyItems;
import org.claros.intouch.calendar.models.CalendarHourItems;
import org.claros.intouch.calendar.models.CalendarObject;
import org.claros.intouch.calendar.models.CalendarObjectWrap;
import org.claros.intouch.calendar.models.CalendarWeeklyItems;
import org.claros.intouch.calendar.utility.Constants;
import org.claros.intouch.calendar.utility.Utility;

/**
 * @author Umut Gokbayrak
 */
public class CalendarController {
	private static Log log = LogFactory.getLog(CalendarController.class); 

	/**
	 * @param auth
	 * @param cal
	 * @return
	 */
	public static CalendarDailyItems populateDailyContent(AuthProfile auth, Calendar cal, boolean getAll) throws Exception {
		CalendarDailyItems out = new CalendarDailyItems();
		ArrayList apps = getAppointments(auth, cal, Constants.DAY, getAll);

		CalendarHourItems tmp = null;
		Timestamp begin, end = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd " + "00:00:00.000");
		Timestamp beginDay = Timestamp.valueOf(sdf.format(cal.getTime()));

//		Timestamp beginDay = Utility.getLimitTimestampFromCalendar(cal, true);
		cal.setTime(new Date(beginDay.getTime()));

		int iCalHours = 24;

		CalendarObjectWrap app = null;
		for (int i=0;i<iCalHours;i++) {
			tmp = new CalendarHourItems();
			tmp.setHour(new Integer(cal.get(Calendar.HOUR_OF_DAY)));
			
			for (int j=0;j<apps.size();j++) {
				app = (CalendarObjectWrap)apps.get(j);

				begin = Utility.getHourLimitTimestampFromCalendar(cal, true);
				end = Utility.getHourLimitTimestampFromCalendar(cal, false);

				if (app.getOccuringDate().equals(begin) || (app.getOccuringDate().before(end) && app.getOccuringDate().after(begin))) {
					tmp.addAppointment(app);
				}
			}
			out.addHour(tmp);
			cal.add(Calendar.HOUR_OF_DAY, 1);
		}
		return out;
	}

	/**
	 * Ekranda g�stermek �zere haftal�k i�eri�i haz�rlayan metoddur. Gerekli tekrarlanan 
	 * objeleri roll ettirir ve geriye g�stermeye haz�r bir arraylist ve i�erisinde 
	 * AgendaDailyItem objeleri d�nd�r�r. 
	 * @param cif
	 * @param cal
	 * @return
	 */
	public static ArrayList populateWeekContent(AuthProfile auth, Calendar cal) {
		ArrayList out = new ArrayList();
		ArrayList apps = getAppointments(auth, cal, Constants.WEEK, false);

		CalendarDailyItems tmp = null;
		Timestamp begin = null;
		Timestamp end = null;
		CalendarObjectWrap app = null;
		for (int i=0;i<7;i++) {
			tmp = new CalendarDailyItems();
			tmp.setDate(new Timestamp(cal.getTime().getTime()));
			begin = Utility.getLimitTimestampFromCalendar(cal, true);
			end = Utility.getLimitTimestampFromCalendar(cal, false);

			for (int j=0;j<apps.size();j++) {
				app = (CalendarObjectWrap)apps.get(j);
				if (app.getOccuringDate().before(end) && app.getOccuringDate().after(begin)) {
					tmp.addAppointment(app);
				}
			}
			out.add(tmp);
			cal.add(Calendar.DATE, 1);
		}
		return out;
	}

	/**
	 * Ayl�k olarak listelenecek kay�tlar�n listesini �eker.  
	 * @param cif
	 * @param cal
	 * @return
	 */
	public static ArrayList populateMonthContent(AuthProfile auth, Calendar cal) {
		ArrayList out = new ArrayList();
		ArrayList apps = getAppointments(auth, cal, Constants.MONTH, false);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		int emptyDaysInFirstWeek = 0;
		Calendar tmpCal = (Calendar)cal.clone();
		while (dayOfWeek != Calendar.SUNDAY) {
			tmpCal.add(Calendar.DATE, -1);
			emptyDaysInFirstWeek++;
			dayOfWeek = tmpCal.get(Calendar.DAY_OF_WEEK);
		}
		
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		int totalDays = emptyDaysInFirstWeek + daysInMonth;
		int weeks = (int)Math.ceil((double)totalDays / 7);
		int exactDays = weeks * 7;
//		int month = cal.get(Calendar.MONTH);

		cal.add(Calendar.DATE, - emptyDaysInFirstWeek);
		CalendarDailyItems day = null;
		CalendarWeeklyItems week = new CalendarWeeklyItems();
		int weekCounter = 0;
		CalendarObjectWrap app = null;
		Timestamp begin = null;
		Timestamp end = null;
		for (int i=0;i<exactDays;i++) {
			day = new CalendarDailyItems();
			begin = Utility.getLimitTimestampFromCalendar(cal, true);
			end = Utility.getLimitTimestampFromCalendar(cal, false);

			for (int j=0;j<apps.size();j++) {
				app = (CalendarObjectWrap)apps.get(j);
				if (app.getOccuringDate().before(end) && app.getOccuringDate().after(begin)) {
					String desc = app.getDescription();
					if (desc == null) {
						desc = Formatter.formatDate(app.getOccuringDate(), "HH:mm"); 
					} else if (desc.length() > 15) {
						desc = desc.substring(0, 15) + "...";
					}
					app.setDescription(desc);
					day.addAppointment(app);
				}
			}

			day.setDate(new Timestamp(cal.getTime().getTime()));
			week.addDay(day);
			cal.add(Calendar.DATE, 1);
			weekCounter++;
			if (weekCounter >= 7) {
				week.setWeekInYear(cal.get(Calendar.WEEK_OF_YEAR) -1 );
				out.add(week);
				week = new CalendarWeeklyItems();
				weekCounter = 0;
			}
		}
		return out;
	}


	/**
	 * @param cif
	 * @param cal
	 * @return
	 */	
	private static ArrayList getAppointments(AuthProfile auth, Calendar cal, int timeLimit, boolean getAll) {
		ArrayList out = new ArrayList();

		try {
			ArrayList res = Utility.getBeginEndTimestamps(cal, timeLimit);
			Timestamp begin = (Timestamp)res.get(0);
			Timestamp end = (Timestamp)res.get(1);

			List allApps = CalendarDBController.getCalendarObjectsByUser(auth, getAll);

			CalendarObject tmpCal = null;
			CalendarObjectWrap tmpApp = null;
			Timestamp tmpDate = null;
			for (int i=0; i<allApps.size(); i++) {
				tmpCal = (CalendarObject)allApps.get(i);
				tmpApp = new CalendarObjectWrap(tmpCal);
				
				tmpDate = tmpApp.getRecordDate();
				
				switch (tmpApp.getRepeatType().intValue()) {
					case Constants.REPEAT_TYPE_ONCE :
						if (tmpDate.after(begin) && tmpDate.before(end)) {
							tmpApp.setOccuringDate(tmpDate);
							out.add(tmpApp.clone());
						}
						break;
					case Constants.REPEAT_TYPE_MONTH : 
						tmpApp = Utility.rollAppointment(tmpApp, Calendar.MONTH, 1, begin, end);
						if (tmpApp != null) {
							out.add(tmpApp.clone());
						}
						break;
					case Constants.REPEAT_TYPE_YEAR : 
						tmpApp = Utility.rollAppointment(tmpApp, Calendar.YEAR, 1, begin, end);
						if (tmpApp != null) {
							out.add(tmpApp.clone());
						}
						break;
					case Constants.REPEAT_TYPE_WEEK : 
						tmpApp = Utility.rollAppointment(tmpApp, Calendar.WEEK_OF_YEAR, 1, begin, end);
						if (tmpApp != null) {
							out.add(tmpApp.clone());
							if (timeLimit == Constants.MONTH) {
								while (tmpApp != null && (tmpApp.getOccuringDate() == null || tmpApp.getOccuringDate().before(end))) {
									tmpApp = Utility.rollAppointmentOnce(tmpApp, Calendar.WEEK_OF_YEAR, 1, end);
									if (tmpApp != null) {
										out.add(tmpApp.clone());
									}
								}
							}
						}
						break;
					case Constants.REPEAT_TYPE_TWO_WEEK :
						tmpApp = Utility.rollAppointment(tmpApp, Calendar.WEEK_OF_YEAR, 2, begin, end);
						if (tmpApp != null) {
							out.add(tmpApp.clone());
							if (timeLimit == Constants.MONTH) {
								while (tmpApp != null && (tmpApp.getOccuringDate() == null || tmpApp.getOccuringDate().before(end))) {
									tmpApp = Utility.rollAppointmentOnce(tmpApp, Calendar.WEEK_OF_YEAR, 2, end);
									if (tmpApp != null) {
										out.add(tmpApp.clone());
									}
								}
							}
						}
						break;
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		Collections.sort(out);

		return out;
	}

	/**
	 * 
	 * @param auth
	 * @return
	 * @throws Exception
	 */
	public static List getAlertsByUser(AuthProfile auth) throws Exception {
		Calendar cal = Calendar.getInstance();
		CalendarDailyItems items = populateDailyContent(auth, cal, false);
		return parseAlerts(items);
	}

	/**
	 * 
	 * @param auth
	 * @return
	 * @throws Exception
	 */
	public static List getAlertsForAll(AuthProfile auth) throws Exception {
		Calendar cal = Calendar.getInstance();
		CalendarDailyItems items = populateDailyContent(auth, cal, true);
		return parseAlerts(items);
	}

	/**
	 * 
	 * @param items
	 * @return
	 */
	private static List parseAlerts(CalendarDailyItems items) {
		Timestamp now = new Timestamp(new Date().getTime());
		List lst = items.getHours();
		List out = new ArrayList();
		if (lst != null) {
			CalendarHourItems tmpH = null;
			CalendarObjectWrap obj = null;
			for (int j=0;j<lst.size();j++) {
				tmpH = (CalendarHourItems)lst.get(j);
				List apps = tmpH.getAppointments();
				
				for (int i=0;i<apps.size();i++) {
					obj = (CalendarObjectWrap)apps.get(i);

					// check to see if this item is dismissed but
					// it my be a recurring event and can be dismissed 
					// by many times. so we shall check if it was last 
					// dismissed before today
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date(now.getTime()));
					Timestamp lastDismissed = obj.getLastDismissedAt();
					ArrayList beginEnd = Utility.getBeginEndTimestamps(cal, Constants.DAY);
					Timestamp beginToday = (Timestamp)beginEnd.get(0);
					
					boolean recurringAlert = false;
					if (lastDismissed != null && lastDismissed.before(beginToday)) {
						recurringAlert = true;
					}
					if (recurringAlert || obj.getRemindedBefore() == null || obj.getRemindedBefore().equals("false")) {
						Timestamp occursAt = obj.getOccuringDate();
						int remindBefore = obj.getReminderDays() * 1000;
						Calendar cal2 = Calendar.getInstance();
						cal2.setTime(new Date(occursAt.getTime()));
						cal2.add(Calendar.MILLISECOND, 0 - remindBefore);
						occursAt = new Timestamp(cal2.getTime().getTime());
						
						if (now.after(occursAt)) {
							out.add(obj);
						}
					}
				}
			}
		}
		return out;
	}
}
