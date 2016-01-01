<%@ page contentType="text/html; charset=UTF-8" %>

<div id="createDirDialog" class="dialogBox" style="display:none;width: 350px;height: 200px;z-index: 6001">
	<div class="dialogHeader"><i18n:message key="create.new.folder"/></div>
	<div class="dialogBody">
		<img src="images/info.gif" class="dialogBoxIcon" style="padding-bottom: 100px;"/>
		<div class="dialogText">
			<i18n:message key="enter.name.folder"/><br/>
			<strong><i18n:message key="parent.folder"/>:</strong>: <span id="newWdFolderParent"></span><br/> <br/>
			<i18n:message key="name"/>: <input type='text' name='newWdFolderName' id="newWdFolderName" style="1px solid #999999;"/>
			<br/><br/>
		</div>

		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="createWdFolder();"><i18n:message key="ok"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td width="50%">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('createDirDialog');"><i18n:message key="cancel"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
