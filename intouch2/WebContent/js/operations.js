var loggedIn = false;
var prefMailSound = 'yes';
var prefSignature = '';
var prefSignaturePos = 'top';
var prefSendReadReceipt = 'prompt';
var prefChatAwayMins = '15';
var prefChatSound = 'yes';

var latestImapIdleLength = 0;
function imapIdleCallback() {
	try {
	    if (req.readyState == 3) {
	    	if (req.responseText !== undefined) {
		    	eval(req.responseText.substr(latestImapIdleLength));
	    		latestImapIdleLength = req.responseText.length;
	    	}
	    }			
	
	} catch (e) {
		alert(e.message);
	}
}

function checkImapIdleMessages() {
	try {
		var url = "webmail/imapIdleCheck.service";
		if (window.XMLHttpRequest) {
			req = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}
		req.open("POST", url, true);
		req.onreadystatechange = imapIdleCallback;
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		req.send("folder=INBOX");
	} catch (e) {
		alert(e.message);
	}

	/*
	var url = "webmail/imapIdleCheck.service";
	var callback = {
	  success: 	function(o) {
	  		alert("success");
			if(o.responseText !== undefined) {
				eval(o.responseText);
			}
	  },

	  onprocess: function(o) {
			if(o.responseText !== undefined) {
		  		alert(o.responseText);
		  		eval(o.responseText);
			}
	  },

	  failure: 	function(o) {
		checkImapIdleMessages();
	  },
	  argument: [] /*,
	  timeout: 70000000 */
	//}
	// params = "folder=INBOX";
	// var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
}

function login() {
	var username = document.forms['loginForm'].username.value;
	var password = document.forms['loginForm'].password.value;
	var res = Dom.get('loginResult');

	if (username == null || username.length == 0 || password == null || password.length == 0) {
		res.style.color = 'red';
		res.innerHTML = invalid_login_info;
		return;
	}

	var paramData = "username=" + username + "&password=" + password;
	var url = "profiling/login.cl";
	new Rico.Effect.FadeTo('login', 0, 700, 10, {
		complete:function() {
			Dom.get('login').style.display = 'none';
			Dom.get('loginstatus').style.display = 'block';
			}
		});

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined){
				if (o.responseText != 'ok') {
					for(var i=0;i<5000;i++){}
					new Rico.Effect.FadeTo('login', 1, 700, 10, {
						complete:function() {
							res.style.color = 'red';
							if (o.responseText == 'system') {
								res.innerHTML = system_error;
							} else {
								res.innerHTML = login_invalid;
							}
							Dom.get('login').style.display = 'block';
							Dom.get('loginstatus').style.display = 'none';
						}
					});
				} else {
					loggedIn = true;
					
					Dom.get('loginstatus').style.display = 'none';
					// login is successful go on.
					location.href = "intouch.jsp";
				}
			}
		},
	  failure: 	function(o) {
			if (o.responseText !== undefined){
				res.style.color = 'red';
				res.innerHTML = system_error;
				Dom.get('loginstatus').style.display = 'none';
			}
		},
	  argument: []
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function refreshMail() {
	checkingMail = false;
	checkMail();
}

var checkingMail = false;
var unreadMessages = -1;
function checkUnreadMailCountFirstTime(noRepeat) {
	var url = "webmail/getUnreadCount.cl";
	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					var tmpUnreadMessages = -1;
					try {
						tmpUnreadMessages = parseInt(o.responseText);
					} catch (hj) {}
					if (tmpUnreadMessages != -1) {
						unreadMessages = tmpUnreadMessages;
					}
				} finally {
					checkingMail = false;
					if (noRepeat == null || noRepeat == false) {
						checkNewMailExists();
					}
				}
			}
	  },

	  failure: 	function(o) {
		checkNewMailExists();
	  },
	  argument: [],
	  timeout: 7000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function checkNewMailExists() {
	if (!checkingMail) {
		var url = "webmail/getUnreadCount.cl";
		checkingMail = true;
		var callback = {
		  success: 	function(o) {
				if(o.responseText !== undefined) {
					var tmpUnreadMessages = -1;
					try {
						checkingMail = false;
						
						try {
							tmpUnreadMessages = parseInt(o.responseText);
						} catch (hj) {}

						if (tmpUnreadMessages != -1 && tmpUnreadMessages > 0) {
							document.title = o.responseText + " " + unread +" - " + title;
							if (tmpUnreadMessages > unreadMessages && unreadMessages != -1) {
								showNewMessageIndicator();
								if (prefMailSound != 'no') {
									Playa.doPlay();
								}
								checkMail();
								//sometimes fixed header doesn't show up for some ridicilous reason. 
								if (Dom.get('mailtitleCloneDiv')) {
									Dom.get('mailtitleCloneDiv').style.display = 'block';
								}
								window.focus();
							} else {
								unreadMessages = tmpUnreadMessages;
							}
						} else {
							unreadMessages = -1;
							if (document.title.indexOf(reminder_txt) < 0) {
								document.title = title;
							}
						}
					} finally {
						window.setTimeout(checkNewMailExists, checkPeriod);
					}
					if (tmpUnreadMessages != -1) {
						unreadMessages = tmpUnreadMessages;
					}
				}
		  },
	
		  failure: 	function(o) {
		  	checkingMail = false;
			window.setTimeout(checkNewMailExists, checkPeriod);
		  },
		  argument: [],
		  timeout: checkPeriod - 1000
		}
		var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
	} else {
		window.setTimeout(checkNewMailExists, checkPeriod);
	}
}

