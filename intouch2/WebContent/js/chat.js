// JavaScript Document
var windowwidth;
var windowheight;
var netscape = false;
var ie = false;
var infoHide = true;
var infoFor = null;
var gtalkClicked = false;
var jabberClicked = false;
var userStatus = new Array();
var logged = false;
var listenerThread;
var contactsThread;

//	if (!window.event) event=arguments.callee.caller.arguments[0];
//	var obj=event.srcElement || event.currentTarget || event.target;

function showInfoWin(obj) {
	if (infoFor == null || infoHide == true || (obj.getAttribute('user') != infoFor)) {
		infoHide = false;
		if (animations) {
			new Rico.Effect.FadeTo('infowin', .2, 0, 1, {} );
		}
		var infoWin = Dom.get('infowin');

		infoWin.getElementsByTagName('img')[0].src = "images/chat/avatar_load.gif";

		infoWin.style.visibility = 'visible';
		if (animations) {
			new Rico.Effect.FadeTo('infowin', 1, 400, 10, {} );
		}
		
		var user = obj.getAttribute('user');
		var username = obj.getAttribute('username');
		var presence = obj.getAttribute('presence');
		var msg = obj.getAttribute('msg');
		if (msg == null || msg == 'null' || msg == "") msg = "<i>" + no_status_text + "</i>";
		
		var nlcount = 0;
		var tmp = "";
		for (i=0; i<msg.length; i++) {
			var c = msg.charAt(i);
			if (c != '\n') {
				nlcount++;
			}
			if (nlcount == 32) {
				tmp = tmp + "<br />" + c;
				nlcount = 0;
			} else {
				tmp = tmp + c;
			}
		}
		msg = tmp;
		
		infoFor = user;
	
		var contactWin = Dom.get('chatcontacts');
		var contactWinLeft = contactWin.offsetLeft;
		var infoLeft = contactWinLeft + contactWin.offsetWidth - 20;
//		if (infoLeft + 199 > windowwidth) {
		if (infoLeft - 370 > 0) {
			infoLeft = contactWinLeft - 180;
		}
	
		var xy = getAbsolutePosition(obj);
		var infoTop = xy['y'];
		if ((infoLeft + "").indexOf('px') < 0) {
			infoLeft += 'px';
		}
		infoTop += 'px';
		
		infoWin.style.left = infoLeft;
		
		infoWin.style.top = infoTop;
		infoWin.user = user;
		infoWin.username = username;
		var infoUserName = Dom.get('infotexttitle');

		var presTxt;
		if (presence == 'available') {
			presTxt = tavailable;
		} else if (presence == 'away') {
			presTxt = taway;
		} else if (presence == 'chat') {
			presTxt = tchatting;
		} else if (presence == 'disturb') {
			presTxt = tdont_disturb;
		} else if (presence == 'extended_away') {
			presTxt = textended_away;
		} else if (presence == 'invisible') {
			presTxt = tinvisible;
		} else {
			presTxt = toffline;
		}

		if (user.indexOf('%') > 0) {
			// umutgokbayrak2%hotmail.com (umutgokbayrak2%hotmail.com@msn.claros.org)
			var posAt = user.indexOf('@');
			var posPer = user.indexOf('%');
			user = user.substr(0, posPer) + "@" + user.substr(posPer + 1, posAt - posPer - 1);
		}
		
		infoUserName.innerHTML = username + " (" + user + ") - " + presTxt;
		
		var infoMsg = Dom.get('infotextstatus');
		infoMsg.innerHTML = msg;
		
		infoWin.style.visibility = 'visible';
		infoWin.style.display = '';
		infoWin.style.zIndex = 4000;
	
		var divs = document.getElementsByTagName('div');
		for (i=0; i<divs.length; i++) {
			var div = divs[i];
			if (div.id == 'contact') {
				unhoverContact(div);
			}
		}

		if (presence == 'available' || presence == 'away' || presence == 'chat' || presence == 'disturb' || presence == 'extended_away') {
			infoWin.getElementsByTagName('img')[0].src = "chat/avatar.cl?u=" + user;
		} else {
			infoWin.getElementsByTagName('img')[0].src = "images/chat/spacer.gif";
		}
		overInfoWin();	
	}
	obj.className = "contactHover";
	infoHide = false;
}

