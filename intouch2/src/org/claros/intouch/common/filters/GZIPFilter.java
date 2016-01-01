/*
 * Copyright 2003 Jayson Falkner (jayson@jspinsider.com)
 * This code is from "Servlets and JavaServer pages; the J2EE Web Tier",
 * http://www.jspbook.com. You may freely use the code both commercially
 * and non-commercially. If you like the code, please pick up a copy of
 * the book and help support the authors, development of more free code,
 * and the JSP/Servlet/J2EE community.
 */
package org.claros.intouch.common.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GZIPFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String ae = request.getHeader("accept-encoding");
			if (ae != null && ae.indexOf("gzip") != -1) {
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(response);
				try {
					chain.doFilter(req, wrappedResponse);
				} catch (ServletException e) {
					throw e;
				} catch (Exception e) {
					throw new ServletException(e);
				} finally {
					wrappedResponse.finishResponse();
				}
				return;
			} else {
				chain.doFilter(req, res);
			}
		}
	}

	public void init(FilterConfig filterConfig) {
		// noop
	}

	public void destroy() {
		// noop
	}
}
