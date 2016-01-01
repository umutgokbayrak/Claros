package org.claros.intouch.calendar.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.utility.Utility;
import org.claros.intouch.calendar.controllers.CalendarController;
import org.claros.intouch.calendar.models.CalendarObjectWrap;
import org.claros.intouch.common.services.BaseService;

public class CheckAlertService extends BaseService {
	private static final long serialVersionUID = 1443175579513896492L;
	private static DecimalFormat df = new DecimalFormat("00");

	/**
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		try {
			out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			out.write("<data>");
			out.print("<events>");

			List alerts = CalendarController.getAlertsByUser(getAuthProfile(request));
			if (alerts != null && alerts.size() > 0) {
				CalendarObjectWrap tmp = null;
				Calendar calTmp2 = Calendar.getInstance();
				Calendar calTmp = Calendar.getInstance();
				for (int i=0;i<alerts.size();i++) {
					tmp = (CalendarObjectWrap)alerts.get(i);
					if (tmp.getReminderMethod().intValue() == 1 && (tmp.getRemindedBefore() == null || tmp.getRemindedBefore().equals("false"))) { // is it a popup reminder
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
						out.print("<remindedBefore>" + tmp.getRemindedBefore() + " </remindedBefore>");
						out.print("</event>");
					}
				}
			}
			out.print("</events>");
			out.print("<result>0</result>");
			out.write("</data>");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("</events>");
			out.print("<result>1</result>");
			out.write("</data>");
		}

	
	}

}
