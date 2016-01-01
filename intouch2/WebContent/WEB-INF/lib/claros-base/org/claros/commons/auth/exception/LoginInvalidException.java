package org.claros.commons.auth.exception;

import org.claros.commons.exception.ClarosBaseException;

/**
 * @author Umut Gokbayrak
 */
public class LoginInvalidException extends ClarosBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public LoginInvalidException() {
		super();

	}

	/**
	 * @param errorKey
	 */
	public LoginInvalidException(String errorKey) {
		super(errorKey);

	}

	/**
	 * @param nestedException
	 * @param errorKey
	 */
	public LoginInvalidException(Exception nestedException, String errorKey) {
		super(nestedException, errorKey);

	}

	/**
	 * @param encapsulatedException
	 */
	public LoginInvalidException(Exception nestedException) {
		super(nestedException);
	}
}
