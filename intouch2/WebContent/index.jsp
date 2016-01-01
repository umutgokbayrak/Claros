<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="org.claros.commons.configuration.PropertyFile" %>
<%@ page import="org.claros.commons.mail.models.ConnectionProfile" %>
<%@ page import="org.claros.commons.mail.utility.Constants" %>
<%@ page import="org.claros.commons.mail.models.ConnectionMetaHandler" %>
<%@ page import="org.claros.commons.auth.models.AuthProfile" %>
<%@ page import="org.claros.commons.mail.protocols.ProtocolFactory" %>
<%@ page import="org.claros.commons.mail.protocols.Protocol" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% 
	response.setHeader("Expires","-1");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-control","no-cache");

	String lang = request.getParameter("lang");
	if (lang == null) {
		lang = (String)session.getAttribute("lang");
	}
	if (lang == null) {
		Cookie cookies[] = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie.getName().equals("lang")) {
					lang = cookie.getValue();
				}
			}
		}
	}

	String defaultLang = org.claros.commons.configuration.PropertyFile.getConfiguration("/config/config.xml").getString("common-params.default-lang"); 
	if (lang == null) lang = defaultLang;
	Locale loc = new Locale(defaultLang);
	try {
		loc = new Locale(lang);
	} catch (Exception e) {}

	session.setAttribute("lang", lang);
	Cookie c1 = new Cookie("lang", lang);
	c1.setMaxAge(Integer.MAX_VALUE);
	response.addCookie(c1);

	// if connected disconnect first
	try {
		ConnectionProfile profile = (ConnectionProfile)session.getAttribute("profile");
		if (profile != null && profile.getProtocol().equals(Constants.IMAP)) {
			ConnectionMetaHandler handler = (ConnectionMetaHandler)session.getAttribute("handler");
			AuthProfile auth = (AuthProfile)session.getAttribute("auth");
			ProtocolFactory factory = new ProtocolFactory(profile, auth, handler);
			Protocol protocol = factory.getProtocol(null);
			protocol.disconnect();
		}
	} catch (Exception e) {}
	
	// clear all session variables
	/*
	Enumeration en = session.getAttributeNames();
	String el = null;
	while (en.hasMoreElements()) {
		el = (String)en.nextElement();
		if (!el.equals("lang")) {
			session.setAttribute("" + el, null);
		}
	}
	*/
	String title = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.title");
%>
<script>
	var title = '<%= title %>';
	document.title = title;
</script>
<i18n:bundle baseName="org.claros.intouch.i18n.lang" locale="<%= loc %>"/>
<%@include file="login_progress.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Claros inTouch</title>
	<link href="css/all.css" rel="stylesheet" type="text/css" />
	<%@ include file="js_messages.jsp" %>
	<script type="text/javascript" src="yui/yahoo/yahoo-min.js"></script>
	<script type="text/javascript" src="yui/dom/dom-min.js"></script>
	<script type="text/javascript" src="yui/event/event-min.js"></script>
	<script type="text/javascript" src="yui/connection/connection-min.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/rico.js"></script>
	<script type="text/javascript" src="js/mm_functions.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
	<script type="text/javascript" src="js/layout.js"></script>
	<script type="text/javascript" src="js/operations.js"></script>
</head>
<body oncontextmenu="return false" onselectstart="return selectstart(event)" onload="initLogin(event)">
	<div id="login" align="center" style="visibility:hidden">
   	  <form id="loginForm" method="post">
		<div id="mylogin">
		  <div id="loginResult"><i18n:message key="please.login"/></div>
		  <div id="loginTable">
		  	<table border="0" cellspacing="1" cellpadding="5" width="400">
		  	  <tr>
		  		<td width="130" align="right"><i18n:message key="username"/> : </td>
		  		<td width="270" align="left"><input name="username" type="text" id="username" tabindex="1" onkeydown="return(loginCatchEnter(event))" style="width:175px"/></td>
		  	  </tr>
		  	  <tr>
		  	  	<td align="right"><i18n:message key="password"/> : </td>
		  	  	<td align="left"><input name="password" type="password" id="password" tabindex="2" onkeydown="return(loginCatchEnter(event))" style="width:175px"/></td>
		  	  </tr>
		  	  <tr>
		  	  	<td>&nbsp;</td>
		  	  	<td align="left">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="login();"><i18n:message key="login"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
		  	  	</td>
		  	  </tr>
		    </table>
		  </div>
		</div>
		<br/>
		[ <!-- <a href="index.jsp?lang=bg">Bǎlgarski</a> |  -->
		  <a href="index.jsp?lang=da">Dansk</a> | 
		  <a href="index.jsp?lang=de">Deutsch</a> | 
		  <a href="index.jsp?lang=en">English</a> | 
		  <a href="index.jsp?lang=es">Español</a> | 
		  <a href="index.jsp?lang=fr">Français</a> | 
		  <a href="index.jsp?lang=it">Italiano</a> | 
		  <a href="index.jsp?lang=pt_br">Português Brasil</a> | <br/>
		  <a href="index.jsp?lang=pl">Polski</a> |
		  <a href="index.jsp?lang=sk">Slovensky</a> | 
		  <a href="index.jsp?lang=tr">Türkçe</a> | 
		  <a href="index.jsp?lang=vi">Việt nam</a> | 
		  <a href="index.jsp?lang=zh_tw_utf8"><img src="images/trad_chinese.gif" border="0" align="absbottom"/></a> | 
		  <a href="index.jsp?lang=zh_cn_utf8"><img src="images/chinese.gif" border="0" align="absbottom"/></a>
		]
	  </form>
	  <iframe id="jsFrame" width="1" height="1" src="#" style="visibility:hidden"></iframe>
	</div>
</body>
</html>

