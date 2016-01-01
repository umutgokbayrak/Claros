var initialScrH;
var calendarDate;

function organizeDailyEvents() {
	var calendarDaily = document.getElementById("daily");

	var calendardiv = calendarDaily.getElementsByTagName("div");
	for (var i = 0; i < calendardiv.length; i++){
		if (calendardiv[i].className.indexOf("event") != -1){
			try {
				calendardiv[i].style.width = calendardiv[i].parentNode.offsetWidth - 57 + "px";

				var eventp = calendardiv[i].getElementsByTagName("p");
				for (var j = 0; j < eventp.length; j++) {
					if (eventp[j].className.indexOf("title") != -1) {
						var duration = calendardiv[i].getAttribute("range");
						
						while (duration.indexOf(":") != -1) {
							duration = duration.replace(":",".");
							duration = duration.replace(":30",".5");
						}
						var a = duration.split("-");
						duration = Number(a[0]) - Number(a[1]);
						one = calendardiv[i].parentNode.offsetHeight;
						calendardiv[i].style.height = one * (duration * -1) - 6 + "px";
						if (Number(a[0])/parseInt(Number(a[0])) != 1) {
							calendardiv[i].style.marginTop = (parseInt(one / 2) + 1) + "px";
							calendardiv[i].style.height = one * (duration * -1) - 14 + "px";
						}
						
						try {
							var innerp = calendardiv[i].getElementsByTagName("p")[1];
							innerp.style.height = (getHeight(calendardiv[i]) - 1) + "px";
							innerp.style.overflow = "hidden";
						} catch (op) {}
					}
				}
			} catch (e) {
				alert(e.message);
			}
		}
	}
}

function organizeWeeklyEvents() {
	var calendarWeekly = Dom.get("weekly");
	
	var tbl = Dom.get('weeklyCalendarTable');
	tbl.style.width = (calendarWeekly.offsetWidth - 17) + "px";

	var offW = Dom.get('weeklyCalendarTable').offsetWidth - 12;
	var oneW = (offW - 45) / 7;
	var tds = Dom.get('weeklyCalendarTable').getElementsByTagName('tr')[0].getElementsByTagName('td');
	for (var w=0;w<tds.length;w++) {
		if (w==0) {
			tds[w].style.width="45px";
		} else {
			tds[w].style.width= oneW + "px";
		}
	}


	var calendardiv = calendarWeekly.getElementsByTagName("div");
	for (var i = 0; i < calendardiv.length; i++){
		if (calendardiv[i].className.indexOf("event") != -1){
			try {
				calendardiv[i].style.marginLeft = '3px';
				var eventp = calendardiv[i].getElementsByTagName("p");
				for (var j = 0; j < eventp.length; j++) {
					if (eventp[j].className.indexOf("title") != -1) {
						var duration = calendardiv[i].getAttribute("range");
						
						while (duration.indexOf(":") != -1) {
							duration = duration.replace(":",".");
							duration = duration.replace(":30",".5");
						}
						var a = duration.split("-");
						duration = Number(a[0]) - Number(a[1]);
						one = calendardiv[i].parentNode.offsetHeight;
						calendardiv[i].style.height = one * (duration * -1) - 6 + "px";
						if (Number(a[0])/parseInt(Number(a[0])) != 1) {
							calendardiv[i].style.marginTop = (parseInt(one / 2) - 11) + "px";
							calendardiv[i].style.height = one * (duration * -1) - 14 + "px";
						} else {
							calendardiv[i].style.marginTop = "-11px";
						}
						
						try {
							var innerp = calendardiv[i].getElementsByTagName("p")[1];
							innerp.style.height = (getHeight(calendardiv[i]) - 1) + "px";
							innerp.style.overflow = "hidden";
						} catch (op) {}
					}
				}
			} catch (e) {
			//	alert(e.message);
			}
		}
	}
	cloneWeeklyHeader();
}

function cloneWeeklyHeader() {
	Dom.get('calendarDateDisplayed').innerHTML = "<table align='left' id='calendarWeekHeadTbl' cellspacing='0' cellpadding='0' border='0'><tbody id='calendarWeekHeadTblBody'></tbody></table>";
	var tbl = Dom.get('calendarWeekHeadTbl');
	tbl.width = (Dom.get('weeklyCalendarTable').offsetWidth) + "px";
	tbl.style.height = '20px'
	
	var wH = Dom.get('weeklyCalendarTableHeader');
	var wC = wH.cloneNode('true');
	wC.id = 'weeklyCalendarTableHeaderClone';
	wC.style.visibility = 'visible';
	Dom.get('calendarDateDisplayed').appendChild(tbl);
	
	tbl.style.position = 'relative';
	tbl.style.top = '0px';
	tbl.style.left = '0px';
	tbl.style.padding = "0px";
	
	var tbody = Dom.get('calendarWeekHeadTblBody');
	detectBrowser();
	if (!is_ie6) {
		tbody.appendChild(wC);
	}
	
	var tdsC = wC.getElementsByTagName('td');
	var tdsH = wH.getElementsByTagName('td');

	for (var i=0;i<tdsC.length;i++) {
		if (i==0) {
			tdsC[i].style.width = (tdsH[i].offsetWidth - 3) + "px";
		} else {
			tdsC[i].style.width = (tdsH[i].offsetWidth) + "px";
		}
	}
}

