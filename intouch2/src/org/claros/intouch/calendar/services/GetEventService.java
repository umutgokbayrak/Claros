package org.claros.intouch.calendar.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.utility.Utility;
import org.claros.intouch.calendar.controllers.CalendarDBController;
import org.claros.intouch.calendar.models.CalendarObject;
import org.claros.intouch.common.services.BaseService;

public class GetEventService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1977163899790595007L;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		out.print("<events>");

		try {
			String sEventId = request.getParameter("eventId");
			AuthProfile auth = getAuthProfile(request);
			
			CalendarObject tmp = CalendarDBController.getEventById(auth, new Long(sEventId));
			Calendar cal = Calendar.getInstance();
			Calendar calTmp = Calendar.getInstance();
			cal.setTimeInMillis(tmp.getRecordDate().getTime());
			DecimalFormat df = new DecimalFormat("00");
			
			out.print("<event>");
			out.print("<id>" + tmp.getId() + " </id>");
			out.print("<year>" + cal.get(Calendar.YEAR) + " </year>");
			out.print("<month>" + df.format((cal.get(Calendar.MONTH)  + 1)) + " </month>");
			out.print("<day>" + df.format(cal.get(Calendar.DATE)) + " </day>");
			out.print("<begin>" + df.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(cal.get(Calendar.MINUTE)) + " </begin>");
			calTmp.setTimeInMillis(tmp.getEndDate().getTime());
			out.print("<end>" + df.format(calTmp.get(Calendar.HOUR_OF_DAY)) + ":" + df.format(calTmp.get(Calendar.MINUTE)) + " </end>");
			out.print("<color>" + tmp.getColor() + " </color>");
			out.print("<description>" + Utility.htmlSpecialChars(tmp.getDescription()) + " </description>");
			out.print("<hasReminder>" + (tmp.getReminderDays() != null && tmp.getReminderDays().equals("-1") ? "false" : "true") + " </hasReminder>");
			out.print("<repeatType>" + tmp.getRepeatType() + " </repeatType>");
			out.print("<location>" + tmp.getLocation() + " </location>");
			out.print("<reminderDays>" + tmp.getReminderDays() + " </reminderDays>");
			out.print("<reminderMethod>" + tmp.getReminderMethod() + " </reminderMethod>");
			
			out.print("</event>");
				
			out.print("</events>");
			out.print("<result>0</result>");
			out.write("</data>");
		} catch (Exception e) {
			out.print("</events>");
			out.print("<result>1</result>");
			out.write("</data>");
		}

	}

}
