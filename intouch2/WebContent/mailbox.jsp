<%@ page contentType="text/html; charset=UTF-8" %>

<!-- INBOX STARTS -->
<div id="inbox">
	<div class="inboxholder">
		<div class="inner">
			<ul id="tools">
				<li id="refreshMail"><a href="javascript:refreshMail();"><span><img alt="" src="images/refresh-icon.gif" id="refreshMailImg"/></span><i18n:message key="refresh"/></a></li>
				<li id="deleteMail"><a href="javascript:deleteMail();"><span><img alt="" src="images/delete-icon.gif" id="deleteMailImg"/></span><i18n:message key="delete"/></a></li>
				<li id="newMail"><a href="javascript:newMail()"><span><img alt="" src="images/new-icon.gif" id="newMailImg"/></span><i18n:message key="compose"/></a></li>
				<li id="replyMail"><a href="javascript:replyMail()"><span><img alt="" src="images/reply-icon.gif" id="replyMailImg"/></span><i18n:message key="reply"/></a></li>
				<li id="replyAllMail"><a href="javascript:replyAllMail()"><span><img alt="" src="images/replyall-icon.gif" id="replyAllMailImg"/></span><i18n:message key="reply.all"/></a></li>
				<li id="forwardMail"><a href="javascript:forwardMail()"><span><img alt="" src="images/forward-icon.gif" id="forwardMailImg"/></span><i18n:message key="forward"/></a></li>
				<li class="sub last"><a href="#"><span><img alt="" src="images/more-icon.gif"/></span><i18n:message key="more.actions"/></a>
					<ul>
						<li id="saveSenderMail"><a href="javascript:saveSender();"><span><img alt="" src="images/savesender-icon.gif" id="saveSenderMailImg"/></span><i18n:message key="save.sender"/></a></li>
						<li id="showHeadersMail"><a href="javascript:showHeaders();"><span><img alt="" src="images/shoheaders-icon.gif" id="showHeadersMailImg"/></span><i18n:message key="show.headers"/></a></li>
						<!-- <li id="createFilterMail"><a href="javascript:showFilterPreferences();"><span><img alt="" src="images/createfilter-icon.gif" id="createFilterMailImg"/></span><i18n:message key="create.filter"/></a></li>  -->
					</ul>
				</li>
			</ul>
			<div class="inboxTitle" id="inboxTitle1"><i18n:message key="INBOX"/></div><div style="display:none;margin-left:8px;height:27px;padding-top: 6px;" id="kitwait"><img src="images/ajax-load-mini.gif"/></div>
			<div id="mailList"></div>
			<div id="folderseperator" onmousedown="setPos(event)" onmouseup="mouseStatus = 'up'"></div>
			<div id="mailBody"></div>
			<div align="center" style="width:100%;height:18px;"><div id="pager"><img alt="" src="images/space.gif" width="150" height="1"></div></div>
		</div>
	</div>
</div>	
<!-- INBOX ENDS -->
