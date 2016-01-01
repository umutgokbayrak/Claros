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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.cache.Cache;
import org.claros.commons.cache.CacheManager;
import org.claros.commons.configuration.Paths;
import org.claros.commons.configuration.PropertyFile;
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
import org.claros.intouch.contacts.controllers.ContactsController;
import org.claros.intouch.preferences.controllers.UserPrefsController;
import org.claros.intouch.webmail.controllers.FolderController;
import org.claros.intouch.webmail.controllers.MailController;
import org.claros.intouch.webmail.factory.FolderControllerFactory;
import org.claros.intouch.webmail.factory.MailControllerFactory;
import org.claros.intouch.webmail.models.MsgDbObject;
import org.claros.intouch.webmail.models.FolderDbObject;

public class SendMailService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7451365138227115574L;
	private static Log log = LogFactory.getLog(SendMailService.class);

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
			String from = request.getParameter("from");
			String to = request.getParameter("to");
			String cc = request.getParameter("cc");
			String bcc = request.getParameter("bcc");
			String subject = request.getParameter("subject");
			String body = request.getParameter("body");
			String requestReceiptNotification = request.getParameter("requestReceiptNotification");
			String priority = request.getParameter("priority");
			String sensitivity = request.getParameter("sensitivity");
			
			// learn the global charset setting.

			// learn user preferences from the DB.
			AuthProfile auth = getAuthProfile(request);

			String saveSentContacts = UserPrefsController.getUserSetting(auth, "saveSentContacts");
			if (saveSentContacts == null) {
				saveSentContacts = "yes";
			}
			
			// now create a new email object.
			Email email = new Email();
			EmailHeader header = new EmailHeader();
			
			Address adrs[] = Utility.stringToAddressArray(from);
			header.setFrom(adrs);
			
			Address tos[] = Utility.stringToAddressArray(to);
			header.setTo(tos);
			if (saveSentContacts != null && saveSentContacts.equals("yes")) {
				saveContacts(auth, tos);
			}
			
			if (cc != null && cc.trim() != "") {
				Address ccs[] = Utility.stringToAddressArray(cc);
				header.setCc(ccs);
				if (saveSentContacts != null && saveSentContacts.equals("yes")) {
					saveContacts(auth, ccs);
				}
			}
			if (bcc != null && bcc.trim() != "") {
				Address bccs[] = Utility.stringToAddressArray(bcc);
				header.setBcc(bccs);
				if (saveSentContacts != null && saveSentContacts.equals("yes")) {
					saveContacts(auth, bccs);
				}
			}
			header.setSubject(subject);
			header.setDate(new Date());

			String replyTo = UserPrefsController.getUserSetting(auth, "replyTo");
			if (replyTo != null && replyTo.trim().length() != 0) {
				header.setReplyTo(new Address[] {new InternetAddress(replyTo)});
			}
			
			if (requestReceiptNotification!=null && requestReceiptNotification.equals("1")) {
				header.setRequestReceiptNotification(Boolean.valueOf(true));
			}
			
			if (priority!=null) {
				header.setPriority(Short.valueOf(priority).shortValue());
			}

			if (sensitivity!=null) {
				header.setSensitivity(Short.valueOf(sensitivity).shortValue());
			}
			
			email.setBaseHeader(header);

			ArrayList parts = new ArrayList();
			EmailPart bodyPart = new EmailPart();
			bodyPart.setContentType("text/html; charset=UTF-8");
			/*
			HtmlCleaner cleaner = new HtmlCleaner(body);
			cleaner.clean(false,false);
			*/
			
			String appendSignature = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.append-signature");
			String sign = "";
			if (appendSignature != null && appendSignature.toLowerCase().equals("true")) {
				Cache cache = CacheManager.getContent("server.signature");
				if (cache == null) {
					BufferedInputStream is = new BufferedInputStream(new FileInputStream(Paths.getCfgFolder() + "/server_signature.txt"));
					int byte_;
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					while ((byte_ = is.read ()) != -1) {
						bos.write (byte_);
					}
					is.close();
					sign = new String(bos.toByteArray());
					bos.close();
					
					cache = new Cache();
					CacheManager.putContent("server.signature", sign, Integer.MAX_VALUE);
				} else {
					sign = (String)cache.getValue();
				}
			}
			body = body + sign;
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
						byte data[] = new byte[(int)f.length() + 2];
						bis.read(data);
						bis.close();

						MimeBodyPart bp = new MimeBodyPart();
						DataSource ds = new ByteArrayDataSource(data, tmp.getContentType(), tmp.getFilename());
						bp.setDataHandler(new DataHandler(ds));
						bp.setDisposition("attachment; filename=\"" + tmp.getFilename() + "\"");
						tmp.setDisposition(bp.getDisposition());
						bp.setFileName(tmp.getFilename());
						tmp.setDataSource(ds);
						tmp.setContent(bp.getContent());
						newLst.add(tmp);
						
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
			
			HashMap sendRes = smtp.send(email, false, proxyIp + "(" + auth.getUsername() + ")");
			MimeMessage msg = (MimeMessage)sendRes.get("msg");
			
			// if we fail to send the message to any of the recepients
			// we should make a report about it to the user. 
			Address[] sent = (Address[])sendRes.get("sent");
//			Address[] fail = (Address[])sendRes.get("fail");
//			Address[] invalid = (Address[])sendRes.get("invalid");
			
			if (sent == null || sent.length == 0) {
				out.print("fail");
			} else {
				// if save to sent items enabled, save the sent mail.
				String saveEnabled = UserPrefsController.getUserSetting(auth, "saveSent");
				if (saveEnabled == null) {
					saveEnabled = "yes";
				}
				if (saveEnabled == null || saveEnabled.equals("yes")) {
					saveSentMail(auth, msg, request);
				}
				out.print("ok");
			}
		} catch (Exception e) {
			out.print("fail");
		}
	}

	/**
	 * 
	 * @param auth
	 * @param adrs
	 */
	private void saveContacts(AuthProfile auth, Address[] adrs) {
		try {
			if (adrs != null) {
				InternetAddress adr = null;
				for (int i=0;i<adrs.length;i++) {
					adr = (InternetAddress)adrs[i];
					ContactsController.saveSenderFromAddr(auth, adr);
				}
			}
		} catch (Exception e) {
			log.debug("save contact failed.", e);
		}
		
	}

	/**
	 * 
	 * @param auth
	 * @param msg
	 * @param request
	 * @throws Exception
	 */
	private void saveSentMail(AuthProfile auth, MimeMessage msg, HttpServletRequest request) throws Exception {
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
		FolderDbObject fItem = foldCont.getSentItems();

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

