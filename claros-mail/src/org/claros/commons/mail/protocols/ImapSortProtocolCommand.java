package org.claros.commons.mail.protocols;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.mail.models.ConnectionProfile;

import com.sun.mail.iap.Argument;
import com.sun.mail.iap.ProtocolException;
import com.sun.mail.iap.Response;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.protocol.IMAPProtocol;
import com.sun.mail.imap.protocol.IMAPResponse;

public class ImapSortProtocolCommand implements IMAPFolder.ProtocolCommand {
	private ArrayList<Integer> sortedList = new ArrayList<Integer>();

	public static final String SORT_DATE = "DATE";
	public static final String SORT_SUBJECT = "SUBJECT";
	public static final String SORT_SIZE = "SIZE";
	public static final String SORT_FROM = "FROM";
	public static final String SORT_TO = "TO";
	private static final String REVERSE_STR = "REVERSE";
	
	private String sortCriteria = SORT_DATE;
	private boolean ascending = false;
	private ConnectionProfile profile;

	private static Log log = LogFactory.getLog(ImapSortProtocolCommand.class);
	
	public ImapSortProtocolCommand() {
		super();
	}
	
	public ImapSortProtocolCommand(String sortCriteria, boolean ascending, ConnectionProfile profile) {
		this.sortCriteria = sortCriteria;
		this.ascending = ascending;
		this.profile = profile;
	}
	
	public Object doCommand(IMAPProtocol p) throws ProtocolException {
		sortedList = new ArrayList<Integer>();
        Argument args = new Argument();
        Argument list = new Argument();
		if (!ascending) {
			list.writeString(REVERSE_STR);
		}
        list.writeString(sortCriteria);
        args.writeArgument(list);
        args.writeString("UTF-8");
        args.writeString("ALL");
        Response[] r = p.command("SORT", args);
        Response response = r[r.length-1];

        // Grab response
        if (response.isOK()) { // command succesful
        	// the server supports imap sort.
        	log.info("IMAP Server supports server side sorting. This is good news!!! So performance will be much better!!!");
        	profile.setSupportSort(true);

        	for (int i = 0, len = r.length; i < len; i++) {
                if (!(r[i] instanceof IMAPResponse)) {
                    continue;
                }

                IMAPResponse ir = (IMAPResponse)r[i];
                if (ir.keyEquals("SORT")) {
                    String num;
                    while ((num = ir.readAtomString()) != null) {
                    	sortedList.add(new Integer(num));
                    	log.debug("The message with number: " + num + " added to the sorted fetch list");
                    }
                    r[i] = null;
                }
            }
        } else {
        	log.info("IMAP Server doesn't support server side sorting. This is bad news :( So performance will be not as good as it could be!!!");
        	profile.setSupportSort(false);
        	throw new ProtocolException();
        }
        // dispatch remaining untagged responses
        p.notifyResponseHandlers(r);
        p.handleResult(response);
        return null;
	}

	public ArrayList<Integer> getSortedList() {
		return sortedList;
	}

	public ConnectionProfile getProfile() {
		return profile;
	}

}
