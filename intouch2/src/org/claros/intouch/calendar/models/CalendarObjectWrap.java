package org.claros.intouch.calendar.models;

import java.sql.Timestamp;

/**
 * @author Umut Gokbayrak
 */
public class CalendarObjectWrap extends CalendarObject implements Comparable, Cloneable {
	private Timestamp occuringDate;

	/**
	 * 
	 */
	public CalendarObjectWrap(CalendarObject item) {
		if (item != null) {
			this.setDescription(item.getDescription());
			this.setId(item.getId());
			this.setRecordDate(item.getRecordDate());
			this.setEndDate(item.getEndDate());
			this.setReminderDays(item.getReminderDays());
			this.setRepeatType(item.getRepeatType());
			this.setUsername(item.getUsername());
			this.setColor(item.getColor());
			this.setLocation(item.getLocation());
			this.setReminderMethod(item.getReminderMethod());
			this.setRemindedBefore(item.getRemindedBefore());
			this.setLastDismissedAt(item.getLastDismissedAt());
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return -1;
		} else {
			CalendarObjectWrap app = (CalendarObjectWrap)o;
			Timestamp occur = app.getOccuringDate();
			if (occur.after(occuringDate)) {
				return 1;
			} else if (occur.equals(occuringDate)) {
				return 0;
			} else if (occur.before(occuringDate)) {
				return -1;
			}
		}
		return -1;
	}
	/**
	 * @return
	 */
	public Timestamp getOccuringDate() {
		return occuringDate;
	}

	/**
	 * @param timestamp
	 */
	public void setOccuringDate(Timestamp timestamp) {
		occuringDate = timestamp;
	}

	public CalendarObject getUnwrapped() {
		return (CalendarObject)this;
	}
}
