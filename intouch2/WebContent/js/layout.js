var anim = true;
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
		login();
		return false;
	}
	return true;
}

function loginProgress() {
	hideAllLayers();
//	layoutHome();
	layoutMail();
}

function hideAllLayers() {
	if (Dom.get("navcalendar")) {
		Dom.get("navcalendar").className = '';
	}
	for (var i=0;i<clarosLayers.length;i++) {
		try {
			Dom.get(clarosLayers[i]).style.display = 'none';
			Dom.get("nav" + clarosLayers[i]).className = '';
		} catch (e) {}
	}
}

function layoutHome() {
	hideAllLayers();
	getNewMessagesSummary();
	Dom.get('loginstatus').style.display = 'none';
	Dom.get('header').style.display = 'block';
	Dom.get('home').style.display = 'block';
	Dom.get('navhome').className = 'active';
	fixLayout();
}

function layoutMail(scr) {
	hideAllLayers();
	Dom.get('loginstatus').style.display = 'none';
	Dom.get('header').style.display = 'block';
	Dom.get('mail').style.display = 'block';
	Dom.get('navmail').className = 'active';
	fixLayout();

	if (scr == null) {
		scr = 'inbox';
	}
	
	if (scr == 'inbox') {
		Dom.get('inbox').style.display = 'block';
		Dom.get('compose').style.display = 'none';
		fixLayout();
	} else if (scr == 'compose') {
		Dom.get('inbox').style.display = 'none';
		Dom.get('compose').style.display = 'block';
		
		if (tinyMCE.getEditorId('composeBody') == null) {
	  		tinyMCE.execCommand('mceAddControl', true, 'composeBody');
		}
		tinyMCE.execCommand('mceFocus', true, 'composeBody');
	}
	checkMailButtons()
	initPng();
}

function layoutContacts() {
	hideAllLayers();
	Dom.get('loginstatus').style.display = 'none';
	Dom.get('header').style.display = 'block';
	Dom.get('contacts').style.display = 'block';
	Dom.get('navcontacts').className = 'active';
	fixLayout();
}

function layoutCalendar(scr) {
	try {
		clearAllEvents();
		hideAllLayers();
		Dom.get('calendarDateDisplayed').innerHTML = "";
		new Rico.Effect.FadeTo('monthlyCalendarTable', .2, 0, 1, {} );
	
		Dom.get('loginstatus').style.display = 'none';
		Dom.get('header').style.display = 'block';
		Dom.get('calendardiv').style.display = 'block';
		Dom.get('navcalendar').className = 'active';
		
		fixLayout();
		if (scr == null) {
			scr = 'weekly';
			selectedTask = null;
			disableButton(Dom.get('deleteTask'));
			initialScrH = null;
			getTasks();
		}
		populateCalendarYear();
		
		if (scr == 'daily') {
			Dom.get('daily').style.display = 'block';
			Dom.get('weekly').style.display = 'none';
			Dom.get('monthly').style.display = 'none';
	
			Dom.get('dailyCal').className = 'active';
			Dom.get('weeklyCal').className = '';
			Dom.get('monthlyCal').className = '';
			
			Dom.get('calendarTitle').innerHTML = daily_calendar;
	
			Dom.get('daily').scrollTop = 330;
			
			var st = Dom.get('daily').style;
		} else if (scr == 'weekly') {
			Dom.get('daily').style.display = 'none';
			Dom.get('weekly').style.display = 'block';
			Dom.get('monthly').style.display = 'none';
	
			Dom.get('dailyCal').className = '';
			Dom.get('weeklyCal').className = 'active';
			Dom.get('monthlyCal').className = '';
	
			Dom.get('calendarTitle').innerHTML = weekly_calendar;
	
			Dom.get('weekly').scrollTop = 328;
		} else if (scr == 'monthly') {
			Dom.get('daily').style.display = 'none';
			Dom.get('weekly').style.display = 'none';
			Dom.get('monthly').style.display = 'block';
	
			Dom.get('dailyCal').className = '';
			Dom.get('weeklyCal').className = '';
			Dom.get('monthlyCal').className = 'active';
	
			Dom.get('calendarTitle').innerHTML = monthly_calendar;
		}
	
		Dom.get(scr).style.display = 'block';
		fixLayout();
		getEvents(scr);
	} catch (r) {
		alert(r.message);
	}
}

