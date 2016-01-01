package org.claros.intouch.contacts.utility;

import java.util.Comparator;

import org.claros.intouch.contacts.models.Contact;

/**
 * @author Umut Gokbayrak
 */
public class ContactsComparator implements Comparator {
	private boolean nameFirst = true;
	
	public ContactsComparator(boolean nameFirst) {
		this.nameFirst = nameFirst;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		Contact c1 = (Contact)arg0;
		Contact c2 = (Contact)arg1;

		String f1 = Utility.getFullName(c1, nameFirst);
		String f2 = Utility.getFullName(c2, nameFirst);
		return f1.compareTo(f2);
	}
}
