var highZ = 110;

var selectedNotebook = 0;
var yellowBar = "#ffff7f";
var yellowBorder = "#e6e643";
var yellowBg = "#fdf7ad";
var redBg = "#ffc4bf";
var redBorder = "#8d3f39";
var redBar = "#ff7f7f";
var blueBg = "#bfdfff";
var blueBorder = "#426d98";
var blueBar = "#7fb8ff";
var greenBg = "#e9ffbf";
var greenBorder = "#7c954d";
var greenBar = "#d9ff7f";

function addNewNote() {

	var paramData = "folderId=" + selectedNotebook;
	var url = "notes/createNewNote.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null && o.responseText.indexOf('ok') == 0) {
					noteId = o.responseText.substr(2);
					
					var nw = Dom.get('noteWin');
					var nl = nw.cloneNode(true);
					nl.id = "note" + noteId;
					nl.setAttribute('noteId', noteId);
					nl.style.left = "237px";
					nl.style.top = "100px";
					nl.style.width = "225px";
					nl.style.height = "225px";
					nl.style.backgroundColor = yellowBg;
					nl.style.borderColor = yellowBorder;
					
					Dom.get('memoBoard').appendChild(nl);
					
					// created the note both at the database and screen. It is time for setup
					var note = xGetElementById("note" + noteId);
					var divs = note.getElementsByTagName('div');
					
					var rBtn = null;
					var dBar = null;
					var mBtn = null;
					var dCol = null;
					var dCont = null;
					if (divs != null) {
						for (var i=0;i<divs.length;i++) {
							if (divs[i].id == 'noteResizeBtn') {
								rBtn = divs[i];
							}
							if (divs[i].id == 'noteBar') {
								dBar = divs[i];
							}
							if (divs[i].id == 'noteDelBtn') {
								mBtn = divs[i];
							}
							if (divs[i].id == 'noteColorSelect') {
								dCol = divs[i];
							}
							if (divs[i].id == 'demoContent') {
								dCont = divs[i];
							}
						}
					}
					dBar.style.backgroundColor = yellowBar;

					xMoveTo(rBtn, xWidth(note) - xWidth(rBtn) - 2, xHeight(note) - xHeight(rBtn) - 2);
					xMoveTo(mBtn, xWidth(note) - xWidth(mBtn), 0);
					xMoveTo(dCol, 0, xHeight(note) - xHeight(dCol) - 5);
					xResizeTo(dCont, xWidth(note) - 10, xHeight(note) - 42);

//					xEnableDrag(dBar, noteBarOnDragStart, noteBarOnDrag, null);
					xEnableDrag(rBtn, noteRBtnOnResizeStart, noteRBtnOnResize, null);
					
					var lc = new YAHOO.util.DD(note.id);
					lc.setXConstraint(10, windowwidth - xWidth(note) - 240);
					lc.setYConstraint(13, windowheight - xHeight(note) - 102);
					lc.setHandleElId("noteBar");
					
					xZIndex("note" + noteId, highZ++);
					note.style.display = 'block';
					setNoteDate(note);
					xShow(note);
				} else {
					showDialog("defaultError");
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

function bringNoteToFront(ele) {
	var id = ele.parentNode.id;
	xZIndex(id, highZ++);
}

function noteBarOnDragStart(ele, mx, my) {
	var id = ele.parentNode.id;
	xZIndex(id, highZ++);
}

function noteBarOnDrag(ele, mdx, mdy) {
	var id = ele.parentNode.id;
	xMoveTo(id, xLeft(id) + mdx, xTop(id) + mdy);
}

function noteRBtnOnResizeStart(ele, mx, my) {
	var id = ele.parentNode.id;
	xZIndex(id, highZ++);
}

function noteRBtnOnResize(ele, mdx, mdy) {
	var id = ele.parentNode.id;
	
	if ((xWidth(id) > 500 && mdx > 0) || (xHeight(id) > 500 && mdy > 0)) {
		return;
	}

	xResizeTo(id, xWidth(id) + mdx, xHeight(id) + mdy);
	var note = xGetElementById(id);
	var divs = note.getElementsByTagName('div');
	
	var rBtn = null;
	var dBar = null;
	var mBtn = null;
	var dCol = null;
	var dCont = null;
	if (divs != null) {
		for (var i=0;i<divs.length;i++) {
			if (divs[i].id == 'noteResizeBtn') {
				rBtn = divs[i];
			}
			if (divs[i].id == 'noteBar') {
				dBar = divs[i];
			}
			if (divs[i].id == 'noteDelBtn') {
				mBtn = divs[i];
			}
			if (divs[i].id == 'noteColorSelect') {
				dCol = divs[i];
			}
			if (divs[i].id == 'demoContent') {
				dCont = divs[i];
			}
		}
	}
	
	xMoveTo(rBtn, xWidth(note) - xWidth(rBtn) - 2, xHeight(note) - xHeight(rBtn) - 2);
	xMoveTo(mBtn, xWidth(note) - xWidth(mBtn), 0);
	xMoveTo(dCol, 0, xHeight(note) - xHeight(dCol) - 5);
	xResizeTo(dCont, xWidth(note) - 10, xHeight(note) - 42);

	var lc = new YAHOO.util.DD(note.id);
	lc.setXConstraint(xLeft(note) - 227, windowwidth - xWidth(note) - xLeft(note) - 3);
	lc.setYConstraint(xTop(note) - 87, windowheight - xHeight(note) - xTop(note) - 2);
	lc.setHandleElId("noteBar");
}

function setNoteSizeAndPos(obj) {
	var note = obj;
	var paramData = "id=" + note.getAttribute('noteId') + "&left=" + xLeft(note) + "&top=" + xTop(note) + "&width=" + xWidth(note) + "&height=" + xHeight(note);
	var url = "notes/setPos.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != 'ok') {
					// showDialog("defaultError");
				}
			}
	  },
	  failure: 	function(o) {
		// showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function changeNoteColor(obj, color) {
	var note = obj.parentNode.parentNode;
	var divs = note.getElementsByTagName('div');
	
	var dBar = null;
	var mBtn = null;
	if (divs != null) {
		for (var i=0;i<divs.length;i++) {
			if (divs[i].id == 'noteBar') {
				dBar = divs[i];
			}
		}
	}
	
	if (color == 'yellow') {
		note.style.backgroundColor = yellowBg;
		note.style.borderColor = yellowBorder;
		dBar.style.backgroundColor = yellowBar;
	} else if (color == 'blue') {
		note.style.backgroundColor = blueBg;
		note.style.borderColor = blueBorder;
		dBar.style.backgroundColor = blueBar;
	} else if (color == 'red') {
		note.style.backgroundColor = redBg;
		note.style.borderColor = redBorder;
		dBar.style.backgroundColor = redBar;
	} else if (color == 'green') {
		note.style.backgroundColor = greenBg;
		note.style.borderColor = greenBorder;
		dBar.style.backgroundColor = greenBar;
	}

	var paramData = "id=" + note.getAttribute('noteId') + "&bg=" + note.style.backgroundColor + "&border=" + note.style.borderColor + "&bar=" + dBar.style.backgroundColor;
	var url = "notes/changeColor.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != 'ok') {
					// showDialog("defaultError");
				}
			}
	  },
	  failure: 	function(o) {
		// showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function noteEditContents(obj) {
	var note = obj.parentNode;
	var noteContent = obj.innerHTML;
	obj.style.visibility = 'hidden';

	var ntt = Dom.get('noteTextAreaTemplate');
	var ta = ntt.cloneNode(true);
	ta.style.width = (xWidth(note) - 2) + "px";
	ta.style.height = (xHeight(note) - 33) + "px";
	ta.style.display = 'block';
	ta.value = br2nl(noteContent);
	note.appendChild(ta);
	ta.focus();
}

