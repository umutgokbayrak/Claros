var Dom=YAHOO.util.Dom;
var C_WHITE="#ffffff";
var C_BLACK="#000000";
var C_ROYALBLUE="#3875d7";
var C_LIGHTBLUE="#dae2ed";
function initLogin(event){
	Dom.get('login').style.visibility = 'visible';
	Dom.get('login').style.display = 'block';
	Dom.get('username').focus();
	new Rico.Effect.FadeTo('login', 1, 1000, 10, {
		complete:function() {}
	});
	Dom.get('jsFrame').src = "js_preload.html";
}
function isCursorOnInputArea(event){
	var e=event||window.event;
	if(!e) return true;
	var src=e.srcElement||e.target;
	var t="";
	if(src) t=src.tagName.toLowerCase();
	if(t=='textarea'||t=='iframe'||t=='input') return true;
	else return false;
}
function selectstart(event){
	return isCursorOnInputArea(event);
}
function loadChat() {
	preloadChatImages();
	initChatLogin();
	statusTimerIncrement();
	statusListener();
	initToolbar();
	if (window.addEventListener) {
		window.addEventListener("click", outInfoWin, false);
		window.addEventListener("mousemove", arrangeStatus, false);
	} else if (window.attachEvent && !window.opera){
		window.attachEvent("onclick",outInfoWin);
		window.attachEvent("onmousemove", arrangeStatus);
	}
}
function unloadHandler() {
	var url = "profiling/logout.cl";
	var callback = {
	  success: 	function(o) {},
	  failure: 	function(o) {},
	  argument: []
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}
function onloadHandler(event){
	try{onloadH(event)}catch(e){}
	try{loginProgress()}catch(e){}
	try{loadPreferences()}catch(e){}
	checkingMail=false;
	try{checkMail(null,null,true)}catch(e){}
	try{fetchFolders()}catch(e){}
	try{window.setTimeout(checkUnreadMailCountFirstTime(), 10000)}catch(e){}
	try{window.setTimeout(loadChat(), 3500)}catch(e){}
	try{window.setTimeout(getContacts,3000)}catch(e){}
	try{window.setTimeout(clearContactForm(), 2000)}catch(e){}
	try{window.setTimeout(getNotebooks,6000)}catch(e){}
	try{window.setTimeout(getRssNews,4000)}catch(e){}
	try{window.setTimeout(checkAlerts(),5000)}catch(e){}
	try{preloadCommonImages()}catch(e){}
	try{window.setTimeout(notesCleaner,300000)}catch(e){}
}
function initPage() {
	fixLayout();
	var tools = document.getElementById("tools");
	if (tools && (window.attachEvent && !window.opera)){
		var nodes = tools.getElementsByTagName("li");
		for (var i=0; i<nodes.length; i++) {
			nodes[i].onmouseover = function() {
				if ((this.className.indexOf("sub")) != -1)
					this.className += " hover";
			}
			nodes[i].onmouseout = function() {
				this.className = this.className.replace(new RegExp(" hover"),"");
			}
		}
	}

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
	Dom.get('loginstatus').style.display = 'none';
	renderDivs();
	try {
		registerAutoComplete("to", "autoCompleteTo");
		registerAutoComplete("cc", "autoCompleteCc");
		registerAutoComplete("bcc", "autoCompleteBcc");
		
		//IE BUG&FIX
		//Prevents javascript error which 'contentWindow.document.body' is null 
		//or not an object when compose is loading first.
		if(tinyMCE.getEditorId('composeBody')==null)
			tinyMCE.execCommand('mceAddControl',true,'composeBody');

	} catch (e) {}
}

function initPng() {
	if (navigator.appVersion.indexOf("MSIE 6") != -1){
		var images = document.getElementsByTagName("img");
		if (images){

			for (var i = 0; i < images.length; i++){
				if ((images[i].src.indexOf(".png")) != -1){
					var srcname = images[i].src.replace(new RegExp('(.*)\/(.*)?\.png'),"$2");
					images[i].parentNode.style.display = "inline-block"
					images[i].style.visibility = "hidden";
					images[i].style.marginTop = "0";
					images[i].parentNode.style.filter = "progid:dximagetransform.microsoft.alphaimageloader(src='images/"+ srcname +".png',sizingmethod='crop');"
				}
			}
		}
	}
}

var windowheight;
var windowwidth;
var mainHeight;
var minHeight = 490;
var wysiwygHeight = 300;

function fixLayout() {
	var main = document.getElementById("main");
	var mailList = document.getElementById("mailList");
	var mailBody = document.getElementById("mailBody");
	var newMessages = document.getElementById("newMessages");
	var events = document.getElementById("events");
	var news = document.getElementById("news");
	var folders = document.getElementById("folders");
	var leftColumn = document.getElementById("leftColumn");
	var rightColumn = document.getElementById("rightColumn");
	var calendarDaily = document.getElementById("daily");
	var calendarWeekly = document.getElementById("weekly");
	var calendarMonthly = document.getElementById("monthly");
	var calendars = document.getElementById("calendars");
	var tasks = document.getElementById("tasks");
	var chat = document.getElementById("chat");
	var contactFolders = document.getElementById("contactFolders");
	var contactList = document.getElementById("contactListReal");
	var noteFolders = document.getElementById("notesFolders");
	var noteList = document.getElementById("noteFolderListReal");
	var notesDetails = document.getElementById("notesDetails");
	var memoBoard = document.getElementById("memoBoard");
	var webdiskFolders = document.getElementById("webdiskFolders");
	var webdiskList = document.getElementById("webdiskFolderListReal");
	var webdiskDetails = document.getElementById("webdiskDetails");
	var webdiskFileList = document.getElementById("fileList");
	var webdiskTreeWrapper = document.getElementById("treewrapper");

	if (main) {
		main.style.height = "0";
		mainHeight = document.documentElement.clientHeight - 82;
		if (window.opera) mainHeight = document.body.clientHeight - 82;
		if (navigator.appVersion.indexOf("Safari") != -1) mainHeight = self.innerHeight - 82;

		windowwidth = document.documentElement.clientWidth;
		if (window.opera) windowwidth = document.body.clientWidth;
		if (navigator.appVersion.indexOf("Safari") != -1) windowwidth = self.innerWidth;

		if (mainHeight < minHeight) mainHeight = minHeight;
		if (loggedIn) {
			main.style.height = mainHeight + "px";
		}
//		mainHeight = mainHeight - 3;
		windowheight = mainHeight + 82;
	}

	document.body.style.overflow = "hidden";
	
	// WYSIWYG Width and Height
	
	var attr = Dom.get('attachmentstr');
	var diff = 0;
	if (attr) {
		diff = attr.offsetHeight;
	}
	wysiwygHeight = windowheight - (295 + diff);
	if (wysiwygHeight < 200) {
		wysiwygHeight = 200;
	}
	try {
		if (!document.all) {
			var eid = tinyMCE.getEditorId('composeBody');
			Dom.get('composeBody').style.height = wysiwygHeight + "px";
			var te = Dom.get(eid);
			te.style.height = (wysiwygHeight - 29) + "px";
			te.parentNode.parentNode.parentNode.style.height= (wysiwygHeight - 5) + "px";
			te.parentNode.parentNode.parentNode.parentNode.style.height= (wysiwygHeight -5) + "px";
		}
	} catch (e) {}

	// locate the folder actions buttons
	try {
		if (!folderActionsExpanded) {
			Dom.get('folderActionsBtn').style.top = mainHeight + "px";
			Dom.get('folderActions').style.display = 'none';
		} else {
			Dom.get('folderActionsBtn').style.top = (getHeight(Dom.get('folders')) - 143) + "px";
			Dom.get('folderActions').style.top = (getHeight(Dom.get('folders')) - 143) + "px";
			Dom.get('folderActions').style.display = 'block';
		}
	} catch (e) {}

	// arrange mail, contact folders etc...		
	if (folders) folders.style.height = mainHeight + "px";
	if (contactFolders) contactFolders.style.height = mainHeight + "px";
	if (contactList) contactList.style.height = (mainHeight - 80) + "px";
	if (noteFolders) noteFolders.style.height = mainHeight + "px";
	if (noteList) noteList.style.height = (mainHeight - 50) + "px";
	if (notesDetails) notesDetails.style.height = mainHeight + "px";
	if (memoBoard) memoBoard.style.height = (mainHeight - 120) + "px";
	if (webdiskFolders) webdiskFolders.style.height = mainHeight + "px";
	if (webdiskList) webdiskList.style.height = (mainHeight - 240) + "px";
	if (webdiskDetails) webdiskDetails.style.height = mainHeight + "px";
	if (webdiskFileList) webdiskFileList.style.height = (mainHeight - 80) + "px";
	if (webdiskTreeWrapper) webdiskTreeWrapper.style.height = (mainHeight - 85) + "px";

	try {
		var mtc = Dom.get('mailtitleCloneDiv');
		if (mtc) {
			var lst1 = Dom.get('mailList');
			if (lst1) {
				if (lst1.offsetWidth && lst1.offsetWidth > 0) {
					var psCount = lst1.getElementsByTagName('p').length;
					
					var lstHeight = lst1.style.height;
					if (lstHeight.indexOf('px') > 0) {
						lstHeight = lstHeight.substr(0, lstHeight.length - 2);
					}
					var iLastHeight = parseInt(lstHeight);
					maxP = 7;
					try {
						if (iLastHeight != null && iLastHeight > 0) {
							maxP = parseInt(iLastHeight / 18);
						}
					} catch (jkl) {}
					if (psCount > maxP) {
						mtc.style.width = (lst1.offsetWidth - 18) + "px";
					} else {
						mtc.style.width = (lst1.offsetWidth - 2) + "px";
					}
					var xy = getAbsolutePosition(lst1);
					mtc.style.top = (xy['y'] + 1 ) + "px";
					mtc.style.left = (xy['x'] + 1) + "px";
				}
			}
		}
	} catch (p) {}

	part = parseInt((mainHeight - 83) / 3);

	if (chat) {
		chat.style.height = mainHeight + "px";
		if (logged) {
			initToolbar();
		}
		initContacts();
	}

	if (mailList) mailList.style.height = part - 2 +  "px";

	if (mailBody) mailBody.style.height = part * 2 + 10 + "px";
	if (mailList && mailBody && (mailBody.parentNode.className.indexOf("full")) != -1) {
		mailList.style.display = "none";
		mailBody.style.height = part * 3 + 31 + "px";
	}
	fixMsgViewIframeLayout();
	
	if (events && news){
		var inners = main.getElementsByTagName("div");
		for (var i = 0; i < inners.length; i++){
			if (inners[i].className.indexOf("inner") != -1) {
				inners[i].style.height = "0";
//				inners[i].style.width = "0";
			}
		}
		itsheight = (mainHeight - 84 - 60);
		newMessages.style.height = itsheight + "px";
		itshalf = parseInt(itsheight / 2);
		events.style.height = itshalf + "px";
		news.style.height = itshalf + "px";
		if (itsheight%2 != 0) news.style.height = itshalf + 1 + "px";
		var inners = main.getElementsByTagName("div");
		for (var i = 0; i < inners.length; i++){
			if (inners[i].className.indexOf("inner") != -1) inners[i].style.height = inners[i].parentNode.parentNode.parentNode.parentNode.parentNode.style.height;
			if (inners[i].className.indexOf("inner") != -1) inners[i].style.width = inners[i].parentNode.parentNode.parentNode.parentNode.parentNode.offsetWidth - 56 + "px";
		}
	}

	if (leftColumn) {
		leftColumn.style.height = mainHeight + "px";
		rightColumn.style.height = mainHeight + "px";

		Dom.get('daily').style.height = (mainHeight - 80) + "px";
		Dom.get('weekly').style.height = (mainHeight - 80) + "px";
		Dom.get('monthly').style.height = (mainHeight - 80) + "px";
	}
	if (leftColumn && rightColumn){
		calendars.style.height = mainHeight - 188 + "px";
		tasks.style.height = mainHeight - 20 + "px";
		try {
			if (Dom.get('daily') && Dom.get('daily').style.display == 'block') {
				organizeDailyEvents();
			}
			if (Dom.get('weekly') && Dom.get('weekly').style.display == 'block') {
				organizeWeeklyEvents();
			}
			if (Dom.get('monthly') && Dom.get('monthly').style.display == 'block') {
				organizeMonthlyEvents();
			}
		} catch (dhj) {}
	}
	
	try { reLocateFolderActions(); } catch (u) {}
	/*try { scrollTaskList(); } catch (erf) {} */
}

if (window.addEventListener) {
	window.addEventListener("load",initPage,false);
	window.addEventListener("resize",fixLayout,false);
} else if (window.attachEvent && !window.opera){
	window.attachEvent("onload",initPage);
	window.attachEvent("onresize",fixLayout);
	window.attachEvent("onload",initPng);
}

function trim(str) {
	if (str == null) return null;
	return str.replace(/^\s*|\s*$/g,"");
}

function htmlSpecialChars(str) {
	str = str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\"/g, "&quot;");
	return str;
}

function undoHtmlSpecialChars(str) {
	str = str.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, "\"").replace(/&amp;/g, "&");
	return str;
}

