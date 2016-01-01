<%@ page contentType="text/html; charset=UTF-8" %>

<div id="showHeadersInfo" class="dialogBox" style="display:none;width: 500px;height: 350px;z-index: 6001">
	<div class="dialogHeader"><i18n:message key="message.headers"/></div>
	<div class="dialogBody" style="width: 480px;height: 300px;">
		<div class="dialogText" style="overflow:auto;padding: 10px;width: 460px;height: 250px;" id="showHeaderBox">
		</div>

		<table border="0" cellspacing="0" cellpadding="0" align="right">
			<tr>
				<td width="50%">&nbsp;</td>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('showHeadersInfo');"><i18n:message key="ok"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
