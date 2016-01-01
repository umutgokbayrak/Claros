var CustomDraggableWd = Class.create();

var draggedObj;
CustomDraggableWd.prototype = (new Rico.Draggable()).extend( {
   initialize: function( htmlElement, name) {
      this.type        = 'Custom';
      this.htmlElement = $(htmlElement);
      this.name        = name;
   },

   select: function() {
      this.selected = true;
   },

   deselect: function() {
      this.selected = false;
   },

   startDrag: function() {
      var el = this.htmlElement;

	  new Rico.Effect.FadeTo(el.id, .5, 100, 1);

      return null;
   },

   cancelDrag: function() {
      var el = this.htmlElement;
	  new Rico.Effect.FadeTo(el.id, 1, 100, 1);
   },

   endDrag: function() {
      var el = this.htmlElement;
      // this.htmlElement.style.display = 'none';
	  // el.style.display = 'none';
   },

   getSingleObjectDragGUI: function() {
      var el = this.htmlElement;
		var dragMsg = "<div id='dragObject2' style='background-color:transparent;background-image:url(images/file-drag-bg.gif);background-repeat:no-repeat;width:47px;height:38px;z-order:100;position:absolute;'>"

		var div = document.createElement("div");
		div.style.backgroundColor = 'transparent';
		
		new Insertion.Top(div, dragMsg);
		return div;
	},

   getDroppedGUI: function() {
      var el = this.htmlElement;
	  return "";
   }
} );


var CustomDropzoneWd = Class.create();
CustomDropzoneWd.prototype = (new Rico.Dropzone()).extend( {

   initialize: function( htmlElement, header) {
      this.htmlElement  = $(htmlElement);
      this.header       = $(header);
      this.absoluteRect = null;

      this.offset = navigator.userAgent.toLowerCase().indexOf("msie") >= 0 ? 0 : 1;
   },

   activate: function() {
   	if (this.htmlElement != null) {
		blockCurtain('curtain');
   	}
   },

   deactivate: function() {
   	if (this.htmlElement != null) {
   		unblockCurtain('curtain');
   	}
   },

   showHover: function() {
      if ( this.showingHover ) return;
      this.showingHover = true;
   },

   hideHover: function() {
		if ( !this.showingHover ) return;
		this.showingHover = false;
   },


 getAbsoluteRect: function() {
	 if ( this.absoluteRect == null ) {
		 var htmlElement = this.getHTMLElement();
		 if (htmlElement != null) {
			var pos = RicoUtil.toViewportPosition(htmlElement);
		 	try {
				 this.absoluteRect = {
					 top: pos.y,
					 left: pos.x,
					 bottom: pos.y + htmlElement.offsetHeight,
					 right: pos.x + htmlElement.offsetWidth
				 };
			} catch (e) {
				var h = htmlElement.style.height;
				var w = htmlElement.style.width;
				
				if (h.indexOf('px') > 0) {
					h = h.substr(0,h.indexOf('px'));
				}
				if (w.indexOf('px') > 0) {
					w = h.substr(0,w.indexOf('px'));
				}
				
				 this.absoluteRect = {
					 top: pos.y,
					 left: pos.x,
					 bottom: pos.y + h,
					 right: pos.x + w
				 };
			}
		 }
	 }
	 return this.absoluteRect;
 },

   accept: function(draggableObjects) {
		var el = this.htmlElement;
		if (el.id == 'wdTrash') {
			if (draggableObjects[0].htmlElement.id.indexOf('wdListItem') == 0) {
				var path = draggableObjects[0].htmlElement.id.substr(10);
				draggedObj = draggableObjects[0].htmlElement;
				showWDDeleteDialog(path);
				// deleteItem(path);
			} else if (draggableObjects[0].htmlElement.id.indexOf('folder') == 0) {
				var path = draggableObjects[0].htmlElement.id.substr(6);
				draggedObj = draggableObjects[0].htmlElement;
				showWDDeleteDialog(path);
				// deleteItem(path);
			}
		} else {
			if (draggableObjects[0].htmlElement.id.indexOf('wdListItem') == 0) {
				var path = draggableObjects[0].htmlElement.id.substr(10);
				var moveTo = el.getAttribute('pathenc');
				moveItem(path, moveTo);
			} else if (draggableObjects[0].htmlElement.id.indexOf('folder') == 0) {
				var path = draggableObjects[0].htmlElement.id.substr(6);
				var moveTo = el.getAttribute('pathenc');
				moveItem(path, moveTo);
			}
		}
   },

   canAccept: function(draggableObjects) {
   		var objDragged = draggableObjects[0];
   		
   		// TODO: ayni dizinin icine move edilmesini engellemek lazim. 
		return true;
   }
} );