function clickEvent(obj) {
	showClickIndicator();
	clearEventBox();
	
	var month = calendarDate.getMonth() + 1;
	var year = calendarDate.getFullYear();
	var day = calendarDate.getDate(); 
	
	var params = "eventId=" + obj.getAttribute('eventId');
	var url = "calendar/getEvent.cl";
	
	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						hideClickIndicator();
						
						var events = o.responseXML.getElementsByTagName('events');
						if (events != null && events.length > 0) {
							var nodes = events[0].childNodes;
							try {
								var event = nodes[0];
								
								var eventId = getFirstCleanValByTagName(event, 'id');
								var year = getFirstCleanValByTagName(event, 'year');
								var month = getFirstCleanValByTagName(event, 'month');
								var day = getFirstCleanValByTagName(event, 'day');
								var begin = getFirstCleanValByTagName(event, 'begin');
								var end = getFirstCleanValByTagName(event, 'end');
								var color = getFirstCleanValByTagName(event, 'color');
								var note = getFirstCleanValByTagName(event, 'description');
								var hasReminder = getFirstCleanValByTagName(event, 'hasReminder');
								var repeatType = getFirstCleanValByTagName(event, 'repeatType');
								var location = getFirstCleanValByTagName(event, 'location');
								var reminderDays = getFirstCleanValByTagName(event, 'reminderDays');
								var reminderMethod = getFirstCleanValByTagName(event, 'reminderMethod');

								Dom.get('eventId').value = eventId;
								
								Dom.get('eventStartTime').value = begin;
								Dom.get('eventEndTime').value = end;
								Dom.get('eventColor').value = color;
								Dom.get('eventRepeatType').value = repeatType;
								Dom.get('eventDescription').value = note;
								Dom.get('eventLocation').value = location;
								Dom.get('eventReminderDays').value = reminderDays;
								Dom.get('eventReminderMethod').value = reminderMethod;
								Dom.get('eventDateDay').value = day;
								Dom.get('eventDateMonth').value = month;
								Dom.get('eventDateYear').value = year;

								Dom.get('eventEditDialogHeader').innerHTML = edit_event;
								Dom.get('deleteEventButton').style.display = 'block';
								showDialog('createEvent');
								Dom.get('eventDescription').focus();
							} catch (e) {
								alert(e.message);
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
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
	return false;
}

function saveEvent(obj) {
	var eventId = Dom.get('eventId').value;
	var eventStartTime = Dom.get('eventStartTime').value;
	var eventEndTime = Dom.get('eventEndTime').value;
	var eventColor = Dom.get('eventColor').value;
	var eventRepeatType = Dom.get('eventRepeatType').value;
	var eventDescription = Dom.get('eventDescription').value;
	var eventLocation = Dom.get('eventLocation').value;
	var eventReminderDays = Dom.get('eventReminderDays').value;
	var eventReminderMethod = Dom.get('eventReminderMethod').value;

	var month = Dom.get('eventDateMonth').value;
	var year = Dom.get('eventDateYear').value;
	var day = Dom.get('eventDateDay').value;
	
	// save the event
	var paramData = "eventId=" + eventId + "&begin=" + eventStartTime + 
					"&end=" + eventEndTime + "&color=" + eventColor + "&repeatType=" + eventRepeatType + 
					"&description=" + myEncode(eventDescription) + "&location=" + myEncode(eventLocation) + 
					"&reminderDays=" + eventReminderDays + "&reminderMethod=" + eventReminderMethod + 
					"&year=" + year + "&month=" + month + "&day=" + day;
	var url = "calendar/saveEvent.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null && o.responseText.indexOf('ok') == 0) {
					eventId = o.responseText.substr(2);

					var hasReminder = false;
					if (eventRepeatType != '-1') {
						hasReminder = 'true';
					}
					
					getEvents();
					
					hideDialog('createEvent');
				} else if (o.responseText != null && o.responseText == 'collapse') {
					alert(error_collapse);
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

function newEvent(halfClear) {
	if (halfClear) {
		halfClearEventBox();
	} else {
		Dom.get('deleteEventButton').style.display = 'none';
		Dom.get('eventEditDialogHeader').innerHTML = new_event;
		clearEventBox();
	}
	showDialog('createEvent');
	Dom.get('eventDescription').focus();
}

function deleteEvent() {
	var eventId = Dom.get('eventId').value;
	var paramData = "id=" + eventId;
	var url = "calendar/deleteEvent.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null) {
					if (o.responseText != 'ok') {
						showDialog("defaultError");
					} else {
						getEvents();
						hideDialog('createEvent');
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

function showDailyEvent(eventId, begin, end, color, note, reminder) {
	var nw = Dom.get('eventTemplate');
	var nl = nw.cloneNode(true);
	nl.id = "eventDaily" + eventId;
	nl.setAttribute('eventId', eventId);
	nl.className = "event" + color;
	nl.setAttribute("range", begin + " - " + end);
	nl.style.display = "";
	var ps = nl.getElementsByTagName('p');
	for (var t=0;t<ps.length;t++) {
		var ptmp = ps[t];
		if (ptmp.id == 'eventDailyContent') {
			ptmp.innerHTML = note;
			break;
		}
	}
	
	var itemBegin = begin.substr(0,2) + ":00";
	
	Dom.get('daily-' + itemBegin).appendChild(nl);
	organizeDailyEvents();
}

function showWeeklyEvent(eventId, begin, end, color, note, reminder, day, month, year) {
	var nw = Dom.get('eventTemplate');
	var nl = nw.cloneNode(true);
	nl.id = "eventWeekly" + eventId;
	nl.setAttribute('eventId', eventId);
	nl.className = "event" + color;
	nl.setAttribute("range", begin + " - " + end);
	nl.style.display = "";
	var ps = nl.getElementsByTagName('p');
	for (var t=0;t<ps.length;t++) {
		var ptmp = ps[t];
		if (ptmp.id == 'eventDailyContent') {
			ptmp.innerHTML = note;
			break;
		}
	}
	
	var itemBegin = begin.substr(0,2) + ":00";
	var itmDom = 'weekly-' + itemBegin + '-' + day + '-' + month + '-' + year;
	Dom.get(itmDom).appendChild(nl);
}

function selectCalendar(type,args,obj) {
	calendarDate = args[0][0];
	calendarDate = this._toDate(calendarDate);
	
	showClickIndicator();

	Dom.get('calendarDateDisplayed').innerHTML = "";
	getEvents();
}

function getEvents(type) {
	showClickIndicator();
	clearAllEvents();
	if (Dom.get('daily').style.display == 'block') {
		type = "daily";
	} else if (Dom.get('weekly').style.display == 'block') {
		type = "weekly";
	} else if (Dom.get('monthly').style.display == 'block') {
		type = "monthly";
	}

	if (calendarDate == null) {
		var arrDates = cal1.getSelectedDates();
		for (var i = 0; i < arrDates.length; ++i) {
			calendarDate = arrDates[i];
		}	
	}
	if (calendarDate == null) {
		calendarDate = new Date();
	}
	
	var month = calendarDate.getMonth() + 1;
	var year = calendarDate.getFullYear();
	var day = calendarDate.getDate(); 
	
	var params = "year=" + year + "&month=" + month + "&day=" + day + "&type=" + type;
	var url = "calendar/getEvents.cl";
	
	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						hideClickIndicator();
						
						if (type == "monthly") {
							parseMonthlyEvents(o.responseXML);
						} else {
							var dateDisplay = getFirstCleanValByTagName(o.responseXML, 'date-display');
							Dom.get('calendarDateDisplayed').innerHTML = dateDisplay;
	
							var yearD;
							var monthD;
							var dayD;
							try {
								yearD = getFirstCleanValByTagName(o.responseXML, 'year');
								monthD = getFirstCleanValByTagName(o.responseXML, 'month');
								dayD = getFirstCleanValByTagName(o.responseXML, 'day');
							} catch (jku) {
								monthD = calendarDate.getMonth() + 1;
								yearD = calendarDate.getFullYear();
								dayD = calendarDate.getDate(); 
							}
							Dom.get('eventDateDay').value = dayD;
							Dom.get('eventDateMonth').value = monthD;
							Dom.get('eventDateYear').value = yearD;
	
							// organize the weekly view and append the day values.
							if (type == 'weekly') {
								for (var p=0;p<7;p++) {
									var domP = Dom.get('day' + p);
									var lis = domP.getElementsByTagName('li');
									for (var k=0;k<lis.length;k++) {
										lis[k].id = lis[k].id.substr(0,12)  + "-" + getFirstCleanValByTagName(o.responseXML, 'day' + p);
									}
								}
							}
	
							// now parse the events
							var events = o.responseXML.getElementsByTagName('events');
							
							if (events != null && events.length > 0) {
								var nodes = events[0].childNodes;
								for (var p=0;p<nodes.length;p++) {
									try {
										var event = nodes[p];
										
										var eventId = getFirstCleanValByTagName(event, 'id');
										var year = getFirstCleanValByTagName(event, 'year');
										var month = getFirstCleanValByTagName(event, 'month');
										var day = getFirstCleanValByTagName(event, 'day');
										var begin = getFirstCleanValByTagName(event, 'begin');
										var end = getFirstCleanValByTagName(event, 'end');
										var color = getFirstCleanValByTagName(event, 'color');
										var note = getFirstCleanValByTagName(event, 'description');
										var hasReminder = getFirstCleanValByTagName(event, 'hasReminder');
										var repeatType = getFirstCleanValByTagName(event, 'repeatType');
										var location = getFirstCleanValByTagName(event, 'location');
										var reminderDays = getFirstCleanValByTagName(event, 'reminderDays');
										var reminderMethod = getFirstCleanValByTagName(event, 'reminderMethod');
	
										if (Dom.get('daily').style.display == 'block') {
											showDailyEvent(eventId, begin, end, color, note, hasReminder);
										} else if (Dom.get('weekly').style.display == 'block') {
											showWeeklyEvent(eventId, begin, end, color, note, hasReminder, day, month, year);
										}
									} catch (e) {
										alert(e.message);
									}
								}
							}
							if (type == "weekly") {
								prepareWeeklyHeader(o.responseXML);
								organizeWeeklyEvents();
							}
						}
					}
				} catch (nnnn) {
					alert("events:" + nnnn.message);
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
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
}

function prepareWeeklyHeader(xml) {
	// Now show the week table headers.
	var wC = Dom.get('weeklyCalendarTableHeader');
	var tds = wC.getElementsByTagName('td');

	var sunday = getFirstCleanValByTagName(xml, 'day0');
	var monday = getFirstCleanValByTagName(xml, 'day1');
	var tuesday = getFirstCleanValByTagName(xml, 'day2');
	var wednesday = getFirstCleanValByTagName(xml, 'day3');
	var thursday = getFirstCleanValByTagName(xml, 'day4');
	var friday = getFirstCleanValByTagName(xml, 'day5');
	var saturday = getFirstCleanValByTagName(xml, 'day6');

	tds[1].innerHTML = sunday.substr(0, 2) + " " +  getMonthShortName(sunday.substr(3, 2)) + ", " + sunday_medium;
	tds[2].innerHTML = monday.substr(0, 2) + " " +  getMonthShortName(monday.substr(3, 2)) + ", " + monday_medium;
	tds[3].innerHTML = tuesday.substr(0, 2) + " " +  getMonthShortName(tuesday.substr(3, 2)) + ", " + tuesday_medium;
	tds[4].innerHTML = wednesday.substr(0, 2) + " " +  getMonthShortName(wednesday.substr(3, 2)) + ", " + wednesday_medium;
	tds[5].innerHTML = thursday.substr(0, 2) + " " +  getMonthShortName(thursday.substr(3, 2)) + ", " + thursday_medium;
	tds[6].innerHTML = friday.substr(0, 2) + " " +  getMonthShortName(friday.substr(3, 2)) + ", " + friday_medium;
	tds[7].innerHTML = saturday.substr(0, 2) + " " +  getMonthShortName(saturday.substr(3, 2)) + ", " + saturday_medium;
}

function getMonthShortName(mon) {
	if (mon == '01') {
		return january_short;
	} else if (mon == '02') {
		return february_short;
	} else if (mon == '03') {
		return march_short;
	} else if (mon == '04') {
		return april_short;
	} else if (mon == '05') {
		return may_short;
	} else if (mon == '06') {
		return june_short;
	} else if (mon == '07') {
		return july_short;
	} else if (mon == '08') {
		return august_short;
	} else if (mon == '09') {
		return september_short;
	} else if (mon == '10') {
		return october_short;
	} else if (mon == '11') {
		return november_short;
	} else if (mon == '12') {
		return december_short;
	}
}

function clearAllEvents() {
	var calendarDaily = document.getElementById("daily");
	var calendarWeekly = document.getElementById("weekly");
	var calendarMonthly = document.getElementById("monthly");

	clearEvents(calendarDaily);
	clearEvents(calendarWeekly);
	clearEvents(calendarMonthly);
}

function clearEvents(obj) {
	var calendardiv = obj.getElementsByTagName("div");
	for (var i = 0; i < calendardiv.length; i++){
		if (calendardiv[i].className.indexOf("event") != -1){
			calendardiv[i].style.display = "none";
		}
	}
} 

function clearEventBox() {
	var month = String(calendarDate.getMonth() + 1);
	var year = String(calendarDate.getFullYear());
	var day = String(calendarDate.getDate()); 

	if (month.length == 1) month = "0" + month;
	if (day.length == 1) day = "0" + day;

	Dom.get('eventId').value = "";
	Dom.get('eventDateDay').value = day;
	Dom.get('eventDateMonth').value = month;
	Dom.get('eventDateYear').value = year;
	
	var dt = new Date();
	var hours = String(dt.getHours());
	if (hours.length == 1) hours = "0" + hours;
	Dom.get('eventStartTime').value =  hours + ":00";

	if (Dom.get('eventStartTime').selectedIndex + 2 < Dom.get('eventStartTime').options.length) {
		Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex + 2;
	} else {
		Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex;
	}
	Dom.get('eventColor').value = "Blue";
	Dom.get('eventRepeatType').value = "1";
	Dom.get('eventDescription').value = "";
	Dom.get('eventLocation').value = "";
	Dom.get('eventReminderDays').value = "0";
	Dom.get('eventReminderMethod').value = "1";
}

function halfClearEventBox() {
	Dom.get('eventColor').value = "Blue";
	Dom.get('eventRepeatType').value = "1";
	Dom.get('eventDescription').value = "";
	Dom.get('eventLocation').value = "";
	Dom.get('eventReminderDays').value = "0";
	Dom.get('eventReminderMethod').value = "1";
}

function hoverWeekColumn(col) {
	try { Dom.get('weeklyCalendarTableHeaderClone').getElementsByTagName('td')[col].style.background = 'transparent url(images/accordion-header-reverse.gif) repeat-x scroll 0%'; } catch (e) {}
}

function unhoverWeekColumn(col) {
	try { Dom.get('weeklyCalendarTableHeaderClone').getElementsByTagName('td')[col].style.background = ''; } catch (e) {}
}

function clickWeekNewEvent(e) {
	// posXMouse and posYMouse gets set...
	getMouseXY(e);
	
	var lis = Dom.get('weekly').getElementsByTagName('li');
	for (var i=0;i<lis.length;i++) {
		var xy = getAbsolutePosition(lis[i]);
		var leftLi = xy['x'];
		var topLi = xy['y'] - Dom.get('weekly').scrollTop;
		var widthLi = lis[i].offsetWidth;
		var heightLi = lis[i].offsetHeight;
		var rightLi = leftLi + widthLi;
		var bottomLi = topLi + heightLi;
		
		if (posXMouse >= leftLi && posXMouse < rightLi &&
			posYMouse >= topLi && posYMouse < bottomLi) {
			// mouse clicked in this li
			var idLi = lis[i].id;
			if (idLi.indexOf('weekly-time-') < 0) {
				// format: weekly-00:00-27-08-2007
				if (idLi != null && idLi.indexOf('weekly-') == 0) {
					var hourLi = idLi.substr(7, 2);
					var minLi = idLi.substr(10,2);
					var dayLi = idLi.substr(13,2);
					var monLi = idLi.substr(16,2);
					var yearLi = idLi.substr(19,4);
					
					Dom.get('eventId').value = "";
					Dom.get('eventDateDay').value = dayLi;
					Dom.get('eventDateMonth').value = monLi;
					Dom.get('eventDateYear').value = yearLi;
					Dom.get('eventStartTime').value = hourLi + ":00";
	
					if (Dom.get('eventStartTime').selectedIndex + 2 < Dom.get('eventStartTime').options.length) {
						Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex + 2;
					} else {
						Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex;
					}
				}
				Dom.get('deleteEventButton').style.display = 'none';
				Dom.get('eventEditDialogHeader').innerHTML = new_event;
				newEvent(true);
				break;
			}
		}
	}
}

function clickDayNewEvent(e) {
	// posXMouse and posYMouse gets set...
	getMouseXY(e);
	
	var lis = Dom.get('daily').getElementsByTagName('li');
	for (var i=0;i<lis.length;i++) {
		var xy = getAbsolutePosition(lis[i]);
		var leftLi = xy['x'];
		var topLi = xy['y'] - Dom.get('daily').scrollTop;
		var widthLi = lis[i].offsetWidth;
		var heightLi = lis[i].offsetHeight;
		var rightLi = leftLi + widthLi;
		var bottomLi = topLi + heightLi;
		
		if (posXMouse >= leftLi && posXMouse < rightLi &&
			posYMouse >= topLi && posYMouse < bottomLi) {
			// mouse clicked in this li
			var idLi = lis[i].id;
			
			// format: weekly-00:00-27-08-2007
			if (idLi != null && idLi.indexOf('daily-') == 0) {
				var hourLi = "" + idLi.substr(6, 2);
				var minLi = "" + idLi.substr(9,2);
				var monLi = "" + (calendarDate.getMonth() + 1);
				var yearLi = "" + calendarDate.getFullYear();
				var dayLi = "" + calendarDate.getDate();
				
				if (monLi.length == 1) monLi = "0" + monLi;
				if (dayLi.length == 1) dayLi = "0" + dayLi;
				
				Dom.get('eventId').value = "";
				Dom.get('eventDateDay').value = dayLi;
				Dom.get('eventDateMonth').value = monLi;
				Dom.get('eventDateYear').value = yearLi;
				Dom.get('eventStartTime').value = hourLi + ":00";

				if (Dom.get('eventStartTime').selectedIndex + 2 < Dom.get('eventStartTime').options.length) {
					Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex + 2;
				} else {
					Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex;
				}
			}
			Dom.get('deleteEventButton').style.display = 'none';
			Dom.get('eventEditDialogHeader').innerHTML = new_event;
			newEvent(true);
			break;
		}
	}
}

function populateCalendarYear() {
	var combo = Dom.get('eventDateYear');
	combo.options.length = 0;
	var dt = new Date();
	var yearNow = dt.getFullYear();

	var counter = 0;
	for (var i=yearNow - 1; i < yearNow + 2; i++) {
		combo.options[counter] = new Option('' + i, '' + i);
		counter++;
	}
	combo.options[1].selected = true;
}

var weekCount = 1;
function parseMonthlyEvents(xml) {
	var monthHeader = Dom.get('monthlyCalendarTableHeader');
	var monBody = Dom.get('monthlyCalendarTableBody');
	monBody.style.visibility = 'hidden';
	
	while(monBody.firstChild) monBody.removeChild(monBody.firstChild);

	monBody.appendChild(monthHeader);

	weekCount = parseInt(getFirstCleanValByTagName(xml, 'week-count'));
	var realMonth = getFirstCleanValByTagName(xml, 'real-month');
	var dateDisplay = getFirstCleanValByTagName(xml, 'date-display');
	Dom.get('calendarTitle').innerHTML = monthly_calendar + " - " + dateDisplay;
	
	var prevMonth = "00";
	var weeks = xml.getElementsByTagName('week-event');
	if (weeks != null && weeks.length > 0) {
		for (var p=0;p<weeks.length;p++) {
			var week = weeks[p];

			var tr = document.createElement('tr');

			var days = week.getElementsByTagName('day-event');
			if (days != null && days.length > 0) {
				for (var k=0;k<days.length;k++) {
					var day = days[k];
					
					var td = document.createElement('td');
					td.className = "monthTd";
					td.setAttribute('dayNo', '' + k);
					
					var eventDay = getFirstCleanValByTagName(day, 'day-event-day');
					var eventMonth = getFirstCleanValByTagName(day, 'day-event-month');
					var eventYear = getFirstCleanValByTagName(day, 'day-event-year');
					
					td.id = "monthly-08:00-" + eventDay + "-" + eventMonth + "-" + eventYear;
					
					var str = "<div style='height:10px;'><strong ";
					var isForeign = false;
					if (eventMonth == realMonth) {
						str += "style='color:#2A2A2A'>";
						isForeign = false;
						td.setAttribute('isForeign', 'false');
 					} else {
						str += "style='color:#999999'>";
						isForeign = true;
						td.className = "monthTdForeign";
						td.setAttribute('isForeign', 'true');
					}
					if (eventMonth != prevMonth) {
						str += eventDay + " " + getMonthShortName(eventMonth) + "</strong>";
					} else {
						str += eventDay + "</strong>";
					}
					str += "</div>";
					
					prevMonth = eventMonth;

					td.innerHTML = str;
					tr.appendChild(td);
				}
			}
			monBody.appendChild(tr);
		}
	}
	parseMonthlyEvents2(xml);
	organizeMonthlyEvents();
}

function parseMonthlyEvents2(xml) {
	var monthly = Dom.get('monthly');
	var monthlyTable = Dom.get('monthlyCalendarTable');
	var monthHeight = monthly.offsetHeight;
	monthlyTable.style.height = monthHeight + "px";
	var one = monthHeight / weekCount;

	var weeks = xml.getElementsByTagName('week-event');
	if (weeks != null && weeks.length > 0) {
		for (var p=0;p<weeks.length;p++) {
			var week = weeks[p];
			var days = week.getElementsByTagName('day-event');
			if (days != null && days.length > 0) {
				for (var k=0;k<days.length;k++) {
					var day = days[k];
					var events = day.getElementsByTagName('event');
					if (events != null && events.length > 0) {
					
						var howMany = Math.floor((one - 15) / 16);
						
						var topTmp = 2;
						for (var h=0;h<events.length;h++) {
							var event = events[h];
							var eventId = getFirstCleanValByTagName(event, 'id');
							var year = getFirstCleanValByTagName(event, 'year');
							var month = getFirstCleanValByTagName(event, 'month');
							var day = getFirstCleanValByTagName(event, 'day');
							var begin = getFirstCleanValByTagName(event, 'begin');
							var end = getFirstCleanValByTagName(event, 'end');
							var color = getFirstCleanValByTagName(event, 'color');
							var note = getFirstCleanValByTagName(event, 'description');
							var hasReminder = getFirstCleanValByTagName(event, 'hasReminder');
							var repeatType = getFirstCleanValByTagName(event, 'repeatType');
							var location = getFirstCleanValByTagName(event, 'location');
							var reminderDays = getFirstCleanValByTagName(event, 'reminderDays');
							var reminderMethod = getFirstCleanValByTagName(event, 'reminderMethod');

							// if there a lot of items at the monthly view send to daily view.
							if (h >= howMany -1) {
								showToDailyArrow(year, month, day, events.length, topTmp - 6);
								break;
							}
							showMonthlyEvent(eventId, day, month, year, begin, end, color, note, hasReminder, topTmp);
							topTmp += 20;
						}
					}
				}
			}
		}
	}
}

function showToDailyArrow(year, month, day, totalEvents, topTmp) {
	var nl = document.createElement('div');
	nl.align = 'center';
	nl.style.height = '5px';
	nl.style.width = '100%';
	nl.style.fontWeight = 'bold';
	nl.style.position = "relative";
	nl.style.top = topTmp + "px";
	nl.innerHTML = "...";
	nl.style.color = '#0E56A0';
	Dom.get('monthly-08:00-' + day + "-" + month + "-" + year).appendChild(nl);
}

function showMonthlyEvent(eventId, day, month, year, begin, end, color, note, reminder, top) {
	var nw = Dom.get('eventTemplate');
	var nl = nw.cloneNode(true);
	nl.id = "eventMonthly" + eventId;
	nl.setAttribute('eventId', eventId);

	nl.className = "event" + color;
	nl.setAttribute("range", begin + " - " + end);
	nl.style.display = "block";

	nl.style.height = "15px";
	nl.style.position = "relative";
	nl.style.top = top + "px";
	var ps = nl.getElementsByTagName('p');
	for (var t=0;t<ps.length;t++) {
		var ptmp = ps[t];
		if (ptmp.id == 'eventDailyContent') {
			ptmp.innerHTML = note;
			break;
		}
	}
	Dom.get('monthly-08:00-' + day + "-" + month + "-" + year).appendChild(nl);
}

function organizeMonthlyEvents() {
	cloneMonthHeader();

	var monthly = Dom.get('monthly');
	var monthlyTable = Dom.get('monthlyCalendarTable');
	var monthHeight = monthly.offsetHeight;
	monthlyTable.style.height = monthHeight + "px";
	var one = monthHeight / weekCount;
	
	var trs = monthlyTable.getElementsByTagName('tr');
	for (var i=1;i<trs.length;i++) {
		var tds = trs[i].getElementsByTagName('td');
		for (var j=0;j<tds.length;j++) {
			var td = tds[j];
			td.style.height = one + "px";
		}
	}

	var monBody = Dom.get('monthlyCalendarTableBody');
	monBody.style.visibility = 'visible';
	new Rico.Effect.FadeTo('monthlyCalendarTable', 1, 1000, 6, {} );
}

/*
function cloneMonthHeader() {
	Dom.get('calendarDateDisplayed').innerHTML = "";
	var tbl = document.createElement('table');
	tbl.style.width = Dom.get('monthlyCalendarTable').offsetWidth + "px";
	tbl.style.height = '20px'
	tbl.id = 'calendarMonthHeadTbl';
	
	var wH = Dom.get('monthlyCalendarTableHeader');
	var wC = wH.cloneNode('true');
	wC.id = 'monthlyCalendarTableHeaderClone';
	wC.style.visibility = 'visible';
	Dom.get('calendarDateDisplayed').appendChild(tbl);
	
	tbl = Dom.get('calendarMonthHeadTbl');
	tbl.style.position = 'relative';
	tbl.style.top = '0px';
	tbl.style.left = '0px';
	tbl.style.padding = "0px";
	tbl.appendChild(wC);
	
	var tdsC = wC.getElementsByTagName('td');
	var tdsH = wH.getElementsByTagName('td');

	for (var i=0;i<tdsC.length;i++) {
		tdsC[i].style.width = (tdsH[i].offsetWidth + 1) + "px";
	}
}
*/

function cloneMonthHeader() {
	Dom.get('calendarDateDisplayed').innerHTML = "<table align='left' id='calendarMonthHeadTbl' cellspacing='0' cellpadding='0' border='0'><tbody id='calendarMonthHeadTblBody'></tbody></table>";
	var tbl = Dom.get('calendarMonthHeadTbl');
	tbl.width = (Dom.get('monthlyCalendarTable').offsetWidth) + "px";
	tbl.style.height = '20px'
	
	var wH = Dom.get('monthlyCalendarTableHeader');
	var wC = wH.cloneNode(true);
	wC.id = 'monthlyCalendarTableHeaderClone';
	wC.style.visibility = 'visible';
	Dom.get('calendarDateDisplayed').appendChild(tbl);
	
	tbl.style.position = 'relative';
	tbl.style.top = '0px';
	tbl.style.left = '0px';
	tbl.style.padding = "0px";
	
	var tbody = Dom.get('calendarMonthHeadTblBody');
	tbody.appendChild(wC);
	
	var tdsC = wC.getElementsByTagName('td');
	var tdsH = wH.getElementsByTagName('td');

	for (var i=0;i<tdsC.length;i++) {
		tdsC[i].style.width = (tdsH[i].offsetWidth) + "px";
	}
}


function hoverMonthColumn(e) {
	unhoverMonthColumn();
	// posXMouse and posYMouse gets set...
	getMouseXY(e);
	
	var lis = Dom.get('monthly').getElementsByTagName('td');
	for (var i=0;i<lis.length;i++) {
		var xy = getAbsolutePosition(lis[i]);
		var leftLi = xy['x'];
		var topLi = xy['y'];
		var widthLi = lis[i].offsetWidth;
		var heightLi = lis[i].offsetHeight;
		var rightLi = leftLi + widthLi;
		var bottomLi = topLi + heightLi;
		
		if (posXMouse >= leftLi && posXMouse < rightLi &&
			posYMouse >= topLi && posYMouse < bottomLi) {
			// mouse clicked in this li
			var col = lis[i].getAttribute('dayNo');
			if (lis[i].getAttribute('isForeign') == 'false') {
				if (!document.all) {
					lis[i].style.backgroundColor = '#ffffff';
				}
				lis[i].setAttribute('hovered', 'true');
			}
			try { Dom.get('monthlyCalendarTableHeaderClone').getElementsByTagName('td')[col].style.background = 'transparent url(images/accordion-header-reverse.gif) repeat-x scroll 0%'; } catch (e) {}
			break;
		}
	}
}

function unhoverMonthColumn() {
	try {
		var tds = Dom.get('monthlyCalendarTableHeaderClone').getElementsByTagName('td');
		for (var i=0; i<tds.length;i++) {
			tds[i].style.background = '';
		}

		var lis = Dom.get('monthly').getElementsByTagName('td');
		for (var i=0;i<lis.length;i++) {
			if (lis[i].getAttribute('hovered') == 'true') {
				lis[i].style.backgroundColor = '#f4f4f4';
				lis[i].setAttribute('hovered', 'false');
			}
		}
	} catch (e) {}
}

function clickMonthNewEvent(e) {
	// posXMouse and posYMouse gets set...
	getMouseXY(e);
	
	var lis = Dom.get('monthly').getElementsByTagName('td');
	for (var i=0;i<lis.length;i++) {
		var xy = getAbsolutePosition(lis[i]);
		var leftLi = xy['x'];
		var topLi = xy['y'] - Dom.get('monthly').scrollTop;
		var widthLi = lis[i].offsetWidth;
		var heightLi = lis[i].offsetHeight;
		var rightLi = leftLi + widthLi;
		var bottomLi = topLi + heightLi;
		
		if (posXMouse >= leftLi && posXMouse < rightLi &&
			posYMouse >= topLi && posYMouse < bottomLi) {
			// mouse clicked in this li
			
			if (lis[i].getAttribute('isForeign') == 'false') {
				var idLi = lis[i].id;
				
				// format: monthly-08:00-27-08-2007
				if (idLi != null && idLi.indexOf('monthly-') == 0) {
					var hourLi = idLi.substr(8, 2);
					var minLi = idLi.substr(11,2);
					var dayLi = idLi.substr(14,2);
					var monLi = idLi.substr(17,2);
					var yearLi = idLi.substr(20,4);
					
					Dom.get('eventId').value = "";
					Dom.get('eventDateDay').value = dayLi;
					Dom.get('eventDateMonth').value = monLi;
					Dom.get('eventDateYear').value = yearLi;
					Dom.get('eventStartTime').value = hourLi + ":00";
	
					if (Dom.get('eventStartTime').selectedIndex + 2 < Dom.get('eventStartTime').options.length) {
						Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex + 2;
					} else {
						Dom.get('eventEndTime').selectedIndex = Dom.get('eventStartTime').selectedIndex;
					}
				}
				Dom.get('deleteEventButton').style.display = 'none';
				Dom.get('eventEditDialogHeader').innerHTML = new_event;
				newEvent(true);
				break;
			}
		}
	}
}

function hoverWeekItem(e) {
	unhoverWeekItem();
	// posXMouse and posYMouse gets set...
	getMouseXY(e);
	
	var lis = Dom.get('weekly').getElementsByTagName('li');
	for (var i=0;i<lis.length;i++) {
		var xy = getAbsolutePosition(lis[i]);
		var leftLi = xy['x'];
		var topLi = xy['y'] - Dom.get('weekly').scrollTop;
		var widthLi = lis[i].offsetWidth;
		var heightLi = lis[i].offsetHeight;
		var rightLi = leftLi + widthLi;
		var bottomLi = topLi + heightLi;
		
		if (posXMouse >= leftLi && posXMouse < rightLi &&
			posYMouse >= topLi && posYMouse < bottomLi) {
			// mouse over in this li
			if (!document.all) {
				lis[i].style.backgroundColor = '#ffffff';
			}
			lis[i].setAttribute('hovered', 'true');
			break;
		}
	}
}

function unhoverWeekItem() {
	try {
		var lis = Dom.get('weekly').getElementsByTagName('li');
		for (var i=0;i<lis.length;i++) {
			if (lis[i].getAttribute('hovered') == 'true') {
				lis[i].style.backgroundColor = '#f4f4f4';
				lis[i].setAttribute('hovered', 'false');
			}
		}
	} catch (e) {}
}


function hoverDayItem(e) {
	unhoverDayItem();
	// posXMouse and posYMouse gets set...
	getMouseXY(e);
	
	var lis = Dom.get('daily').getElementsByTagName('li');
	for (var i=0;i<lis.length;i++) {
		var xy = getAbsolutePosition(lis[i]);
		var leftLi = xy['x'];
		var topLi = xy['y'] - Dom.get('daily').scrollTop;
		var widthLi = lis[i].offsetWidth;
		var heightLi = lis[i].offsetHeight;
		var rightLi = leftLi + widthLi;
		var bottomLi = topLi + heightLi;
		
		if (posXMouse >= leftLi && posXMouse < rightLi &&
			posYMouse >= topLi && posYMouse < bottomLi) {
			// mouse over in this li
			if (!document.all) {
				lis[i].style.backgroundColor = '#ffffff';
			}
			lis[i].setAttribute('hovered', 'true');
			break;
		}
	}
}

function unhoverDayItem() {
	try {
		var lis = Dom.get('daily').getElementsByTagName('li');
		for (var i=0;i<lis.length;i++) {
			if (lis[i].getAttribute('hovered') == 'true') {
				lis[i].style.backgroundColor = '#f4f4f4';
				lis[i].setAttribute('hovered', 'false');
			}
		}
	} catch (e) {}
}

function getTasks() {
	var url = "tasks/getTasks.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						Dom.get('tasksLoading').style.display = "none";
						Dom.get('tasksList').innerHTML = "";
						Dom.get('tasksList').style.display = 'block';
						
						var tasks = o.responseXML.getElementsByTagName('tasks');
						if (tasks != null) {
							var nodes = tasks[0].childNodes;
							for (var p=0;p<nodes.length;p++) {
								try {
									var task = nodes[p];
									var taskId = getFirstCleanValByTagName(task, 'id');
									var checked = getFirstCleanValByTagName(task, 'checked');
									var color = getFirstCleanValByTagName(task, 'color');
									var note = getFirstCleanValByTagName(task, 'description');
									var priority = getFirstCleanValByTagName(task, 'priority');
									var recordDate = getFirstCleanValByTagName(task, 'record-date');

									/*
									 sample item
									<li class="three"><img src="images/unchecked.gif" onlick="checkListTask(this)"/> <span>Hebelek yapilacak ve ardindan da su bu yapilacak</span></li>
									 */
									var tt = Dom.get('taskTemplate');
									var tc = tt.cloneNode(true);
									if (priority == '1') {
										tc.className = "one";
									} else if (priority == '2') {
										tc.className = "two";
									} else if (priority == '3') {
										tc.className = "three";
									}
									tc.id = "task" + taskId;
									tc.setAttribute('taskid', taskId);
									if (checked == 'true') {
										tc.getElementsByTagName('img')[0].setAttribute('checked', "true");
										tc.getElementsByTagName('img')[0].src = "images/checked.gif";
										tc.getElementsByTagName('span')[0].style.color = '#999999';
										tc.getElementsByTagName('span')[0].style.textDecoration = 'line-through';
									} else {
										tc.getElementsByTagName('img')[0].setAttribute('checked', "false");
										tc.getElementsByTagName('img')[0].src = "images/unchecked.gif";
										tc.getElementsByTagName('span')[0].style.color = '#2a2a2a';
										tc.getElementsByTagName('span')[0].style.textDecoration = 'none';
										
									}
									tc.getElementsByTagName('span')[0].innerHTML = htmlSpecialChars(note);
									tc.style.display = 'block';
									Dom.get('tasksList').appendChild(tc);
								} catch (e) {
									alert(e.message);
								}
							}
							initialScrH = Dom.get('tasks').scrollHeight;
						}
					}
				} catch (e) {
					alert(e.message);
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

function checkListTask(obj) {
	var taskId = obj.parentNode.getAttribute('taskId');

	var paramData = "id=" + taskId;
	var url = "tasks/clickTask.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == 'ok') {
					if (obj.getAttribute('checked') == 'false') {
						obj.parentNode.getElementsByTagName('img')[0].setAttribute('checked', "true");
						obj.src = "images/checked.gif";
						obj.parentNode.getElementsByTagName('span')[0].style.textDecoration = 'line-through';
						obj.parentNode.getElementsByTagName('span')[0].style.color = '#999999';
					} else {
						obj.parentNode.getElementsByTagName('img')[0].setAttribute('checked', "false");
						obj.src = "images/unchecked.gif";
						obj.parentNode.getElementsByTagName('span')[0].style.textDecoration = 'none';
						obj.parentNode.getElementsByTagName('span')[0].style.color = '#2a2a2a';
						obj.parentNode.style.backgroundColor = '';
						selectedTask = null;
						disableButton(Dom.get('deleteTask'));
					}
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

var selectedTask;
function selectTask(obj) {
	var lis = obj.parentNode.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			lis[i].style.backgroundColor = '';
			lis[i].setAttribute('clicked', 'false');
			lis[i].getElementsByTagName('img')[1].style.display = 'none';
		}
	}
	selectedTask = null;
	
	selectedTask = obj.getAttribute('taskId');
	enableButton(Dom.get('deleteTask'));

	obj.style.backgroundColor = '#E6ECF1';
	obj.setAttribute('clicked', 'true');
	obj.getElementsByTagName('img')[1].style.display = '';
}

function deleteTask() {
	if (selectedTask == null) return;
	
	var params = "id=" + selectedTask;
	
	var url = "tasks/deleteTask.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				initialScrH = null;
				getTasks();
				
				/*
				var deleted = Dom.get('task' + selectedTask);
				deleted.parentNode.removeChild(deleted);
				selectedTask = null;
				disableButton(Dom.get('deleteTask'));
				var tk = Dom.get('tasks');
				initialScrH = tk.scrollHeight;
				*/
			}
	  },

	  failure: 	function(o) {
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
}

function newTask() {
	var td = Dom.get('taskDescription')
	td.value = "";
	Dom.get('taskPriority').value = '2';
	Dom.get('taskId').value = "";
	showDialog('createTask');
	td.focus();
}

function saveTask() {
	var note = myEncode(Dom.get('taskDescription').value);
	var taskId = Dom.get('taskId').value;
	var priority = Dom.get('taskPriority').value;
	
	var params = "id=" + taskId + "&content=" + note + "&priority=" + priority;
	
	var url = "tasks/saveTask.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == 'ok') {
					hideDialog('createTask');
					getTasks();
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
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
}

function hoverTask(obj) {
	if (obj.getAttribute('clicked') != 'true') {
		obj.style.backgroundColor = '#f4f4f4';
	}
}

function unhoverTask(obj) {
	var lis = obj.parentNode.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			if (lis[i].getAttribute('clicked') != 'true') {
				lis[i].style.backgroundColor = '';
			}
		}
	}
}

