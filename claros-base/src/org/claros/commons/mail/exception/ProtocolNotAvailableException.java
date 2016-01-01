package org.claros.commons.mail.exception;

/**
 * @author Umut Gokbayrak
 */
public class ProtocolNotAvailableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3603006603986265671L;

	/**
	 * 
	 */
	public ProtocolNotAvailableException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ProtocolNotAvailableException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ProtocolNotAvailableException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ProtocolNotAvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