function showWDDeleteDialog(path) {
	Dom.get('wdDeletePath').value = path;
	showDialog('deleteWdFileQuestion');
}

function moveItem(path, moveTo) {
	showClickIndicator();
	var paramData = "path=" + path + "&moveto=" + moveTo;
	var url = "webdisk/moveItem.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != 'ok') {
					showDialog("defaultError");
				} else {
				  hideFileInfo();
				  fileListInit = false;
				  showFileTree();
				}
				hideClickIndicator();
			}
	  },
	  failure: 	function(o) {
		// showDialog("defaultError");
		hideClickIndicator();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function deleteItem() {
	var path = Dom.get('wdDeletePath').value;
	hideDialog('deleteWdFileQuestion');
	
	showClickIndicator();
	var paramData = "path=" + path;
	var url = "webdisk/deleteItem.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != 'ok') {
					showDialog("defaultError");
				} else {
				  hideFileInfo();
				  fileListInit = false;
				  showFileTree();
				}
				hideClickIndicator();
			}
	  },
	  failure: 	function(o) {
		showDialog("defaultError");
		hideClickIndicator();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

var fileListInit = false;

function dumpList(node, parentNode) {
	var name = getFirstCleanValByTagName(node, 'name');
	var path = getFirstCleanValByTagName(node, 'path');
	var pathEnc = getFirstCleanValByTagName(node, 'path-enc');

	if (node.tagName == 'folder') {
		var txtNode = "<span id='folder" + pathEnc + "' name='" + name + "' path='" + path + "' pathenc='" + pathEnc + "'>" + name + "</span>";

		var tmpParent = new YAHOO.widget.TextNode(txtNode, parentNode, true);
		tmpParent.onLabelClick = folderClick;
		
		new YAHOO.widget.HTMLNode(".", tmpParent, false, true);
		
		var childs = node.childNodes;
		if (childs != null) {
			for (var p=0;p<childs.length;p++) {
				if (childs[p].tagName == 'file' || childs[p].tagName == 'folder') {
					dumpList(childs[p], tmpParent);
				}
			}
		}
	} else {
		// this is a file
		var icon = getFirstCleanValByTagName(node, 'icon');
		var mime = getFirstCleanValByTagName(node, 'mime');
		var size = getFirstCleanValByTagName(node, 'size');
		var date = getFirstCleanValByTagName(node, 'date');

		var dv2 = '<div id="wdListItem' + pathEnc + '"><table style="positon:relative;" border="0" cellspacing="0" cellpadding="0" width="100%" id="file' + pathEnc + '" ' + 
				  'class="fileListItem" onclick="clickFileListItem(this);" onmouseover="hoverFileItem(this)" onmouseout="unhoverFileItem(this);" ' + 
				  'name="' + name + '" path="' + path + '" mime="' + mime + '" size="' + size + '" date="' + date + '"  pathenc="' + pathEnc + '">' +
				  ' <tr><td><table border="0" cellspacing="0" cellpadding="0" width="100%"><tr><td width="1%" align="left" id="iconfile"><img src="images/mime/mini/' + icon + '"></td>' + 
				  '<td width="99%" align="left" id="nameFile" style="padding-left:5px">' + name + '</td></tr></table>' + 
				  '</td><td>' + date + '</td><td align="right" style="padding-right:3px;">' + size + '</td><td>&nbsp</td></table></div>';
		
		var tmpFile = new YAHOO.widget.HTMLNode(dv2, parentNode, false, true);
	}
}

function fileDragInit() {
	dndMgr.registerDropZone( new CustomDropzoneWd('wdTrash', 'wdTrash'));

	var lines = Dom.get('treewrapper').getElementsByTagName('table');
	for (var m=0;m<lines.length;m++) {
		if (lines[m].id != null && lines[m].id.indexOf('file') == 0) {
			dndMgr.registerDraggable(new CustomDraggableWd(lines[m].parentNode.id, "<div id='dragObject' style='border:1px solid #999999;padding-left:10px;padding-right:20px;'>&nbsp;</div>"));
		}
	}

	var folders = Dom.get('treewrapper').getElementsByTagName('span');
	for (var m=0;m<folders.length;m++) {
		if (folders[m].id != null && folders[m].id.indexOf('folder') == 0) {
			dndMgr.registerDropZone(new CustomDropzoneWd(folders[m].id, folders[m].id));
			dndMgr.registerDraggable(new CustomDraggableWd(folders[m].id, "<div id='dragObject' style='border:1px solid #999999;padding-left:10px;padding-right:20px;'>&nbsp;</div>"));
		}
	}
}

var webdiskSelectedDir;
function folderClick(node) {
	var fold;
	if (node.expanded) {
		node = tree.getRoot();
	}
	selectWdFolder(node);
}

function selectWdFolder(node) {
	fold = node.getEl().getElementsByTagName('span')[0];
	var name = fold.getAttribute('name');
	var path = fold.getAttribute('path');
	var pathEnc = fold.getAttribute('pathEnc');
	Dom.get('inboxTitleWd').innerHTML = name;
	webdiskSelectedDir = pathEnc;
}

var tree;
function showFileTree() {
	if (!fileListInit) {
		showClickIndicator();
		
		var url = "webdisk/getFileList.cl";
	
		var callback = {
		  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						var tmp = o.responseXML.getElementsByTagName('folder');
						if (tmp != null && tmp.length > 0) {
							tree = new YAHOO.widget.TreeView("treediv");
							var root = tree.getRoot();
							dumpList(tmp[0], root);
							if (!document.all) {
								tree.setExpandAnim(YAHOO.widget.TVAnim.FADE_IN);
								tree.setCollapseAnim(YAHOO.widget.TVAnim.FADE_OUT);
							}
							tree.draw();
							afterTreeDraw();
						}
					}
				} catch (hjdhd) {
					alert(hjdhd.message);
				} finally {
					hideClickIndicator();
				}
			}
		  },
	
		  failure: 	function(o) {
			showDialog("defaultError");
		  },
		  argument: [],
		  timeout: 1200000
		}
		var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
	}
}

