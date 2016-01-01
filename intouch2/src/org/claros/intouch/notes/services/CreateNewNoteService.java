package org.claros.intouch.notes.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.notes.controllers.NotesController;
import org.claros.intouch.notes.models.Note;

/**
 * 
 * @author umut
 * 
 */
public class CreateNewNoteService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1520417818456610709L;
	private static Log log = LogFactory.getLog(CreateNewNoteService.class);

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

		String sId = request.getParameter("folderId");
		if (sId != null) {
			try {
				Note note = new Note();
				note.setNoteContent("");
				note.setUsername(getAuthProfile(request).getUsername());
				note.setPosTop(new Integer(100));
				note.setPosLeft(new Integer(237));
				note.setPosWidth(new Integer(225));
				note.setPosHeight(new Integer(225));
				note.setFolderId(new Long(sId));
				note.setNoteBorderColor("#e6e643");
				note.setNoteColor("#fdf7ad");
				note.setNoteBarColor("#ffff7f");
				note.setNoteDate(new Timestamp(new Date().getTime()));
				NotesController.saveNote(getAuthProfile(request), note);

				List notes = NotesController.getNotesByFolderId(getAuthProfile(request), new Long(sId));
				Long lastId = null;
				if (notes != null) {
					Note tmp = (Note) notes.get(0);
					lastId = tmp.getId();
				}
				if (lastId != null) {
					out.print("ok" + lastId.intValue());
				} else {
					out.print("fail");
				}
			} catch (Exception e) {
				log.warn("error while creating note", e);
				out.print("fail");
			}
		} else {
			out.print("fail");
		}
	}
}
