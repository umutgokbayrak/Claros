package org.claros.commons.errors;

public class SystemException extends ClarosException {
	private static final long serialVersionUID = -3342022583278439648L;

	public SystemException() {
		super();
	}

	public SystemException(Exception nestedException, String errorMessage) {
		super(nestedException, errorMessage);
	}

	public SystemException(Exception nestedException) {
		super(nestedException);
	}

	public SystemException(String errorMessage) {
		super(errorMessage);
	}
}
