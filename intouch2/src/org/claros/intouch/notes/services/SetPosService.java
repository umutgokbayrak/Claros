package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesController;
import org.claros.intouch.notes.models.Note;

public class SetPosService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6059932042409057155L;

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
		String left = request.getParameter("left");
		String top = request.getParameter("top");
		String width = request.getParameter("width");
		String height = request.getParameter("height");

		if (sId != null) {
			try {
				Note note = NotesController.getNoteById(getAuthProfile(request), new Long(sId));
				if (note != null) {
					note.setPosHeight(new Integer(height));
					note.setPosLeft(new Integer(left));
					note.setPosTop(new Integer(top));
					note.setPosWidth(new Integer(width));

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
