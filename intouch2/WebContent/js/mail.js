var CustomDraggable = Class.create();

CustomDraggable.prototype = (new Rico.Draggable()).extend( {
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
	
	  if (!el.clicked) {
		el.style.backgroundColor = C_LIGHTBLUE;
		el.style.color = C_BLACK;
		el.clicked = true;
	  }

		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		if (ps != null) {
			for (var i=0;i<ps.length;i++) {
				if (ps[i].id != 'mailtitle' && ps[i].clicked) {
			      new Rico.Effect.FadeTo(ps[i], .5, 100, 1 );
				}
			}
		}

      return null;
   },

   cancelDrag: function() {
      var el = this.htmlElement;

		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		if (ps != null) {
			for (var i=0;i<ps.length;i++) {
				if (ps[i].id != 'mailtitle' && ps[i].clicked) {
			      new Rico.Effect.FadeTo(ps[i], 1, 100, 1 );
				}
			}
		}

   },

   endDrag: function() {
      var el = this.htmlElement;
      this.htmlElement.style.display = 'none';

		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		if (ps != null) {
			for (var i=0;i<ps.length;i++) {
				if (ps[i].id != 'mailtitle' && ps[i].clicked) {
			      ps[i].style.display = 'none';
				}
			}
		}


   },

   getSingleObjectDragGUI: function() {

      var el = this.htmlElement;

	/*
      var div = document.createElement("div");
      div.className = 'customDraggable';
	  //div.style.width = (windowwidth - 500) + "px";
		div.style.width = "350px";
		var dragMsg = "<div id='dragObject' style='overflow-x:hidden;border:1px solid #4f88b4;padding-top:2px;padding-bottom:2px;padding-left:10px;padding-right:20px;white-space:nowrap;'>";

		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		if (ps != null) {
			for (var i=0;i<ps.length;i++) {
				if (ps[i].id != 'mailtitle' && ps[i].clicked) {
			      var sbj = ps[i].getElementsByTagName("span")[1].innerHTML + " - " + ps[i].getElementsByTagName("span")[2].innerHTML;
			      dragMsg += sbj;
			      if (ps.length != 1 && i != (ps.length - 1)) {
			      	dragMsg += "<br/>";
			      }
				}
			}
		}
		dragMsg += "</div>";
	*/
	
		var dragMsg = "<div id='dragObject2' style='background-color:transparent;background-image:url(images/mouse-drag-bg.gif);background-repeat:no-repeat;width:47px;height:38px;z-order:100;position:absolute;'>"
		var dragCount = 0;
	
		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		if (ps != null) {
			for (var i=0;i<ps.length;i++) {
				if (ps[i].id != 'mailtitle' && ps[i].id != 'mailtitleClone' && ps[i].clicked) {
					dragCount++;
				}
			}
		}
	
		dragMsg += "<div style='padding-top:19px;padding-left:31px;color:white;font-size:12px;font-weight:bold;'>" + dragCount + "</div>";
		dragMsg += "</div>";

		var div = document.createElement("div");
		div.style.backgroundColor = 'transparent';
		
		new Insertion.Top(div, dragMsg);
		return div;
	},

/*
   getMultiObjectDragGUI: function( draggables ) {
      var el = this.htmlElement;

      var names = "";
      for ( var i = 0 ; i < draggables.length ; i++ ) {
         names += draggables[i].name;
         if ( i != (draggables.length - 1) )
            names += ",<br/>";
      }

      var div = document.createElement("div");
      div.className = 'customDraggable';
//      div.style.width = (this.htmlElement.offsetWidth - 10) + "px";
      new Insertion.Top( div, names );
      return div;
   },
*/
   getDroppedGUI: function() {
      var el = this.htmlElement;
	  return "";
   }
} );

var CustomDropzone = Class.create();

