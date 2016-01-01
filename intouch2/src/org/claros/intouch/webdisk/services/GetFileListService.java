package org.claros.intouch.webdisk.services;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.webdisk.controllers.WebdiskController;
import org.claros.intouch.webdisk.models.ClarosWebDskFile;
import org.claros.intouch.webdisk.models.ClarosWebDskFolder;
import org.claros.intouch.webdisk.models.ClarosWebDskObject;

public class GetFileListService extends BaseService {
	private static final long serialVersionUID = 985019861308833738L;
	private static DecimalFormat df = new DecimalFormat("00");
	private static final String months[] = new String[] {"january.short", "february.short", "march.short", "april.short", "may.short", "june.short", "july.short", "august.short", "september.short", "october.short", "november.short", "december.short"};
	
	/**
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Expires", "-1");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();

		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		try {
			AuthProfile auth = getAuthProfile(request);
			TreeSet contents = WebdiskController.getUserFiles(auth.getUsername());
			
			String homeDir = WebdiskController.getUserHome(auth.getUsername()).getAbsolutePath();
			if (contents != null) {
				Iterator iter = contents.iterator();
				ClarosWebDskObject tmp = null;
				while (iter.hasNext()) {
					tmp = (ClarosWebDskObject)iter.next();
					displayObject(tmp, homeDir, out, request);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("</data>");
	}

	private void displayObject(ClarosWebDskObject tmp, String homeDir, PrintWriter out, HttpServletRequest request) throws Exception {
		if (tmp instanceof ClarosWebDskFile) {
			ClarosWebDskFile tmpF = (ClarosWebDskFile)tmp;
			displayFile(tmpF, homeDir, out, request);
		} else if (tmp instanceof ClarosWebDskFolder){ 
			ClarosWebDskFolder tmpD = (ClarosWebDskFolder)tmp;
			out.print("<folder>");
			
			String tmpPath = WebdiskController.correctPath(tmpD.getPath().substring(tmpD.getPath().indexOf(homeDir) + homeDir.length()));
			
			out.print("<name>" + tmpD.getName() + " </name>");
			out.print("<path>" + WebdiskController.correctPath(tmpPath) + " </path>");
			out.print("<path-enc>" + URLEncoder.encode(tmpPath, "utf-8") + " </path-enc>");
			
			TreeSet files = tmpD.getContents();
			Iterator iterF = files.iterator();
			while (iterF.hasNext()) {
				displayObject((ClarosWebDskObject)iterF.next(), homeDir, out, request);
			}
			out.print("</folder>");
		}
	}

	private void displayFile(ClarosWebDskFile tmpF, String homeDir, PrintWriter out, HttpServletRequest request) throws Exception {
		String icon, mime, name, path, size = null;
		File ref = null;
		Calendar cal = Calendar.getInstance();

		out.print("<file>");
		icon = tmpF.getIcon();
		mime = tmpF.getMimeType();
		ref = tmpF.getFile();
		name = tmpF.getName();
		path = tmpF.getPath().substring(tmpF.getPath().indexOf(homeDir) + homeDir.length());
		size = org.claros.commons.mail.utility.Utility.sizeToHumanReadable(ref.length());
		
		cal.setTime(new Date(ref.lastModified()));
		String day = "" + cal.get(Calendar.DATE);
		String month = getText(request, months[cal.get(Calendar.MONTH)]);
		String year = "" + cal.get(Calendar.YEAR);
		String hour = df.format(cal.get(Calendar.HOUR));
		String minutes = df.format(cal.get(Calendar.MINUTE));
		String dateDisplay = day + " " + month + " " + year + " " + hour + ":" + minutes;
		
		if (mime == null) {
			mime = "application/octet-stream";
		}
		
		out.print("<icon>" + icon + " </icon>");
		out.print("<mime>" + mime + " </mime>");
		out.print("<name>" + name + " </name>");
		out.print("<path>" + WebdiskController.correctPath(path) + " </path>");
		out.print("<path-enc>" + URLEncoder.encode(WebdiskController.correctPath(path), "utf-8") + " </path-enc>"); 
		out.print("<size>" + size + " </size>");
		out.print("<date>" + dateDisplay + " </date>");
		out.print("</file>");
	}

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	
}
