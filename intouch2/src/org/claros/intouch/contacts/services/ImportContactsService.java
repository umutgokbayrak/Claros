package org.claros.intouch.contacts.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.intouch.common.exceptions.UnableToImportException;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.contacts.controllers.ImportExportController;

public class ImportContactsService extends BaseService {
	private static int MAX_MEM_SIZE = 1024 * 1024;
	private static int MAX_ATT_SIZE = 1024 * 1024 * Constants.maxAttSize;
	private static HashMap fields = new HashMap();

	private static String tmpDir = Constants.tmpDir;

	/**
	 * 
	 */
	private static final long serialVersionUID = 56265867864257045L;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/html; charset=utf-8");

		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			factory.setSizeThreshold(MAX_MEM_SIZE);
			factory.setRepository(new File(tmpDir));

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(MAX_ATT_SIZE);

			// Parse the request
			List items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator iter = items.iterator();
			
			// we can parse the attachments now
			iter = items.iterator();

			ArrayList columns = new ArrayList();
			ArrayList data = new ArrayList();
			int i = 0;
			
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();

			    if (!item.isFormField()) {
					BufferedReader br = new BufferedReader(new InputStreamReader(item.getInputStream()));
					String line = "";
					String tmp = null;
					try {
						String charset = Constants.charset;
						while ((line = br.readLine()) != null) {
							line = new String(line.getBytes(charset), "utf-8");
							try {
								line = org.claros.commons.utility.Utility.replaceAllOccurances(line, ";;", "; ;");
								line = org.claros.commons.utility.Utility.replaceAllOccurances(line, ";;", "; ;");
								StringTokenizer token = new StringTokenizer(line, ";");
								if (i == 0) {
									// the first line tells about what the fields stand for.
									String fieldName = null;
									while (token.hasMoreTokens()) {
										tmp = token.nextToken().toUpperCase(new Locale("en", "US"));
										fieldName = (String)fields.get(tmp);
										if (fieldName == null) {
											throw new UnableToImportException("Field " + tmp + " is an invalid column name");
										}
										columns.add(fieldName);
									}
								} else {
									// these are the data to be processed.
									ArrayList row = new ArrayList();
									while (token.hasMoreTokens()) {
										tmp = token.nextToken();
										row.add(tmp);
									}
									data.add(row);
								}
							} catch (UnableToImportException ue) {
								throw ue;
							} catch (Exception e) {
								// do nothing sier
							}
							i++;
						}
					} catch (Exception e) {
						response.sendRedirect("../import_ok.jsp?result=1");
					}
			    }
			}

			AuthProfile auth = getAuthProfile(request);
			int success = ImportExportController.importContacts(auth, columns, data);
			int fail = i - success - 1;
			
			response.sendRedirect("../import_ok.jsp?result=0&success=" + success + "&fail=" + fail);
		} catch (Exception e) {
			response.sendRedirect("../import_ok.jsp?result=1");
		}
	}

	static {
		fields.put("FIRST NAME", "FIRST_NAME");
		fields.put("MIDDLE NAME", "MIDDLE_NAME");
		fields.put("LAST NAME", "LAST_NAME");
		fields.put("FIRSTNAME", "FIRST_NAME");
		fields.put("MIDDLENAME", "MIDDLE_NAME");
		fields.put("LASTNAME", "LAST_NAME");
		fields.put("TITLE", "TITLE");
		fields.put("SEX", "SEX");
		fields.put("GSM PRIMARY", "GSM_NO_PRIMARY");
		fields.put("GSM ALTERNATE", "GSM_NO_ALTERNATE");
		fields.put("PRIMARY GSM", "GSM_NO_PRIMARY");
		fields.put("ALTERNATE GSM", "GSM_NO_ALTERNATE");
		fields.put("EMAIL PRIMARY", "EMAIL_PRIMARY");
		fields.put("EMAIL ALTERNATE", "EMAIL_ALTERNATE");
		fields.put("PRIMARY EMAIL", "EMAIL_PRIMARY");
		fields.put("ALTERNATE EMAIL", "EMAIL_ALTERNATE");
		fields.put("WEB PAGE", "WEB_PAGE");
		fields.put("URL", "WEB_PAGE");
		fields.put("WEBSITE", "WEB_PAGE");
		fields.put("WEBPAGE", "WEB_PAGE");
		fields.put("PERSONAL NOTE", "PERSONAL_NOTE");
		fields.put("NOTE", "PERSONAL_NOTE");
		fields.put("SPOUSE NAME", "SPOUSE_NAME");
		fields.put("NICKNAME", "NICKNAME");
		fields.put("NICK NAME", "NICKNAME");
		fields.put("BIRTHDAY", "BIRTHDAY");
		fields.put("BIRTHMONTH", "BIRTHMONTH");
		fields.put("ANNIVERSARYDAY", "ANNIVERSARYDAY");
		fields.put("ANNIVERSARYMONTH", "ANNIVERSARYMONTH");
		fields.put("HOME ADDRESS", "HOME_ADDRESS");
		fields.put("HOME CITY", "HOME_CITY");
		fields.put("HOME PROVINCE", "HOME_PROVINCE");
		fields.put("HOME ZIP", "HOME_ZIP");
		fields.put("HOME COUNTRY", "HOME_COUNTRY");
		fields.put("HOME PHONE", "HOME_PHONE");
		fields.put("HOME FAX", "HOME_FAKS");
		fields.put("COMPANY", "WORK_COMPANY");
		fields.put("JOB TITLE", "WORK_JOB_TITLE");
		fields.put("DEPARTMENT", "WORK_DEPARTMENT");
		fields.put("OFFICE", "WORK_OFFICE");
		fields.put("PROFESSION", "WORK_PROFESSION");
		fields.put("MANAGER NAME", "WORK_MANAGER_NAME");
		fields.put("ASSISTANT NAME", "WORK_ASSISTANT_NAME");
		fields.put("WORK ADDRESS", "WORK_ADDRESS");
		fields.put("WORK CITY", "WORK_CITY");
		fields.put("WORK PROVINCE", "WORK_PROVINCE");
		fields.put("WORK ZIP", "WORK_ZIP");
		fields.put("WORK COUNTRY", "WORK_COUNTRY");
		fields.put("WORK PHONE", "WORK_PHONE");
		fields.put("WORK FAX", "WORK_FAKS");
	}

}