function editTask(obj) {
	var taskId = obj.parentNode.getAttribute('taskId');

	var params = "id=" + taskId;

	var url = "tasks/getTask.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						var xml = o.responseXML;
						var taskId = getFirstCleanValByTagName(xml, 'id');
						var checked = getFirstCleanValByTagName(xml, 'checked');
						var color = getFirstCleanValByTagName(xml, 'color');
						var note = getFirstCleanValByTagName(xml, 'description');
						var priority = getFirstCleanValByTagName(xml, 'priority');
						var recordDate = getFirstCleanValByTagName(xml, 'record-date');

						var td = Dom.get('taskDescription')
						td.value = note;
						Dom.get('taskPriority').value = priority;
						Dom.get('taskId').value = taskId;
						showDialog('createTask');
						td.focus();
					}
				} catch (e) {
					alert(e.message);
				}
			}
	  },

	  failure: 	function(o) {
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
}

function scrollTaskList() {
	var tk = Dom.get('tasks');
	var tb = Dom.get('taskButtons');
	var scr = tk.scrollTop;
	var scrH = tk.scrollHeight;
	
	var newTop = (tk.offsetHeight + scr);
	if (initialScrH == null || newTop > initialScrH) {
		newTop = initialScrH;
	}
	tb.style.top = newTop + "px";
}