CustomDropzone.prototype = (new Rico.Dropzone()).extend( {

   initialize: function( htmlElement, header) {
      this.htmlElement  = $(htmlElement);
      this.header       = $(header);
      this.absoluteRect = null;

      this.offset = navigator.userAgent.toLowerCase().indexOf("msie") >= 0 ? 0 : 1;
   },

   activate: function() {
   	if (this.htmlElement != null) {
		blockCurtain('curtain');
   		this.htmlElement.style.backgroundColor = "#ebf1f6";
		var ems = this.htmlElement.getElementsByTagName('em');
		if (ems != null && ems.length > 0) {
			ems[0].style.visibility = 'visible';
		}
   	}
   },

   deactivate: function() {
   	if (this.htmlElement != null) {
   		unblockCurtain('curtain');
		var ems = this.htmlElement.getElementsByTagName('em');
		if (ems != null && ems.length > 0) {
			ems[0].style.visibility = 'visible';
		}
   		this.htmlElement.style.backgroundColor = "#E6ECF1";
   	}
   },

   showHover: function() {
      if ( this.showingHover ) return;
	   	if (this.htmlElement != null) {
			var ems = this.htmlElement.getElementsByTagName('em');
			if (ems != null && ems.length > 0) {
				ems[0].style.visibility = 'hidden';
			}
	   		this.htmlElement.style.backgroundColor = C_WHITE;
	    }
      this.showingHover = true;
   },

   hideHover: function() {
		if ( !this.showingHover ) return;
		var ems = this.htmlElement.getElementsByTagName('em');
		if (ems != null && ems.length > 0) {
			ems[0].style.visibility = 'visible';
		}
		this.htmlElement.style.backgroundColor = "#ebf1f6";
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

		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		var moveArr = new Array();
		var counter = 0;
		if (ps != null) {
			// we must have moved e-mail ids in array
			for (var i=0;i<ps.length;i++) {
				if (ps[i].id != 'mailtitle' && ps[i].clicked) {
				  moveArr[counter] = ps[i].id.substr(4);
				  counter++;
				}
			}

			// we've got the ids now learn about the target folder
			var folderName = "";
			var el = this.htmlElement;
			if (el != null) {
				var fid = el.getAttribute('folderid');
				folderName = el.id.substr(10);

				if (fid != null && fid != '' && fid != 'null') {
					folderName = fid;
				}
			}
			var currentFolder = getCurrentFolder();
			if (moveMails(moveArr, currentFolder, folderName)) {
				return false;
			}
		}

   },

   canAccept: function(draggableObjects) {
		// we've got the ids now learn about the target folder
		var folderName = "";
		var el = this.htmlElement;

		if (el != null) {
			var fid = el.getAttribute('folderid');
			folderName = el.id.substr(10);

			if (fid != null && fid != '' && fid != 'null') {
				folderName = fid;
			}
			
			// trying to determine if this is a pop3 inbox folder
			try {
				// folderid="6" foldertype="1"
				var tmpId = parseInt(fid);
				var fType = el.getAttribute('foldertype');
				if (fType == '1' && protocol == 'pop3') {
					return false;
				}
			} catch (mm) {}
		}
		var currentFolder = getCurrentFolder();
		if (currentFolder == folderName) {
			return false;
		}
		
		// TODO: if pop3 mode INBOX must return false
		return true;
   }

} );

function mailDragInit() {
	var list = Dom.get('mailList');
	var ps = list.getElementsByTagName('p');
	
	if (ps != null) {
		for (var i=0;i<ps.length;i++) {
			if (ps[i].id != 'mailtitle' && ps[i].id.indexOf('Clone') < 0) {
				dndMgr.registerDraggable(new CustomDraggable(ps[i].id, "<div id='dragObject' style='border:1px solid #999999;padding-left:10px;padding-right:20px;'>&nbsp;</div>"));
			}
		}
	}
}

function mailDragFolderInit() {
	var folders = Dom.get('mailFolders');
	var lis = folders.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			dndMgr.registerDropZone( new CustomDropzone(lis[i].id, lis[i].id));
//			dndMgr.registerDropZone( new Rico.Dropzone(lis[i].id) );
		}
	}
}

var curHeight=0
var curHeight2=0
var curPos=0
var newPos=0
var mouseStatus='up'

//this function gets the original div height
function setPos(e) {
	//for handling events in ie vs. w3c
	curevent=(typeof event=='undefined'?e:event)
	//sets mouse flag as down
	mouseStatus='down'
	//gets position of click
	curPos=curevent.clientY
	//accepts height of the div
	tempHeight=document.getElementById('mailList').style.height
	tempHeight2=document.getElementById('mailBody').style.height
	//these lines split the height value from the 'px' units
	heightArray=tempHeight.split('p')
	heightArray2=tempHeight2.split('p')
	curHeight=parseInt(heightArray[0])
	curHeight2=parseInt(heightArray2[0])
}

