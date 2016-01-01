// JavaScript Document
var userMe;
var fullName;
var awayTimeout = 15;
var animations = true;
var soundAlert = true;
var statusTimer = 0;
var currStatus = "available";
var autosetAway = false;
function loginCatchEnter(e) {
	var code;
	if (!e) {
		code = e.keyCode;
	} else {
		code = e.which;
		if (code == null) {
			code = e.keyCode;
		}
	}
	if (code == 13) { // ENTER
		claroslogin();
		return false;
	}
	return true;
}
function claroslogin() {
	Dom.get("logintext").innerHTML = "";
	Dom.get("loginResult").innerHTML = "";

	var username = document.forms["loginform"].username.value;
	var password = document.forms["loginform"].password.value;
	var server = document.forms["loginform"].server.value;
	if (username == null || username.length == 0 || password == null || password.length == 0) {
		return;
	}
	Dom.get("loginprogress").style.display = "";
	userMe = "";
	var paramData = "username=" + username + "&password=" + password + "&server=" + server;
	var url = "chat/authenticate.cl";
	var pText = Dom.get("logintext");
	var callback = {success:function (o) {
		Dom.get("loginprogress").style.display = "none";
		if (o.responseText !== undefined) {
			Dom.get("logintext").innerHTML = "";
			if (o.responseText != "ok") {
				var res = Dom.get("loginResult");
				res.style.color = "red";
				res.innerHTML = invalid_login_info;
				pText = "&nbsp;";
				return;
			}
			logged = true;
			var userMe = username;
						
			// login is successful go on.
			Dom.get("loginResult").innerHTML = "";

			initContacts();
			if (animations) {
				new Rico.Effect.SizeAndPosition("logincenter", -500, null, 0, 0, 500, 10, {});
			}
			var lgn = Dom.get("chatlogin");
			if (animations) {
				new Rico.Effect.FadeTo("chatlogin", 0, 300, 5, {complete:function () {
					lgn.style.display = "none";
				}});
				new Rico.Effect.FadeTo("chatcontacts", 1, 300, 5, {});
			} else {
				lgn.style.display = "none";
				new Rico.Effect.FadeTo("chatcontacts", 1, 0, 1, {});
			}
			callListener();
			callContacts();
			loadStatus();
			initToolbar();
			var console = Dom.get("consoleText");
			console.innerHTML = succesfully_logged_in + formatDate(new Date(), "hh:mm dd/MM/yy") + "<br/>";
			console.scrollTop = console.scrollHeight;
			// var lc = new YAHOO.util.DD("chatcontacts");
			// lc.setHandleElId("contactshandle");
			Dom.get("contactsscroll").style.overflow = "auto";
			Dom.get("avatarme").src = "images/chat/avatar.png";
			Dom.get("avatarme").src = "chat/avatar.cl";
			pText = "&nbsp;";
		}
	}, failure:function (o) {
		if (o.responseText !== undefined) {
			res.style.color = "red";
			Dom.get("loginResult").innerHTML = system_error;
		}
	}, argument:[]};
	Dom.get("logintext").innerHTML = logging_in_wait;
	var request = YAHOO.util.Connect.asyncRequest("POST", url, callback, paramData);
}

function changeStatus() {
	loadStatus();
	showDialog('changeStatus');
}