function saveNoteContents(obj) {
	var note = obj.parentNode;

	var divs = note.getElementsByTagName('div');
	var dCont = null;
	if (divs != null) {
		for (var i=0;i<divs.length;i++) {
			if (divs[i].id == 'demoContent') {
				dCont = divs[i];
				break;
			}
		}
	}
	dCont.innerHTML = nl2br(obj.value);
	dCont.style.visibility = 'visible';

	note.removeChild(obj);
	setNoteDate(note);

	mybody = myEncode(dCont.innerHTML);

	var paramData = "id=" + note.getAttribute('noteId') + "&content=" + mybody;
	var url = "notes/saveNote.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != 'ok') {
					showDialog("defaultError");
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

function setNoteDate(note) {
	var dt = new Date();
	var month = dt.getMonth() + 1;
	var day = dt.getDate();
	var year = dt.getFullYear();
	var hour = dt.getHours();
	var mins = dt.getMinutes();
	var sec = dt.getSeconds();
	
	var divs = note.getElementsByTagName('div');
	var tmp = null;
	if (divs != null) {
		for (var i=0;i<divs.length;i++) {
			if (divs[i].id == 'noteBar') {
				tmp = divs[i];
				break;
			}
		}
	}
	tmp.innerHTML = zeroCheck(day) + "." + zeroCheck(month) + "." + year; // + " " + zeroCheck(hour) + ":" + zeroCheck(mins) + ":" + zeroCheck(sec);
}

function zeroCheck(iStr) {
	var str = iStr + "";
	if (str.length == 1) {
		str = "0" + str;
	}
	return str;
}

function deleteNote(obj) {
	var note = obj.parentNode;

	new Rico.Effect.Size(note.id, 10, 10, 100, 10, {complete:function() {
		Dom.get('memoBoard').removeChild(note);
	}});

	var paramData = "id=" + note.getAttribute('noteId');
	var url = "notes/deleteNote.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != 'ok') {
					showDialog("defaultError");
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

function notesCleaner() {
	try {
		var mb = Dom.get('memoBoard');
		var ds = mb.getElementsByTagName('div');
		if (ds != null) {
			for (var t=0;t<ds.length;t++) {
				var dsid = ds[t].id;
				if (ds[t].style.display == 'none' && dsid.indexOf('note') == 0 && dsid != 'noteWin' && ds[t].className == 'noteBox') {
					mb.removeChild(ds[t]);
				}
			}
		}
	} finally {
		window.setTimeout(notesCleaner, 300000);
	}
}

function getNotes(folderId) {
	showClickIndicator();
	if (folderId == null) {
		selectedNotebook = 0;
		folderId = selectedNotebook;
	} else {
		selectedNotebook = parseInt(folderId);
	}

	if (selectedNotebook == 0) {
		disableButton('deleteNoteFolder');
	} else {
		enableButton('deleteNoteFolder');
	}

	// do some cleaning first
	var mb = Dom.get('memoBoard');
	var ds = mb.getElementsByTagName('div');
	if (ds != null) {
		for (var t=0;t<ds.length;t++) {
			var dsid = ds[t].id;
			if (dsid.indexOf('note') == 0 && dsid != 'noteWin' && ds[t].className == 'noteBox') {
				ds[t].style.display = 'none';
				// mb.removeChild(ds[t]);
			}
		}
	}

	var paramData = "folderId=" + folderId;
	var url = "notes/getNotesByFolder.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						selectCurrentNotebook();
						var notes = o.responseXML.getElementsByTagName('notes');
						if (notes != null) {
							var nodes = notes[0].childNodes;
							for (var p=0;p<nodes.length;p++) {
								try {
									var note = nodes[p];
									displayNote(note);
								} catch (e) {
									alert(e.message);
								}
							}
						}
					}
				} finally {
					hideClickIndicator();
				}
			}
	  },
	  failure: 	function(o) {
		hideClickIndicator();
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function displayNote(note) {
	var noteId = getFirstCleanValByTagName(note, 'id');
	var barColor = getFirstCleanValByTagName(note, 'bar-color');
	var borderColor = getFirstCleanValByTagName(note, 'border-color');
	var bgColor = getFirstCleanValByTagName(note, 'bg-color');
	var content = getFirstCleanValByTagName(note, 'content');
	var date = getFirstCleanValByTagName(note, 'date');
	var height = getFirstCleanValByTagName(note, 'height');
	var left = getFirstCleanValByTagName(note, 'left');
	var top = getFirstCleanValByTagName(note, 'top');
	var width = getFirstCleanValByTagName(note, 'width');
	
	var nw = Dom.get('noteWin');
	var nl = nw.cloneNode(true);
	nl.id = "note" + noteId;
	nl.setAttribute('noteId', noteId);
	nl.style.left = left + "px";
	nl.style.top = top + "px";
	nl.style.width = width + "px";
	nl.style.height = height + "px";
	nl.style.backgroundColor = bgColor;
	nl.style.border = "1px solid " + borderColor;
	
	Dom.get('memoBoard').appendChild(nl);
	
	// get all the info. It is time for setup.
	var myNote = xGetElementById("note" + noteId);
	var divs = myNote.getElementsByTagName('div');
	
	var rBtn = null;
	var dBar = null;
	var mBtn = null;
	var dCol = null;
	var dCont = null;
	if (divs != null) {
		for (var i=0;i<divs.length;i++) {
			if (divs[i].id == 'noteResizeBtn') {
				rBtn = divs[i];
			}
			if (divs[i].id == 'noteBar') {
				dBar = divs[i];
			}
			if (divs[i].id == 'noteDelBtn') {
				mBtn = divs[i];
			}
			if (divs[i].id == 'noteColorSelect') {
				dCol = divs[i];
			}
			if (divs[i].id == 'demoContent') {
				dCont = divs[i];
			}
		}
	}
	dBar.style.background = barColor;

	dCont.innerHTML = nl2br(content);

	xMoveTo(rBtn, getWidth(myNote) - getWidth(rBtn), getHeight(myNote) - getHeight(rBtn));
	xMoveTo(mBtn, getWidth(myNote) - getWidth(mBtn) - 4, 0);
	xMoveTo(dCol, 0, getHeight(myNote) - getHeight(dCol) - 3);
	xResizeTo(dCont, getWidth(myNote) - 10, getHeight(myNote) - 42);

	xEnableDrag(rBtn, noteRBtnOnResizeStart, noteRBtnOnResize, null);

	var lc = new YAHOO.util.DD(myNote.id);
	lc.setXConstraint(xLeft(myNote) - 227, windowwidth - xWidth(myNote) - xLeft(myNote) - 3);
	lc.setYConstraint(xTop(myNote) - 87, windowheight - xHeight(myNote) - xTop(myNote) - 2);
	lc.setHandleElId("noteBar");
	
	xZIndex("note" + noteId, highZ++);
	myNote.style.display = 'block';
	dBar.innerHTML = date;
	xShow(myNote);
}

function getNotebooks() {
	var url = "notes/getNotebooks.cl";
	if (selectedNotebook == null) {
		selectedNotebook = 0;
	}
	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null) {
					var list = Dom.get('noteFolderListReal');
					var listHead = Dom.get('noteFolderListRealHead');
					list.innerHTML = 
						'<table cellSpacing=0 cellPadding=0 width=200 align=center border=0>'
						+ '<thead id="noteFolderListRealHead">' + listHead.innerHTML + '</thead>'
						+ '<tbody id="noteFolderListRealBody">' + o.responseText + '</tbody>'
						+ '</table>';
					selectCurrentNotebook();
					getNotes(selectedNotebook);
				}
			}
	  },
	  failure: 	function(o) {
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function addNotebook() {
	var nn = Dom.get('newNotebookName').value;
	if (nn = null || trim(nn).length == 0) {
		alert(empty_notebook_name);
		return;
	}

	var paramData = "folderName=" + myEncode(Dom.get('newNotebookName').value);
	var url = "notes/addNotebook.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null) {
					if (o.responseText == 'fail') {
						showDialog("defaultError");
					} else {
						selectedNotebook = parseInt(o.responseText);
						folderId = selectedNotebook;
						getNotebooks();
					}
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

function deleteNotebook() {
	var paramData = "id=" + selectedNotebook;
	var url = "notes/deleteNotebook.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null) {
					if (o.responseText != 'ok') {
						showDialog("defaultError");
					} else {
						selectedNotebook = 0;
						folderId = selectedNotebook;
						getNotebooks();
					}
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

function selectCurrentNotebook() {
	var trs = Dom.get('noteFolderListRealBody').getElementsByTagName('tr');
	if (trs != null) {
		for (var i=0;i<trs.length;i++) {
			trs[i].style.backgroundColor = C_WHITE;
			trs[i].style.color = C_BLACK;
			trs[i].clicked = false;
		}
	}
	
	var el = Dom.get('notebook' + selectedNotebook)
	el.style.backgroundColor = C_ROYALBLUE;
	el.style.color = C_WHITE;
	el.clicked = true;
	Dom.get('notebookNameShow').innerHTML = el.getElementsByTagName("td")[0].innerHTML;
}

function setDragConstraints(myNote) {
	var lc = new YAHOO.util.DD(myNote.id);
	lc.setXConstraint(xLeft(myNote) - 227, windowwidth - xWidth(myNote) - xLeft(myNote) - 3);
	lc.setYConstraint(xTop(myNote) - 87, windowheight - xHeight(myNote) - xTop(myNote) - 2);
	lc.setHandleElId("noteBar");
}
