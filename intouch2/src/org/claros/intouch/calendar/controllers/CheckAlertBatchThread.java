package org.claros.intouch.calendar.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.configuration.Paths;
import org.claros.commons.configuration.PropertyFile;
import org.claros.commons.exception.SystemException;
import org.claros.commons.mail.models.ByteArrayDataSource;
import org.claros.commons.mail.models.ConnectionProfile;
import org.claros.commons.mail.models.ConnectionProfileList;
import org.claros.commons.mail.models.Email;
import org.claros.commons.mail.models.EmailHeader;
import org.claros.commons.mail.models.EmailPart;
import org.claros.commons.mail.protocols.Smtp;
import org.claros.commons.mail.utility.Utility;
import org.claros.commons.utility.MD5;
import org.claros.intouch.calendar.models.CalendarObject;
import org.claros.intouch.calendar.models.CalendarObjectWrap;
import org.claros.intouch.preferences.controllers.UserPrefsController;

public class CheckAlertBatchThread extends Thread {
	private static DecimalFormat df = new DecimalFormat("00");

	public void run() {
		while (true) {
			try {
				List alerts = CalendarController.getAlertsForAll(null);
				if (alerts != null && alerts.size() > 0) {
					CalendarObjectWrap tmp = null;

					String monYear = null;
					for (int i=0;i<alerts.size();i++) {
						tmp = (CalendarObjectWrap)alerts.get(i);
						
						try {
							// we only parse the e-mail(and other) reminders not the popup ones. 
							if (tmp.getReminderMethod().intValue() == 2) {
								String username = tmp.getUsername();
								AuthProfile auth = new AuthProfile();
								auth.setUsername(username);
								
								// get user's e-mail address, cause we'll be sending e-mail to him/her
								String email = UserPrefsController.getUserSetting(auth, "emailAddress");
								if (email != null && !email.trim().equals("") && email.indexOf("@") > 1) {
									Calendar calendar = new Calendar();
									PropertyList props1 = calendar.getProperties();
									props1.add(new ProdId("-//Claros//inTouch2//EN"));
									props1.add(Version.VERSION_2_0);
									props1.add(CalScale.GREGORIAN);
									props1.add(Method.PUBLISH);
									
									
									//PropertyList props = new PropertyList();
									java.util.Calendar calTmp2 = java.util.Calendar.getInstance();
									java.util.Calendar calTmp = java.util.Calendar.getInstance();

									calTmp.setTimeInMillis(tmp.getEndDate().getTime());
									calTmp2.setTimeInMillis(tmp.getOccuringDate().getTime());
									
									monYear = calTmp2.get(java.util.Calendar.YEAR) + "-" + df.format(calTmp2.get(java.util.Calendar.MONTH) + 1) + "-" + df.format(calTmp2.get(java.util.Calendar.DATE));
									Timestamp start = Timestamp.valueOf(monYear + " " + df.format(calTmp2.get(java.util.Calendar.HOUR_OF_DAY)) + ":" + df.format(calTmp2.get(java.util.Calendar.MINUTE)) + ":00.000");
									Timestamp end = Timestamp.valueOf(monYear + " " + df.format(calTmp.get(java.util.Calendar.HOUR_OF_DAY)) + ":" + df.format(calTmp.get(java.util.Calendar.MINUTE)) + ":00.000");

									/*
									props.add(new DtStart(new DateTime(new Date(start.getTime()))));
									props.add(new DtEnd(new DateTime(new Date(end.getTime()))));
									props.add(new DtStamp(new DateTime(new Date())));
									props.add(new Summary(tmp.getDescription()));
									props.add(new Location(tmp.getLocation()));
									props.add(new Trigger(new DateTime(new Date(start.getTime()))));
									props.add(new Created(new DateTime(new Date())));
									*/
									
									ComponentList comps = calendar.getComponents();
									VEvent ev = new VEvent(new net.fortuna.ical4j.model.Date(start.getTime()), new net.fortuna.ical4j.model.Date(end.getTime()), tmp.getDescription());
									ev.getProperties().add(new Uid(MD5.getHashString(username + Math.random()) + "@claros.org"));
									comps.add(ev);
									
									/*
									int recur = tmp.getRepeatType().intValue();
									switch (recur) {
									case Constants.REPEAT_TYPE_ONCE:
										break;
									case Constants.REPEAT_TYPE_DAY:
										props.add(new Recur(Recur.DAILY));
										break;
									case Constants.REPEAT_TYPE_MONTH:
										props.add(new Recur(Recur.MONTHLY));
										break;
									case Constants.REPEAT_TYPE_WEEK:
										props.add(new Recur(Recur.WEEKLY));
										break;
									case Constants.REPEAT_TYPE_YEAR:
										props.add(new Recur(Recur.YEARLY));
										break;
									}
									*/

									String fileName = org.claros.intouch.common.utility.Constants.tmpDir + "/" + MD5.getHashString(username + Math.random()) + ".ics";
									FileOutputStream fout = new FileOutputStream(fileName);
									CalendarOutputter outputter = new CalendarOutputter();
									outputter.output(calendar, fout);
									fout.close();
									
									
									sendMail(tmp, auth.getUsername(), email, start, end, fileName);

								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				try { Thread.sleep(300000); } catch (InterruptedException e) {}
			}
		}
	}

	private static String fromName;
	private static String fromAddr;
	private static String fromUsername;
	private static String fromPassword;
	private static String lang;
	private static ConnectionProfile connProfile; 
	static {
		try {
			fromName = PropertyFile.getConfiguration("/config/config.xml").getString("calendar-smtp.mail-from-name");
			fromAddr = PropertyFile.getConfiguration("/config/config.xml").getString("calendar-smtp.mail-from-address");
			fromUsername = PropertyFile.getConfiguration("/config/config.xml").getString("calendar-smtp.username");
			fromPassword = PropertyFile.getConfiguration("/config/config.xml").getString("calendar-smtp.password");
			lang = PropertyFile.getConfiguration("/config/config.xml").getString("calendar-smtp.default-lang");

			HashMap map = ConnectionProfileList.getConList();
			if (map != null) {
				Set set = map.keySet();
				if (set == null) {
					throw new SystemException();
				}
				Object arr[] = set.toArray();
				if (arr == null || arr.length <= 0) {
					throw new SystemException();
				}
				connProfile = ConnectionProfileList.getProfileByShortName((String)arr[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMail(CalendarObjectWrap obj, String toUsername, String to, Timestamp start, Timestamp end, String icsFileName) throws Exception {
		String from = "";
		if (fromName != null && fromName.trim().length() > 0) {
			from = fromName + "<" + fromAddr + ">";
		}
		// learn the global charset setting.

		// learn user preferences from the DB.
		AuthProfile auth = new AuthProfile();
		auth.setPassword(fromPassword);
		auth.setUsername(fromUsername);
		
		// now create a new email object.
		Email email = new Email();
		EmailHeader header = new EmailHeader();
		
		Address adrs[] = Utility.stringToAddressArray(from);
		header.setFrom(adrs);
		
		Address tos[] = Utility.stringToAddressArray(to);
		header.setTo(tos);

		header.setSubject(getText("calendar") + ":" + formatDate(obj.getOccuringDate()));
		header.setDate(new Date());

		String replyTo = "noreply@" + fromAddr.substring(fromAddr.indexOf("@") + 1);
		if (replyTo != null && replyTo.trim().length() != 0) {
			header.setReplyTo(new Address[] {new InternetAddress(replyTo)});
		}
		email.setBaseHeader(header);

		ArrayList parts = new ArrayList();
		EmailPart bodyPart = new EmailPart();
		bodyPart.setContentType("text/html; charset=UTF-8");
		
		String body = "<strong>" + obj.getDescription() + "</strong><br/><br/>" + getText("duration") + ": " + formatDate(start) + " - " + formatDate(end) + "<br/>" + getText("location") + ": " + obj.getLocation();
		bodyPart.setContent(body);
		parts.add(0, bodyPart);

		EmailPart tmp = new EmailPart();
		File f = new File(icsFileName);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
		byte data[] = new byte[(int)f.length() + 2];
		bis.read(data);
		bis.close();

		MimeBodyPart bp = new MimeBodyPart();
		DataSource ds = new ByteArrayDataSource(data, "text/vCalendar", "calendar.ics");
		bp.setDataHandler(new DataHandler(ds));
		bp.setDisposition("attachment; filename=calendar.ics");
		tmp.setDisposition(bp.getDisposition());
		bp.setFileName("calendar.ics");
		tmp.setDataSource(ds);
		tmp.setContent(bp.getContent());
		parts.add(tmp);
		tmp.setFileName("calendar.ics");
		tmp.setContentType("text/vCalendar");
		email.setParts(parts);
		
		// it is time to send the email object message

		Smtp smtp = new Smtp(connProfile, auth);
		// we are not really a lot interested if the mail is sent or not. 
		smtp.send(email, false, "");
		
		AuthProfile authTmp = new AuthProfile();
		authTmp.setUsername(toUsername);
		
		obj.setRemindedBefore("true");
		obj.setLastDismissedAt(new Timestamp(new Date().getTime()));
		
		CalendarObject objW = obj.getUnwrapped();
		
		CalendarDBController.saveEvent(authTmp, objW);
	}

	private static HashMap configs = new HashMap();
	public static String getText(String key) {
		try {
			if (lang == null) lang = "en";
			Locale loc = new Locale("en");
			try {
				loc = new Locale(lang);
			} catch (Exception e) {}
			
			String clsPath = Paths.getClsFolder();
			
			Configuration config = (Configuration)configs.get(lang);
			if (config == null) {
				config = new PropertiesConfiguration(new File(clsPath + "/org/claros/intouch/i18n/lang_" + loc + ".properties"));
				configs.put(lang, config);
			}
			return config.getString(key);
		} catch (ConfigurationException e) {
			return null;
		}
	}

	private static String formatDate(Timestamp ts) {
		Locale loc = new Locale(lang);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM yyyy HH:mm", loc);
		return sdf.format(new Date(ts.getTime()));
	}
}
