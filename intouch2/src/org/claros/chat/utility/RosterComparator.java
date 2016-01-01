package org.claros.chat.utility;

import java.util.Comparator;
import java.util.HashMap;

import org.claros.chat.models.Contact;

/**
 * @author Umut Gokbayrak
 */
public class RosterComparator implements Comparator {
	private static HashMap map = new HashMap();
	
	static {
		map.put("chat", new Integer(1));
		map.put("available", new Integer(2));
		map.put("away", new Integer(3));
		map.put("extended_away", new Integer(3));
		map.put("disturb", new Integer(4));
		map.put("invisible", new Integer(5));
		map.put("offline", new Integer(6));
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object arg0, Object arg1) {
		Contact c1 = (Contact)arg0;
		Contact c2 = (Contact)arg1;
		Integer p1 = (Integer)map.get(c1.getPresence());
		Integer p2 = (Integer)map.get(c2.getPresence());
		if (p1 == null) {
			p1 = new Integer(10);
		}
		if (p2 == null) {
			p2 = new Integer(10);
		}
		if (p1.equals(p2)) {
			return c1.getName().compareTo(c2.getName());
		}
		return p1.compareTo(p2);
	}
}
