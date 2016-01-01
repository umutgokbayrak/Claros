package org.claros.commons.mail.exception;

import org.claros.commons.exception.ClarosBaseException;

/**
 * @author Umut Gokbayrak
 *
 */
public class MailboxActionException extends ClarosBaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public MailboxActionException() {
        super();

    }

    /**
     * @param errorKey
     */
    public MailboxActionException(String errorKey) {
        super(errorKey);

    }

    /**
     * @param nestedException
     * @param errorKey
     */
    public MailboxActionException(Exception nestedException, String errorKey) {
        super(nestedException, errorKey);

    }

    /**
     * @param encapsulatedException
     */
    public MailboxActionException(Exception encapsulatedException) {
        super(encapsulatedException);

    }

}
