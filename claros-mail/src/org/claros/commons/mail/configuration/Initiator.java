package org.claros.commons.mail.configuration;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.configuration.Paths;
import org.xml.sax.SAXException;

public class Initiator extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2277013867291644084L;
	private static Log log = LogFactory.getLog(Initiator.class);

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException if an error occure
     */
    public void init() throws ServletException {
		try {
			Digester digester = new Digester();
			digester.setValidating(false);
			digester.addObjectCreate("claros-config/servers", "org.claros.commons.mail.models.ConnectionProfileList");
			digester.addObjectCreate("claros-config/servers/server", "org.claros.commons.mail.models.ConnectionProfile");
			digester.addCallMethod("claros-config/servers/server/shortname", "setShortName", 0);
			digester.addCallMethod("claros-config/servers/server/fetch-server", "setFetchServer", 0);
			digester.addCallMethod("claros-config/servers/server/fetch-server-port", "setFetchPort", 0);
			digester.addCallMethod("claros-config/servers/server/fetch-protocol", "setProtocol", 0);
			digester.addCallMethod("claros-config/servers/server/fetch-ssl", "setFetchSSL", 0);

			digester.addCallMethod("claros-config/servers/server/smtp-server", "setSmtpServer", 0);
			digester.addCallMethod("claros-config/servers/server/smtp-server-port", "setSmtpPort", 0);
			digester.addCallMethod("claros-config/servers/server/smtp-authenticated", "setSmtpAuthenticated", 0);
			digester.addCallMethod("claros-config/servers/server/smtp-ssl", "setSmtpSSL", 0);
			digester.addCallMethod("claros-config/servers/server/folder-namespace", "setFolderNameSpace", 0);
			digester.addSetNext("claros-config/servers/server", "addConnectionProfile", "org.claros.commons.mail.models.ConnectionProfile");
			digester.parse(new File(Paths.getCfgFolder() + "/config.xml"));
		} catch (IOException e) {
			log.fatal("Could not find config.xml file in your config path.(" + Paths.getCfgFolder() + ")", e);
		} catch (SAXException e) {
			log.fatal("Could not validate config.xml file or could not read its contents", e);
		}
    }
}
