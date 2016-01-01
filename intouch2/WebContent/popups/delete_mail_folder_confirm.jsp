<%@ page contentType="text/html; charset=UTF-8" %>

<div id="deleteMailFolderConfirm" class="dialogBox" style="display:none;width: 400px;height: 150px;">
	<div class="dialogHeader"><i18n:message key="mail.folder.delete.confirm"/></div>
	<div class="dialogBody">
		<img src="images/question.gif" class="dialogBoxIcon"/>
		<div class="dialogText">
			<i18n:message key="sure.delete.folder"/><br/><br/>
		</div>

		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="deleteMailFolder();"><i18n:message key="yes"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td width="50%">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('deleteMailFolderConfirm');"><i18n:message key="no"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
