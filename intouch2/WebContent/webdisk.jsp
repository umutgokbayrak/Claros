<%@ page contentType="text/html; charset=UTF-8" %>
<div id="webdiskFolders">
	<div id="webdiskList">
		<div class="inner">
			<div id="webdiskFolderListReal">
				<table border="0" cellspacing="0" cellpadding="0" width="200" align="center">
					<thead id="webdiskFolderListRealHead">
						<tr>
							<th colspan="2">&nbsp;<i18n:message key="file.information"/>:</th>
						</tr>
					</thead>
					<tbody id="webdiskFolderListRealBody">
						<tr id="wdNnoFileSelectedTr">
							<td colspan="2"><i18n:message key="no.file.selected"/></td>
						</tr>
						<tr id="wdImagePreviewTr" style="display:none;">
							<td colspan="2" style="padding-top:3px;padding-bottom: 15px;width:192px;height:144px;" align="center" valign="middle"><a href="#" id="wdImagePreviewDown" target="downloader"><img src="images/avatar_load.gif" id="wdImagePreviewImg" border="0"/></a></td>
						</tr>
						<tr id="wdNameTr" style="display:none;">
							<td><strong><i18n:message key="name"/>:</strong></td>
							<td align="left"><input type="text" id="wdName" class="txt125" onblur="renameFile();"/></td>
						</tr>
						<tr id="wdKindTr" style="display:none;">
							<td><strong><i18n:message key="type"/>:</strong></td>
							<td align="left" id="wdMime">&nbsp;</td>
						</tr>
						<tr id="wdSizeTr" style="display:none;">
							<td><strong><i18n:message key="size"/>:</strong></td>
							<td  align="left" id="wdSize">&nbsp;</td>
						</tr>
						<tr id="wdDateTr" style="display:none;">
							<td><strong><i18n:message key="date"/>:</strong></td>
							<td id="wdDate" align="left">&nbsp;</td>
						</tr>
						<tr id="wdActions" style="display:none;">
							<td colspan="2" width="100%">
								<table border="0" cellspacing="1" cellpadding="3" width="100%">
									<tr>
										<td align="right" style="padding-left: 0px;padding-right: 0px;">
										  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
											<tr>
											  <td width="1%" style="padding-left: 0px;padding-right: 0px;width:1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
											  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer;width:98%" width="98%" height="23" onclick="emailFile();"><i18n:message key="email"/></td>
											  <td width="1%" style="padding-left: 0px;padding-right: 0px;width:1%"><img src="images/button-right-bg.gif;" width="9" height="23"/></td>
											</tr>
										  </table>
										</td>
										<td align="left" style="padding-left: 3px;padding-right: 0px;">
										  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
											<tr>
											  <td width="1%" style="padding-left: 0px;padding-right: 0px;;width:1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
											  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer;width:98%" width="98%" height="23" onclick="downloadFile();"><i18n:message key="download"/></td>
											  <td width="1%" style="padding-left: 0px;padding-right: 0px;width:1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
											</tr>
										  </table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<img id="wdTrash" src="images/trashcan-huge.gif" width="96" height="96" style="margin-top: 20px;margin-bottom: 20px;margin-left: 60px;"/>
</div>
<div id="webdiskDetails" style="overflow: auto;">
	<div class="webdiskHolder">
		<div class="inner">
			<ul id="tools">
				<li id="uploadFile" style="margin-top: 7px;">
					<div class="uploader">
						<input onchange="uploadFile()" class="uploadbox" type="file" id="inputfileWd" name="inputfileWd" />
						<img alt="" src="images/export-contacts.gif" id="uploadFileImg" /><i18n:message key="upload.file"/>
					</div>
				</li>
				<li id="createDir"><a href="javascript:createDir();"><span><img alt="" src="images/new-icon.gif" id="createDirImg"/></span><i18n:message key="create.folder"/></a></li>
				<li id="renameDir"><a href="javascript:renameDir();"><span><img alt="" src="images/createfilter-icon.gif" id="renameDirImg"/></span><i18n:message key="rename.folder"/></a></li>
			</ul>
			<div class="inboxTitle" id="inboxTitleWd"><i18n:message key="web.disk"/></div><div style="display:none;margin-left:8px;height:27px;padding-top: 6px;" id="kitwait"><img src="images/ajax-load-mini.gif"/></div>

			<div style="width:100%;clear: both;	outline: none;">
		      <table border="0" cellspacing="0" cellpadding="0" style="width: 100%;margin:0px;padding:0px;">
		      	<thead>
		      		<tr id="fileListTitle">
		      			<th style="width: auto;border-left:1px solid #B9B9B9;">&nbsp;</th>
		      			<th style="width: 150px;"><i18n:message key="modified.date"/></th>
		      			<th style="width: 150px;"><i18n:message key="size"/></th>
		      		</tr>
		      	</thead>
			  </table>
			</div>

		    <div id="fileList">
			  <div id="treewrapper" style="overflow: auto;">
			  	<div><div>
					<div id="treediv"> </div>
				</div></div>
		      </div>
		    </div>
		</div>
	</div>
</div> 

<%@ include file="popups/rename_file_error.jsp" %>
<%@ include file="popups/create_dir.jsp" %>
<%@ include file="popups/rename_dir_dialog.jsp" %>
<%@ include file="popups/webdisk_delete_confirm.jsp" %>