function hideInfoWinFade() {
	if (!infoHide) {
		if (animations) {
			new Rico.Effect.FadeTo('infowin', 0, 400, 10, {complete:function() {Dom.get('infowin').style.display = 'none'}});
		} else {
			Dom.get('infowin').style.display = 'none';
		}
		infoHide = true;
		Dom.get('contactsscroll').style.overflow = 'auto';
	}
}

function unhoverContact(obj) {
	obj.className = null;
}

function overInfoWin() {
		var infoWin = Dom.get('infowin');
		infoWin.style.visibility = 'visible';
		var cont = Dom.get('chatcontacts');
		var divs = cont.getElementsByTagName('div');
		for (i=0; i<divs.length; i++) {
			var div = divs[i];
			if (div.id == 'contact' && div.getAttribute('user') != null && div.getAttribute('user') == infoWin.user) {
				div.className = 'contactHover';
			} else if (div.id.indexOf('chatWin') < 0 && div.className != 'curtain') {
				unhoverContact(div);
			}
		}
}

function outInfoWin() {
	hideInfoWinFade();
}

function initContacts() {
	var contacts = Dom.get('chatcontacts');
	contacts.style.left = (windowwidth - 204) + "px";
}

function initChatLogin() {
	initWinSize();
	var login = document.getElementById('chatlogin');
	if (login) {
		login.style.height = (mainHeight - 10) + "px";
		
		var logcent = document.getElementById('logincenter');
		
		var lc = new YAHOO.util.DD("logincenter");
		lc.setHandleElId("loginhandle");
	
		var myTop = (windowheight / 2) - 200;
		if (myTop < 110) {
			myTop = 110;
		}
		var myLeft = (windowwidth / 2) - 214;
		if (myLeft < 0) {
			myLeft = 0;
		}
		logcent.style.top = myTop + "px";
		logcent.style.left = myLeft + "px";
		login.style.display = 'block';
	}
}

function f_clientWidth() {
	return f_filterResults (
		window.innerWidth ? window.innerWidth : 0,
		document.documentElement ? document.documentElement.clientWidth : 0,
		document.body ? document.body.clientWidth : 0
	);
}
function f_clientHeight() {
	return f_filterResults (
		window.innerHeight ? window.innerHeight : 0,
		document.documentElement ? document.documentElement.clientHeight : 0,
		document.body ? document.body.clientHeight : 0
	);
}
function f_scrollLeft() {
	return f_filterResults (
		window.pageXOffset ? window.pageXOffset : 0,
		document.documentElement ? document.documentElement.scrollLeft : 0,
		document.body ? document.body.scrollLeft : 0
	);
}
function f_scrollTop() {
	return f_filterResults (
		window.pageYOffset ? window.pageYOffset : 0,
		document.documentElement ? document.documentElement.scrollTop : 0,
		document.body ? document.body.scrollTop : 0
	);
}
function f_filterResults(n_win, n_docel, n_body) {
	var n_result = n_win ? n_win : 0;
	if (n_docel && (!n_result || (n_result > n_docel)))
		n_result = n_docel;
	return n_body && (!n_result || (n_result > n_body)) ? n_body : n_result;
}

function initWinSize() {
	if (self.innerHeight) {
		windowwidth = self.innerWidth;
		windowheight = self.innerHeight;
		ie = false;
		netscape = true;
	} else if(document.layers || (document.getElementById&&!document.all)) {
		windowwidth=window.innerWidth;
		windowheight=window.innerHeight;
		ie = false;
		netscape = true;
	} else if (document.documentElement) {
		windowwidth = document.documentElement.clientWidth;
		windowheight = document.documentElement.clientHeight;
		ie = true;
		netscape = false;
	} else if(document.all) {
		windowwidth=document.body.clientWidth;
		windowheight=document.body.clientHeight;
		ie = true;
		netscape = false;
	}
}