function getCurrentFolder() {
	var folderName = "";
	if (selectedFolder == null || selectedFolder == '') {
		var title = Dom.get('mailtitle');
		if (title != null) {
//			var fid = title.getAttribute('folderid');
			folderName = title.getAttribute('foldername').substr(10);
			
//			if (fid != null && fid != '' && fid != 'null') {
//				folderName = fid;
//			}
		}
		if (folderName == null || folderName == 'null') {
			folderName = '';
		}
	} else {
		folderName = selectedFolder;
	}
	if (folderName == '') {
		folderName = "INBOX";
	}
	return folderName;
}

function getCurrentFolderDisplayName() {
	var folders = Dom.get('folders');
	var lis = folders.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			if (lis[i].className == 'active') {
				return lis[i].getAttribute('folderName');
			}
		}
	}
	//return "INBOX";
}

function findMailPByInnerHTML(str) {
	var list = Dom.get('mailList');
	var ps = list.getElementsByTagName('p');
	if (ps != null) {
		for (var i=0;i<ps.length;i++) {
			var tmp = ps[i].getElementsByTagName('span')[1].innerHTML + ps[i].getElementsByTagName('span')[2].innerHTML;
			if (tmp == str && ps[i].id.indexOf('Clone') < 0) {
				return ps[i];
			}
		}
	}
	return null;
}
function goPage(v){
	clearPreview();
	checkMail(null,null,null,null,v);
}
function preparePager() {
	var pageSize = 25;
	try {
		var pagerParams = Dom.get('pagerParams').innerHTML;
		var pagerParamsArr = pagerParams.split(':');
		var pageNo = parseInt(pagerParamsArr[0]);
		var pageCount = parseInt(pagerParamsArr[1]);
		var messageCount = parseInt(pagerParamsArr[2]);
		pageSize = parseInt(pagerParamsArr[3]);
		var startMessage = (pageNo-1)*pageSize+1;
	
		if(startMessage < 0) {
			startMessage = 0;	
		}
		var endMessage = startMessage+pageSize-1;
		if(endMessage > messageCount) {
			endMessage = messageCount;	
		}
		var html="<table cellpadding='0' cellspacing='0' border='0' align='center'><tr>";
		if(pageNo<2) {
			html+="<td class='passive'>&laquo;</td><td class='passive'>&lsaquo;</td>";	
		} else {
			html+="<td onclick='goPage(1)' class='active'>&laquo;</td><td onclick='goPage("+(pageNo-1)+")' class='active'>&lsaquo;</td>";	
		}
		html += "<td>" + startMessage + "&nbsp;-&nbsp;" + endMessage + "&nbsp;/&nbsp;" + messageCount + "</td>";
		if(pageNo>pageCount-1) {
			html+="<td class='passive'>&rsaquo;</td><td class='passive'>&raquo;</td>";	
		} else {
			html+="<td onclick='goPage("+(pageNo+1)+")' class='active'>&rsaquo;</td><td onclick='goPage("+pageCount+")' class='active'>&raquo;</td>";;	
		}
		html+="</tr></table>";
		Dom.get('pager').innerHTML = html;
	} catch (e) {}
}
function checkMail(sortItem, sortOrder, noFolders, preSelectId, pageNo) {
	if (!checkingMail) {
		checkingMail = true;
		
		// first determine which e-mail was clicked and save their content HTML in array and vars
		var clicked = new Array();
		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
		var clickedCounter = 0;
		var clickedAndActive;
		if (ps != null) {
			for (var i=0;i<ps.length;i++) {
				if (ps[i].clicked && ps[i].id.indexOf('Clone') < 0) {
					clicked[clickedCounter] = ps[i].getElementsByTagName('span')[1].innerHTML + ps[i].getElementsByTagName('span')[2].innerHTML;
					clickedCounter++;
					if (ps[i].lastClicked) {
						clickedAndActive = ps[i].getElementsByTagName('span')[1].innerHTML + ps[i].getElementsByTagName('span')[2].innerHTML;
					}
				}
			}
		}
		
		// no go make the operations.fetch the headers.
		var folderName = getCurrentFolder();
		var cfdn = getCurrentFolderDisplayName();
		if(undefined != cfdn) {
			if (cfdn.length > 13) {
				cfdn = cfdn.substr(0,13) + "...";
			}
			Dom.get('inboxTitle1').innerHTML = cfdn;
		}
		var paramData = "folder=" + myEncode(folderName);
		
		if (sortItem != null) {
			if (sortOrder == null || sortOrder == '') {
				sortOrder = 'desc';
			} else if (sortOrder == 'desc') {
				sortOrder = 'asc';
			} else if (sortOrder == 'asc') {
				sortOrder = 'desc';
			}
			paramData += "&mailSort=" + sortItem + "&mailSortDirection=" + sortOrder;
		} 
		if(pageNo != null) paramData += "&pageNo=" + pageNo;
		else paramData += "&pageNo=1";
		var url = "webmail/listHeaders.cl";
		Dom.get('kitwait').style.display = 'block';
	
		var callback = {
		  success: 	function(o) {
				if(o.responseText !== undefined) {
					try {
						var list = Dom.get('mailList');
						list.innerHTML = o.responseText;
						preparePager();
						prepareMailListHeader();
						initPng();
						mailDragInit();
						Dom.get('kitwait').style.display = 'none';

						// re-select the previously clicked ones
						for (var k=0;k<clicked.length;k++) {
							var tmpClicked = findMailPByInnerHTML(clicked[k]);
							if (tmpClicked != null) {
								mailListChangeStyle(tmpClicked,C_LIGHTBLUE,C_BLACK,true,false,1);
							}
						}
						if (clickedAndActive != null&&1==clicked.length) {
							var obj = findMailPByInnerHTML(clickedAndActive);
							if (obj != null) {
								mailListChangeStyle(obj,C_ROYALBLUE,C_WHITE,true,true,1);
								lastClickedId = obj.id.substr(4);
							}
						}
						
						if (preSelectId != null) {
							var obj = Dom.get('mail' + preSelectId);
							if (obj != null) {
								/*
								obj.style.backgroundColor = C_ROYALBLUE;
								obj.style.color = C_WHITE;
								obj.clicked = true;
								obj.getElementsByTagName('span')[0].style.backgroundImage = 'url(images/checked.gif)';
								obj.lastClicked = true;
								lastClickedId = preSelectId;
								*/
								try {mailListClick(null, obj.getElementsByTagName('span')[1], true) } catch (k) {};
							}
						}
		
						// parallelly fetch folders again
						if (noFolders == null || noFolders == false) {
							fetchFolders();
							Dom.get('kitwait').style.display = 'none';
						}
						checkingMail = false;
						// unreadMessages = -1;
					} catch(e) {
						// do nothing sier
					}
				}
		  },
	
		  failure: 	function(o) {
			Dom.get('kitwait').style.display = 'none';
			// showDialog("defaultError");
			checkingMail = false;
		  },
		  argument: [],
		  timeout: 300000
		}
		var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
	}
}

