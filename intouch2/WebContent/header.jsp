<%@ page contentType="text/html; charset=UTF-8" %>

<div id="header" style="display:none;">
	<ul class="welcome">
		<li><i18n:message key="welcome"/> <span id="welcomeName"></span></li> 
		<li><a href="index.jsp"><span><img alt="" src="images/top-logoff.gif"/></span><i18n:message key="logout"/></a></li>
		<li><a href="javascript:showPreferences(false, 'Mail');"><span><img alt="" src="images/top-pref.gif"/></span><i18n:message key="preferences"/></a></li>
	</ul>
	<h1><a href="http://www.claros.org" target="_blank">claros.org</a></h1>
	<ul class="nav">
		<li class="active" id="navhome"><a href="javascript:layoutHome();"><span><img alt="" src="images/top-home.png"/></span><i18n:message key="home"/></a></li>
		<li id="navmail"><a href="javascript:layoutMail();"><span><img alt="" src="images/top-mail.png"/></span><i18n:message key="mail"/></a></li>
		<li id="navcontacts"><a href="javascript:layoutContacts();"><span><img alt="" src="images/top-contacts.png"/></span><i18n:message key="contacts"/></a></li>
 		<li id="navcalendar"><a href="javascript:layoutCalendar();"><span><img alt="" src="images/top-calendar.png"/></span><i18n:message key="calendar"/></a></li>
		<li id="navnotes"><a href="javascript:layoutNotes();"><span><img alt="" src="images/top-notes.png"/></span><i18n:message key="notes"/></a></li>
		<li id="navwebdisk"><a href="javascript:layoutWebdisk();"><span><img alt="" src="images/top-disk.png"/></span><i18n:message key="web.disk"/></a></li>
		<li id="navchat"><a href="javascript:layoutChat();"><span><img alt="" src="images/top-chat.png"/></span><i18n:message key="chat"/></a></li>
	</ul>
</div>
