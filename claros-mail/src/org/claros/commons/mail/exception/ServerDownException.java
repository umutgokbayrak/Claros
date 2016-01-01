package org.claros.commons.mail.exception;

/**
 * @author Umut Gokbayrak
 */
public class ServerDownException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9053779960615352062L;

	/**
	 * 
	 */
	public ServerDownException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ServerDownException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ServerDownException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ServerDownException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
