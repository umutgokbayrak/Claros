package org.claros.intouch.tasks.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.tasks.controllers.TaskController;
import org.claros.intouch.tasks.models.Task;

public class GetTasksService extends BaseService {
	private static final long serialVersionUID = 3228489296620024338L;

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		out.print("<tasks>");
		try {
			List<Task> tasks = TaskController.getTasks(getAuthProfile(request).getUsername());
			if (tasks != null) {
				Task tmp = null;
				for (int i = 0; i < tasks.size(); i++) {
					try {
						tmp = (Task) tasks.get(i);
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print("</tasks>");
		out.write("</data>");
	}

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
