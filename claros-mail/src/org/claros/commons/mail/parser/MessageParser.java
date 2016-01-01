package org.claros.commons.mail.parser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.mail.Header;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.models.EmailSensitivity;
import org.claros.commons.mail.utility.Utility;
import org.claros.commons.utility.Formatter;

/**
 * @author Umut Gokbayrak
 *
 */
public class MessageParser {
    private static Log log = LogFactory.getLog(MessageParser.class);
    
    /**
     * 
     */
    public MessageParser() {
        super();
    }

	@SuppressWarnings("unchecked")
	public static final Email parseMessage(Message pop3Msg) {
		Email msg = new Email();

    	// get base headers
		try {
            EmailHeader header = new EmailHeader();
            header.setFrom(pop3Msg.getFrom());
            header.setTo(pop3Msg.getRecipients(Message.RecipientType.TO));
            header.setCc(pop3Msg.getRecipients(Message.RecipientType.CC));
            header.setBcc(pop3Msg.getRecipients(Message.RecipientType.BCC));
            header.setReplyTo(pop3Msg.getReplyTo());
            header.setDate(pop3Msg.getSentDate());
            header.setSize(pop3Msg.getSize());
            header.setSubject(pop3Msg.getSubject());
           	header.setUnread(!pop3Msg.isSet(javax.mail.Flags.Flag.SEEN));

			// now set the human readables.
			header.setDateShown(Formatter.formatDate(header.getDate(), "dd.MM.yyyy HH:mm"));
			header.setFromShown(Utility.addressArrToString(header.getFrom()));
			header.setToShown(Utility.addressArrToString(header.getTo()));
			header.setCcShown(Utility.addressArrToString(header.getCc()));
			header.setSizeShown(Utility.sizeToHumanReadable(header.getSize()));
			
			setHeaders(pop3Msg, header);

            msg.setBaseHeader(header);
        } catch (Exception e) {
            log.error("Exception occured while parsing the message base headers", e);
        }

		ArrayList<EmailPart> parts = new ArrayList<EmailPart>();
		parts = fetchParts(pop3Msg, parts);
		if (parts != null) {
			EmailPart part = null;
			for (int i=0;i<parts.size();i++) {
				part = (EmailPart)parts.get(i);
				part.setId(i);
			}
		}
		msg.setParts(parts);
		
		// store all headers
        /*
		try {
            Enumeration en = pop3Msg.getAllHeaders();
            String name, val = "";
            Object tmp = null;
            while (en.hasMoreElements()) {
            	tmp = en.nextElement();
            	name = (tmp == null) ? "" : tmp.toString();
            	tmp = pop3Msg.getHeader(name);
            	val = (tmp == null) ? "" : tmp.toString();
            	msg.addHeader(name, val);
            }
        } catch (MessagingException e1) {
            log.error("Exception occured while parsing the message generic all headers", e1);
        }
        */
		try {
            Enumeration<Header> en = pop3Msg.getAllHeaders();
            String name, val = "";
            Header tmp = null;
            while (en.hasMoreElements()) {
            	tmp = (Header)en.nextElement();
            	name = tmp.getName();
            	val = tmp.getValue();
            	/*
            	name = (tmp == null) ? "" : tmp.toString();
            	tmp = pop3Msg.getHeader(name);
            	val = (tmp == null) ? "" : tmp.toString();
            	*/
            	msg.addHeader(name, val);
            }
        } catch (MessagingException e1) {
            log.error("Exception occured while parsing the message generic all headers", e1);
        }
        
		return msg;
	}

	/**
	 * A recursive algorithm travelling through a MIME message, looking
	 * at the mime types of each part and decodes it into a text content.
	 * @param p
	 * @param parts
	 * @return ArrayList of EmailParts
	 */
	private static ArrayList<EmailPart> fetchParts(Part p, ArrayList<EmailPart> parts) {
		if (p == null) return null;

		try {
            if (!p.isMimeType("text/rfc822-headers") && p.isMimeType("text/*")) {
            	try {
                    EmailPart myPart = new EmailPart();
                    myPart.setSize(p.getSize());
                    myPart.setContentType(p.getContentType());
                    myPart.setFileName(p.getFileName());
                    myPart.setDisposition(p.getDisposition());
                    Object pContent;
					try {
						pContent = p.getContent();
					} catch (UnsupportedEncodingException e) {
						pContent = "Message has an illegal encoding. " + e.getLocalizedMessage();
					}
                    if (pContent != null) {
                    	myPart.setContent(pContent.toString());
                    } else {
                    	myPart.setContent("Illegal content");
                    }
                    parts.add(myPart);
                } catch (Exception e) {
                    log.error("Part is mimeType text/rfc822-headers and is mimeType text/* but exception occured", e);
                }
            } else if (p.isMimeType("multipart/*")) {
                try {
                    Multipart mp = (Multipart) p.getContent();
                    int count = mp.getCount();
                    for (int i = 0; i < count; i++) {
                    	fetchParts(mp.getBodyPart(i), parts);
                    }
                } catch (Exception e) {
                    log.error("Part is mimeType multipart/* but exception occured", e);
                }
		} else if (p.isMimeType("message/rfc822")) {
            	fetchParts((Part) p.getContent(), parts);
            } else {
            	try {
                    EmailPart myPart = new EmailPart();
                    myPart.setSize(p.getSize());
                    myPart.setContentType(p.getContentType());
                    myPart.setFileName((p.getFileName() == null) ? "rfc822.txt" : p.getFileName());
                    myPart.setDisposition(p.getDisposition());
                    String headContentID[] = p.getHeader("Content-ID");
                    if (headContentID != null) {
                    	myPart.setContentId(headContentID[0]);
                    }
                    InputStream is = p.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int c;
                    while ((c = is.read()) != -1) {
                    	baos.write(c);
                    }
                    myPart.setContent(baos);
                    parts.add(myPart);
                    is.close();
                    baos.close();
                } catch (Exception e) {
                    log.error("An exception occured while parsing this part.", e);
                }
            }
        } catch (Exception e) {
            log.error("An exception occured while parsing the parts of the message.", e);
        }
		return parts;
	}

	/**
	 * @param msg
	 * @param header
	 */
	@SuppressWarnings("unchecked")
	public static void setHeaders(Message msg, EmailHeader header) throws javax.mail.MessagingException {
		Enumeration<Header> msgHeaders = msg.getAllHeaders();
		Header msgHeader;
		String key;
		String value;
		while(msgHeaders.hasMoreElements()){
			msgHeader = (javax.mail.Header)msgHeaders.nextElement();
			key = msgHeader.getName().toLowerCase();
			if(key.equals("disposition-notification-to")){
				value = msgHeader.getValue().trim();
				if(value!=null && value.length() > 0){
					header.setRequestReceiptNotification(true);
					header.setReceiptNotificationEmail(value);
				}
			}
			else if(key.equals("x-priority")){
				value = msgHeader.getValue().trim();
				try{
					header.setPriority(Short.valueOf(value).shortValue());
				}catch(Exception e){}
			}
			else if(key.equals("x-msmail-priority")){
				if(header.getPriority() == 0){
					value = msgHeader.getValue().trim();
					try{
						header.setPriority(Short.valueOf(value).shortValue());
					}catch(Exception e){}
				}
			}
			else if(key.equals("sensitivity")){
				value = msgHeader.getValue().trim();
				try{
					header.setSensitivity(EmailSensitivity.valueOf(value));
				}catch(Exception e){}
			}
		}
	}
}