//this changes the height of the div while the mouse button is depressed
function getPos(e){
	if(mouseStatus=='down'){
		curevent=(typeof event=='undefined'?e:event)
		//get new mouse position
		newPos=curevent.clientY
		//calculate movement in pixels
		var pxMove=parseInt(newPos-curPos)
		//determine new height
		var newHeight=parseInt(curHeight+pxMove)
		//conditional to set minimum height to 5
//								newHeight=(newHeight<5?5:newHeight)
		var newHeight2=parseInt(curHeight2-pxMove)
		//conditional to set minimum height to 5
		newHeight2=(newHeight2<200?200:newHeight2)
		
		if (newHeight2 > 200 && newHeight > 50) {
			//set the new height of the div
			document.getElementById('mailList').style.height=newHeight+'px'
			
			//set the new height of the div
			document.getElementById('mailBody').style.height=newHeight2+'px'
		}
		fixMsgViewIframeLayout();
	}
} 

function arrangeFolderActions(fold) {
	
	if (fold == null) {
		var folders = Dom.get('folders');
		var lis = folders.getElementsByTagName('li');
		if (lis != null) {
			for (var i=0;i<lis.length;i++) {
				if (lis[i].className == 'active') {
					fold = lis[i];
				}
			}
		}
	}
	
	var folderName = fold.getAttribute('folderName');
	Dom.get('renameMailFolderOld1').innerHTML = folderName;
	Dom.get('renameMailFolderOld2').innerHTML = folderName;
	Dom.get('folderActionsFolder').innerHTML = folderName;
	Dom.get('folderActionsId').value = fold.getAttribute('folderId');
	Dom.get('deleteFolderName').innerHTML = folderName;
	Dom.get('emptyFolderName').innerHTML = folderName;

	var folderType = fold.getAttribute('folderType');
	if (folderType == '6') {
		show('renameMailFolderTr');
		show('deleteMailFolderTr');
	} else {
		hide('renameMailFolderTr');
		hide('deleteMailFolderTr');
	}
	reLocateFolderActions();
}
	
function selectInbox() {
	var folders = Dom.get('folders');
	var inb = folders.getElementsByTagName('li')[0];
	var id = inb.id.substr(10);
	selectMailFolder(id);
}

var selectedFolder = "";
function selectMailFolder(id, preselectId) {
	checkingMail = false;
	var folders = Dom.get('folders');
	var lis = folders.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			lis[i].className = '';
		}
	}
	
	var fold = Dom.get('mailFolder' + id);
	fold.className = 'active';
	layoutMail('inbox');
	selectedFolder = id;

	arrangeFolderActions(fold);
	// do some cleaning on the user interface
	var list = Dom.get('mailList');
	var ps = list.getElementsByTagName('p');
	if (ps != null) {
		var tmp = null;
		var newCont = "";
		for (var i=0;i<ps.length;i++) {
			tmp = ps[i];
			if (tmp.className == 'title') {
				newCont = "<div id=\"ieHolder\" class=\"ieHolder\"><div class=\"ie7Holder\"><p class=\"title id='mailtitle' foldername='mailFolder" + id + "'\">" + tmp.innerHTML + "</p></div></div>";
				break;
			}
		}
		list.innerHTML = newCont;
	}
	
	// check the mailbox content
	//	var title = Dom.get('mailtitle');
	//	title.setAttribute('foldername', 'mailFolder' + id);
	unreadMessages = -1;
	clearPreview();
	if (preselectId == null) {
		checkMail(null, null, true, null);
	} else {
		checkMail(null, null, true, preselectId);
	}
}

var lastClickedId;
function mailListChangeStyle(o,bc,fc,c,lc,bi) {
	o.style.backgroundColor=bc;
	o.style.color=fc;
	o.clicked=c;
	o.lastClicked=lc;
	var bio=o.getElementsByTagName('span')[0];
	if(0==bi) bio.style.backgroundImage='url(images/unchecked.gif)';
	else bio.style.backgroundImage='url(images/checked.gif)';
}

