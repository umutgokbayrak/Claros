package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesController;
import org.claros.intouch.notes.models.Note;

public class SaveNoteService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8523420192102158048L;

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
		// String charset = Constants.charset;
		// String content = new
		// String(request.getParameter("content").getBytes(charset), "utf-8");
		String content = request.getParameter("content");

		if (sId != null) {
			try {
				Note note = NotesController.getNoteById(getAuthProfile(request), new Long(sId));
				if (note != null) {
					note.setNoteContent(content);
					note.setNoteDate(new Timestamp(new Date().getTime()));
					NotesController.saveNote(getAuthProfile(request), note);
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
