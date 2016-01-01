<%@ page contentType="text/html; charset=UTF-8" %>
<div id="askSendReadReceipt" class="dialogBox" style="display:none;width:500px;height:120px;z-index:6001">
	<div class="dialogBody">
		<img src="images/info.gif" class="dialogBoxIcon"/>
		<div class="dialogText">
			<i18n:message key="ask.send.read.receipt"/><br/><br/>
			<input name="dontAskSendReadReceipt" id="dontAskSendReadReceipt" type="checkbox" value="1" style="border:0;font-size:9px;">
			<i18n:message key="dont.ask.send.read.receipt"/>
		</div>
		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="sendReadReceiptMail()"><i18n:message key="yes"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td>
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('askSendReadReceipt')"><i18n:message key="no"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
