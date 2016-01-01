package org.claros.intouch.tasks.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.tasks.controllers.TaskController;
import org.claros.intouch.tasks.models.Task;

public class GetTaskService extends BaseService {
	private static final long serialVersionUID = -8130885905235566600L;

	/**
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();
 
		String sId = request.getParameter("id");
		
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		try {
			Task tmp = TaskController.getTaskById(getAuthProfile(request).getUsername(), new Long(sId));
			if (tmp != null) {
				try {
					out.print("<task>");
					
					out.print("<id>" + tmp.getId() + " </id>");
					out.print("<checked>" + tmp.getChecked() + " </checked>");
					out.print("<color>" + tmp.getColor() + " </color>");
					out.print("<description>" + Utility.htmlSpecialChars(tmp.getDescription()) + " </description>");
					out.print("<priority>" + tmp.getPriority() + " </priority>");
					out.print("<record-date>" + tmp.getRecordDate() + " </record-date>");
					
					out.print("</task>");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("</data>");
	}

}
