package org.claros.commons.comparators;

import java.util.Comparator;

/**
 * @author Umut Gokbayrak
 */
public class StringComparator implements Comparator<String> {
	private int left = 1;
	private int right = -1;

	public StringComparator(boolean ascending) {
		if (ascending) {
			left = -1;
			right = 1;
		}		
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(String arg0, String arg1) {
		if (arg0 != null && arg1 != null && arg0 instanceof String && arg1 instanceof String) {
			String s1 = (String)arg0;
			String s2 = (String)arg1;

			if (s1.compareTo(s2) > 1) {
				return right;
			} else if (s1.compareTo(s2) < 1) {
				return left;
			} else {
				return 0;
			}
		}
		return right;
	}
}