function layoutNotes() {
	hideAllLayers();
	Dom.get('loginstatus').style.display = 'none';
	Dom.get('header').style.display = 'block';
	Dom.get('notes').style.display = 'block';
	Dom.get('navnotes').className = 'active';
	fixLayout();
}

function layoutWebdisk() {
	hideAllLayers();
	Dom.get('loginstatus').style.display = 'none';
	Dom.get('header').style.display = 'block';
	Dom.get('webdisk').style.display = 'block';
	Dom.get('navwebdisk').className = 'active';
	showFileTree();
	fixLayout();
}

function layoutChat() {
	hideAllLayers();
	Dom.get('loginstatus').style.display = 'none';
	Dom.get('header').style.display = 'block';
	Dom.get('chat').style.display = 'block';
	Dom.get('navchat').className = 'active';
	fixLayout();
//	initToolbar();
//	initLogin();
}

function disableButton(btn) {
	var obj = Dom.get(btn);
	var a = obj.getElementsByTagName('A')[0];
	var img = obj.getElementsByTagName('IMG')[0];
	if(a.href != 'javascript:;') a.orighref = a.href;
	a.href = 'javascript:;';
	a.style.color = '#999999';
	a.style.cursor = 'default';
	a.style.textDecoration = 'none';
	img.style.cursor = 'default';
	new Rico.Effect.FadeTo(img.id, 0.3, 1, 1, {} );
}
function disableButtons(){
	for(var i=0;i<arguments.length;i++)
		disableButton(arguments[i]);
}
function enableButton(btn) {
	var obj = Dom.get(btn);
	var a = obj.getElementsByTagName('A')[0];
	var img = obj.getElementsByTagName('IMG')[0];
	if(a.orighref != null) a.href = a.orighref;
	a.style.color = C_BLACK;
	a.style.cursor = 'pointer';
	a.style.textDecoration = '';
	img.style.cursor = 'pointer';
	new Rico.Effect.FadeTo(img.id, 1, 1, 1, {} );
}
function enableButtons(){
	for(var i=0;i<arguments.length;i++)
		enableButton(arguments[i]);
}
function blockCurtain(id) {
	var curtain = Dom.get(id);
	curtain.style.height = windowheight;
	curtain.style.width = "100%";
	curtain.style.display = "block";
}

function unblockCurtain(id) {
	Dom.get(id).style.display = "none";
}

function showCurtain(id, opacity) {
	blockCurtain(id);
	if (!opacity) {
		opacity = .30;
	}
	new Rico.Effect.FadeTo(id, opacity, 100, 1, {} );
}

function hideCurtain(id) {
	var curtain = Dom.get(id);
	new Rico.Effect.FadeTo(id, 0, 1, 1, {complete:function() {unblockCurtain(id)}} );
}

function getWidth(obj) {
	var w = obj.getOffsetWidth;
	if (w == null || w == 0) {
		var sw = obj.style.width;
		
		if (sw != null && sw.indexOf("px") > 0) {
			w = sw.substr(0, sw.indexOf("px"));
		}
	}
	return parseInt(w);
}

function getHeight(obj) {
	var w = obj.getOffsetHeight;
	if (w == null || w == 0) {
		var sw = obj.style.height;
		if (sw != null && sw.indexOf("px") > 0) {
			w = sw.substr(0, sw.indexOf("px"));
		}
	}
	return parseInt(w);
}

function changeComboVisibility(val) {
	if(document.getElementsByTagName) { 
		var combos = document.getElementsByTagName("SELECT");
		for(i = 0; i < combos.length; i++) {
			if (combos[i].nohide == null && combos[i].getAttribute('nohide') == null) {
				combos[i].style.visibility = val;
			}
		}
	}
}