function initToolbar() {
	initWinSize();
	var toolbar = Dom.get('toolbar');
	if (toolbar) {
		toolbar.style.left = '15px';
		toolbar.style.width = (windowwidth - 30) + "px";
	
		if (windowheight - 73 < 480) {
			toolbar.style.top = "480px";
		} else {
			toolbar.style.top = (windowheight - 73) + "px";
		}
		if (logged) {
			toolbar.style.visibility = 'visible';
			toolbar.style.display = 'block';
			Dom.get('avatarme').src = 'images/chat/avatar.png';
			Dom.get('avatarme').src = 'chat/avatar.cl';
		} else {
			toolbar.style.visibility = 'hidden';
			toolbar.style.display = 'none';
		}
		var objDiv = Dom.get('consoleText');
		objDiv.scrollTop = objDiv.scrollHeight;
	}
}

function addConsoleText(txt) {
	var objDiv = Dom.get('consoleText');
	objDiv.innerHTML += "- " + txt + "<br />";
	objDiv.scrollTop = objDiv.scrollHeight;
}

function scrollChatWinMsgBtm(obj) {
	obj.scrollTop = obj.scrollHeight;
}	

function openChat(obj, usr, name, initVal, inout, dbId) {
	var presence;
	if (usr == null) {
		if (obj != null) {
			usr = obj.getAttribute('user');
			name = obj.getAttribute('username');
			presence = obj.getAttribute('presence');
		}
		if (usr == null) {
			var inf = Dom.get('infowin');
			usr = inf.user;
			name = inf.username;
		}
		hideInfoWinFade();
	}
	
	var actWin = null;
	if (windows[hex_md5(usr)] != null) {
		actWin = Dom.get('chatWin' + usr);
		actWin.style.display = 'block';
	} else {
		var cw = new ChatWin(usr, userMe, usr, name, 'online');
		actWin = cw.init();
	}

	// trying to determine the true online status image while first opening the chat window	
	if (presence) {
		var imgs = actWin.getElementsByTagName('img');
		if (imgs != null) {
			for (var i=0;i<imgs.length;i++) {
				if (imgs[i].id = 'statusindicator') {
					if (presence == "available") {
						color = "green";
					} else if (presence == "away") {
						color = "orange";
					} else if (presence == "chat") {
						color = "green";
					} else if (presence == "disturb") {
						color = "red";
					} else if (presence == "extended_away") {
						color = "red";
					} else if (presence == "invisible") {
						color = "orange";
					}
					imgs[i].src = "images/chat/indicators/" + color + ".gif"
					break;
				}
			}
		}
	}
	
	if (initVal != null) {
		if (inout == 'in') {
			chatMarkAsRead(actWin, name, initVal, inout, dbId);
		} else {
			saySomething(actWin, txtMe, initVal, inout);
		}
		takeAttention(actWin);
	} else {
		moveOnTop(actWin);
	}
}

function contactsHandler(txt, xmlDoc) {
	try {
		var obj;
		if (txt != null && txt.length != 0) {
			obj = Dom.get('contactsscroll');
			obj.innerHTML = txt;
		}
		// time to set statuses
		var divs = obj.getElementsByTagName('div');
		for (i=0; i<divs.length; i++) {
			var div = divs[i];
			if (div.id == 'contact') {
				var usr = div.getAttribute('user');
				var name = div.getAttribute('username');
				var status = div.getAttribute('presence');
//				setStatusChatWin(usr, name, status);
			}
		}
	} catch (e) {
//		alert(e.message);
	}
}