function mailListClick(event,obj,fetch,ctrlForced) {
	obj=obj.parentNode;
	if(obj.className=='title') {
		return;
	}
	var shift=false,ctrl=false;
	var list=Dom.get('mailList');
	var ps=list.getElementsByTagName('p');
	var curIndex=-1,nextIndex=-1;
	if(ps!=null) {
	
		var e=event||window.event;
		if(e){
			ctrl=e.ctrlKey;
			shift=e.shiftKey;
		}
		if(ctrlForced||keyCode + "" == '224') {
			ctrl = true;	
		}

		if(obj.clicked && (shift || ctrl)) {
			mailListChangeStyle(obj,C_WHITE,C_BLACK,false,false,0);
			checkMailButtons();
			return;
		}
		// shift pressed is a special case
		if(shift) {
			// find the latest shown header which is dark blue
			for(var i=0;i<ps.length;i++) {
				if(ps[i].lastClicked) {
					curIndex=i;
				}
				if(obj.id==ps[i].id) {
					nextIndex=i;
				}
			}
			if(nextIndex!=-1 && curIndex!=-1) {
				// found the latest clicked and current clicked ones.
				if(nextIndex>curIndex) {
					for(var j=curIndex;j<=nextIndex;j++)
						mailListChangeStyle(ps[j],C_LIGHTBLUE,C_BLACK,true,false,1);
				} else {
					for(var j=curIndex;j>=nextIndex;j--)
						mailListChangeStyle(ps[j],C_LIGHTBLUE,C_BLACK,true,false,1);
				}
			}
			mailListChangeStyle(obj,C_LIGHTBLUE,C_BLACK,true,false,1);
			Dom.get('mailBody').innerHTML="";
			checkMailButtons();
			return;
		} else if(ctrl) {
			for(var i=0;i<ps.length;i++) {
				if(ps[i].clicked) {
					mailListChangeStyle(ps[i],C_LIGHTBLUE,C_BLACK,true,false,1);
				}
			}
			mailListChangeStyle(obj,C_LIGHTBLUE,C_BLACK,true,false,1);
			Dom.get('mailBody').innerHTML="";
			checkMailButtons();
			return;
		} else {
			for(var i=0;i<ps.length;i++) {
				if(ps[i].clicked) {
					mailListChangeStyle(ps[i],C_WHITE,C_BLACK,false,false,0);
				}
			}
			mailListChangeStyle(obj,C_ROYALBLUE,C_WHITE,true,false,1);
			lastClickedId = obj.id.substr(4);
		}
		// message fetched here
		if (fetch) {
			// clearly set the lastClicked
			for(var i=0;i<ps.length;i++) {
				ps[i].lastClicked = false;
			}
			obj.lastClicked = true;
			
			// go fetch the message
			var msgId = obj.id.substr(4);
			if (obj.style.fontWeight == 'bold') {
				showMail(msgId);
				obj.style.fontWeight = 'normal';
				
				// minus one the unread mail in the folder view
				var folders = Dom.get('folders');
				var lis = folders.getElementsByTagName('li');
				if (lis != null) {
					for (var i=0;i<lis.length;i++) {
						if (lis[i].className == 'active') {
							var uc = lis[i].getElementsByTagName('b')[0];
							if (uc != null) {
								var iuc = parseInt(uc.innerHTML);
								iuc--;
								if (iuc == 0) {
									lis[i].getElementsByTagName('em')[0].innerHTML = '';
								} else {
									uc.innerHTML = iuc + "";
								}
							}
						}
					}
				}
				
				// now re-organize the window title. because some mail are deleted and they may be indicated on the window title as unread
				unreadMessages--;
				if (unreadMessages != -1 && unreadMessages > 0) {
					document.title = unreadMessages + " " + unread +" - " + title;
				} else {
					document.title = title;
				}
			} else {
				showMail(msgId);
			}
		}
	}
	checkMailButtons();
}

var selectAllMailNext = true;

