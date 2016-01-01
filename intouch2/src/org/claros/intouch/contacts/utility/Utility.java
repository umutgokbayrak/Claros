package org.claros.intouch.contacts.utility;

import java.util.Collections;
import java.util.List;

import org.claros.intouch.contacts.models.Contact;

/**
 * @author Umut Gokbayrak
 */
public class Utility {

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public static List sortContacts(List contacts, boolean nameFirst) {
		if (contacts != null) {
			Collections.sort(contacts, new ContactsComparator(nameFirst));
		}
		return contacts;
	}

	public static String getFullName(Contact contact, boolean nameFirst) {
		String firstName = contact.getFirstName();
		String middleName = contact.getMiddleName();
		String lastName = contact.getLastName();
		
		String fullName = "";	
		if (lastName == null || lastName.trim().equals("")) {
			fullName = firstName;
		} else {
			if (nameFirst) {
				fullName = firstName + " " + lastName;
				if (middleName != null && !middleName.trim().equals("")) {
					fullName = firstName + " " + middleName + " " + lastName;
				}
			} else {
				fullName = lastName + ", " + firstName;
				if (middleName != null && !middleName.trim().equals("")) {
					fullName = lastName + ", " + firstName + " " + middleName;
				}
			}
		}
		return fullName.trim();
	}

}