function setStatusChatWin(usr, name, status) {
	var txtstatus;
	if (status == 'available') {
		txtstatus = tavailable;
		imgstatus = "green";
	} else if (status == 'away') {
		txtstatus = taway;
		imgstatus = "orange";
	} else if (status == 'chat') {
		txtstatus = tchatting;
		imgstatus = "green";
	} else if (status == 'disturb') {
		txtstatus = tdont_disturb;
		imgstatus = "red";
	} else if (status == 'extended_away') {
		txtstatus = textended_away;
		imgstatus = "orange";
	} else if (status == 'invisible') {
		txtstatus = tinvisible;
		imgstatus = "gray";
	} else {
		txtstatus = toffline;
		imgstatus = "gray";
	}

	var toSay = name + " " + changed_status + ": " + txtstatus;
	var chatwin = Dom.get('chatWin' + usr);
	if (chatwin != null) {
		var doSay = false;
		if (userStatus[usr] == null) {
			doSay = true;
		} else {
			if (status != userStatus[usr]) {
				doSay = true;
			}
		}
		
		if (doSay) {
			var imgstatus;
			var tds = chatwin.getElementsByTagName('div');
			for (i=0;i<tds.length;i++) {
				if (tds[i].id == 'myText') {
					var div = tds[i];
					var txt = div.innerHTML;

					txt = "<p><i style=\"color:#666666\">" + toSay + "</i></p>";
					div.innerHTML += txt;
					div.parentNode.scrollTop = div.parentNode.scrollHeight;

					break;
				}
			}

			var imgs = chatwin.getElementsByTagName('img');
			for (i=0;i<imgs.length;i++) {
				if (imgs[i].id == 'statusindicator') {
					imgs[i].src = "images/chat/indicators/" + imgstatus + ".gif";
					break;
				}
			}
		}
	}

	var doSay2 = false;
	if (userStatus[usr] == null) {
	} else {
		if (status != userStatus[usr]) {
			doSay2 = true;
		}
	}
	if (doSay2) {
		var console = Dom.get('consoleText');
		console.innerHTML += " - " + toSay + " " + formatDate(new Date(), "hh:mm dd/MM/yy") + "<br/>";
		console.scrollTop = console.scrollHeight;
	}
	userStatus[usr] = status;
}

function showAskWin() {
	var ask = Dom.get('askWin')
	var tool = Dom.get('toolbar')

	var left = (windowwidth - 241) ;
	
	if (windowheight - 73 < 480) {
		ask.style.top = (480 - 135) + "px";
	} else {
		ask.style.top = (windowheight - 73 - 135) + "px";
	}
	
	ask.style.left = left + "px";
	ask.style.display  = "block";

	if (animations) {
		new Rico.Effect.FadeTo('askWin', 1, 700, 10, {complete:function() {}});
	}
}

function selectGtalk() {
	var gtalk = Dom.get('gtalk');
	var jabber = Dom.get('jabber');
	gtalk.src = 'images/chat/buttons/gtalk_clicked.gif';
	jabber.src = 'images/chat/buttons/jabber.gif';
	gtalkClicked = true;
	jabberClicked = false;
	document.forms['loginform'].server.value = 'Google Talk';
	document.forms['loginform'].server.readOnly = true;
}

function selectJabber() {
	var gtalk = Dom.get('gtalk');
	var jabber = Dom.get('jabber');
	
	gtalk.src = 'images/chat/buttons/gtalk.gif';
	jabber.src = 'images/chat/buttons/jabber_clicked.gif';
	gtalkClicked = false;
	jabberClicked = true;
	document.forms['loginform'].server.value = '';
	document.forms['loginform'].server.readOnly = false;
}

