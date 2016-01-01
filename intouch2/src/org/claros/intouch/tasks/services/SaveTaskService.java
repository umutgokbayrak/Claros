package org.claros.intouch.tasks.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.tasks.controllers.TaskController;
import org.claros.intouch.tasks.models.Task;

public class SaveTaskService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7792311499215943522L;

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
		String content = request.getParameter("content");
		String priority = request.getParameter("priority");
		
		try {
			if (content == null) {
				content = "";
			}
			
			Task task = null;
			try {
				if (sId != null && sId.trim().length() > 0)
					task = TaskController.getTaskById(getAuthProfile(request).getUsername(), new Long(sId));
			} catch (Exception e) {}
			
			if (task != null) {
				task.setDescription(content);
				task.setPriority(Integer.parseInt(priority));
			} else {
				task = new Task();
				task.setChecked("false");
				task.setColor("black");
				task.setDescription(content);
				task.setId(null);
				task.setPriority(Integer.parseInt(priority));
				task.setRecordDate(new Timestamp(new Date().getTime()));
				task.setUsername(getAuthProfile(request).getUsername());
			}
			TaskController.saveTask(getAuthProfile(request).getUsername(), task);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}
}

