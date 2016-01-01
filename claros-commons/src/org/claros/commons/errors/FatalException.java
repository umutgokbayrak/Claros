package org.claros.commons.errors;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FatalException extends ClarosException {
	private static final long serialVersionUID = -1410578487969099271L;
	private static Log log = LogFactory.getLog(FatalException.class);

	public FatalException() {
	}

	public FatalException(Exception nestedException, String errorMessage) {
		setNestedException(nestedException);

		if (errorMessage == null) {
			ByteArrayOutputStream bOut = new ByteArrayOutputStream();
			PrintStream out = new PrintStream(bOut);
			getNestedException().printStackTrace(out);
			setMessage(bOut.toString());
			try {
				bOut.close();
			} catch (IOException e) {}
		} else {
			setMessage(errorMessage);
		}
		log.fatal("An exception is created. Message: " + getMessage());
	}

	public FatalException(Exception nestedException) {
		setNestedException(nestedException);
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bOut);
		getNestedException().printStackTrace(out);
		setMessage(bOut.toString());
		try {
			bOut.close();
		} catch (IOException e) {}
		log.fatal("An exception is created. Message: " + getMessage());
	}

	public FatalException(String errorMessage) {
		setMessage(errorMessage);
		log.fatal("An exception is created. Message: " + errorMessage);
	}

	
}
