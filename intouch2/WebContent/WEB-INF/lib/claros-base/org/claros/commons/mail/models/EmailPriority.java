package org.claros.commons.mail.models;

public class EmailPriority {
	
	public static final int HIGHEST = 1;
	public static final int HIGH = 2;
	public static final int NORMAL = 3;
	public static final int LOW = 4;
	public static final int LOWEST = 5;
	
	public static String toStringValue(int val){
		switch(val){
			case HIGHEST: case HIGH: return "High";
			case LOW: case LOWEST: return "Low";
			default: return "Normal";
		}
	}
}