function clickAll() {
	// check if all mails are already clicked or not
	var lstMail = Dom.get('mailList').getElementsByTagName('P');
	if (lstMail != null) {
		var allClicked = true;
		for (var i=0;i<lstMail.length;i++) {
			var obj = lstMail[i];
			if (obj.id != 'mailtitle' && obj.id.indexOf('Clone') < 0) {
				if (!obj.clicked) {
					allClicked = false;
					break;
				}
			}
		}
		// not all of them clicked. do normal operation
		if (allClicked) {
			selectAllMailNext = false;
			Dom.get('mailtitleClone').getElementsByTagName('span')[0].style.backgroundImage = 'url(images/unchecked.gif)';
		} else {
			selectAllMailNext = true;
			Dom.get('mailtitleClone').getElementsByTagName('span')[0].style.backgroundImage = 'url(images/checked.gif)';
		}
	}


	var lstMail = Dom.get('ie7Holder').getElementsByTagName('P');
	if (lstMail != null) {
		for (var i=0;i<lstMail.length;i++) {
			var obj = lstMail[i];
			if (obj.id != 'mailtitle' && obj.id.indexOf('Clone') < 0) {
				if(selectAllMailNext) {
					mailListChangeStyle(obj,C_LIGHTBLUE,C_BLACK,true,false,1);
				} else {
					mailListChangeStyle(obj,C_WHITE,C_BLACK,false,false,0);
				}
			}
		}
	}
	Dom.get('mailBody').innerHTML="";
	checkMailButtons();
}

// checks to see if any tool buttons needed to be disabled or enabled
function checkMailButtons() {
	var rowsSelected = 0;
	var lst = Dom.get('mailList').getElementsByTagName('p');
	var lastClickSame = false;
	if (lst != null) {
		for (var i=0;i<lst.length;i++) {
			if(lst[i].clicked) {
				rowsSelected++;
				if (lst[i].lastClicked) {
					lastClickSame = true;
				} else {
					lastClickSame = false;
				} 
			}
		}
	}
	switch (rowsSelected) {
		case 0:
			disableButtons('deleteMail','replyMail','replyAllMail','forwardMail',
				'saveSenderMail','showHeadersMail');
			// disableButton('createFilterMail');
			break;
		case 1:
			// if the clicked one is not the lastClicked item then do not show reply etc... icons. 
			if (lastClickSame) {
				enableButtons('deleteMail','replyMail','replyAllMail','forwardMail',
					'saveSenderMail','showHeadersMail');
				// enableButton('createFilterMail');
			} else {
				enableButton('deleteMail');
				disableButtons('replyMail','replyAllMail','forwardMail','saveSenderMail',
					'showHeadersMail');
			}
			break;
		default:
			enableButton('deleteMail');
			disableButtons('replyMail','replyAllMail','forwardMail','saveSenderMail',
				'showHeadersMail');
			// disableButton('createFilterMail');
	}
}

function sortColumn(obj, col) {
	var sort = obj.getAttribute('sort');
	if (sort == null) {
		sort = 'desc';
	}
	checkMail(col, sort)	
}

function previewPart(obj) {
	var src = Dom.get('mailViewTitle');
	var folder = src.getAttribute('folder');
	var msgId = src.getAttribute('msgid');
	var preview = obj.getAttribute('preview');
	var partId = obj.getAttribute('partid');

	Dom.get('previewPanel').style.display = 'block';
	
	if (preview == 'true') {
		Dom.get("previewPanelContent").style.display = 'block';
	} else {
		Dom.get("previewPanelContent").style.display = 'none';
	}
	
	Dom.get("previewPanelContent").innerHTML = "<iframe align='center' frameborder='0' height='400' width='100%' style='font-size: 11px;font: arial, sans-serif;' scrolling='auto' marginheight='10' marginwidth='10' src='webmail/dumpPart.service?partid=" + partId + "&msgId=" + msgId + "&folder=" + folder + "' width='100%' border='0'/>";
	YAHOO.claros.container.previewPanel.show();
}

var headerCompact = true;

function toggleHeader() {
	try {
		if (headerCompact) {
			headerCompact = false;
			showToggleHeader();
		} else {
			headerCompact = true;
			showToggleHeader();
		}
	} catch (e) {
		alert(e.message);
	}
}

function showToggleHeader() {
	var title = Dom.get('mailViewTitle');
	if (!headerCompact) {
		if (title != null) {
			var ps = title.getElementsByTagName('p');
			if (ps != null) {
				for (var i=0;i<ps.length;i++) {
					ps[i].style.display = 'block';
				}
			}
		}
		if(Dom.get('headerChoose')) 
			Dom.get('headerChoose').innerHTML = show_compact_headers;
	} else {
		var dh = Dom.get('dateHeader');
		if (dh != null) {
			dh.style.display = 'none';
		}
		var th = Dom.get('toHeader');
		if (th != null) {
			th.style.display = 'none';
		}
		var ch = Dom.get('ccHeader');
		if (ch != null) {
			ch.style.display = 'none';
		}
		if(Dom.get('headerChoose'))
			Dom.get('headerChoose').innerHTML = show_more_headers;
	}
	fixMsgViewIframeLayout();
}

