package org.claros.intouch.calendar.models;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Umut Gokbayrak
 */
public class CalendarDailyItems {
	private Timestamp date;
	private ArrayList hours;
	private ArrayList appointments;

	public CalendarDailyItems() {
		hours = new ArrayList();
		appointments = new ArrayList();
	}

	public void addHour(CalendarHourItems hour) {
		hours.add(hour);
	}

	public void addAppointment(CalendarObjectWrap app) {
		appointments.add(app);
	}
	
	/**
	 * @return
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp) {
		date = timestamp;
	}
	/**
	 * @return
	 */
	public ArrayList getHours() {
		return hours;
	}

	/**
	 * @param list
	 */
	public void setHours(ArrayList list) {
		hours = list;
	}

	/**
	 * @return
	 */
	public ArrayList getAppointments() {
		return appointments;
	}

	/**
	 * @param list
	 */
	public void setAppointments(ArrayList list) {
		appointments = list;
	}

}