function loadStatus() {
	var status = currStatus;
	var imgstatus;
	var txtstatus;
	if (status == "available") {
		txtstatus = tavailable;
		imgstatus = "green";
	} else {
		if (status == "away") {
			txtstatus = taway;
			imgstatus = "orange";
		} else {
			if (status == "chat") {
				txtstatus = tchatting;
				imgstatus = "green";
			} else {
				if (status == "disturb") {
					txtstatus = tdont_disturb;
					imgstatus = "red";
				} else {
					if (status == "extended_away") {
						txtstatus = textended_away;
						imgstatus = "orange";
					} else {
						if (status == "invisible") {
							txtstatus = tinvisible;
							imgstatus = "gray";
						} else {
							txtstatus = toffline;
							imgstatus = "gray";
						}
					}
				}
			}
		}
	}
	Dom.get("userStatus").innerHTML = txtstatus;
	Dom.get("statusindic").src = "images/chat/indicators/" + imgstatus + ".gif";
	Dom.get("statusnow").innerHTML = txtstatus;
	Dom.get("avatarme").src = "images/chat/avatar.png";
	Dom.get("avatarme").src = "chat/avatar.cl";
	statusTimer = 0;
}
function statusTimerIncrement() {
	if (logged == true) {
		statusTimer++;
	}
	window.setTimeout("statusTimerIncrement()", 1000);
}
function arrangeStatus() {
	if (currStatus == "away" && autosetAway) {
		saveStatus("available");
		autosetAway = false;
	} else {
		statusTimer = 0;
	}
}
function statusListener() {
	if (logged == true) {
		awayTimeout = parseInt(prefChatAwayMins);
		if (currStatus == "available" && statusTimer > awayTimeout * 60) {
			saveStatus("away");
			autosetAway = true;
		}
	}
	window.setTimeout("statusListener()", 10000);
}
function saveStatus(newStatus, msg) {
	if (newStatus == null) {
		var msg = Dom.get("newstatusmsg").value;
		var newStatus = Dom.get("newstatus").value;
	}
	var url = "chat/status.cl";
	var params = "act=save&newstat=" + newStatus;
	if (msg != null) {
		params += "&newstatmsg=" + msg;
	}
	var callback = {success:function (o) {
		if (o.responseText !== undefined) {
			statusTimer = 0;
			currStatus = newStatus;
			loadStatus();
		}
	}, failure:function (o) {
	}, argument:[], timeout:20000};
	YAHOO.util.Connect.asyncRequest("POST", url, callback, params);
}

