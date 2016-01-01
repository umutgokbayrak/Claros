package org.claros.intouch.calendar.models;

import java.util.ArrayList;

/**
 * 
 * @author Umut Gokbayrak
 */
public class CalendarWeeklyItems {
	private int weekInYear;
	private ArrayList days;
	
	public CalendarWeeklyItems() {
		days = new ArrayList();
	}

	public void addDay(CalendarDailyItems day) {
		days.add(day);
	}
	/**
	 * @return
	 */
	public ArrayList getDays() {
		return days;
	}

	/**
	 * @param list
	 */
	public void setDays(ArrayList list) {
		days = list;
	}

	/**
	 * @return
	 */
	public int getWeekInYear() {
		return weekInYear;
	}

	/**
	 * @param i
	 */
	public void setWeekInYear(int i) {
		weekInYear = i;
	}

}
