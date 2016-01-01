package org.claros.chat.ajax;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.chat.controllers.AvatarController;
import org.claros.chat.models.Avatar;
import org.claros.commons.configuration.Paths;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.packet.VCard;

public class ShowAvatar extends HttpServlet {
	private static final long serialVersionUID = 5777405777244865645L;
	private static Log log = LogFactory.getLog(ShowAvatar.class);
	
	/**
	 * Constructor of the object.
	 */
	public ShowAvatar() {
		super();
	}
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setContentType("image/png");
		OutputStream s = response.getOutputStream();

		try {
			XMPPConnection conn = (XMPPConnection)request.getSession().getAttribute("conn");
			String user = request.getParameter("u");

			try {
				if (conn != null && conn.isConnected()) {
					VCard vCard = new VCard();
					// picture is me myself
					if (user == null) {
						vCard.load(conn, user);
						byte[] data = vCard.getAvatar();
						if (data != null && data.length > 0) {
							s.write(data);
							s.close();
							return;
						}
					} else {
						// somebody other's picture
						vCard.load(conn, user);
						byte[] data = vCard.getAvatar();
						String hash = vCard.getAvatarHash();
						
						AvatarController.addAvatar(user, data, hash);
					}
				}
			} catch (Exception e) {
				log.debug("unable to fetch vcard for user: " + user, e);
			}
			
			Avatar avatar = (Avatar)AvatarController.getAvatar(user);
			if (avatar != null) {
				byte[] b = avatar.getData();
				if (b != null && b.length > 0) {
					s.write(b);
				} else {
					showDefaultAvatar(s);
				}
			} else {
				showDefaultAvatar(s);
			}
		} catch (RuntimeException e) {
			// do nothing sier
		}
		s.flush();
		s.close();
	}
	
	/**
	 * 
	 * @param s
	 */
	private void showDefaultAvatar(OutputStream s) {
		try {
			BufferedInputStream is = new BufferedInputStream(new FileInputStream(Paths.getResFolder() + "/avatar.png"));
			int byte_;
			while ((byte_ = is.read ()) != -1) {
				s.write (byte_);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
