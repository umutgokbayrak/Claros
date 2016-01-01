package org.claros.chat.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.claros.chat.threads.ClearThread;


public class ClearServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6167028875377096374L;

	/**
	 * Constructor of the object.
	 */
	public ClearServlet() {
		super();
	}

	public void init() throws ServletException {
		new ClearThread().start();
	}
}
