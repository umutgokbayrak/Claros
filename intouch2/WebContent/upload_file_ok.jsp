<%@ page contentType="text/html; charset=UTF-8" %>
<%
	response.setHeader("Expires","-1");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-control","no-cache");
%>
<%@page import ="java.util.Locale" %>
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
<%@ include file="js_messages.jsp" %>
<script>
	var result = '<%= request.getParameter("result") %>';
	var maxAttSize = '<%= request.getParameter("maxAttSize") %>';
	
	switch (result) {
		case '0':
			parent.document.getElementById('uploadFileImg').src = 'images/export-contacts.gif';
			parent.fileListInit = false;
			parent.showFileTree();
			break;
		case '1':
			// attachment exceeded the max att size limit
			alert(size_exceeds_att_limit + maxAttSize);
			parent.document.getElementById('uploadFileImg').src = 'images/export-contacts.gif';
			parent.fileListInit = false;
			parent.showFileTree();
			break;
		case '3':
			// attachment exceeded the max att size limit
			alert(system_error);
			parent.document.getElementById('uploadFileImg').src = 'images/export-contacts.gif';
			parent.fileListInit = false;
			parent.showFileTree();
			break;
	}
</script>
OK