function doLogoutChat() {
	var url = "chat/logout.cl";
	var callback = {
	  success: 	function(o) {
	  	if (o.responseText !== undefined) {
			window.clearTimeout(listenerThread);
			window.clearTimeout(contactsThread);
			new Rico.Effect.FadeTo("chatcontacts", 0, 100, 1, {});
			new Rico.Effect.FadeTo("chatlogin", 1, 100, 1, {});
			Dom.get('toolbar').style.visibility = 'hidden';
			logged = false;
			Dom.get('contactsscroll').innerHTML = "";
			Dom.get('chatPassword').value = "";
			initChatLogin();
		}
	  },
	  failure: 	function(o) {},
	  argument: []
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function doRemoveBuddy() {
	var infowin = Dom.get("infowin");
	var buddy = infowin.user;

	buddy = encodeURI(buddy);

	var url = "chat/roaster.cl";
	var params = "act=remove&removeBuddy=" + buddy;
	var callback = {success:function (o) {
	}, failure:function (o) {
	}, argument:[], timeout:20000};
	YAHOO.util.Connect.asyncRequest("POST", url, callback, params);
	closeCurtain("removeUser");
}

function removeBuddy() {
	var infowin = Dom.get("infowin");
	var user = infowin.user;

	if (user.indexOf('%') > 0) {
		// umutgokbayrak2%hotmail.com (umutgokbayrak2%hotmail.com@msn.claros.org)
		var posAt = user.indexOf('@');
		var posPer = user.indexOf('%');
		user = user.substr(0, posPer) + "@" + user.substr(posPer + 1, posAt - posPer - 1);
	}

	Dom.get('removeUserName').innerHTML = user;
	showDialog('removeBuddy');
}

function doAddContact() {
	var url = "chat/roaster.cl";
	var buddy = Dom.get('newBuddy').value;
	if (buddy == null || trim(buddy).length == 0) {
		alert(enter_valid_buddy);
		return;
	}
	var params = "act=save&newBuddy=" + buddy;
	var callback = {success:function (o) {
	}, failure:function (o) {
	}, argument:[], timeout:20000};
	YAHOO.util.Connect.asyncRequest("POST", url, callback, params);
}

function chatAddContact() {
	showDialog('addBuddy');
}

function chatPreferences() {
	showPreferences(false, 'Chat');
}

/*
function loadChatPreferences() {
	var url = "chat/preferences.cl?act=load";
	var callback = {success:function (o) {
		if (o.responseText !== undefined) {
			if (o.responseXML != null) {
				var fullname = o.responseXML.getElementsByTagName("fullname");
				var txtfullname = fullname[0].firstChild.nodeValue;
				fullName = txtfullname;
//				alert(Dom.get('fullName').innerHTML);
				Dom.get("fullName").innerHTML = txtfullname;
				Dom.get("preffullname").value = txtfullname;
				var preferences = o.responseXML.getElementsByTagName("preference");
				if (preferences != null) {
					for (i = 0; i < preferences.length; i++) {
						var key = preferences[i].getElementsByTagName("key")[0].firstChild.nodeValue;
						var val = preferences[i].getElementsByTagName("value")[0].firstChild.nodeValue;
						if (key == "awayTimeout") {
							awayTimeout = parseInt(val);
							Dom.get("mins").innerHTML = awayTimeout;
						}
						if (key == "animations") {
							if (val == "true") {
								document.forms["chatPreferencesForm"].anims[0].checked = true;
								animations = true;
							} else {
								document.forms["chatPreferencesForm"].anims[1].checked = true;
								animations = false;
							}
						}
						if (key == "soundAlert") {
							if (val == "true") {
								document.forms["chatPreferencesForm"].sndalert[0].checked = true;
								soundAlert = true;
							} else {
								document.forms["chatPreferencesForm"].sndalert[1].checked = true;
								soundAlert = false;
							}
						}
					}
				} else {
					Dom.get("mins").innerHTML = awayTimeout;
					document.forms["chatPreferencesForm"].anims[0].checked = true;
					animations = true;
					document.forms["chatPreferencesForm"].sndalert[0].checked = true;
					soundAlert = true;
				}
			}
		}
	}, failure:function (o) {
		Dom.get("mins").innerHTML = awayTimeout;
		document.forms["chatPreferencesForm"].anims[0].checked = true;
		animations = true;
		document.forms["chatPreferencesForm"].sndalert[0].checked = true;
		soundAlert = true;
	}, argument:[], timeout:3000};
	YAHOO.util.Connect.asyncRequest("POST", url, callback);
}
function saveChatPreferences() {
	var url = "chat/preferences.cl";
	var a1 = (document.forms["chatPreferencesForm"].anims[0].checked == true) ? true : false;
	var a2 = (document.forms["chatPreferencesForm"].sndalert[0].checked == true) ? true : false;
	var s = Dom.get("mins").innerHTML;
	var n = Dom.get("preffullname").value;
	var params = "act=save&fullName=" + n + "&awayTimeout=" + s + "&animations=" + a1 + "&soundAlert=" + a2;
	var callback = {success:function (o) {
		if (o.responseText !== undefined) {
			loadChatPreferences();
		}
	}, failure:function (o) {
	}, argument:[], timeout:20000};
	YAHOO.util.Connect.asyncRequest("POST", url, callback, params);
	closeCurtain("chatPreferences");
}
function closeCurtain(id) {
	var curtain = Dom.get(id);
	if (animations) {
		new Rico.Effect.FadeTo(id, 0, 300, 5, {complete:function () {
			curtain.style.display = "none";
		}});
	} else {
		curtain.style.display = "none";
	}
}
function prefArrUp() {
	var mins = Dom.get("mins");
	var iMin = parseInt(mins.innerHTML);
	if (iMin < 180) {
		iMin++;
		mins.innerHTML = iMin + "";
	}
}
function prefArrDown() {
	var mins = Dom.get("mins");
	var iMin = parseInt(mins.innerHTML);
	if (iMin > 1) {
		iMin--;
		mins.innerHTML = iMin + "";
	}
}
*/

function chatMarkAsRead(actWin, name, initVal, inout, dbId) {
	var paramData = "id=" + dbId;

	var url = "chat/markAsRead.cl";
	var callback = {
	  success: 	function(o) {
		if (o.responseText != null && o.responseText != '') {
			saySomething(actWin, name, initVal, inout);
		}
	  },
	  failure: 	function(o) {},
	  argument: [],
	  timeout: 3000
	}
	YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}
