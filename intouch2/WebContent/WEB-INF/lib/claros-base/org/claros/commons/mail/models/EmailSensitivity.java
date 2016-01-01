package org.claros.commons.mail.models;

public class EmailSensitivity {
	
	public static final short NORMAL = 1;
	public static final short PERSONAL = 2;
	public static final short PRIVATE = 3;
	public static final short CONFIDENTIAL = 4;
	
	public static String toStringValue(int val){
		switch(val){
			case PERSONAL: return "Personal";
			case PRIVATE: return "Private";
			case CONFIDENTIAL: return "Company-Confidential";
			default: return "Normal";
		}
	}
	
	public static short valueOf(String val){
		String value = val.toLowerCase(); 
		if(value.equals("personal")) return PERSONAL;
		else if(value.equals("private")) return PRIVATE;
		else if(value.indexOf("confidential") > 0) return CONFIDENTIAL;
		else return NORMAL;
	}
}
