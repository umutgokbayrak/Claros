package org.claros.intouch.webmail.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.mail.models.ByteArrayDataSource;
import org.claros.commons.mail.models.ConnectionMetaHandler;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.protocols.Smtp;
import org.claros.commons.mail.utility.Utility;
import org.claros.commons.utility.MD5;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.common.utility.Constants;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.controllers.MailController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.factory.MailControllerFactory;
import org.claros.intouch.webmail.models.MsgDbObject;
import org.claros.intouch.webmail.models.FolderDbObject;

public class SaveDraftService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8437322711523600816L;

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

		PrintWriter out = response.getWriter();

		try {
			String charset = Constants.charset;
			/*
			String from = new String(request.getParameter("from").getBytes(charset), "utf-8");
			String to = new String(request.getParameter("to").getBytes(charset), "utf-8");
			String cc = new String(request.getParameter("cc").getBytes(charset), "utf-8");
			String bcc = new String(request.getParameter("bcc").getBytes(charset), "utf-8");
			String subject = new String(request.getParameter("subject").getBytes(charset), "utf-8");
			String body= new String(request.getParameter("body").getBytes(charset), "utf-8");
			*/
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			String cc = request.getParameter("cc");
			String bcc = request.getParameter("bcc");
			String subject = request.getParameter("subject");
			String body= request.getParameter("body");

			// learn the global charset setting.

			// learn user preferences from the DB.
			AuthProfile auth = getAuthProfile(request);

			// now create a new email object.
			Email email = new Email();
			EmailHeader header = new EmailHeader();
			
			Address adrs[] = Utility.stringToAddressArray(from);
			header.setFrom(adrs);
			
			header.setTo(Utility.stringToAddressArray(to));
			if (cc != null && cc.trim() != "") {
				header.setCc(Utility.stringToAddressArray(cc));
			}
			if (bcc != null && bcc.trim() != "") {
				header.setBcc(Utility.stringToAddressArray(bcc));
			}
			header.setSubject(subject);
			header.setDate(new Date());

			email.setBaseHeader(header);

			ArrayList parts = new ArrayList();
			EmailPart bodyPart = new EmailPart();
			bodyPart.setContentType("text/html; charset=" + charset);
			bodyPart.setContent(body);
			parts.add(0, bodyPart);
			
			// attach some files...
			ArrayList attachments = (ArrayList)request.getSession().getAttribute("attachments");
			if (attachments != null) {
				List newLst = new ArrayList();
				EmailPart tmp = null;
				for (int i=0;i<attachments.size();i++) {
					try {
						tmp = (EmailPart)attachments.get(i);
						String disp = tmp.getDisposition();
						File f = new File(disp);
						BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
						byte data[] = new byte[(int)f.length()];
						bis.read(data);

						MimeBodyPart bp = new MimeBodyPart();
						DataSource ds = new ByteArrayDataSource(data, tmp.getContentType(), tmp.getFilename());
						bp.setDataHandler(new DataHandler(ds));
						bp.setDisposition("attachment; filename=\"" + tmp.getFilename() + "\"");
						tmp.setDisposition(bp.getDisposition());
						bp.setFileName(tmp.getFilename());
						tmp.setDataSource(ds);
						tmp.setContent(bp.getContent());
						newLst.add(tmp);
						
						bis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				parts.addAll(newLst);
			}
			email.setParts(parts);
			
			// it is time to send the email object message
			Smtp smtp = new Smtp(getConnectionProfile(request), getAuthProfile(request));

			String proxyIp = request.getHeader("x-forwarded-for");
			if (proxyIp == null || proxyIp.equals("")) {
				proxyIp = request.getRemoteAddr();
			}
			
			HashMap sendRes = smtp.send(email, true, proxyIp + "(" + auth.getUsername() + ")");
			MimeMessage msg = (MimeMessage)sendRes.get("msg");

			saveDraft(auth, msg, request);
			out.print("ok");
		} catch (Exception e) {
			out.print("fail");
		}
	}

	/**
	 * 
	 * @param auth
	 * @param msg
	 * @param request
	 * @throws Exception
	 */
	private void saveDraft(AuthProfile auth, MimeMessage msg, HttpServletRequest request) throws Exception {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		msg.writeTo(bos);
		byte bMsg[] = bos.toByteArray();
					
		// serialize the message byte array
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(bMsg);

		// create an email db item
		MsgDbObject item = new MsgDbObject();
		item.setEmail(bMsg);
		String md5Header = new String(MD5.getHashString(bMsg)).toUpperCase(new Locale("en", "US"));

		ConnectionMetaHandler handler = getConnectionHandler(request);
		ConnectionProfile profile = getConnectionProfile(request);

		FolderControllerFactory factory = new FolderControllerFactory(auth, profile, handler);
		FolderController foldCont = factory.getFolderController();
		FolderDbObject fItem = foldCont.getDraftsFolder();

		item.setUniqueId(md5Header);
		item.setFolderId(fItem.getId());
		item.setUnread(new Boolean(false));
		item.setUsername(auth.getUsername());
		item.setMsgSize(new Long(bMsg.length));

		// save the email db item.
		MailControllerFactory mailFact = new MailControllerFactory(auth, profile, handler, fItem.getFolderName());
		MailController mailCont = mailFact.getMailController();
		mailCont.appendEmail(item);
	}

}
