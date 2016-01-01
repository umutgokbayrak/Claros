package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesController;
import org.claros.intouch.notes.models.Note;

/**
 * 
 * @author umut
 * 
 */
public class ChangeColorService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4964565569645398170L;

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
		String bg = request.getParameter("bg").trim();
		String border = request.getParameter("border").trim();
		String bar = request.getParameter("bar").trim();

		if (sId != null) {
			try {
				Note note = NotesController.getNoteById(getAuthProfile(request), new Long(sId));
				if (note != null) {

					if (border.startsWith("rgb")) {
						if (border.indexOf(") ") > 0) {
							border = border.substring(0, border.indexOf(") ") + 1);
						}
					}
					if (bg.startsWith("rgb")) {
						if (bg.indexOf(") ") > 0) {
							bg = bg.substring(0, bg.indexOf(") ") + 1);
						}
					}
					if (bar.startsWith("rgb")) {
						if (bar.indexOf(") ") > 0) {
							bar = bar.substring(0, bar.indexOf(") ") + 1);
						}
					}

					note.setNoteBorderColor(border);
					note.setNoteColor(bg);
					note.setNoteBarColor(bar);
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