function showDialog(id) {
	showCurtain('curtain');
	var d = Dom.get(id);
	d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
	d.style.top = (((windowheight - getHeight(d)) / 2) - 50) + "px";
	d.style.display = "block";
	changeComboVisibility('hidden');
}

function hideDialog(id) {
	hideCurtain('curtain');
	var d = Dom.get(id);
	d.style.display = "none";
	changeComboVisibility('visible');
}

function showClickIndicator() {
	var d = Dom.get('clickIndicator');
	d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
	d.style.top = "-1px";
	d.style.display = "block";
}

function hideClickIndicator() {
	var d = Dom.get('clickIndicator');
	d.style.display = "none";
}

function hideSendResultIndicator() {
	var d = Dom.get('sendResultIndicator');
	d.style.display = "none";
}
function hideSendReceiptResultIndicator() {
	var d = Dom.get('sendReceiptResultIndicator');
	d.style.display = "none";
}
function showNewMessageIndicator() {
	var d = Dom.get('newMessageIndicator');
	d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
	d.style.top = "22px";
	d.style.display = "block";
	setTimeout(hideNewMessageIndicator, 8000);
}

function hideNewMessageIndicator() {
	var d = Dom.get('newMessageIndicator');
	d.style.display = "none";
}

var cal1 = null;
function renderDivs() {
	try {
		// YAHOO.namespace("claros.calendar");
		
		var dt = new Date();
		var fY = dt.getFullYear();
		
		cal1 = new YAHOO.widget.Calendar("cal1","smallCalendar", { mindate: "1/1/" + (fY - 1), maxdate: "12/31/" + (fY + 1)});

		cal1.cfg.setProperty("DATE_FIELD_DELIMITER", ".");
		
		cal1.cfg.setProperty("MDY_DAY_POSITION", 1);
		cal1.cfg.setProperty("MDY_MONTH_POSITION", 2);
		cal1.cfg.setProperty("MDY_YEAR_POSITION", 3);

		cal1.cfg.setProperty("MD_DAY_POSITION", 1);
		cal1.cfg.setProperty("MD_MONTH_POSITION", 2);
		
		cal1.cfg.setProperty("MONTHS_SHORT",   [january_short, february_short, march_short, april_short, may_short, june_short, july_short, august_short, september_short, october_short, november_short, december_short]);
		cal1.cfg.setProperty("MONTHS_LONG",    [january_long, february_long, march_long, april_long, may_long, june_long, july_long, august_long, september_long, october_long, november_long, december_long]);
		cal1.cfg.setProperty("WEEKDAYS_1CHAR", [sunday_1char, monday_1char, tuesday_1char, wednesday_1char, thursday_1char, friday_1char, saturday_1char]);
		cal1.cfg.setProperty("WEEKDAYS_SHORT", [sunday_mini, monday_mini, tuesday_mini, wednesday_mini, thursday_mini, friday_mini, saturday_mini]);
		cal1.cfg.setProperty("WEEKDAYS_MEDIUM",[sunday_medium, monday_medium, tuesday_medium, wednesday_medium, thursday_medium, friday_medium, saturday_medium]);
		cal1.cfg.setProperty("WEEKDAYS_LONG",  [sunday_long, monday_long, tuesday_long, wednesday_long, thursday_long, friday_long, saturday_long]);

		cal1.selectEvent.subscribe(selectCalendar, cal1, true);
		cal1.render();
	} catch (e) {}
}

function showRssPreferences() {
	showPreferences(false, 'RSS');
}

function showFilterPreferences() {
	showPreferences(false, 'Filters');
}

/*
function showCreateMailFolder() {
	Dom.get('createNewMailFolder').style.display = 'block';
	YAHOO.claros.container.createNewMailFolder.show();
}
*/

