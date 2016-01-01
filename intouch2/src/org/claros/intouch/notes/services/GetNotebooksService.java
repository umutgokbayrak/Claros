package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesFolderController;
import org.claros.intouch.notes.models.NotesFolder;

public class GetNotebooksService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5409377535613883558L;

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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		try {
			out.print("<tr onclick='getNotes(0);' id='notebook0'>" + "<td>" + getText(request, "unorganized.notes") + "</td>" + "</tr>");

			List folders = NotesFolderController.getFolders(getAuthProfile(request));
			if (folders != null) {
				NotesFolder tmp = null;
				for (int i = 0; i < folders.size(); i++) {
					tmp = (NotesFolder) folders.get(i);

					out.print("<tr onclick='getNotes(" + tmp.getId() + ");' id='notebook" + tmp.getId() + "'>" + "<td>" + tmp.getFolderName() + "</td>" + "</tr>");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
