package org.claros.intouch.common.utility;

import org.claros.commons.configuration.PropertyFile;
import org.jasen.JasenScanner;

import com.jenkov.mrpersister.PersistenceManager;

/**
 * @author Umut Gokbayrak
 *
 */
public class Constants {
	public static PersistenceManager persistMan = new PersistenceManager();
	public static JasenScanner spamScanner = null;
	
	public static final Integer FOLDER_TYPE_INBOX = new Integer(1);
	public static final Integer FOLDER_TYPE_DRAFTS = new Integer(2);
	public static final Integer FOLDER_TYPE_SENT = new Integer(3);
	public static final Integer FOLDER_TYPE_TRASH = new Integer(4);
	public static final Integer FOLDER_TYPE_JUNK = new Integer(5);
	public static final Integer FOLDER_TYPE_CUSTOM = new Integer(6);
	
	public static String tmpDir;
	public static String charset;
	public static int maxAttSize;
	public static int maxMailSize;

	public static final String PORTION_SUBJECT = "portion.subject";
	public static final String PORTION_FROM = "portion.from";
	public static final String PORTION_TO = "portion.to";
	public static final String PORTION_CC = "portion.cc";
	public static final String PORTION_MESSAGE_BODY = "portion.message.body";
	
	public static final String CONDITION_CONTAINS = "condition.contains";
	public static final String CONDITION_EQUALS = "condition.equals";
	public static final String CONDITION_NOT_CONTAINS = "condition.not.contains";

	public static final String ACTION_MOVE = "action.move";
	public static final String ACTION_DELETE = "action.delete";

	// Strings
	public static final String PORTION_SUBJECT_STR = "Subject";
	public static final String PORTION_FROM_STR = "From";
	public static final String PORTION_TO_STR = "To";
	public static final String PORTION_CC_STR = "Cc";
	public static final String PORTION_MESSAGE_BODY_STR = "Message Body";
	
	public static final String CONDITION_CONTAINS_STR = "Contains";
	public static final String CONDITION_EQUALS_STR = "Equals";
	public static final String CONDITION_NOT_CONTAINS_STR = "Does Not Contain";

	public static final String ACTION_MOVE_STR = "Move To Folder";
	public static final String ACTION_DELETE_STR = "Delete";

	public static final String DISPLAY_TYPE_NAME_FIRST = "nameFirst";
	public static final String DISPLAY_TYPE_SURNAME_FIRST = "surnameFirst";

	static {
		try {
			charset = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.charset");
		} catch (Exception e) {
			charset = "utf-8";
			tmpDir = ".";
		}

		try {
			tmpDir = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.tmp-dir");
		} catch (Exception e) {
			tmpDir = ".";
		}

		try {
			maxAttSize = Integer.parseInt(PropertyFile.getConfiguration("/config/config.xml").getString("common-params.max-attachment-size"));
		} catch (Exception e) {
			maxAttSize = 5;
		}

		try {
			maxMailSize = Integer.parseInt(PropertyFile.getConfiguration("/config/config.xml").getString("common-params.max-mail-size"));
		} catch (Exception e) {
			maxMailSize = 10;
		}

	}
}
