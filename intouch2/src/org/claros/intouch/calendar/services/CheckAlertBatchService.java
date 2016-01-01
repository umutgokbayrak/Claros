package org.claros.intouch.calendar.services;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.claros.intouch.calendar.controllers.CheckAlertBatchThread;


public class CheckAlertBatchService extends HttpServlet {
	private static final long serialVersionUID = 6918085472374290104L;
	
	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		new CheckAlertBatchThread().start();
	}
}