function fixMsgViewIframeLayout() {
	try {
		var h = Dom.get('mailBody').style.height;
		if (h.indexOf('px') > 0) {
			h = h.substr(0,h.indexOf('px'));
		}
		var ih = parseInt(h);
		
		var hTitle = Dom.get('mailViewTitle').style.height + "";
		if (hTitle == null || hTitle.length == 0) {
			hTitle = Dom.get('mailViewTitle').offsetHeight + "";
		}
		if (hTitle == null || hTitle.length == 0) {
			hTitle = "90";
		}
		if (hTitle.indexOf('px') > 0) {
			hTitle = hTitle.substr(0,hTitle.indexOf('px'));
		}
		var ihTitle = parseInt(hTitle);
		if (isNaN(ihTitle)) {
			ihTitle = 90;
		}
		ih = ih - (ihTitle + 10);
		
		var iframe = Dom.get("msgTextIframe");
		if (iframe != null) {
			iframe.style.height = ih + "px";
		}
	} catch (e) {}
}
/*
var altPressed = false;
var ctrlPressed = false;
var shiftPressed = false;
*/
var keyCode;
function onKeyDownH(event) {
	if(isCursorOnInputArea(event)) return;
	var e=event||window.event;
	if(!e) return;
	var keyCode=e.keyCode||e.which;

	var mailView = false;
	// if mail view is shown go to the next/previous email
	if (Dom.get('inbox') != null && Dom.get('inbox').style.display == 'block') {
		var list = Dom.get('mailList');
		var ps = list.getElementsByTagName('p');
			
		// find the latest shown mail
		var curIndex = -1;
		for (var i=0;i<ps.length/2;i++) {
			if (ps[i].lastClicked ) {
				curIndex = i;
			}
		}
			
		if (keyCode + "" == "68" || keyCode + "" == "8" || keyCode + "" == "46") {
			// delete
			deleteMail();
		}

		var operate = false;
		var operateNext = false;
		var operatePrev = false;
			
		// right arrow
		if (keyCode + "" == "39" || keyCode + "" == "40") {
			curIndex++;
			operate = true;
			operateNext = true;
		}
		// left arrow
		if (keyCode + "" == "37"  || keyCode + "" == "38") {
			curIndex--;
			operate = true;
			operatePrev = true;
		}

		if (ps.length/2 > 1 && curIndex <= 0) {
			curIndex = 1;
		}
		if (operate && curIndex >= 1 && curIndex < ps.length/2) {
			// show the mail move the index disable the others

			// if ctrl is not pressed then only select the clicked one so disable the others 
			for (var i=0;i<ps.length/2;i++) {
				if (ps[i].clicked) {
					ps[i].style.backgroundColor = C_WHITE;
					ps[i].style.color = C_BLACK;
					ps[i].clicked = false;
					ps[i].lastClicked = false;
					
					ps[i].getElementsByTagName('span')[0].style.backgroundImage = 'url(images/unchecked.gif)';
				}
			}
			
			obj = ps[curIndex];
			obj.style.backgroundColor = C_ROYALBLUE;
			obj.style.color = C_WHITE;
			obj.clicked = true;
			obj.getElementsByTagName('span')[0].style.backgroundImage = 'url(images/checked.gif)';
			obj.lastClicked = true;
			
			var xy = getAbsolutePosition(obj);
			var selectedTop = (xy['y'] + 1 );
			
			var xy = getAbsolutePosition(obj);
			var selectedTop = (xy['y'] + 1 );

			var xyL = getAbsolutePosition(list);
			var selectedTopL = (xyL['y'] + 1 );
			list.scrollTop = selectedTop - selectedTopL - 40;
			
			// message fetched here
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
			} else {
				showMail(msgId);
			}
		}
	} else if (Dom.get('contacts') != null && Dom.get('contacts').style.display == 'block') {
		if (keyCode + "" == "68" || keyCode + "" == "8" || keyCode + "" == "46") {
			deleteContact();
		}

		var clickedIndex = -1;
		var trs = Dom.get('contactListRealBody').getElementsByTagName('tr');
		if (trs != null) {
			for (var i=0;i<trs.length;i++) {
				if (trs[i].clicked == true) {
					clickedIndex = i;
					break;
				}
			}
		}

		// right arrow -> next contact
		if (keyCode + "" == "39" || keyCode + "" == "40") {
			clickedIndex++;
			if (clickedIndex < trs.length) {
				var newContId = trs[clickedIndex].id.substr(7);
				showContactDetails(newContId);
			}
		}
		// left arrow
		if (keyCode + "" == "37"  || keyCode + "" == "38") {
			clickedIndex--;
			if (clickedIndex > -1) {
				var newContId = trs[clickedIndex].id.substr(7);
				showContactDetails(newContId);
			}
		}
	}
	return false;
}
function onKeyUpH() {
	keyCode = null;
}
function onMouseUpH() {
	mouseStatus='up'
}
function showHide(id) {
	var obj = Dom.get(id);
	if (obj.style.display == 'none') {
		obj.style.display = '';
	} else {
		obj.style.display = 'none';
	}
}

