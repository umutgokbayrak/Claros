package org.claros.commons.mail.comparator;

import java.util.Comparator;

import org.claros.commons.mail.models.EmailHeader;

public class ComparatorSize implements Comparator<EmailHeader> {
	private int left = 1;
	private int right = -1;
	
	public ComparatorSize(boolean ascending) {
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
			long dt1 = h1.getSize();
			long dt2 = h2.getSize();

			if (dt1 == -1) {
				return right;
			} else if (dt2 == -1) {
				return left;
			} else if (dt1 > dt2) {
				return right;
			} else if (dt1 <dt2) {
				return left;
			} else {
				return 0;
			}
		}
		return right;
	}
}
