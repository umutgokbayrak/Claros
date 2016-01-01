<%@ page contentType="text/html; charset=UTF-8" %>

<div id="contactSaveNoMailError" class="dialogBox" style="display:none;width: 400px;height: 120px;">
	<div class="dialogHeader"><i18n:message key="error"/></div>
	<div class="dialogBody">
		<img src="images/error.gif" class="dialogBoxIcon"/>
		<div class="dialogText">
			<i18n:message key="contact.save.no.mail.error"/><br/><br/>
		</div>

		<table border="0" cellspacing="0" cellpadding="0" align="right">
			<tr>
				<td width="50%" align="right">&nbsp;</td>
				<td width="50%">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('contactSaveNoMailError');"><i18n:message key="ok"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
