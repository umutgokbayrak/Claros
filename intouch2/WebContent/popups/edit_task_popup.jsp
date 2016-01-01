<%@ page contentType="text/html; charset=UTF-8" %>
<div id="createTask" class="dialogBox" style="display:none;width: 400px;height: 210px;z-index: 6001">
	<div class="dialogHeader" id="eventEditDialogHeader"><i18n:message key="edit.task"/></div>
	<div class="dialogBody">
		<img src="images/task.gif" class="dialogBoxIcon" style="padding-bottom: 100px;"/>
		<div class="dialogText">
			<table border="0" cellspacing="1" cellpadding="3" width="75%">
				<tr>
					<td><strong><i18n:message key="priority"/>: </strong></td>
					<td>
						<input type="hidden" id="taskId" value=""/>
						<select id="taskPriority" nohide="true" class="txt75">
						  <option value="1"><i18n:message key="priority.low"/></option>
						  <option value="2" selected><i18n:message key="priority.normal"/></option>
						  <option value="3"><i18n:message key="priority.high"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="notes"/>: </strong></td>
					<td>
						<textarea id="taskDescription" cols="30" rows="6" class="txtArea" style="height: 60px;width: 220px;"></textarea>
					</td>
				</tr>
			</table>
			<br/><br/>
		</div>

		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="saveTask();"><i18n:message key="save.task"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td>
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('createTask');"><i18n:message key="cancel"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