function preloadChatImages() {
	MM_preloadImages('images/chat/buttons/gtalk_clicked.gif', 
					 'images/chat/buttons/jabber_clicked.gif',
					 'images/chat/bg/askwin_bg.gif', 
					 'images/chat/bg/chatwin_bg.gif', 
					 'images/chat/bg/chatwin_bottom.gif', 
					 'images/chat/bg/chatwin_top.gif', 
					 'images/chat/bg/chatwin_top_disactive.gif', 
					 'images/chat/bg/chatwin_top_notice.gif', 
					 'images/chat/bg/contact_item_click_bg.gif', 
					 'images/chat/bg/contact_item_hover_bg.gif', 
					 'images/chat/bg/contact_win_bg.gif', 
					 'images/chat/bg/contact_win_bottom.gif', 
					 'images/chat/bg/contact_win_top.gif', 
					 'images/chat/bg/info_bg.gif', 
					 'images/chat/bg/info_bg_bottom.gif', 
					 'images/chat/bg/info_bg_top.gif', 
					 'images/chat/bg/login_bg.gif', 
					 'images/chat/bg/login_bottom.gif', 
					 'images/chat/bg/login_bottom.png', 
					 'images/chat/bg/login_top.gif', 
					 'images/chat/bg/logo.gif', 
					 'images/chat/bg/main.gif', 
					 'images/chat/bg/toolbar_bg.gif', 
					 'images/chat/bg/toolbar_left.gif', 
					 'images/chat/bg/toolbar_right.gif', 
					 'images/chat/bg/toolbar_seperator.gif', 
					 'images/chat/buttons/add_buddy.gif', 
					 'images/chat/buttons/add_contact.gif', 
					 'images/chat/buttons/allow.gif', 
					 'images/chat/buttons/arr_down.gif', 
					 'images/chat/buttons/arr_up.gif', 
					 'images/chat/buttons/chat.gif', 
					 'images/chat/buttons/deny.gif', 
					 'images/chat/buttons/gtalk.gif', 
					 'images/chat/buttons/gtalk_clicked.gif', 
					 'images/chat/buttons/jabber.gif', 
					 'images/chat/buttons/jabber_clicked.gif', 
					 'images/chat/buttons/login.gif', 
					 'images/chat/buttons/remove.gif', 
					 'images/chat/buttons/save.gif',
					 'images/chat/avatar_load.gif',
					 'images/chat/ico/claros.gif',
					 'images/chat/ico/logout.gif', 
					 'images/chat/ico/preferences.gif',
					 'images/chat/indicators/gray.gif', 
					 'images/chat/indicators/green.gif', 
					 'images/chat/indicators/orange.gif',
					 'images/chat/emotions/angry_smile.gif', 
					 'images/chat/emotions/confused_smile.gif',
					 'images/chat/emotions/cry_smile.gif',
					 'images/chat/emotions/loading_mini.gif',
					 'images/chat/emotions/omg_smile.gif',
					 'images/chat/emotions/regular_smile.gif',
					 'images/chat/emotions/sad_smile.gif',
					 'images/chat/emotions/teeth_smile.gif',
					 'images/chat/emotions/tongue_smile.gif',
					 'images/chat/emotions/what_smile.gif',
					 'images/chat/emotions/wink_smile.gif',
					 'images/chat/emotions/shades_smile.gif',
					 'images/chat/emotions/angel_smile.gif',
					 'images/chat/emotions/dont_tell_smile.gif',
					 'images/chat/emotions/nerd_smile.gif',
					 'images/chat/emotions/dont_know_smile.gif',
					 'images/chat/emotions/bat.gif',
					 'images/chat/emotions/heart.gif',
					 'images/chat/emotions/rose.gif',
					 'images/chat/emotions/red_smile.gif',
					 'images/chat/indicators/red.gif');
}

function callContacts() {
	var url = "chat/contacts.cl";
	var callback = {
	  success: 	function(o) {
		if(o.responseText !== undefined) {
			if (o.responseText != null && o.responseText != '') {
				try {
					var obj;
					if (o.responseText != null && o.responseText.length != 0) {
						obj = Dom.get('contactsscroll');
						obj.innerHTML = o.responseText;
					}
					// time to set statuses
					var divs = obj.getElementsByTagName('div');
					for (i=0; i<divs.length; i++) {
						var div = divs[i];
						if (div.id == 'contact') {
							var usr = div.getAttribute('user');
							var name = div.getAttribute('username');
							var status = div.getAttribute('presence');
							setStatusChatWin(usr, name, status);
						}
					}
				} catch (e) {
			//		alert(e.message);
				}
			}
			contactsThread = window.setTimeout("callContacts()", 8000);
	  	}
	  },
	  failure: 	function(o) {
		contactsThread = window.setTimeout("callContacts()", 8000);
	  },
	  argument: [],
	  timeout: 7000
	}

	YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function callListener() {
	var url = "chat/listener.cl";
	var callback = {
	  success: 	function(o) {
		if(o.responseText !== undefined) {
			if (o.responseText != null && o.responseText != '') {
				try {
					eval(o.responseText);
				} catch (e) {
//					alert(e.message);
				}
			}
			listenerThread = window.setTimeout("callListener()", 5000);
	  	}
	  },
	  failure: 	function(o) {
		listenerThread = window.setTimeout("callListener()", 5000);
	  },
	  argument: [],
	  timeout: 3000
	}

	YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function trim(str) {
	if (str == null) return null;
	return str.replace(/^\s*|\s*$/g,"");
}

function dragStop(e) {
	if (!e) {
		e = window.event;
	}
	YAHOO.util.DragDropMgr.stopDrag(e);
	document.onmousemove = null;
	document.onmouseup = null;
}

function showChatLogout() {
	showDialog('logoutChat');
}
