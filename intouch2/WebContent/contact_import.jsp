<%@ page contentType="text/html; charset=UTF-8" %>

<div id="contactImportWin" class="dialogBox" style="display:none;width: 550px;height: 450px;z-index: 6001">
	<div class="dialogHeader">Import Contacts</div>
	<div class="dialogBody">
		<img src="images/info.gif" class="dialogBoxIcon" style="padding-bottom: 20px;"/>
		<div class="dialogText">
			<i18n:message key="import.contact.msg1"/>
			<i18n:message key="import.contact.msg2"/>
			<i18n:message key="import.contact.msg3"/>
			<i18n:message key="import.contact.msg4"/>
			<i18n:message key="import.contact.msg5"/>
			<i18n:message key="import.contact.msg6"/>
			<iframe src="contact_import_iframe.jsp" frameborder="0" marginheight="0" marginwidth="0" scrolling="no" width="100%" height="90" id="contactImportIframe" name="contactImportIframe"></iframe>
		</div>
		<table border="0" width="100%" cellspacing="1" cellpadding="3">
			<tr>
				<td width="80%" align="right" nowrap="nowrap">
					<table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					  <tr>
					    <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					    <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="uploadImportCsv();"><i18n:message key="start.importing"/></td>
					    <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					  </tr>
					</table>
				</td>
				<td width="20%" align="right">
					<table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
						<tr>
						  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
						  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('contactImportWin');"><i18n:message key="cancel"/></td>
						  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</div>
</div>
