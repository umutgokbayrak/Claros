<%@ page contentType="text/html; charset=UTF-8" %>

<!--  MAIL FOLDERS START -->
<div id="folders" onscroll="reLocateFolderActions();">
	<div id="mailFolders"></div>
	
	<ul class="buttons" id="folderActionsBtn">
		<li class="sub" onclick="showHideFolderActions();">
			<div>
				<span>
					<img alt="" src="images/forders-actions.gif"/>
				</span>
				<i18n:message key="folder.actions"/>
			</div>
		</li>
	</ul>
	
	<div id="folderActions" style="display:none;">
		<table border="0" cellspacing="1" cellpadding="0" width="100%">
			<tr>
				<td colspan="2" style="border-bottom: 1px solid #fafafa;height: 20px;">
					<input type="hidden" name="folderActionsId" id="folderActionsId"/>
					<strong><i18n:message key="folder"/>: <span id="folderActionsFolder">INBOX</span></strong>
				</td>
			</tr>
			<tr>
				<td width="1%"><img src="images/new-icon.gif"/></td>
				<td><a href="javascript:showCreateMailFolder();"><i18n:message key="create.new.folder"/></a></td>
			</tr>
			<tr id="renameMailFolderTr">
				<td width="1%"><img src="images/createfilter-icon.gif"/></td>
				<td><a href="javascript:showRenameMailFolder();"><i18n:message key="rename.folder"/></a></td>
			</tr>
			<tr id="deleteMailFolderTr">
				<td width="1%"><img src="images/delete-icon-small.gif"/></td>
				<td><a href="javascript:showDeleteMailFolder();"><i18n:message key="delete.folder"/></a></td>
			</tr>
			<tr>
				<td width="1%"><img src="images/trashcan_empty.gif"/></td>
				<td><a href="javascript:showEmptyMailFolder();"><i18n:message key="empty.folder"/></a></td>
			</tr>
		</table>
	</div>
</div>
<!--  MAIL FOLDERS END -->
