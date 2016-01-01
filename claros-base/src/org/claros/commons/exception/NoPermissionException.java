package org.claros.commons.exception;

/**
 * @author Umut Gokbayrak
 */
public class NoPermissionException extends ClarosBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoPermissionException() {
		super();
	}

	/**
	 * @param errorKey
	 */
	public NoPermissionException(String errorKey) {
		super(errorKey);
	}

	/**
	 * @param nestedException
	 * @param errorKey
	 */
	public NoPermissionException(Exception nestedException, String errorKey) {
		super(nestedException, errorKey);
	}

	/**
	 * @param nestedException
	 */
	public NoPermissionException(Exception nestedException) {
		super(nestedException);
	}

}
