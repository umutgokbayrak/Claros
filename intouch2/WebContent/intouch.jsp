<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.claros.commons.configuration.PropertyFile" %>
<%@ page import="org.claros.commons.mail.models.ConnectionProfile"%>
<%@ taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<% 
response.setHeader("Expires","-1");
response.setHeader("Pragma","no-cache");
response.setHeader("Cache-control","no-cache");

Object obj = session.getAttribute("auth");
if (obj == null) { 
	response.sendRedirect("index.jsp"); 
} 
 
String lang = (String)session.getAttribute("lang");

String defaultLang = org.claros.commons.configuration.PropertyFile.getConfiguration("/config/config.xml").getString("common-params.default-lang"); 
if (lang == null) lang = defaultLang;
Locale loc = new Locale(defaultLang);
try {
	loc = new Locale(lang);
} catch (Exception e) {}

session.setAttribute("lang", lang);

String checkPeriod = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.mail-check-interval");
int period = 20 * 1000;
try {
	period = Integer.parseInt(checkPeriod) * 1000;
} catch (NumberFormatException e) {
	period = 20 * 1000;
}
ConnectionProfile profile = (ConnectionProfile)session.getAttribute("profile");
String protocol = "imap";
if (profile != null) {
	protocol = profile.getProtocol().toLowerCase();
}
%>
<script>
	var checkPeriod = <%= "" + period %>;var title = '<%=PropertyFile.getConfiguration("/config/config.xml").getString("common-params.title")%>';var protocol = '<%=protocol%>';
</script>
<i18n:bundle baseName="org.claros.intouch.i18n.lang" locale="<%= loc %>"/>

<%@ include file="login_progress.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><%=PropertyFile.getConfiguration("/config/config.xml").getString("common-params.title")%></title>
	
	<link type="text/css" rel="stylesheet" href="css/all.css" />
	<link type="text/css" rel="stylesheet" href="css/ie6.css">
	<link type="text/css" rel="stylesheet" href="yui/calendar/assets/calendar.css" />
	<!-- link rel="stylesheet" type="text/css" href="yui/assets/css/tree.css"></link -->

	<script>var lang = "<%=loc%>";</script>
	<%@ include file="js_messages.jsp" %>
	<script type="text/javascript" src="yui/yahoo/yahoo-min.js"></script>
	<script type="text/javascript" src="yui/event/event-min.js"></script>
	<script type="text/javascript" src="yui/dom/dom-min.js"></script>
	<script type="text/javascript" src="yui/connection/connection-min.js"></script>
	<script type="text/javascript" src="yui/calendar/calendar-min.js"></script>
	<script type="text/javascript" src="yui/autocomplete/autocomplete-min.js"></script> 
	<script type="text/javascript" src="yui/treeview/treeview-min.js"></script>
	<script type="text/javascript" src="yui/animation/animation-min.js"></script>

	<script type="text/javascript" src="tinymce/tiny_mce.js"></script>
	<script type="text/javascript" src="js/prototype.js"></script>
	<script type="text/javascript" src="js/rico.js"></script>
	
	<script type="text/javascript" src="js/mm_functions.js"></script>
	<script type="text/javascript" src="js/script.js"></script>
	<script type="text/javascript" src="js/layers.js"></script>
	<script type="text/javascript" src="js/layout.js"></script>
	<script type="text/javascript" src="js/mail.js"></script>
	<script type="text/javascript" src="js/operations.js"></script>
	<script type="text/javascript" src="js/dw_viewport.js"></script>
	<script type="text/javascript" src="js/playa.js"></script>
	<script type="text/javascript" src="js/contacts.js"></script>
	<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/home.js"></script>
	<script type="text/javascript" src="js/webdisk.js"></script>
	
	<!--  for chat -->
	<script type="text/javascript" src="js/chatwin.js"></script>
	<script type="text/javascript" src="js/chat.js"></script>
	<script type="text/javascript" src="js/chat_operations.js"></script>
	<script type="text/javascript" src="js/md5.js"></script>
	<script type="text/javascript" src="js/date.js"></script>
	<script type="text/javascript" src="js/playa2.js"></script>
	<script type="text/javascript" src="yui/dragdrop/dragdrop-min.js"></script>
	<!-- end for chat -->
	
	<!-- for notes -->
	<script type="text/javascript" src="js/notes.js"></script>
	<script type="text/javascript" src="js/xlib/x_core.js"></script>
	<script type="text/javascript" src="js/xlib/x_drag.js"></script>
	<script type="text/javascript" src="js/xlib/x_event.js"></script>
	<!-- end for notes -->