function fetchFolders() {
	var paramData = "folder=" + myEncode(selectedFolder);

	var url = "webmail/getFolders.cl";
	Dom.get('kitwait').style.display = 'block';

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				var list = Dom.get('mailFolders');
				list.innerHTML = o.responseText;
				if(""!=selectedFolder) {
					try {
						var cfdn = Dom.get('mailFolder'+selectedFolder).getAttribute('foldername');
						if (cfdn != null) {
							if (cfdn.length > 13) {
								cfdn = cfdn.substr(0,13) + "...";
							}
							Dom.get('inboxTitle1').innerHTML=cfdn;
						}
					} catch (up) {}
				}
				
				mailDragFolderInit();
				reLocateFolderActions();
				Dom.get('kitwait').style.display = 'none';
			}
	  },

	  failure: 	function(o) {
		Dom.get('kitwait').style.display = 'none';
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function showMail(id) {
	Dom.get('kitwait').style.display = 'block';
	var folderName = getCurrentFolder();

	var paramData = "folder=" + folderName + "&msgId=" + id;
	var url = "webmail/fetchMail.cl";
	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined){
				Dom.get('kitwait').style.display = 'none';
				Dom.get('mailBody').innerHTML = o.responseText;

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

				showToggleHeader();
				fixMsgViewIframeLayout();
				showAskSendReadReceiptDialog();
			}
		},
	  failure: 	function(o) {
		Dom.get('kitwait').style.display = 'none';
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function deleteMail() {
	checkingMail = true;
	var ids = new Array();
	var counter = 0;
	var lastId = 0;
	
	// first determine which e-mail are clicked and how many of them were unread
	var list = Dom.get('mailList');
	var ps = list.getElementsByTagName('p');
	var unreadCounter = 0;
	if (ps != null) {
		for (var i=0;i<ps.length;i++) {
			if (ps[i].clicked && ps[i].id.indexOf('Clone') < 0) {
				ids[counter] = ps[i].id.substr(4);
				counter++;
				if (lastId == 0) {
					lastId = i;
				}
				if (ps[i].style.fontWeight == 'bold') {
					unreadCounter++;
				}
			}
		}
	}
	
	// now re-organize the window title. because some mail are deleted and they may be indicated on the window title as unread
	unreadMessages = unreadMessages - unreadCounter;
	if (unreadMessages != -1 && unreadMessages > 0) {
		document.title = unreadMessages + " " + unread +" - " + title;
	} else {
		document.title = title;
		unreadMessages = 0;
	}

	// prepare the paramData whilst removing the e-mail from the list. Please note that CiT uses the optimistic approach while deleting mail
	// to increase the speed on the user interace, usability and for simplicity. If an error occurs while deleting mail, mail may be invisible 
	// on the browser but in normal cases it shouldn't.
	var paramData = "folder=" + myEncode(getCurrentFolder()) + "&ids=";
	var ie7Holder = Dom.get('ie7Holder');
	var ieHolder = Dom.get('mailtitleCloneDiv');
	for (var i=0;i<ids.length;i++) {
		paramData += "_" + ids[i];
		ie7Holder.removeChild(Dom.get('mail' + ids[i]));
		ieHolder.removeChild(Dom.get('mail' + ids[i] + 'Clone'));
	}

	// maillist size is most probably changed so let's take the size of again and then determine the lastId variable. This variable
	// helps us display the next message in the list when a mail is deleted. 
	ps = ie7Holder.getElementsByTagName('p');
	var newLength = ps.length;
	var newMsgNo = -1;
	if (lastId >= newLength && newLength > 1) {
		lastId = newLength - 1;
	}
	if (lastId >= 1) {
		try {
			newMsgNo = ps[lastId].id.substr(4);
		} catch (g) {
//			alert(g.message);
		}
	}

	// now organize the folder list's unread count. some of the deleted e-mail might have not been read at all so
	// they may be visible on the folder list as unread. simply subtract the unreadCounter from the value on the folder list
	var folders = Dom.get('folders');
	var lis = folders.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			if (lis[i].className == 'active') {
				var uc = lis[i].getElementsByTagName('b')[0];
				if (uc != null) {
					var iuc = parseInt(uc.innerHTML);
					iuc = iuc - unreadCounter;
					if (iuc <= 0) {
						lis[i].getElementsByTagName('em')[0].innerHTML = '';
					} else {
						uc.innerHTML = iuc + "";
					}
				}
			}
		}
	}

	// if there are messages show the next one.
	clearPreview();
	if (newMsgNo > 0) {
		var tmpObj = Dom.get('mail' + newMsgNo);
		tmpObj.style.backgroundColor = C_ROYALBLUE;
		tmpObj.style.color = C_WHITE;
		tmpObj.clicked = true;
		tmpObj.getElementsByTagName('span')[0].style.backgroundImage = 'url(images/checked.gif)';
		tmpObj.lastClicked = true;
		lastClickedId = newMsgNo;
		tmpObj.style.fontWeight = 'normal';
		showMail(newMsgNo);
	}
	// it is time to delete the mail from server	
	var url = "webmail/deleteMail.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == 'ok') {
					checkingMail = false;
					checkMailButtons();
					checkUnreadMailCountFirstTime(true);
					fetchFolders();
				}
			}
	  },

	  failure: 	function(o) {
		checkingMail = false;
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
	
}