function afterTreeDraw() {
	var lineTitle = Dom.get('fileListTitle');
	var lines = Dom.get('treewrapper').getElementsByTagName('table');
	var lineTitleThs = lineTitle.getElementsByTagName('th');
	
	for (var m=0;m<lines.length;m++) {
		if (lines[m].id != null && lines[m].id.indexOf('file') == 0) {
			var tds = lines[m].getElementsByTagName('td');
			var xy = getAbsolutePosition(tds[1].getElementsByTagName('img')[0]);
			var imgLeft = xy['x'] - 230;
	
			tds[0].getElementsByTagName('table')[0].style.width = (lineTitleThs[0].offsetWidth - imgLeft) + "px";
			tds[3].style.width = lineTitleThs[1].offsetWidth + "px";
			tds[4].style.width = lineTitleThs[2].offsetWidth + "px";
		}
	}
	
	var gs = Dom.get('treediv');
	gs.style.display = "none";
	var tds = gs.getElementsByTagName('td');
	for (var i=0;i<tds.length;i++) {
		if (tds[i].className != null && tds[i].className.indexOf('ygtvhtml') >= 0) {
			if (tds[i].innerHTML == '.') {
				tds[i].parentNode.parentNode.parentNode.parentNode.style.visibility = 'hidden';
			}
		}
	}
	// because of a bug at internet explorer we have to call the code again. (->use firefox)
	tds = gs.getElementsByTagName('td');
	for (i=0;i<tds.length;i++) {
		if (tds[i].className != null && tds[i].className.indexOf('ygtvhtml') >= 0) {
			if (tds[i].innerHTML == '.') {
				tds[i].parentNode.parentNode.parentNode.parentNode.style.display = 'none';
			}
		}
	}
	gs.style.display = "";
	
	fileListInit = true;
	fileDragInit();
}

