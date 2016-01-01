package org.claros.intouch.calendar.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.calendar.controllers.CalendarController;
import org.claros.intouch.calendar.controllers.CalendarDBController;
import org.claros.intouch.calendar.models.CalendarDailyItems;
import org.claros.intouch.calendar.models.CalendarHourItems;
import org.claros.intouch.calendar.models.CalendarObject;
import org.claros.intouch.calendar.models.CalendarObjectWrap;
import org.claros.intouch.common.services.BaseService;

public class SaveEventService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		DecimalFormat df = new DecimalFormat("00");
		
		try {
			String sEventId = request.getParameter("eventId");
			
			String sBegin = request.getParameter("begin");
			String sEnd = request.getParameter("end");
			String sColor = request.getParameter("color");
			String sRepeatType = request.getParameter("repeatType");
			String sDescription = request.getParameter("description");
			String sLocation = request.getParameter("location");
			String sReminderDays = request.getParameter("reminderDays");
			String sReminderMethod = request.getParameter("reminderMethod");
			String sYear = request.getParameter("year");
			String sMonth = df.format(Integer.parseInt(request.getParameter("month")));
			String sDay = df.format(Integer.parseInt(request.getParameter("day")));
			
			
			Timestamp recordDate = Timestamp.valueOf(sYear + "-" + sMonth + "-" + sDay + " " + sBegin + ":00.000");
			Timestamp endDate = Timestamp.valueOf(sYear + "-" + sMonth + "-" + sDay + " " + sEnd + ":00.000");

			Timestamp ts = Timestamp.valueOf(sYear + "-" + sMonth + "-" + sDay + " 00:00:00.000");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(ts.getTime()));
			CalendarDailyItems items = CalendarController.populateDailyContent(getAuthProfile(request), cal, false);
			List hrs = items.getHours();
			CalendarHourItems hr = null;
			
			for (int i=0;i<hrs.size();i++) {
				hr = (CalendarHourItems)hrs.get(i);
				List apps = hr.getAppointments();
				if (apps != null) {
					CalendarObjectWrap w = null;
					for (int j=0;j<apps.size();j++) {
						w = (CalendarObjectWrap)apps.get(j);

						Timestamp ocEnd = w.getEndDate();
						Calendar cl = Calendar.getInstance();
						cl.setTimeInMillis(ocEnd.getTime());
						Timestamp ocBegin = w.getOccuringDate();
						Calendar cl2 = Calendar.getInstance();
						cl2.setTimeInMillis(ocBegin.getTime());
						ocEnd = Timestamp.valueOf(cl2.get(Calendar.YEAR) + "-" + df.format(cl2.get(Calendar.MONTH) + 1) + "-" + df.format(cl2.get(Calendar.DATE)) + " " + df.format(cl.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(cl.get(Calendar.MINUTE)) + ":00.000");
						
						if ((recordDate.before(w.getOccuringDate()) && (endDate.equals(w.getOccuringDate()) || endDate.before(w.getOccuringDate()))) || 
							(recordDate.equals(ocEnd) || recordDate.after(ocEnd))) {
							// do nothing it is valid
							System.out.println("ab-> " + recordDate + " ae->" + endDate + " xb->" + w.getOccuringDate() + " xe->" + ocEnd);
						} else {
							if (!sEventId.equals(w.getId().toString())) {
								out.print("collapse");
								return;
							}
						}
					}
				}
			}
			
			AuthProfile auth = getAuthProfile(request);
			CalendarObject item = new CalendarObject();
			item.setColor(sColor);
			item.setDescription(sDescription);
			item.setEndDate(endDate);
			if (sEventId != null && !sEventId.equals("null") && sEventId.length() > 0) {
				try {
					item.setId(new Long(sEventId));
				} catch (NumberFormatException nf) {}
			}
			item.setLocation(sLocation);
			item.setRecordDate(recordDate);
			item.setReminderDays(new Integer(sReminderDays));
			item.setReminderMethod(new Integer(sReminderMethod));
			item.setRepeatType(new Integer(sRepeatType));
			item.setUsername(auth.getUsername());
			// this item is modified so we shall set the alarm again. 
			item.setRemindedBefore("false");
			item.setLastDismissedAt(null);
			
			CalendarDBController.saveEvent(auth, item);

			List events = CalendarDBController.getCalendarObjectsByUser(auth, false);
			Long lastId = null;
			if (events != null) {
				CalendarObject tmp = (CalendarObject)events.get(0);
				lastId = tmp.getId();
			}
			out.print("ok" + lastId.longValue());
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