function moveMails(moveArr, sourceName, targetName) {
	if (sourceName == targetName) {
		return false;
	}
	//showDialog('moveMailInfo');
	showClickIndicator();
	var ids = new Array();
	var counter = 0;
	
	var paramData = "source=" + myEncode(sourceName) + "&target=" + myEncode(targetName) + "&ids=";
	for (var i=0;i<moveArr.length;i++) {
		paramData += "_" + moveArr[i];
	}
	
	var url = "webmail/moveMails.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == 'ok') {
					//window.setTimeout(hideMoveMail, 1000);
					hideClickIndicator();
					checkUnreadMailCountFirstTime(true);
					fetchFolders();
					return true;
				}
			}
	  },

	  failure: 	function(o) {
		hideClickIndicator();
		showDialog("defaultError");
		return false;
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function hideMoveMail() {
	hideDialog('moveMailInfo');
}

function saveNewMailFolder() {
	YAHOO.claros.container.createNewMailFolder.hide();
}

function sendMailWait() {
	// check to see if there are currently any active uploads
	var attMail = parent.document.getElementById('composeAttachmentList');
	var lis = attMail.getElementsByTagName("li");

	for (var i=0;i<lis.length;i++) {
		var li = lis[i];

		if (li.getElementsByTagName("img")[0].src.indexOf("images/uploading.gif") > 0) {
			window.setTimeout(sendMailWait, 1000);
			return false;
		}
	}
	actualSendMail();
}
function fixCommas(v){
	if(v==null||v=="") return "";
	var ret="";
	var a1=v.split(";");
	for(var i=0;i<a1.length;i++){
		var a2=a1[i].split(",");
		for(var j=0;j<a2.length;j++){
			var e = trim(a2[j]);
			if(e.length>0) ret+=e+", ";
		}	
	}
	return ret;
}
function sendMail() {
	var frm = document.forms['composeForm'];
	var to = frm.to.value = fixCommas(frm.to.value);
	var cc = frm.cc.value = fixCommas(frm.cc.value);
	var bcc = frm.bcc.value = fixCommas(frm.bcc.value);
	var subject = trim(frm.subject.value);

	if (to.length == 0 && cc.length == 0 && bcc.length == 0) {
		showDialog('messageEmptyRecipientError');
		return;
	}
	
	if (subject.length == 0) {
		showDialog('subjectEmptyQuestion');
	} else {
		sendMail2();
	}
}

function sendMail2() {
	showDialog('sendMailInfo');
	sendMailWait();
}

var sendObj;
function actualSendMail() {
	var eid = tinyMCE.getEditorId('composeBody');
	var te = Dom.get(eid);
	var mybody = te.contentWindow.document.body.innerHTML;

	var paramData = "from=" + myEncode(Dom.get('from').value) 
					+ "&to=" + myEncode(Dom.get('to').value) 
					+ "&cc=" + myEncode(Dom.get('cc').value) 
					+ "&bcc=" + myEncode(Dom.get('bcc').value) 
					+ "&subject=" + myEncode(Dom.get('subject').value) 
					+ "&body=" + myEncode(mybody)
					+ "&priority=" + Dom.get('priority').value
					+ "&sensitivity=" + Dom.get('sensitivity').value;
	if(Dom.get('requestReceiptNotification').checked)
		paramData += "&requestReceiptNotification=1";					
	
	var url = "webmail/sendMail.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					hideDialog('sendMailInfo');
					
					var d = Dom.get('sendResultIndicator');
					d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
					d.style.top = "22px";
					d.style.display = "block";
					setTimeout(hideSendResultIndicator, 8000);
					mailSent = true;
					getContacts();
					clearComposeForm();	
					layoutMail('inbox');
				} else {
					hideDialog('sendMailInfo');
					showDialog("defaultError");
				}
			}
	  },

	  failure: 	function(o) {
		hideDialog('sendMailInfo');
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 450000
	}
	sendObj = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function cancelSendMail() {
	try {
		YAHOO.util.Connect.abort(sendObj);
		hideDialog('sendMailInfo');
	} catch (e) {}
}

