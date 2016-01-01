package org.claros.commons.mail.exception;

import org.claros.commons.exception.ClarosBaseException;

/**
 * @author Umut Gokbayrak
 *
 */
public class ConnectionException extends ClarosBaseException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */
    public ConnectionException() {
        super();

    }
    /**
     * @param nestedException
     */
    public ConnectionException(Exception nestedException) {
        super(nestedException);
    }
    /**
     * @param encapsulatedException
     * @param errorKey
     */
    public ConnectionException(Exception nestedException, String errorKey) {
        super(nestedException, errorKey);
    }
    /**
     * @param errorKey
     */
    public ConnectionException(String errorKey) {
        super(errorKey);
    }
}
