package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesFolderController;
import org.claros.intouch.notes.models.NotesFolder;

public class AddNotebookService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1100948823133187378L;

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

		// String charset = Constants.charset;
		// String name = new
		// String(request.getParameter("folderName").getBytes(charset),
		// "utf-8");
		String name = request.getParameter("folderName");

		try {
			NotesFolder folder = new NotesFolder();
			folder.setFolderName(name);
			folder.setUsername(getAuthProfile(request).getUsername());
			Long resId = NotesFolderController.saveFolder(getAuthProfile(request), folder);
			out.print(resId.toString());
		} catch (Exception e) {
			e.printStackTrace();
			out.print("fail");
		}
	}

}
