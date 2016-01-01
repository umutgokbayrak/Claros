<%@ page contentType="text/html; charset=UTF-8" %>
<div id="notesFolders">
	<div id="notesList">
		<div class="inner">
			<ul id="tools" style="background: transparent;overflow: hidden;margin-top: 5px;margin-right: 10px;overflow: hidden;float: left;">
				<li id="addNoteFolder"><a href="javascript:showDialog('addNotebook');"><span style="background: transparent;"><img alt="" src="images/forders-new.gif" id="addNoteFolderImg"/></span><i18n:message key="add.notebook"/></a></li>
				<li id="deleteNoteFolder"><a href="javascript:showDialog('deleteNotebookQuestion');"><span style="background: transparent;"><img alt="" src="images/delete-icon.gif" id="deleteNoteFolderImg"/></span><i18n:message key="delete"/></a></li>
			</ul>
			<div id="noteFolderListReal">
				<table border="0" cellspacing="0" cellpadding="0" width="200" align="center">
					<thead id="noteFolderListRealHead">
						<tr>
							<th><i18n:message key="notebooks"/>:</th>
						</tr>
					</thead>
					<tbody id="noteFolderListRealBody">
						<tr>
							<td align="center" style="border-bottom: none;">
								<br /><br /><br /><div align="center"><img src="images/chat/loading.gif" width="32" height="32"></div>	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<div id="notesDetails">
	<div id="newNote" align="right">
	  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
		<tr>
		  <td width="1%"><img src="images/newnote-left.gif" width="10" height="29"/></td>
		  <td width="1%" style="background-image: url(images/newnote-bg.gif);cursor:pointer" onclick="addNewNote();"><img src="images/new-icon.png"/></td>
		  <td nowrap="nowrap" style="background-image: url(images/newnote-bg.gif);text-align:center;padding-left:5px;padding-right:5px;cursor:pointer" width="98%" height="23" onclick="addNewNote();"><i18n:message key="new.note"/></td>
		  <td width="1%"><img src="images/newnote-right.gif" width="10" height="29"/></td>
		</tr>
	  </table>
	</div>
	<div id="memoBoard">
		<div id='noteWin' class='noteBox' onmouseout="setNoteSizeAndPos(this);" onmousedown="setDragConstraints(this);">
		  <div id='noteDelBtn' class='demoBtn' onclick="bringNoteToFront(this);deleteNote(this);" style="padding-right:4px;cursor: pointer;width: 15px;" align="right"><img src="images/note-close.png" style="margin-top: 3px;"/></div>
		  <div id='noteBar' class='noteBar' onclick="bringNoteToFront(this);" onmouseup="dragStop(event);">&nbsp;</div>
		  <div id="demoContent" class='demoContent' onclick="bringNoteToFront(this);noteEditContents(this);" style="overflow: auto;outline: none;"></div>
		  <div id='noteColorSelect' style="width: 80px;height:10px;padding-left: 2px;position:absolute;">
			  <div id="yellowNote" class="colorBox" style="background-color:#fdf7ad;" onclick="changeNoteColor(this, 'yellow');">&nbsp;</div>
			  <div id="blueNote" class="colorBox" style="background-color:#bfdfff;" onclick="changeNoteColor(this, 'blue');">&nbsp;</div>
			  <div id="greenNote" class="colorBox" style="background-color:#e9ffbf;" onclick="changeNoteColor(this, 'green');">&nbsp;</div>
			  <div id="redNote" class="colorBox" style="background-color:#ffc4bf;" onclick="changeNoteColor(this, 'red');">&nbsp;</div>
		  </div>
		  <div id='noteResizeBtn' class='demoBtn' style="width:10px;height:10px;float:right;background-image: url('images/note-resize-handler.gif'); background-repeat: no-repeat;cursor: se-resize;" onclick="bringNoteToFront(this);" onmouseup="_xOMU(this);">&nbsp;</div>
		</div>
	</div>
	<div id="notesFolderName" align="right"><div style="padding-top: 32px;" id="notebookNameShow"><i18n:message key="unorganized.notes"/></div></div>
	<textarea rows="3" cols="10" class="noteTextArea" id="noteTextAreaTemplate" value="" onblur="saveNoteContents(this)" style="display: none;"></textarea>
</div>

<%@ include file="popups/add_notebook.jsp" %>
<%@ include file="popups/delete_notebook_question.jsp" %>

