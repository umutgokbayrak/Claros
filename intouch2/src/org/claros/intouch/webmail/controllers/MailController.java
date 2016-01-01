package org.claros.intouch.webmail.controllers;

import org.claros.commons.mail.models.Email;
import org.claros.intouch.webmail.models.MsgDbObject;

/**
 * @author Umut Gokbayrak
 */
public interface MailController {
	public Email getEmailById(Long emailId) throws Exception;
	public void deleteEmail(Long emailId) throws Exception;
	public void deleteEmails(int msgs[]) throws Exception;
	public void moveEmail(Long msgId, String destFolder) throws Exception;
	public void moveEmails(int msgs[], String destFolder) throws Exception;
	public void appendEmail(MsgDbObject item) throws Exception;
	public void markAsRead(Long msgId) throws Exception;
	public void markAsDeleted(int[] ids) throws Exception;
}