function show(id) {
	var obj = Dom.get(id);
	obj.style.display = '';
}

function hide(id) {
	var obj = Dom.get(id);
	obj.style.display = 'none';
}

function showPreferences(noLoad, tab) {
	if (noLoad == null || noLoad == false) {
		loadPreferences();
	}
	Dom.get('prefCancel').style.display = 'block';

	showCurtain('curtain');

	var d = Dom.get('preferences');
	var left = ((windowwidth - getWidth(d)) / 2);
	d.style.left = left + "px";
	var top = (((windowheight - 400) / 2) - 10);

	Dom.get('preferences').style.display = 'block';
	
	if (tab != null) {
		clickPrefTab(tab);
	}
	new Rico.Effect.Position('preferences', null, top, 200, 4, {});
}

function hidePreferences() {
	new Rico.Effect.Position('preferences', null, -300, 200, 4, {complete:function() {
		Dom.get('preferences').style.display = 'none';
		hideCurtain('curtain');
	}});
}

function preloadCommonImages() {
	MM_preloadImages('images/tools-sub-bottom.gif', 
					 'images/more-hover.gif',
					 'images/roundedcornr_171934_tl.png',
					 'images/roundedcornr_171934_tr.png',
					 'images/roundedcornr_171934_bl.png',
					 'images/roundedcornr_171934_br.png',
					 'images/qty-left.gif',
					 'images/qty-right.gif',
					 'images/qty-left-hover.gif',
					 'images/qty-right-hover.gif',
					 'images/mouse-drag-bg.gif',
					 'images/pref-act-tab.gif',
					 'images/pref-dis-tab.gif',
					 'images/pref-tbl-back-top.gif',
					 'images/pref-back-top.gif',
					 'images/pref-back.gif',
					 'images/pref-back-bottom.gif',
					 'images/pref-tbl-back-top.gif',
					 'images/pref-tbl-back.gif',
					 'images/pref-tbl-back-bottom.gif',
					 'images/tools-sub-bg.gif');
}

function clickPrefTab(tab) {
	// first make all tabs disactive
	var tds = Dom.get('prefTabs').getElementsByTagName('td');
	if (tds) {
		for (var i=0;i<tds.length;i++) {
			if (tds[i].id != 'prefTabSpace') {
				tds[i].className = 'prefTabNotActive';
			}
		}
	}
	var divs = Dom.get('prefTabDivs').getElementsByTagName('div');
	if (divs) {
		for (var i=0;i<divs.length;i++) {
			divs[i].style.display = 'none';
		}
	}

	// make the clicked one active
	Dom.get('prefTab' + tab).className = 'prefTabActive';
	Dom.get('pref' + tab).style.display = 'block';

}

function showHidePrefsMail() {
	var optTr = Dom.get("messageOptions");
	showHide(optTr);
}
