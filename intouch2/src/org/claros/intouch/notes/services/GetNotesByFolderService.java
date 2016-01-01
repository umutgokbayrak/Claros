package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesController;
import org.claros.intouch.notes.models.Note;

public class GetNotesByFolderService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 105823654066222540L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	/**
	 * 
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
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		String sId = request.getParameter("folderId");
		if (sId == null || sId.equals("")) {
			sId = "0";
		}
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		out.print("<notes>");
		try {
			List notes = NotesController.getNotesByFolderId(getAuthProfile(request), new Long(sId));
			if (notes != null) {
				Note tmp = null;
				for (int i = 0; i < notes.size(); i++) {
					try {
						tmp = (Note) notes.get(i);
						out.print("<note>");
						out.print("<id>" + tmp.getId().intValue() + "</id>");
						out.print("<bar-color>" + getVal(tmp.getNoteBarColor()) + "</bar-color>");
						out.print("<border-color>" + getVal(tmp.getNoteBorderColor()) + "</border-color>");
						out.print("<bg-color>" + getVal(tmp.getNoteColor()) + "</bg-color>");
						out.print("<content>" + Utility.htmlSpecialChars(getVal(tmp.getNoteContent())) + "</content>");
						out.print("<date>" + sdf.format(new Date(tmp.getNoteDate().getTime())) + "</date>");
						out.print("<height>" + tmp.getPosHeight().intValue() + "</height>");
						out.print("<left>" + tmp.getPosLeft().intValue() + "</left>");
						out.print("<top>" + tmp.getPosTop().intValue() + "</top>");
						out.print("<width>" + tmp.getPosWidth().intValue() + "</width>");
						out.print("</note>");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print("</notes>");
		out.write("</data>");
	}

	private static String getVal(String str) {
		return ((str == null || str.equals("")) ? " " : str);
	}
}
