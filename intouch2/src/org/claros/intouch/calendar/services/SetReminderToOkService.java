package org.claros.intouch.calendar.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.calendar.controllers.CalendarDBController;
import org.claros.intouch.calendar.models.CalendarObject;
import org.claros.intouch.common.services.BaseService;

public class SetReminderToOkService extends BaseService {
	private static final long serialVersionUID = -6372890425614908341L;

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

		try {
			String sEventId = request.getParameter("id");
			
			CalendarObject obj = CalendarDBController.getEventById(getAuthProfile(request), new Long(sEventId));
			obj.setRemindedBefore("true");
			obj.setLastDismissedAt(new Timestamp(new Date().getTime()));
			CalendarDBController.saveEvent(getAuthProfile(request), obj);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

}