function subjectJump(e) {
	keyCode=e.keyCode||e.which;
  	if (keyCode == '9') {
		try {
	        var inst = tinyMCE.selectedInstance;
	        inst.getWin().focus();
	        return false;
		} catch(r) {}
  	}
}

function registerAutoComplete(inp, div) {
	var myServer = "webmail/autocomplete.cl";
	myDataSource = new YAHOO.widget.DS_XHR(myServer, ["\n", "\t"]);
	myDataSource.responseType = YAHOO.widget.DS_XHR.TYPE_FLAT;
	myDataSource.maxCacheEntries = 0;
	myDataSource.queryMatchCase = false;
	
	myAutoComp = new YAHOO.widget.AutoComplete(inp,div, myDataSource); 
	myAutoComp.animVert = false;
	myAutoComp.delimChar = [",",";"];
	myAutoComp.animHoriz = false;
	myAutoComp.animSpeed = 0;
	myAutoComp.maxResultsDisplayed = 20;
	myAutoComp.queryDelay = 0.1;
	myAutoComp.minQueryLength = 2;
	// myAutoComp.useIFrame = true;
	myAutoComp.typeAhead = false;
	myAutoComp.allowBrowserAutocomplete = false;

	myAutoComp.formatResult = function(aResultItem, sQuery) { 
	    var sResult = aResultItem[0]; 
	    var sex = aResultItem[1]; 
	    if(sResult) {
			var img = 'images/contact-unknown-mini.png';
			if (sex == 'F') {
				img = 'images/contact-female-mini.png';
			} else if (sex == 'M') {
				img = 'images/contact-male-mini.png';
			}
	        return "<img src='" + img + "' style='margin-right:5px;float:left;width:14px;height:14px;'/>" + htmlSpecialChars(sResult);
	    } else {
	        return "";
	    }
	};
}


var uploadIframeCount = 0;
function addNewUpload() {
	show('attachmentstr');
	
	//get filename
	var f=Dom.get('inputfile').value;
	var pos=f.lastIndexOf('\\');
	if(pos>=0) f=f.substr(pos+1);
	pos=f.lastIndexOf('/');
	if(pos>=0) f=f.substr(pos+1);
	
	//add attachment to list
	var li=document.createElement("li");
	li.id="uploadli"+uploadIframeCount;
	var img=document.createElement("img");
	img.src="images/uploading.gif";
	li.appendChild(img);
	var sp=document.createElement("span");
	sp.innerHTML=f;
	sp.style.paddingRight=5;
	li.appendChild(sp);
	var a=document.createElement("a");
	a.href="javascript:removeAttach('"+f+"')";
	a.innerHTML=" "+txtRemove;
	a.style.color='#5A799E';
	a.style.display='none';
	li.appendChild(a);
	var list=Dom.get("composeAttachmentList");
	list.appendChild(li);

	var theFile = document.getElementById("inputfile"); 
	var fileParent = theFile.parentNode; 
	var theDiv = document.createElement('div'); 
	theDiv.style.display = 'none'; 
	theDiv.innerHTML = '<iframe id="uploadiframe'+uploadIframeCount+'" name="uploadiframe'+uploadIframeCount+'" src=""></iframe>' + 
	    '<form id="uploadform'+uploadIframeCount+'" target="uploadiframe'+uploadIframeCount+'" action="webmail/uploadAttachment.cl" enctype="multipart/form-data" method="post">' + 
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
	
	fixLayout();
}

function cancelMail() {
	showDialog('cancelComposeQuestion');
}

