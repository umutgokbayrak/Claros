<%@ page contentType="text/html; charset=UTF-8" %>
<div id="eventAlert" class="dialogBox" style="display:none;width: 375px;min-height: 150px;height:150px;z-index: 6001;overflow: visible;">
	<div class="dialogHeader" id="eventAlertDialogHeader"><i18n:message key="warning"/></div>
	<div class="dialogBody">
		<img src="images/alarm-ring2.gif" class="dialogBoxIcon" style="padding-bottom: 60px;" id="alarmRingImg"/>
		<div class="dialogText" id="alertEventNote">
		</div>

		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td align="right" id="deleteEventButton">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="snoozeAlert();"><i18n:message key="snooze"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td>
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideThisAndShowNewAlert();"><i18n:message key="dismiss"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
