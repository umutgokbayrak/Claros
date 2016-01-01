package org.claros.commons.comparators;

import java.util.Comparator;
import java.util.Date;

/**
 * @author Umut Gokbayrak
 */
public class DateComparator implements Comparator<Date> {
	private int left = 1;
	private int right = -1;
	
	public DateComparator(boolean ascending) {
		if (ascending) {
			left = -1;
			right = 1;
		}
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Date arg0, Date arg1) {
		if (arg0 != null && arg1 != null && arg0 instanceof Date && arg1 instanceof Date) {
			Date dt1 = (Date)arg0;
			Date dt2 = (Date)arg1;
			
			if (dt1.after(dt2)) {
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
