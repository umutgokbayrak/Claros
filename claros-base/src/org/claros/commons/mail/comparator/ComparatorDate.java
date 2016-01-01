package org.claros.commons.mail.comparator;

import java.util.Comparator;
import java.util.Date;

import org.claros.commons.mail.models.EmailHeader;

/**
 * @author Umut Gokbayrak
 */
public class ComparatorDate implements Comparator<EmailHeader> {
	private int left = 1;
	private int right = -1;
	
	public ComparatorDate(boolean ascending) {
		if (ascending) {
			left = -1;
			right = 1;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(EmailHeader h1, EmailHeader h2) {
		if (h1 != null && h2 != null) {
			Date dt1 = h1.getDate();
			Date dt2 = h2.getDate();

			if (dt1 == null) {
				return right;
			} else if (dt2 == null) {
				return left;
			} else if (dt1.after(dt2)) {
				return right;
			} else if (dt1.before(dt2)) {
				return left;
			} else {
				return 0;
			}
		}
		return right;
	}
}