function nl2br(str) {
	str = str.replace(/\n/g, "<br />");
	return str;
}

function br2nl(str) {
	str = str.replace(/<br \/>/g, "\n");
	str = str.replace(/<br>/g, "\n");
	str = str.replace(/<br\/>/g, "\n");
	return str;
}
function getFirstValByTagName(o, tn){
	var n = o.getElementsByTagName(tn);
	var fn = n[0].firstChild;
	if(null != fn) return fn.nodeValue;
	else return n.item(0).text;
}
function getFirstCleanValByTagName(o, tn){
	return trim(undoHtmlSpecialChars(getFirstValByTagName(o, tn)));
}

function getAbsolutePosition(element){
    var ret = new Point();
    for(;
        element && element != document.body;
        ret.translate(element.offsetLeft, element.offsetTop), element = element.offsetParent
	);
    return ret;
}

function Point(x,y){
	this.x = x || 0;
	this.y = y || 0;
	this.toString = function(){
	    return '('+this.x+', '+this.y+')';
	};
	this.translate = function(dx, dy){
	    this.x += dx || 0;
	    this.y += dy || 0;
	};
	this.getX = function(){ return this.x; }
	this.getY = function(){ return this.y; }
	this.equals = function(anotherpoint){
	    return anotherpoint.x == this.x && anotherpoint.y == this.y;
	};
}

