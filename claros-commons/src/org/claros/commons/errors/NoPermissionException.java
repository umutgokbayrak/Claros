package org.claros.commons.errors;

/**
 * @author Umut Gokbayrak
 */
public class NoPermissionException extends ClarosException {
	private static final long serialVersionUID = 8199553363488615089L;

	public NoPermissionException() {
		super();
	}

	public NoPermissionException(Exception nestedException, String errorMessage) {
		super(nestedException, errorMessage);
	}

	public NoPermissionException(Exception nestedException) {
		super(nestedException);
	}

	public NoPermissionException(String errorMessage) {
		super(errorMessage);
	}

	
}