function clearPreview() {
	// if there is no message in the list show the list area
	var mailList = document.getElementById("mailList");
	
	var ps = mailList.getElementsByTagName("p");
	var count = 0;
	for (var i=0;i<ps.length;i++) {
		if (ps[i].style.display != 'none') {
			count++;
		}
	}

	if (count == 1) {
		Dom.get('mailBody').innerHTML = '<div class="title"><div class="buttons"><a href="#" class="toggle on">' + toggle_list_on + '</a><a href="#" class="toggle off">' + toggle_list_off + '</a></div><p><span>&nbsp;</span>&nbsp;</p><p><span>&nbsp;</span>&nbsp;</p></div>';

		var links = document.getElementsByTagName("a");
		var mailList = document.getElementById("mailList");
		if (links && mailList){
			for (var i=0; i<links.length; i++)
			if (links[i].className.indexOf("toggle") != -1){
				links[i].onclick = function() {
					if ((this.className.indexOf("on") != -1) && (mailList.parentNode.className.indexOf("full") != -1)){
						mailList.parentNode.className = mailList.parentNode.className.replace(new RegExp(" full"),"");
						mailList.style.display = "";
						}
					if ((this.className.indexOf("off") != -1) && !(mailList.parentNode.className.indexOf("full") != -1))
						mailList.parentNode.className += " full";
					fixLayout();
				}
			}
		}
	} else {
		Dom.get('mailBody').innerHTML = "";
	}
}

var folderActionsExpanded = false;
function showHideFolderActions() {
	if (!folderActionsExpanded) {
		Dom.get('folderActionsBtn').style.top = (getHeight(Dom.get('folders')) - 143) + "px";
		folderActionsExpanded = true;
		Dom.get('folderActions').style.top = (getHeight(Dom.get('folders')) - 143) + "px";
		Dom.get('folderActions').style.display = 'block';
	} else {
		Dom.get('folderActionsBtn').style.top = getHeight(Dom.get('folders')) + "px";
		folderActionsExpanded = false;
		Dom.get('folderActions').style.display = 'none';
	}
	reLocateFolderActions();
	arrangeFolderActions();
}

function reLocateFolderActions() {
	var folders = Dom.get('folders');
	try {
		if (!folderActionsExpanded) {
			Dom.get('folderActionsBtn').style.top = folders.scrollHeight + "px"
			Dom.get('folderActions').style.display = 'none';
		} else {
			Dom.get('folderActionsBtn').style.top = (folders.scrollHeight - 143) + "px";
			Dom.get('folderActions').style.top = (folders.scrollHeight - 143) + "px";
			Dom.get('folderActions').style.display = 'block';
		}
	} catch (e) {}
	
	if (folders.scrollHeight > folders.offsetHeight) {
		var ems = folders.getElementsByTagName('em');
		for (var i=0;i<ems.length;i++) {
			ems[i].style.marginRight = '9px';
		}
	} else {
		var ems = folders.getElementsByTagName('em');
		for (var i=0;i<ems.length;i++) {
			ems[i].style.marginRight = '0px';
		}
	}
}
 
function showCreateMailFolder() {
	showDialog('createMailFolder');
}

function showRenameMailFolder() {
	showDialog('renameMailFolder');
}

function showDeleteMailFolder() {
	showDialog('deleteMailFolderConfirm');
}

function showEmptyMailFolder() {
	showDialog('emptyMailFolderConfirm');
}

function fastEmail(str) {
	var params = null;
	var mailto = str;
	if (str.indexOf('?') > 0) {
		mailto = str.substr(0, str.indexOf('?'));
		params = str.substr(str.indexOf('?') + 1);
	}
	var subject = "";
	var body = "";
	if (params != null) {
		var paramArr = params.split("&");
		subject = paramArr["subject"];
		body = paramArr["body"];
		if (subject == null) {
			subject = "";
		}
		if (body == null) {
			body = "";
		}
	}
	
	clearComposeForm();
	Dom.get('to').value = mailto;
	Dom.get('subject').value = subject;
	
	layoutMail('compose');
	var eid = tinyMCE.getEditorId('composeBody');
	var te = Dom.get(eid);
	te.contentWindow.document.body.innerHTML = body;
	moveCursorPosition(0);
	Dom.get('subject').focus();
}

function hideSenderSaved() {
	var d = Dom.get('senderSavedInfo');
	d.style.display = "none";
}

function showSenderSaved() {
	var d = Dom.get('senderSavedInfo');
	d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
	d.style.top = "22px";
	d.style.display = "block";
	setTimeout(hideSenderSaved, 4000);
}

