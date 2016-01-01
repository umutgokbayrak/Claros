package org.claros.commons.mail.exception;

public class MailSizeExceededException extends Exception {
	private static final long serialVersionUID = -7819262834857638035L;

	public MailSizeExceededException() {
		super();
	}

	public MailSizeExceededException(String arg0) {
		super(arg0);
	}

	public MailSizeExceededException(Throwable arg0) {
		super(arg0);
	}

	public MailSizeExceededException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