</head>
<body scroll="no" style="overflow: hidden;" onunload="unloadHandler()" onmousemove="getPos(event)" onload="onloadHandler(event)" 
	onkeydown="onKeyDownH(event)" onkeyup="onKeyUpH()" onmouseup="onMouseUpH()">

<%@ include file="header.jsp" %>

<div id="main">
	<%@ include file="home.jsp" %>
	
	<!-- MAIL STARTS -->
	<div id="mail" style="display: none;">
		<%@ include file="mail_folders.jsp" %>
		<%@ include file="mailbox.jsp" %>
		<%@ include file="compose.jsp" %>
	</div>
	<!-- MAIL ENDS -->
	
	<!-- CONTACTS STARTS -->
	<div id="contacts" style="display:none;">
		<%@ include file="contacts.jsp" %>
	</div>
	<!-- CONTACTS ENDS -->
	
	<!-- CALENDAR STARTS -->
	<div id="calendardiv" style="display:none;">
		<%@ include file="calendar.jsp" %>
	</div>
	<!-- CALENDAR ENDS -->
	
	<!-- NOTES STARTS -->
	<div id="notes" style="display:none;">
		<%@ include file="notes.jsp" %>
	</div>
	<!-- NOTES ENDS -->
	
	<!-- WEBDISK STARTS -->
	<div id="webdisk" style="display:none;">
		<%@ include file="webdisk.jsp" %>
	</div>
	<!-- WEBDISK ENDS -->
	
	<!-- CHAT STARTS -->
	<div id="chat" style="display:none;" onclick="hideInfoWinFade();">
		<%@ include file="chat.jsp" %>
	</div>
	<!-- CHAT ENDS -->
</div>

<div id="curtain" class="curtain"></div>
<div id="whiteCurtain" class="whiteCurtain"></div>

<%@ include file="popups/default_error.jsp" %>
<%@ include file="popups/message_dirty_warn.jsp" %>
<%@ include file="popups/empty_recipient_error.jsp" %>
<%@ include file="popups/active_uploads_warn.jsp" %>
<%@ include file="popups/mail_stored_draft_info.jsp" %>
<%@ include file="popups/send_mail_info.jsp" %>
<%@ include file="popups/subject_empty_question.jsp" %>
<%@ include file="popups/show_headers_info.jsp" %>
<%@ include file="popups/create_mail_folder.jsp" %>
<%@ include file="popups/rename_mail_folder.jsp" %>
<%@ include file="popups/delete_mail_folder_confirm.jsp" %>
<%@ include file="popups/empty_mail_folder_confirm.jsp" %>
<%@ include file="popups/preferences.jsp" %>
<%@ include file="popups/loading_indicator.jsp" %>
<%@ include file="popups/send_result_indicator.jsp" %>
<%@ include file="popups/new_message_indicator.jsp" %>
<%@ include file="popups/sender_saved_info.jsp" %>
<%@ include file="popups/cancel_compose_question.jsp" %>
<%@ include file="popups/move_mail_info.jsp" %>
<%@ include file="popups/save_contact_duplicate_question.jsp" %>
<%@ include file="popups/contact_save_no_mail_error.jsp" %>
<%@ include file="popups/ask_send_read_receipt.jsp" %>
<%@ include file="popups/send_receipt_result_indicator.jsp" %>
<%@ include file="popups/event_alert_popup.jsp" %>
<iframe src="#" width="100" height="100" style="display: none;" id="downloader" name="downloader"></iframe>
<div id="uploader"></div>
</body>
</html>

