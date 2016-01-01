package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesController;

public class DeleteNoteService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1629039255645939897L;

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
				NotesController.deleteNote(getAuthProfile(request), new Long(sId));
				out.print("ok");
			} catch (Exception e) {
				out.print("fail");
			}
		} else {
			out.print("fail");
		}
	}

}
