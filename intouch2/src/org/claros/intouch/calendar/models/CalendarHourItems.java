package org.claros.intouch.calendar.models;

import java.util.ArrayList;

/**
 * @author Umut Gokbayrak
 */
public class CalendarHourItems {
	private Integer hour;
	private ArrayList appointments;

	public CalendarHourItems() {
		appointments = new ArrayList();
	}

	public void addAppointment(CalendarObject app) {
		appointments.add(app);
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

	/**
	 * @return
	 */
	public Integer getHour() {
		return hour;
	}

	/**
	 * @param integer
	 */
	public void setHour(Integer integer) {
		hour = integer;
	}

}
