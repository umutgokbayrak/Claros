package org.claros.intouch.calendar.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.utility.Formatter;
import org.claros.commons.utility.Utility;
import org.claros.intouch.calendar.controllers.CalendarController;
import org.claros.intouch.calendar.models.CalendarDailyItems;
import org.claros.intouch.calendar.models.CalendarHourItems;
import org.claros.intouch.calendar.models.CalendarObjectWrap;
import org.claros.intouch.calendar.models.CalendarWeeklyItems;
import org.claros.intouch.common.services.BaseService;

public class GetEventsService extends BaseService {
	private static final long serialVersionUID = 1587912882541089504L;
	private static final String days[] = new String[] {"sunday.long", "monday.long", "tuesday.long", "wednesday.long", "thursday.long", "friday.long", "saturday.long"};
	private static final String months[] = new String[] {"january.long", "february.long", "march.long", "april.long", "may.long", "june.long", "july.long", "august.long", "september.long", "october.long", "november.long", "december.long"};
	private static DecimalFormat df = new DecimalFormat("00");
	
	/**
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		try {
			String type = request.getParameter("type");
			String year = request.getParameter("year");
			String month = df.format(Integer.parseInt(request.getParameter("month")));
			String day = df.format(Integer.parseInt(request.getParameter("day")));
			AuthProfile auth = getAuthProfile(request);

			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			out.write("<data>");
			
			out.print("<real-month>" + month + "</real-month>");
			
			out.print("<events>");

			Timestamp ts = Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00.000");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(ts.getTime()));
			Calendar cal2 = (Calendar)cal.clone();

			if (type.equals("daily")) {
				CalendarDailyItems items = CalendarController.populateDailyContent(auth, cal, false);
				List lst = items.getHours();
				if (lst != null) {
					CalendarHourItems tmpH = null;
					for (int j=0;j<lst.size();j++) {
						tmpH = (CalendarHourItems)lst.get(j);
						List lstApp = tmpH.getAppointments();
						
						printAppointments(lstApp, out);
					}
				}
				out.print("</events>");
				out.print("<date-display>" + getDateDisplayed(cal2, year, month, day, request) + " </date-display>");
			} else if (type.equals("weekly")) {
				// print the events for week
				ArrayList lst = CalendarController.populateWeekContent(auth, cal);
				CalendarDailyItems tmp = null;
				if (lst != null) {
					for (int i=0;i<lst.size();i++) {
						tmp = (CalendarDailyItems)lst.get(i);
						printAppointments(tmp.getAppointments(), out);
					}
				}

				out.print("</events>");
				
				// now print the days for week
				if (lst != null) {
					for (int i=0;i<lst.size();i++) {
						tmp = (CalendarDailyItems)lst.get(i);
						Calendar calT = Calendar.getInstance();
						calT.setTimeInMillis(tmp.getDate().getTime());
						String dD = df.format(calT.get(Calendar.DATE));
						String dM = df.format(calT.get(Calendar.MONTH) + 1);
						String dY = "" + calT.get(Calendar.YEAR);
						
						out.print("<day" + i + ">" + dD + "-" + dM + "-" + dY + "</day" + i + ">");
					}
				}
				
				out.print("<date-display>" + getDateDisplayed(cal2, year, month, day, request) + " </date-display>");
			} else if (type.equals("monthly")) {
				ArrayList lst = CalendarController.populateMonthContent(auth, cal);
				out.print("<week-count>" + lst.size() + "</week-count>");
				
				CalendarWeeklyItems weekTmp = null;
				for (int i=0;i<lst.size();i++) {
					out.print("<week-event>");
					weekTmp = (CalendarWeeklyItems)lst.get(i);
					out.print("<week-in-year>" + weekTmp.getWeekInYear() + "</week-in-year>");
					CalendarDailyItems dailyTmp = null;
					ArrayList lstDays = weekTmp.getDays();
					for (int j=0;j<lstDays.size();j++) {
						dailyTmp = (CalendarDailyItems)lstDays.get(j);
						out.print("	<day-event>");
						out.print("<day-event-day>" + Formatter.formatDate(dailyTmp.getDate(), "dd") + "</day-event-day>");
						out.print("<day-event-month>" + Formatter.formatDate(dailyTmp.getDate(), "MM") + "</day-event-month>");
						out.print("<day-event-year>" + Formatter.formatDate(dailyTmp.getDate(), "yyyy") + "</day-event-year>");
						printAppointments(dailyTmp.getAppointments(), out);
						out.print("	</day-event>");
					}
					out.print("</week-event>");
				}
				out.print("</events>");
				out.print("<date-display>" + getDateDisplayed(cal2, year, month, day, request) + " </date-display>");
			}
			out.print("<result>0</result>");
			out.write("</data>");
		} catch (Exception e) {
			out.print("</events>");
			out.print("<result>1</result>");
			out.write("</data>");
		}
		
	}

	private static void printAppointments(List lstApp, PrintWriter out) {
		Calendar calTmp2 = Calendar.getInstance();
		Calendar calTmp = Calendar.getInstance();
		CalendarObjectWrap tmp = null;
		
		for (int i=0;i<lstApp.size();i++) {
			tmp = (CalendarObjectWrap)lstApp.get(i);
			calTmp.setTimeInMillis(tmp.getEndDate().getTime());
			calTmp2.setTimeInMillis(tmp.getOccuringDate().getTime());
			out.print("<event>");
			out.print("<id>" + tmp.getId() + " </id>");
			out.print("<year>" + calTmp2.get(Calendar.YEAR) + " </year>");
			out.print("<month>" + df.format(calTmp2.get(Calendar.MONTH) + 1) + " </month>");
			out.print("<day>" + df.format(calTmp2.get(Calendar.DATE)) + " </day>");
			out.print("<begin>" +  df.format(calTmp2.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(calTmp2.get(Calendar.MINUTE)) + " </begin>");
			out.print("<end>" + df.format(calTmp.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(calTmp.get(Calendar.MINUTE)) + " </end>");
			out.print("<color>" + tmp.getColor() + " </color>");
			out.print("<description>" + Utility.htmlSpecialChars(tmp.getDescription()) + " </description>");
			out.print("<hasReminder>" + (tmp.getReminderDays() != null && tmp.getReminderDays().equals("-1") ? "false" : "true") + " </hasReminder>");
			out.print("<repeatType>" + tmp.getRepeatType() + " </repeatType>");
			out.print("<location>" + tmp.getLocation() + " </location>");
			out.print("<reminderDays>" + tmp.getReminderDays() + " </reminderDays>");
			out.print("<reminderMethod>" + tmp.getReminderMethod() + " </reminderMethod>");
			out.print("</event>");
		}
	}

	/**
	 * 
	 * @param cal
	 * @param year
	 * @param month
	 * @param day
	 * @param request
	 * @return
	 */
	private String getDateDisplayed(Calendar cal, String year, String month, String day, HttpServletRequest request) {
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;

		String sDayWeek = getText(request, days[dayWeek]);
		String sDayMonth = getText(request, months[Integer.parseInt(month) - 1 ]);
		String type = request.getParameter("type");
		
		String str = "";
		if (type.equals("daily")) {
			str = day + " " + sDayMonth + " " + year + ", " + sDayWeek;
		} else if (type.equals("weekly")) {
			
		} else if (type.equals("monthly")) {
			str = sDayMonth + " " + year;
		}
		return str;
	}
}
