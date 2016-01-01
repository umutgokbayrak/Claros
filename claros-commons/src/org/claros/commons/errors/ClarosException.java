package org.claros.commons.errors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Umut Gokbayrak
 */
public class ClarosException extends Exception {
	private static final long serialVersionUID = -2268010989574881830L;
	private static Log log = LogFactory.getLog(ClarosException.class);
	private String message;
	private Exception nestedException;

	public ClarosException() {
		super();
	}

	public ClarosException(String errorMessage) {
		this.message = errorMessage;
		log.error("An exception is created. Message: " + errorMessage);
	}

	public ClarosException(Exception nestedException, String errorMessage) {
		this.nestedException = nestedException;

		if (errorMessage == null) {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			PrintStream out = new PrintStream(bOut);
			this.nestedException.printStackTrace(out);
			this.message = bOut.toString();
			try {
				bOut.close();
			} catch (IOException e) {}
		} else {
			this.message = errorMessage;
		}
		log.error("An exception is created. Message: " + this.message);
	}

	public ClarosException(Exception nestedException) {
		this.nestedException = nestedException;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bOut);
		this.nestedException.printStackTrace(out);
		this.message = bOut.toString();
		try {
			bOut.close();
		} catch (IOException e) {}
		log.error("An exception is created. Message: " + this.message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getNestedException() {
		return nestedException;
	}

	public void setNestedException(Exception nestedException) {
		this.nestedException = nestedException;
	}
}