function clearComposeForm() {
	Dom.get('to').value = "";
	Dom.get('cc').value = "";
	Dom.get('bcc').value = "";
	Dom.get('subject').value = "";

	try {
        var inst = tinyMCE.selectedInstance;
		inst.contentWindow.document.body.innerHTML = "<br>";
		initialComposeSize = inst.contentWindow.document.body.innerHTML.length;
	} catch (e) {
	}
	Dom.get('uploader').innerHTML="";
	Dom.get('composeAttachmentList').innerHTML = "";
	Dom.get('totalSize').innerHTML = "0";
//	hide('bcctr');
	hide('attachmentstr');
	Dom.get('priority').value = '3';
	Dom.get('sensitivity').value = '1';
	Dom.get('requestReceiptNotification').checked = false;

	var url = "webmail/deleteAllAttachments.cl";
	var callback = {
	  success: 	function(o) {
	  	fixLayout();
	  },
	  failure: 	function(o) {
	  	fixLayout();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);

	mailSent = false;
}

var mailSent = false;
var initialComposeSize = 4;
 
function newMail() {
	mailSent = false;
	layoutMail('compose');
	
	var mybody = "";
	try {
		var eid = tinyMCE.getEditorId('composeBody');
		var te = Dom.get(eid);
		mybody = te.contentWindow.document.body.innerHTML;
	} catch (e) {}
	
	var frm = document.forms['composeForm'];
	var to = trim(frm.to.value);
	var cc = trim(frm.cc.value);
	var bcc = trim(frm.bcc.value);
	var subject = trim(frm.subject.value);
	
	var attMail = Dom.get('composeAttachmentList');
	var lis = attMail.getElementsByTagName("li");
	var actCount = 0;
	for (var i=0;i<lis.length;i++) {
		var li = lis[i];
		if (li.style.display != 'none') { 
			actCount++;
		}
	}

	frm.to.focus();

	if (!mailSent && (to.length != 0 || cc.length != 0 || bcc.length != 0 || mybody.length > initialComposeSize || actCount > 0)) {
		showDialog('messageDirtyWarn');
	} else {
		clearComposeForm();
		var eid = tinyMCE.getEditorId('composeBody');
		var te = Dom.get(eid);
		var html = "<br/>" + prefSignature;
		if(te.contentWindow.document.body)
			te.contentWindow.document.body.innerHTML = html;
	}
}

function getMessageBodyText(from, date) {
	var paramData = "partid=-1&dl=false&modify=false";
	var url = "webmail/dumpPart.service";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				layoutMail('compose');
				var eid = tinyMCE.getEditorId('composeBody');
				var te = Dom.get(eid);
				var html = "<br/><br/>On " + date + " " + from + " wrote<br/><p><blockquote>" + nl2br(o.responseText) + "</blockquote></p>";
				if (prefSignaturePos == null || prefSignaturePos == 'null' || prefSignaturePos == 'top') {
					html = "<br/>" + prefSignature + html;
				} else {
					html = html + "<br/>" + prefSignature;
				}
				te.contentWindow.document.body.innerHTML = html;
				te.focus();
				moveCursorPosition(0);
				hideClickIndicator();
			}
	  },

	  failure: 	function(o) {
	  	showDialog('defaultError');
		hideClickIndicator();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function getMessageText(messageType) {
	var url = "webmail/getHeaderInfo.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseXML != null) {
					var from = getFirstValByTagName(o.responseXML, 'from');
					var date = getFirstValByTagName(o.responseXML, 'date');
					var subject = getFirstValByTagName(o.responseXML, 'subject');
					var recipients = getFirstValByTagName(o.responseXML, 'recipients');
					var replyTo = getFirstValByTagName(o.responseXML, 'replyTo');

					if (messageType == 'reply') {
						if (replyTo != null && trim(replyTo) !='null' && trim(replyTo).length > 0) {
							Dom.get('to').value = replyTo;
						} else {
							Dom.get('to').value = from;
						}
						if (!new RegExp(re + "[ :]","gi").test(subject)) {
							Dom.get('subject').value = re + subject;
						} else {
							Dom.get('subject').value = subject;
						}
						getMessageBodyText(from, date);
					} else if (messageType == 'replyall') {
						Dom.get('to').value = recipients;
						if (!new RegExp(re + "[ :]","gi").test(subject)) {
							Dom.get('subject').value = re + subject;
						} else {
							Dom.get('subject').value = subject;
						}
						getMessageBodyText(from, date);
					} else if (messageType == 'forward') {
						Dom.get('to').value = "";
						if (!new RegExp(re + "[ :]","gi").test(subject)) {
							Dom.get('subject').value = fwd + subject;
						} else {
							Dom.get('subject').value = subject;
						}
						getMessageBodyText(from, date);
					}
				}
			}
	  },

	  failure: 	function(o) {
	  	showDialog('defaultError');
		hideClickIndicator();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function replyMail() {
	showClickIndicator();
	clearComposeForm();
	getMessageText('reply');
}

function replyAllMail() {
	showClickIndicator();
	clearComposeForm();
	getMessageText('replyall');
}

function forwardMail() {
	showClickIndicator();
	clearComposeForm();

	// attach the mail from the displayed one.
	var url = "webmail/attForwardArrange.cl";
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
			getMessageText('forward');
			Dom.get('to').focus();
		}
	  },
	  failure: 	function(o) {
	  	fixLayout();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function saveAsDraft() {
	// check to see if there are active uploads
	var attMail = parent.document.getElementById('composeAttachmentList');
	var lis = attMail.getElementsByTagName("li");

	for (var i=0;i<lis.length;i++) {
		var li = lis[i];

		if (li.getElementsByTagName("img")[0].src.indexOf("images/uploading.gif") > 0) {
			showDialog('activeUploadsWarn');
			return;
		}
	}
	
	// saving it.
	var eid = tinyMCE.getEditorId('composeBody');
	var te = Dom.get(eid);
	var mybody = te.contentWindow.document.body.innerHTML;

	var paramData = "from=" + myEncode(Dom.get('from').value) + "&to=" + myEncode(Dom.get('to').value) + "&cc=" + myEncode(Dom.get('cc').value) + "&bcc=" + myEncode(Dom.get('bcc').value) + "&subject=" + myEncode(Dom.get('subject').value) + "&body=" + myEncode(mybody);
	
	var url = "webmail/saveDraft.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					var d = Dom.get('mailStoredDraftInfo');
					d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
					d.style.top = "22px";
					d.style.display = "block";
					setTimeout(hideMailStoredDraftInfo, 4000);
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

function hideMailStoredDraftInfo() {
	var d = Dom.get('mailStoredDraftInfo');
	d.style.display = "none";
}

function removeAttach(fileName) {
	
	var attMail = Dom.get('composeAttachmentList');
	var lis = attMail.getElementsByTagName("li");
	
	for (var i=0;i<lis.length;i++) {
		var li = lis[i];

		if (li.innerHTML.indexOf(fileName) >= 0) {
			li.style.display = "none";
			
			var actCount = 0;
			if (lis.length > 1) {
				for (var j=0;j<lis.length;j++) {
					if (lis[j].style.display != 'none') {
						actCount++;
					}
				}
			}
			if (actCount == 0) {
				Dom.get('attachmentstr').style.display = 'none';
			}
		}
	}

	// remove it from the server
	var paramData = "f=" + myEncode(fileName);

	var url = "webmail/deleteAttachment.cl";
	var callback = {
	  success: 	function(o) {
		if(o.responseText !== undefined) {
	  		fixLayout();
		}
	  },
	  failure: 	function(o) {
	  	fixLayout();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
		
}

function showHeaders() {
	showClickIndicator();

	var url = "webmail/getAllHeaders.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				Dom.get('showHeaderBox').innerHTML = o.responseText;
				showDialog('showHeadersInfo');
				hideClickIndicator();
			}
	  },

	  failure: 	function(o) {
	  	showDialog('defaultError');
		hideClickIndicator();
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function createMailFolder() {
	var paramData = "folder=" + myEncode(Dom.get('newFolderName').value);
	var url = "webmail/createFolder.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					Dom.get('newFolderName').value = "";
					hideDialog('createMailFolder');
				} else {
				  	showDialog('defaultError');
				}
				fetchFolders();
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

function renameMailFolder() {
	var of=myEncode(Dom.get('folderActionsId').value);
	var nf=myEncode(trim(Dom.get('renameFolderName').value));
	var paramData = "old=" + of + "&newName=" + nf;
	var url = "webmail/renameFolder.cl";
	if(0==nf.length){
		hideDialog('renameMailFolder');
		return;
	}
	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText.substr(0,2) == "ok") {
					Dom.get('renameFolderName').value = "";
					hideDialog('renameMailFolder');
					selectedFolder=o.responseText.substr(3);
					fetchFolders();
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

function deleteMailFolder() {
	var paramData = "fid=" + myEncode(Dom.get('folderActionsId').value);
	var url = "webmail/deleteFolder.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					Dom.get('deleteFolderName').value = "";
					hideDialog('deleteMailFolderConfirm');
				} else {
				  	showDialog('defaultError');
				}
				selectInbox();
				fetchFolders();
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

function emptyMailFolder() {
	var paramData = "fid=" + myEncode(Dom.get('folderActionsId').value);
	var url = "webmail/emptyFolder.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					Dom.get('emptyFolderName').value = "";
					hideDialog('emptyMailFolderConfirm');
				} else {
				  	showDialog('defaultError');
				}
				checkMail();
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

function savePreferences() {
	var prefFullName = safeEncode(Dom.get('prefFullName').value);
	var prefEmailAddress = safeEncode(Dom.get('prefEmailAddress').value);
	var prefReplyTo = safeEncode(Dom.get('prefReplyTo').value);
	var prefMailSound = Dom.get('prefMailSound').value;
	// var prefSpamAnalysis = Dom.get('prefSpamAnalysis').value;
	var prefSaveSent = Dom.get('prefSaveSent').value;
	var prefSignature = safeEncode(nl2br(Dom.get('prefSignature').value));
	var prefSignaturePos = Dom.get('prefSignaturePos').value;
	var prefSendReadReceipt = Dom.get('prefSendReadReceipt').value;
	// var prefSafeContacts = Dom.get('prefSafeContacts').value;
	var prefSaveSentContacts = Dom.get('prefSaveSentContacts').value;
	var prefDisplayType = Dom.get('prefDisplayType').value;
	var prefChatAwayMins = safeEncode(Dom.get('prefChatAwayMins').value);
	var prefChatSound = Dom.get('prefChatSound').value;
	var prefNewsUrl = safeEncode(Dom.get('prefNewsUrl').value);
	
	var paramData = "fullName=" + prefFullName + 
					"&emailAddress=" + prefEmailAddress +
					"&replyTo=" + prefReplyTo + 
					"&mailSound=" + prefMailSound + 
					"&saveSent=" + prefSaveSent + 
					"&signature=" + prefSignature + 
					"&signaturePos=" + prefSignaturePos + 
					"&sendReadReceipt=" + prefSendReadReceipt + 
					"&saveSentContacts=" + prefSaveSentContacts + 
					"&displayType=" + prefDisplayType + 
					"&chatAwayMins=" + prefChatAwayMins + 
					"&chatSound=" + prefChatSound + 
					"&newsUrl=" + prefNewsUrl;
	var url = "preferences/savePreferences.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				loadPreferences();
				hidePreferences();
			}
	  },

	  failure: 	function(o) {
		hideDialog('preferences');
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function loadPreferences() {
	var url = "preferences/loadPreferences.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				
				try {
					var name = getFirstCleanValByTagName(o.responseXML, 'fullName');
					var email = getFirstCleanValByTagName(o.responseXML, 'emailAddress');
					var replyTo = getFirstCleanValByTagName(o.responseXML, 'replyTo');
					prefMailSound = getFirstCleanValByTagName(o.responseXML, 'mailSound');
					var saveSent = getFirstCleanValByTagName(o.responseXML, 'saveSent');
					prefSignature  = getFirstCleanValByTagName(o.responseXML, 'signature');
					prefSignaturePos = getFirstCleanValByTagName(o.responseXML, 'signaturePos');
					prefSendReadReceipt = getFirstCleanValByTagName(o.responseXML, 'sendReadReceipt');
					var saveSentContacts = getFirstCleanValByTagName(o.responseXML, 'saveSentContacts');
					var displayType = getFirstCleanValByTagName(o.responseXML, 'displayType');
					prefChatAwayMins = getFirstCleanValByTagName(o.responseXML, 'chatAwayMins');
					prefChatSound = getFirstCleanValByTagName(o.responseXML, 'chatSound');
					var newsUrl = getFirstCleanValByTagName(o.responseXML, 'newsUrl');

					if (name != null || name != "") {
						Dom.get('from').value = name + " <" + email + ">";
					} else {
						Dom.get('from').value = email;
					}
					Dom.get('welcomeName').innerHTML = name;
					Dom.get('chatFullName').innerHTML = name;
					Dom.get('prefFullName').value = name;
					Dom.get('prefEmailAddress').value = email;
					Dom.get('prefReplyTo').value = replyTo;
					Dom.get('prefMailSound').value = prefMailSound;
					Dom.get('prefSaveSent').value = saveSent;
					Dom.get('prefSignature').value = br2nl(prefSignature);
					Dom.get('prefSignaturePos').value = prefSignaturePos;
					Dom.get('prefSendReadReceipt').value = prefSendReadReceipt;
					Dom.get('prefSaveSentContacts').value = saveSentContacts;
					Dom.get('prefDisplayType').value = displayType;
					Dom.get('prefChatAwayMins').value = prefChatAwayMins;
					Dom.get('prefChatSound').value = prefChatSound;
					Dom.get('prefNewsUrl').value = newsUrl;
					getContacts();
					getRssNews();
				} catch (e) {}
				
				if (trim(Dom.get('prefFullName').value) == '' || Dom.get('prefEmailAddress').value == '') {
					showPreferences(true);
					Dom.get('prefCancel').style.display = 'none';
				}
			}
	  },

	  failure: 	function(o) {
		hideDialog('preferences');
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function saveSender() {
	var url = "webmail/saveSender.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == 'ok') {
					showSenderSaved();
					getContacts();
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
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function prepareMailListHeader() {
	try {
		Dom.get('ieHolder').removeChild('mailtitleCloneDiv');
	} catch (e) {}
	var tit1 = Dom.get('mailtitle');

	var lst1 = Dom.get('ie7Holder');
	var lst = lst1.cloneNode(true);
	lst.style.display = 'none';
	tit1.style.display = 'none';
//	tit1.style.visibility = 'hidden';
	
	lst.style.position = 'absolute';
	lst.style.top = "124px";
	lst.style.left = "203px";
	lst.style.height = '20px';
	lst.id = 'mailtitleCloneDiv';
	lst.style.overflow = 'hidden';
	
	var ps = lst.getElementsByTagName('p');
	if (ps != null) {
		for (var u=0;u<ps.length;u++) {
			if (ps[u].id != null) {
				ps[u].id = ps[u].id + "Clone";
			}
		}
	}
	
	Dom.get('ie7Holder').parentNode.appendChild(lst);
	lst.style.width = lst1.offsetWidth + "px";
	lst.style.display = '';
}

function moveCursorPosition(cursorPos){
	var o = Dom.get(tinyMCE.getEditorId('composeBody')).contentWindow.document.body;
    if(o.setSelectionRange){
        o.setSelectionRange(cursorPos,cursorPos);
    }else if(o.createTextRange){
        var r=o.createTextRange();
        r.collapse(true);
        r.moveEnd('character',cursorPos);
        r.moveStart('character',cursorPos);
        r.select();
    }
}
function sendReadReceiptMail(){
	hideDialog('askSendReadReceipt');
	var paramData="";
	var url = "webmail/sendReadReceiptMail.cl";
	if(Dom.get('mailViewTitle')){
		paramData = "from=" + myEncode(myEncode(Dom.get('from').value)) 
					+ "&to=" + myEncode(Dom.get('mailViewTitle').getAttribute('notificationEmail')) 
					+ "&subject=" + myEncode(Dom.get('subjectHeader').getElementsByTagName('span')[1].innerHTML) 
					+ "&date=" + myEncode(Dom.get('dateHeader').getElementsByTagName('span')[1].innerHTML);
	}			
	var callback = {
		success:function(o){
			if(o.responseText !== undefined) {
				if (o.responseText == "ok") {
					var d = Dom.get('sendReceiptResultIndicator');
					d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
					d.style.top = "22px";
					d.style.display = "block";
					setTimeout(hideSendReceiptResultIndicator, 4000);
				}
			}
		},
		argument: [],
		timeout: 90000
	}
	if(paramData!="") YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}
function showAskSendReadReceiptDialog(){
	var m=Dom.get('mailViewTitle');
	if(m){
		if(m.getAttribute('notificationEmail')!=''){
			if(prefSendReadReceipt=='never'){
			}
			else if(prefSendReadReceipt=='always'){
				 sendReadReceiptMail();
			}
			else{
				showDialog('askSendReadReceipt');
			}
		}
	}
}