function safeEncode(str) {
	if (str == null) {
		return "";
	}
	str = encodeURI(str);
	str = str.replace(new RegExp("&",'g'), "%26");
	str = str.replace(new RegExp(";",'g'), "%3B");
	str = str.replace(new RegExp("[?]",'g'), "%3F");
	return str;
}

var posXMouse;
var posYMouse;
function getMouseXY(e) {
	if (!e) var e = window.event;
	if (e.pageX || e.pageY) {
		posXMouse = e.pageX;
		posYMouse = e.pageY;
	} else if (e.clientX || e.clientY) 	{
		try {
			posXMouse = event.clientX + document.body.scrollLeft;
			posYMouse = event.clientY + document.body.scrollTop;
		} catch (e) {}
	}
}

var is_ie;
var is_ie6;
function detectBrowser() {
	var agt=navigator.userAgent.toLowerCase();
	var appVer = navigator.appVersion.toLowerCase();
	var is_minor = parseFloat(appVer);
	var is_major = parseInt(is_minor);
	var iePos = appVer.indexOf('msie');
	if (iePos !=-1) {
		is_minor = parseFloat(appVer.substring(iePos+5,appVer.indexOf(';',iePos)))
		is_major = parseInt(is_minor);
	}
	var is_getElementById = (document.getElementById) ? "true" : "false";
	var is_getElementsByTagName = (document.getElementsByTagName) ? "true" : "false";
	var is_documentElement = (document.documentElement) ? "true" : "false";
	is_ie = ((iePos!=-1));
	var is_ie3 = (is_ie && (is_major < 4));
	var is_ie4 = (is_ie && is_major == 4);
	var is_ie4up = (is_ie && is_minor >= 4);
	var is_ie5 = (is_ie && is_major == 5);
	var is_ie5up = (is_ie && is_minor >= 5);
	var is_ie5_5 = (is_ie && (agt.indexOf("msie 5.5") !=-1));
	var is_ie5_5up =(is_ie && is_minor >= 5.5);
	is_ie6 = (is_ie && is_major == 6);
	var is_ie6up = (is_ie && is_minor >= 6);
}