var waitingQueue = new Array();
var waitingAlerts = new Array();

function checkAlerts() {
	var url = "calendar/checkAlert.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {

						var events = o.responseXML.getElementsByTagName('events');
						if (events != null && events.length > 0) {
							var nodes = events[0].childNodes;
							for (var p=0;p<nodes.length;p++) {
								try {
									var event = nodes[p];

									var eventId = getFirstCleanValByTagName(event, 'id');
									var isWaiting = false;
									for (var i=0;i<waitingQueue.length;i++) {
										if (waitingQueue[i] == eventId) {
											isWaiting = true;
										}
									}
									if (!isWaiting) {
										waitingQueue[waitingQueue.length] = eventId;
										waitingAlerts[waitingAlerts.length] = event;
									}
								} catch (e) {
									alert(e.message);
								}
							}
							getOneFromAlertQueue();
						}
					}
				} catch (e) {
					alert(e.message);
				} finally {
					window.setTimeout(checkAlerts, 60000);
				}
			}
	  },

	  failure: 	function(o) {
		window.setTimeout(checkAlerts, 60000);
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function hideThisAndShowNewAlert(noSetOk) {
	if (!noSetOk) {
		setReminderOk(Dom.get('eventAlert').getAttribute('eventId'));
	}
	document.title = title;
	hideCurtain('whiteCurtain');
	new Rico.Effect.FadeTo('eventAlert', 0, 500, 3, {complete:function() {getOneFromAlertQueue();}});
}

function getOneFromAlertQueue() {
	if (waitingQueue.length == 0) {
		var evAl = Dom.get('eventAlert');
		if (eval != null && evAl.style.display == 'block') {
			hideDialog('eventAlert');
		}
	} else {
		var event = waitingAlerts[0];
		
		var eventId = getFirstCleanValByTagName(event, 'id');
		var year = getFirstCleanValByTagName(event, 'year');
		var month = getFirstCleanValByTagName(event, 'month');
		var day = getFirstCleanValByTagName(event, 'day');
		var begin = getFirstCleanValByTagName(event, 'begin');
		var end = getFirstCleanValByTagName(event, 'end');
		var color = getFirstCleanValByTagName(event, 'color');
		var note = getFirstCleanValByTagName(event, 'description');
		var hasReminder = getFirstCleanValByTagName(event, 'hasReminder');
		var repeatType = getFirstCleanValByTagName(event, 'repeatType');
		var location = getFirstCleanValByTagName(event, 'location');
		var reminderDays = getFirstCleanValByTagName(event, 'reminderDays');
		var reminderMethod = getFirstCleanValByTagName(event, 'reminderMethod');

		Dom.get('alertEventNote').innerHTML = "<strong>" + note + "</strong><br/><br/>Duration: " + begin + " - " + end + "<br/>" + location_txt + ": " + location;
		Dom.get('alarmRingImg').src = "images/alarm-ring-" + color + ".gif"
		Dom.get('eventAlert').setAttribute('eventId', eventId);

		if (color == 'Red') {
			Dom.get('eventAlert').style.borderColor = '#b93636';
			Dom.get('eventAlertDialogHeader').style.color = '#b93636';
		} else if (color == 'Blue') {
			Dom.get('eventAlert').style.borderColor = '#0e56a0';
			Dom.get('eventAlertDialogHeader').style.color = '#0e56a0';
		} else if (color == 'Orange') {
			Dom.get('eventAlert').style.borderColor = '#ed8600';
			Dom.get('eventAlertDialogHeader').style.color = '#ed8600';
		} else if (color == 'Green') {
			Dom.get('eventAlert').style.borderColor = '#61b544';
			Dom.get('eventAlertDialogHeader').style.color = '#61b544';
		}
		
		// remove the shown item from the waiting queue. 
		var newWaitingAlerts = new Array();
		var newWaitingQueue = new Array();
		for (var i=0;i<waitingAlerts.length;i++) {
			if (i != 0) {
				newWaitingAlerts[i-1] = waitingAlerts[i];
				newWaitingQueue[i-1] = waitingQueue[i];
			}
		}
		waitingAlerts = newWaitingAlerts;
		waitingQueue = newWaitingQueue;

		// show it and done. slowly fade in the calendar alert
		showCurtain('whiteCurtain', .60);
		var d = Dom.get('eventAlert');
		d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
		d.style.top = (((windowheight - getHeight(d)) / 2) - 50) + "px";
		changeComboVisibility('hidden');
		new Rico.Effect.FadeTo('eventAlert', 1, 500, 3, {} );
		d.style.display = "block";

		document.title = "1 " + reminder_txt;
	}
}

function setReminderOk(eventId) {
	var params = "id=" + eventId;
	var url = "calendar/setReminderOk.cl";

	var callback = {
	  success: 	function(o) {},

	  failure: 	function(o) {
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, params);
}

function snoozeAlert() {
	window.setTimeout(checkAlerts, 300000);
	hideThisAndShowNewAlert(true);
}