var selectedFilePathEnc;
function clickFileListItem(obj) {
	var lines = Dom.get('treewrapper').getElementsByTagName('table');

	for (var m=0;m<lines.length;m++) {
		if (lines[m].id != null && lines[m].id.indexOf('file') == 0) {
			lines[m].style.backgroundColor = C_WHITE;
			lines[m].style.color = C_BLACK;
			lines[m].style.fontWeight = "normal";
			lines[m].setAttribute('clicked', 'false');
		}
	}
	obj.style.backgroundColor = C_ROYALBLUE;
	obj.style.color = C_WHITE;
	obj.style.fontWeight = "bold";
	obj.setAttribute('clicked', 'true');

	var name = obj.getAttribute('name');
	var mime = obj.getAttribute('mime');
	var size = obj.getAttribute('size');
	var date = obj.getAttribute('date');
	var path = obj.getAttribute('path');
	var pathEnc = obj.getAttribute('pathenc');

	Dom.get('wdName').value = name;
	Dom.get('wdMime').innerHTML = mime;
	Dom.get('wdSize').innerHTML = size;
	Dom.get('wdDate').innerHTML = date;
	
	showFileInfo();
	if (mime == 'image/gif' || mime == 'image/jpeg' || mime == 'image/png') {
		showImagePreview(pathEnc);
	} else {
		showDownloadPreview(pathEnc);
	}
	selectedFilePathEnc = pathEnc;
	
	// find the selected dir.
	var pos = path.lastIndexOf('/');
	var ptParent = path.substr(0, pos);

	var spans = Dom.get('treediv').getElementsByTagName('span');
	for (var i=0;i<spans.length;i++) {
		if (spans[i].getAttribute('path') == ptParent) {
			webdiskSelectedDir = spans[i].getAttribute('pathenc');
			Dom.get('inboxTitleWd').innerHTML = spans[i].getAttribute('name');
			break;
		}
	}
}

function showImagePreview(path) {
	Dom.get('wdImagePreviewImg').src = 'webdisk/showImgPreview.service?width=192&height=144&path=' + path;
	Dom.get('wdImagePreviewDown').href = 'webdisk/download.service?path=' + path;
	Dom.get('wdImagePreviewTr').style.display = '';
}

function showDownloadPreview(path) {
	Dom.get('wdImagePreviewImg').src = 'images/download-big.gif';
	Dom.get('wdImagePreviewDown').href = 'webdisk/download.service?path=' + path;
	Dom.get('wdImagePreviewTr').style.display = '';
}

function hideImagePreview(path) {
	Dom.get('wdImagePreviewImg').src = 'images/avatar_load.gif';
	Dom.get('wdImagePreviewTr').style.display = 'none';
}

function hoverFileItem(obj) {
	if (obj.getAttribute('clicked') != 'true') {
		obj.style.backgroundColor= "#E6ECF1";
	}
}

function downloadFile() {
	Dom.get('downloader').src = 'webdisk/download.service?path=' + selectedFilePathEnc;
} 

