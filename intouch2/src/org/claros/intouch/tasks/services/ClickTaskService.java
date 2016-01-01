package org.claros.intouch.tasks.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.tasks.controllers.TaskController;
import org.claros.intouch.tasks.models.Task;

public class ClickTaskService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3277216365813696293L;

	/**
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		String sId = request.getParameter("id");

		if (sId != null) {
			try {
				Task task = TaskController.getTaskById(getAuthProfile(request).getUsername(), new Long(sId));
				if (task != null) {
					String checked = task.getChecked();
					if (checked != null && checked.equals("true")) {
						task.setChecked("false");
					} else {
						task.setChecked("true");
					}
					TaskController.saveTask(getAuthProfile(request).getUsername(), task);
				} else {
					throw new Exception();
				}
				out.print("ok");
			} catch (Exception e) {
				out.print("fail");
			}
		} else {
			out.print("fail");
		}
	}
}
