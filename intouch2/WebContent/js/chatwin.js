// JavaScript Document
var windows = new Array();
var maxZIndex = 2000;
var nextTop = 90;
var nextLeft = 15;
var rowCount = 0;

function ChatWin(user, username, chatWithUser, chatWithUsername, status) {
	this.user = user;
	this.username = username;
	this.chatWithUser = chatWithUser;
	this.chatWithUsername = chatWithUsername;
	this.status = status;
	this.win = null;
}

ChatWin.prototype.init = function() {
	var cw = Dom.get('chatWin');
	try {
		var winname = this.chatWithUser;

		var cl = cw.cloneNode(true);
		cl.id = "chatWin" + winname;

		cl.style.left = nextLeft + "px";
		cl.style.top = nextTop + "px";

		if (nextLeft + 40 < windowwidth - 290) {
			if (nextTop + 23 > windowwidth - 700) {
				nextLeft = 200;
				rowCount++;
				nextTop = rowCount * 23;
				// screen is full start from beginning.
				if (nextTop + 23 > windowwidth - 700) {
					nextTop = 20;
					nextLeft = 200;
				}
			} else {
				nextLeft = nextLeft + 40;
			}
		} else {
			nextLeft = 200;
			rowCount++;
			nextTop = rowCount * 23;
		}
		nextTop = nextTop + 23;
		
		cl.style.visibility = 'visible';
		cl.style.display = "block";
		cl.setAttribute('user', this.user);
		var bd = document.body;
		bd.appendChild(cl);

		var tds = cl.getElementsByTagName('div');
		for (i=0;i<tds.length;i++) {
			if (tds[i].id == 'chatScroll') {
				tds[i].scrollTop = tds[i].scrollHeight;
			}
			
			if (tds[i].id == 'chatWinTitleBar') {
				tds[i].innerHTML = this.chatWithUsername;
			}
		}

		if (animations) {
			new Rico.Effect.FadeTo("chatWin" + winname, 1, 400, 5, {} );
		} else {
			new Rico.Effect.FadeTo("chatWin" + winname, 1, 0, 1, {} );
		}
		
		var nodes = cl.getElementsByTagName('div');
		for (i=0; i<nodes.length; i++) {
			if (nodes[i].id == 'handle') {
				nodes[i].id = "handle" + this.winname;
				var lc = new YAHOO.util.DD("chatWin" + winname);
				lc.setHandleElId(nodes[i].id);
				Dom.get("chatWin" + winname).setAttribute('dd', lc);
				break;
			}
		}
		infoHide == true;
		this.win = cl;
		
		windows[hex_md5(winname)] = this;
		return cl;
	} catch (e) {
//		alert(e.message);
	}
};

function takeAttention(o) {
	if (o != null && o.getElementsByTagName('td')[0].className != 'handleActive') {
		o.getElementsByTagName('td')[0].className = 'handleNotice';
	}
}

function moveOnTop(o) {
	var divs = document.getElementsByTagName('div');
	for (i=0; i<divs.length; i++) {
		var div = divs[i];
		if (div.id.indexOf('chatWin') == 0 && div.id != 'chatWinTitleBar') {
			var mytd = div.getElementsByTagName('td')[0];
			if (mytd.className != 'handleNotice') {
				mytd.className = 'handle';
			}
		}
	}
	maxZIndex++;
	if (o != null) {
		o.style.zIndex = maxZIndex;
		o.getElementsByTagName('td')[0].className = 'handleActive';
	}
	infoHide == true;
}

function hideChatWin(obj) {
	obj.style.display = 'none';
	obj.getElementsByTagName('td')[0].className = 'handle';
}

function handleKeys(e, obj) {
	var code;
	if (!e) {
		code = e.keyCode;
	} else {
		code = e.which;
		if (code == null) {
			code = e.keyCode;
		}
	}
	
	if (code == 27) { // ESC
		hideChatWin(obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode);
	} else if (code == 13 || code == 11) { // ENTER
		if (!ie) {
			try { e.preventDefault(); } catch (e) {}
			try { e.returnValue = false; } catch (e) {}
			try { e.stopPropagation(); } catch (e) {}
			try { e.cancelBubble = true; } catch (e) {}
		}

		var val = obj.value;
		var valOrig = trim(val);

		val = myEncode(val);
		if (val != null && val.length > 0) {
			var tmpUser = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.getAttribute('user');
			tmpUser = myEncode(tmpUser);
			
			var params = "user=" + tmpUser + "&msg=" + val;
			
			var url = "chat/sender.cl";
			var callback = {
			  success: 	function(o) {},
			  failure: 	function(o) {},
			  timeout: 30000,
			  argument: []
			}
			var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
	
			// append the text
			var chatwin = obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;
			saySomething(chatwin, txtMe, valOrig, "out");
			obj.value = "";
		}
		return false;
	}
	obj.scrollTop = obj.scrollHeight;

	infoHide == true;
	return true;
}

