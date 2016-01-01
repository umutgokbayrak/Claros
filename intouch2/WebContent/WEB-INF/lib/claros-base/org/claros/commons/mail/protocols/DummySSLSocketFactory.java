package org.claros.commons.mail.protocols;

import com.sun.net.ssl.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class DummySSLSocketFactory extends SSLSocketFactory {
	private SSLSocketFactory factory;

	@SuppressWarnings("deprecation")
	public DummySSLSocketFactory() {
		System.out.println("DummySocketFactory instantiated");
		try {
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(
					null, // No KeyManager required
					new TrustManager[] { new DummyTrustManager() },
					new java.security.SecureRandom());
			factory = (SSLSocketFactory) sslcontext.getSocketFactory();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static SocketFactory getDefault() {
		return new DummySSLSocketFactory();
	}

	public Socket createSocket(Socket socket, String s, int i, boolean flag)
			throws IOException {
		return factory.createSocket(socket, s, i, flag);
	}

	public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1,
			int j) throws IOException {
		return factory.createSocket(inaddr, i, inaddr1, j);
	}

	public Socket createSocket(InetAddress inaddr, int i) throws IOException {
		return factory.createSocket(inaddr, i);
	}

	public Socket createSocket(String s, int i, InetAddress inaddr, int j)
			throws IOException {
		return factory.createSocket(s, i, inaddr, j);
	}

	public Socket createSocket(String s, int i) throws IOException {
		return factory.createSocket(s, i);
	}

	public String[] getDefaultCipherSuites() {
		return factory.getSupportedCipherSuites();
	}

	public String[] getSupportedCipherSuites() {
		return factory.getSupportedCipherSuites();
	}

	public Socket createSocket() throws IOException {
		return factory.createSocket();
	}

}