function emailFile() {
	showClickIndicator();
	layoutMail('compose');
	clearComposeForm();

	var paramData = "path=" + selectedFilePathEnc;
	// attach the mail from the displayed one.
	var url = "webdisk/forwardFile.cl";
	var callback = {
	  success: 	function(o) {
		if(o.responseText !== undefined) {
			Dom.get('composeAttachmentList').innerHTML = o.responseText;
			show('attachmentstr');
	  		fixLayout();
			
			var lst = Dom.get('composeAttachmentList').getElementsByTagName('li');
			if (lst == null) {
				hide('attachmentstr');
			} else {
				var total = 0;
				for (var m=0;m<lst.length;m++) {
					var myli = lst[m];
					total += parseInt(myli.getAttribute('size'));
				}
				var totalK = parseInt(total / 1024);
				Dom.get('totalSize').innerHTML = totalK + "K";
			}
			Dom.get('to').focus();
			hideClickIndicator();
		}
	  },
	  failure: 	function(o) {
	  	fixLayout();
		hideClickIndicator();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function unhoverFileItem(obj) {
	if (obj.getAttribute('clicked') != 'true') {
		obj.style.backgroundColor= C_WHITE;
	}
}

function hideFileInfo() {
	Dom.get('wdNnoFileSelectedTr').style.display='';
	Dom.get('wdImagePreviewTr').style.display='none';
	Dom.get('wdNameTr').style.display='none';
	Dom.get('wdKindTr').style.display='none';
	Dom.get('wdSizeTr').style.display='none';
	Dom.get('wdDateTr').style.display='none';
	Dom.get('wdActions').style.display='none';
}

function showFileInfo() {
	Dom.get('wdNnoFileSelectedTr').style.display='none';
	Dom.get('wdImagePreviewTr').style.display='none';
	Dom.get('wdNameTr').style.display='';
	Dom.get('wdKindTr').style.display='';
	Dom.get('wdSizeTr').style.display='';
	Dom.get('wdDateTr').style.display='';
	Dom.get('wdActions').style.display='';
}

function renameDir() {
	Dom.get('newWdRenameName').value = Dom.get('inboxTitleWd').innerHTML;
	showDialog('renameDirDialog');
}

function createDir() {
	Dom.get('newWdFolderParent').innerHTML = Dom.get('inboxTitleWd').innerHTML;
	showDialog('createDirDialog');
}

function createWdFolder() {
	var newName = Dom.get('newWdFolderName').value
	if (newName.length < 1) {
		alert(name_too_short);
		return;
	}

	var paramData = "parent=" + webdiskSelectedDir + "&dir=" + newName;
	var url = "webdisk/createDir.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					fileListInit = false;
					showFileTree();
					Dom.get('newWdFolderName').value = "";
					Dom.get('newWdFolderParent').innerHTML = "";
					hideDialog('createDirDialog');
				} else {
				  	showDialog('defaultError');
				}
			}
	  },

	  failure: 	function(o) {
	  	showDialog('defaultError');
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function renameWdFolder() {
	var newName = Dom.get('newWdRenameName').value
	if (newName.length < 1) {
		alert(name_too_short);
		return;
	}

	var paramData = "from=" + webdiskSelectedDir + "&dir=" + newName;
	var url = "webdisk/renameDir.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					fileListInit = false;
					showFileTree();
					Dom.get('newWdRenameName').value = "";
					hideDialog('renameDirDialog');
				} else {
				  	showDialog('defaultError');
				}
			}
	  },

	  failure: 	function(o) {
	  	showDialog('defaultError');
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function renameFile(path, newName) {
	if (path == null) {
		path = selectedFilePathEnc;
		newName = Dom.get('wdName').value;
	}
	if (newName.length < 2) {
		Dom.get('wdName').focus();
		showDialog('renameFileError');
		return;
	} else {
		var paramData = "path=" + path + '&name=' + newName;
		var url = "webdisk/renameItem.cl";
	
		var callback = {
		  success: 	function(o) {
				if(o.responseText !== undefined) {
					if (o.responseText != 'ok') {
						showDialog("defaultError");
					} else {
					  fileListInit = false;
					  hideFileInfo();
					  showFileTree();
					}
				}
		  },
		  failure: 	function(o) {
			showDialog("defaultError");
		  },
		  argument: [],
		  timeout: 30000
		}
		var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
	}
}

function uploadFile() {
	if (Dom.get('uploadFileImg').src == 'images/uploading.gif') {
		alert(active_uploads);
		return;
	}
	var f=Dom.get('inputfileWd').value;
	var pos=f.lastIndexOf('\\');
	if(pos>=0) f=f.substr(pos+1);
	pos=f.lastIndexOf('/');
	if(pos>=0) f=f.substr(pos+1);
	uploadingFiles[uploadingFiles.length] = f;
	
	var theFile = document.getElementById("inputfileWd");
	var fileParent = theFile.parentNode; 
	var theDiv = document.createElement('div'); 
	theDiv.style.display = 'none'; 
	theDiv.innerHTML = '<iframe id="uploadiframe'+uploadIframeCount+'" name="uploadiframe'+uploadIframeCount+'" src=""></iframe>' + 
	    '<form id="uploadform'+uploadIframeCount+'" target="uploadiframe'+uploadIframeCount+'" action="webdisk/uploadFile.cl" enctype="multipart/form-data" method="post">' + 
	    '<input type="hidden" name="iframeid" id="iframeid" value="'+uploadIframeCount+'"/>' + 
	    '</form>'; 
	Dom.get('uploader').appendChild(theDiv); 
	var uploadform = document.getElementById("uploadform"+uploadIframeCount); 
	fileParent.removeChild(theFile);
	uploadform.appendChild(theFile);
	uploadIframeCount++;
	uploadform.submit();
	uploadform.removeChild(theFile);
	fileParent.appendChild(theFile);
	
	Dom.get('uploadFileImg').src = 'images/uploading.gif';
}

var uploadingFiles = new Array();