function saySomething(chatwin, from, val, inout) {
	var myC=[
		["<","&lt;"],
		[">","&gt;"],
		["\"","&quot;"],
		["&lt;clarosbr&gt;","<br/>"],
		[":[)]","<img src='images/chat/emotions/regular_smile.gif' />"],
		[":-[)]", "<img src='images/chat/emotions/regular_smile.gif' />"],
		[":-O", "<img src='images/chat/emotions/omg_smile.gif' />"],
		[":-o", "<img src='images/chat/emotions/omg_smile.gif' />"],
		[":O", "<img src='images/chat/emotions/omg_smile.gif' />"],
		[":o", "<img src='images/chat/emotions/omg_smile.gif' />"],
		[";-[)]", "<img src='images/chat/emotions/wink_smile.gif' />"],
		[";[)]", "<img src='images/chat/emotions/wink_smile.gif' />"],
		[":S", "<img src='images/chat/emotions/confused_smile.gif' />"],
		[":s", "<img src='images/chat/emotions/confused_smile.gif' />"],
		[":'[(]", "<img src='images/chat/emotions/cry_smile.gif' />"],
		[":D", "<img src='images/chat/emotions/teeth_smile.gif' />"],
		[":d", "<img src='images/chat/emotions/teeth_smile.gif' />"],
		[":P", "<img src='images/chat/emotions/tongue_smile.gif' />"],
		[":-P", "<img src='images/chat/emotions/tongue_smile.gif' />"],
		[":p", "<img src='images/chat/emotions/tongue_smile.gif' />"],
		[":[(]", "<img src='images/chat/emotions/sad_smile.gif' />"],
		[":[|]", "<img src='images/chat/emotions/what_smile.gif' />"],
		[":-@", "<img src='images/chat/emotions/angry_smile.gif' />"],
		[":@", "<img src='images/chat/emotions/angry_smile.gif' />"],
		["[(]H[)]", "<img src='images/chat/emotions/shades_smile.gif' />"],
		["[(]h[)]", "<img src='images/chat/emotions/shades_smile.gif' />"],
		["[(]A[)]", "<img src='images/chat/emotions/angel_smile.gif' />"],
		["[(]a[)]", "<img src='images/chat/emotions/angel_smile.gif' />"],
		[":-#", "<img src='images/chat/emotions/dont_tell_smile.gif' />"],
		["8-[|]", "<img src='images/chat/emotions/nerd_smile.gif' />"],
		["[(]L[)]", "<img src='images/chat/emotions/heart.gif' />"],
		["[(]l[)]", "<img src='images/chat/emotions/heart.gif' />"],
		["[(]F[)]", "<img src='images/chat/emotions/rose.gif' />"],
		["[(]f[)]", "<img src='images/chat/emotions/rose.gif' />"],
		[":-$", "<img src='images/chat/emotions/red_smile.gif' />"],
		[":$", "<img src='images/chat/emotions/red_smile.gif' />"]
	];

	for(var i=0; i<myC.length; i++){
		val = val.replace(new RegExp(myC[i][0],'g'),myC[i][1]);
	}

	var tds = chatwin.getElementsByTagName('div');
	for (i=0;i<tds.length;i++) {
		if (tds[i].id == 'myText') {
			var win = tds[i];
			var txt = win.innerHTML;
			if (inout == 'in') {
				txt = txt + "<p><span id='chatWinTo'>" + from + ": </span> " + val + "</p>";
				if (prefChatSound == null || prefChatSound == 'yes') {
					Playa2.doPlay();
				}
			} else {
				txt = txt + "<p><span id='chatWinFrom'>" + from + ": </span> " + val + "</p>";
			}
			tds[i].innerHTML = txt;
			tds[i].style.height = "152px";
			
			tds[i].parentNode.scrollTop = tds[i].parentNode.scrollHeight;
		}
	}
}

function tidyDiv(obj) {
	var div = obj.parentNode.parentNode.parentNode.parentNode;
	if (div.offsetHeight > 150) {
		var tmps = div.getElementsByTagName('div');
		for (i=0;i<tmps.length;i++) {
			if (tmps[i].id == 'chatScroll') {
				tmps[i].style.height = "0px";
			}
		}
		div.style.height = "22px";
		Dom.get('chatcontacts').focus();
	} else {
		div.style.height = "242px";

		var tmps = div.getElementsByTagName('div');
		for (i=0;i<tmps.length;i++) {
			if (tmps[i].id == 'chatScroll') {
				tmps[i].style.height = "152px";
			}
		}
	}
}