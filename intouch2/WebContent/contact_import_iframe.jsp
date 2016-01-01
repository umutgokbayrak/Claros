<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
	response.setHeader("Expires","-1");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-control","no-cache");
%>
<%@page import ="java.util.Locale" %>
<%@page import ="org.claros.commons.utility.Utility" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String lang = request.getParameter("lang");

	String defaultLang = org.claros.commons.configuration.PropertyFile.getConfiguration("/config/config.xml").getString("common-params.default-lang"); 
	if (lang == null) lang = defaultLang;
	Locale loc = new Locale(defaultLang);
	try {
		loc = new Locale(lang);
	} catch (Exception e) {}
%>
<i18n:bundle baseName="org.claros.intouch.i18n.lang" locale="<%= loc %>"/>
<head>
<style>
body,a, input {
	font:normal 11px arial,sans-serif;
	color:#000;
	text-decoration:none;
	outline:none;
}
</style>
</head>
<body>
	<div align="center" class="roundme" style="background-color: #f4f4f4;height: 65px;">
		<div style="height: 55px;">
			<br/><br/>
			<form id="iform0" name="iform0" action="contacts/importContacts.cl" method="post" enctype="multipart/form-data">
				<strong><i18n:message key="file.name"/>:</strong> <input type="file" hidefocus="true" name="inputfile" id="inputfile"/>
			</form>
		</div>
	</div>
</body>
