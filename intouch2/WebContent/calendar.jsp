	<div id="leftColumn">
		<div id="calendars">
			<p class="title"><i18n:message key="calendars"/></p>
			<ul>
				<li class="active" id="dailyCal"><a href="javascript:layoutCalendar('daily');"><span><img alt="" src="images/calendars-icon.gif"/></span><i18n:message key="daily"/></a></li>
				<li id="weeklyCal"><a href="javascript:layoutCalendar('weekly');"><span><img alt="" src="images/calendars-icon.gif"/></span><i18n:message key="weekly"/></a></li>
				<li id="monthlyCal"><a href="javascript:layoutCalendar('monthly');"><span><img alt="" src="images/calendars-icon.gif"/></span><i18n:message key="monthly"/></a></li>
			</ul>
			<div class="smallCalendar" id="smallCalendar"></div>
		</div>
	</div>

	<div><li class="three" id="taskTemplate" style="display:none;" onclick="selectTask(this);"><img src="images/unchecked.gif" onclick="checkListTask(this)"/> <span>Hebelek yapilacak ve ardindan da su bu yapilacak</span><img src="images/forders-actions.gif" height="13" width="10" style="padding-left: 3px;padding-top: 12px;padding-left: 2px;display:none;" onclick="editTask(this)"/></li></div>
	
	<div id="rightColumn">
		<div id="tasks" style="overflow: auto;overflow-x:hidden;" onscroll="scrollTaskList();">
			<p class="title"><i18n:message key="tasks"/></p>
			<div id="tasksLoading"><br /><br /><br /><div align="center"><img src="images/chat/loading.gif" width="32" height="32"></div></div>
			<form action="#">
				<ul id="tasksList" style="display: none;">
				</ul>
			</form>
			<ul class="buttons" id="taskButtons">
				<li id="addTask"><a href="javascript:newTask();"><span><img alt="" src="images/new-icon.gif" id="addTaskImg"/></span><i18n:message key="new"/></a></li>
				<li id="deleteTask"><a href="javascript:deleteTask();"><span><img alt="" src="images/delete-icon-small.gif" id="deleteTaskImg"/></span><i18n:message key="delete"/></a></li>
			</ul>
		</div>
	</div>
	<div class="calendarholder" id="calendarholder">
		<div class="inner">
			<ul id="tools">
				<li><a href="javascript:newEvent();"><span><img alt="" src="images/new-icon.gif"/></span><i18n:message key="new.event"/></a></li>
			</ul>

			<div class="inboxTitle" id="calendarTitle"><i18n:message key="daily.calendar"/></div>
			
			<div id="eventTemplate" style="display:none;" onclick="clickEvent(this);">
				<p class="title"><span class="left">&nbsp;</span><span class="right">&nbsp;</span></p>
				<p id="eventDailyContent">&nbsp;</p>
				<div class="bottom"><span class="left">&nbsp;</span><span class="right">&nbsp;</span></div>
			</div>
 
			<div style="background: transparent url(images/accordion-header-bg.gif) repeat-x scroll 0%;color:#2A2A2A;float:left;height: 20px;width: 99%;font:bold 11px/19px arial,sans-serif;text-align: center;border-right: 1px solid #B9B9B9;border-left: 1px solid #B9B9B9;border-top: 1px solid #B9B9B9;" id="calendarDateDisplayed">&nbsp;</div>
			<div class="calendar" id="daily" style="height: 400px;overflow: auto;overflow-x:hidden;" onclick="clickDayNewEvent(event);" onmousemove="hoverDayItem(event);">
				<ul style="cursor: pointer;">
					<li style="border-top: none;" id="daily-00:00"><div class="time">00:00</div></li>
					<li id="daily-01:00"><div class="time">01:00</div></li>
					<li id="daily-02:00"><div class="time">02:00</div></li>
					<li id="daily-03:00"><div class="time">03:00</div></li>
					<li id="daily-04:00"><div class="time">04:00</div></li>
					<li id="daily-05:00"><div class="time">05:00</div></li>
					<li id="daily-06:00"><div class="time">06:00</div></li>
					<li id="daily-07:00"><div class="time">07:00</div></li>
					<li id="daily-08:00"><div class="time">08:00</div></li>
					<li id="daily-09:00"><div class="time">09:00</div></li>
					<li id="daily-10:00"><div class="time">10:00</div></li>
					<li id="daily-11:00"><div class="time">11:00</div></li>
					<li id="daily-12:00"><div class="time">12:00</div></li>
					<li id="daily-13:00"><div class="time">13:00</div></li>
					<li id="daily-14:00"><div class="time">14:00</div></li>
					<li id="daily-15:00"><div class="time">15:00</div></li>
					<li id="daily-16:00"><div class="time">16:00</div></li>
					<li id="daily-17:00"><div class="time">17:00</div></li>
					<li id="daily-18:00"><div class="time">18:00</div></li>
					<li id="daily-19:00"><div class="time">19:00</div></li>
					<li id="daily-20:00"><div class="time">20:00</div></li>
					<li id="daily-21:00"><div class="time">21:00</div></li>
					<li id="daily-22:00"><div class="time">22:00</div></li>
					<li id="daily-23:00"><div class="time">23:00</div></li>
				</ul>
			</div>

			<div class="calendar" id="weekly" style="height: 400px;overflow: auto;overflow-x:hidden;" onclick="clickWeekNewEvent(event);" onmousemove="hoverWeekItem(event);">
				<table border="0" cellspacing="0" cellpadding="0" id="weeklyCalendarTable" style="margin-top:-20px;height: 20px;width: 100%">
					<tr id="weeklyCalendarTableHeader">
						<td width="1%" style="overflow: visible;"><img src="images/space.gif" width="45" height="20"/></td>
						<td align="center" nowrap="nowrap"><strong><!-- Sunday  --> &nbsp;</strong></td>
						<td align="center" nowrap="nowrap"><strong><!-- Monday  --> &nbsp;</strong></td>
						<td align="center" nowrap="nowrap"><strong><!-- Tuesday  --> &nbsp;</strong></td>
						<td align="center" nowrap="nowrap"><strong><!-- Wednesday  --> &nbsp;</strong></td>
						<td align="center" nowrap="nowrap"><strong><!-- Thursday  --> &nbsp;</strong></td>
						<td align="center" nowrap="nowrap"><strong><!-- Friday  --> &nbsp;</strong></td>
						<td align="center" nowrap="nowrap"><strong><!-- Saturday  --> &nbsp;</strong></td>
					</tr>
					<tr>
						<td>
							<ul>
								<li style="border-top: none;" id="weekly-00:00" class="calTime"><div class="time">00:00</div><br/><div><img src="images/space.gif" width="45" height="1"/></div></li>
								<li id="weekly-time-01:00" class="calTime"><div class="time">01:00</div></li>
								<li id="weekly-time-02:00" class="calTime"><div class="time">02:00</div></li>
								<li id="weekly-time-03:00" class="calTime"><div class="time">03:00</div></li>
								<li id="weekly-time-04:00" class="calTime"><div class="time">04:00</div></li>
								<li id="weekly-time-05:00" class="calTime"><div class="time">05:00</div></li>
								<li id="weekly-time-06:00" class="calTime"><div class="time">06:00</div></li>
								<li id="weekly-time-07:00" class="calTime"><div class="time">07:00</div></li>
								<li id="weekly-time-08:00" class="calTime"><div class="time">08:00</div></li>
								<li id="weekly-time-09:00" class="calTime"><div class="time">09:00</div></li>
								<li id="weekly-time-10:00" class="calTime"><div class="time">10:00</div></li>
								<li id="weekly-time-11:00" class="calTime"><div class="time">11:00</div></li>
								<li id="weekly-time-12:00" class="calTime"><div class="time">12:00</div></li>
								<li id="weekly-time-13:00" class="calTime"><div class="time">13:00</div></li>
								<li id="weekly-time-14:00" class="calTime"><div class="time">14:00</div></li>
								<li id="weekly-time-15:00" class="calTime"><div class="time">15:00</div></li>
								<li id="weekly-time-16:00" class="calTime"><div class="time">16:00</div></li>
								<li id="weekly-time-17:00" class="calTime"><div class="time">17:00</div></li>
								<li id="weekly-time-18:00" class="calTime"><div class="time">18:00</div></li>
								<li id="weekly-time-19:00" class="calTime"><div class="time">19:00</div></li>
								<li id="weekly-time-20:00" class="calTime"><div class="time">20:00</div></li>
								<li id="weekly-time-21:00" class="calTime"><div class="time">21:00</div></li>
								<li id="weekly-time-22:00" class="calTime"><div class="time">22:00</div></li>
								<li id="weekly-time-23:00" class="calTime"><div class="time">23:00</div></li>
							</ul>
						</td>
						<td>
							<ul id="day0" style="cursor: pointer;" onmouseover="hoverWeekColumn(1);" onmouseout="unhoverWeekColumn(1);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
						<td style="border-left:1px solid #B8B8B8;">
							<ul id="day1" style="cursor: pointer;" onmouseover="hoverWeekColumn(2);" onmouseout="unhoverWeekColumn(2);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
						<td style="border-left:1px solid #B8B8B8;">
							<ul id="day2" style="cursor: pointer;" onmouseover="hoverWeekColumn(3);" onmouseout="unhoverWeekColumn(3);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
						<td style="border-left:1px solid #B8B8B8;">
							<ul id="day3" style="cursor: pointer;" onmouseover="hoverWeekColumn(4);" onmouseout="unhoverWeekColumn(4);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
						<td style="border-left:1px solid #B8B8B8;">
							<ul id="day4" style="cursor: pointer;" onmouseover="hoverWeekColumn(5);" onmouseout="unhoverWeekColumn(5);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
						<td style="border-left:1px solid #B8B8B8;">
							<ul id="day5" style="cursor: pointer;" onmouseover="hoverWeekColumn(6);" onmouseout="unhoverWeekColumn(6);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
						<td style="border-left:1px solid #B8B8B8;">
							<ul id="day6" style="cursor: pointer;" onmouseover="hoverWeekColumn(7);" onmouseout="unhoverWeekColumn(7);">
								<li style="border-top: none;" id="weekly-00:00">&nbsp;</li>
								<li id="weekly-01:00">&nbsp;</li>
								<li id="weekly-02:00">&nbsp;</li>
								<li id="weekly-03:00">&nbsp;</li>
								<li id="weekly-04:00">&nbsp;</li>
								<li id="weekly-05:00">&nbsp;</li>
								<li id="weekly-06:00">&nbsp;</li>
								<li id="weekly-07:00">&nbsp;</li>
								<li id="weekly-08:00">&nbsp;</li>
								<li id="weekly-09:00">&nbsp;</li>
								<li id="weekly-10:00">&nbsp;</li>
								<li id="weekly-11:00">&nbsp;</li>
								<li id="weekly-12:00">&nbsp;</li>
								<li id="weekly-13:00">&nbsp;</li>
								<li id="weekly-14:00">&nbsp;</li>
								<li id="weekly-15:00">&nbsp;</li>
								<li id="weekly-16:00">&nbsp;</li>
								<li id="weekly-17:00">&nbsp;</li>
								<li id="weekly-18:00">&nbsp;</li>
								<li id="weekly-19:00">&nbsp;</li>
								<li id="weekly-20:00">&nbsp;</li>
								<li id="weekly-21:00">&nbsp;</li>
								<li id="weekly-22:00">&nbsp;</li>
								<li id="weekly-23:00">&nbsp;</li>
							</ul>
						</td>
					</tr>
				</table>
			</div>
			
			<div class="calendar" id="monthly" style="height: 450px;overflow: hidden;" onmousemove="hoverMonthColumn(event);" onclick="clickMonthNewEvent(event);">
				<table border="0" cellspacing="0" cellpadding="0" width="100%" id="monthlyCalendarTable" style="margin-top:-21px;">
					<tbody id="monthlyCalendarTableBody">
						<tr id="monthlyCalendarTableHeader" style="height: 20px;visibility: hidden;">
							<td align="center" nowrap="nowrap"><strong><i18n:message key="sunday.long"/></strong></td>
							<td align="center" nowrap="nowrap"><strong><i18n:message key="monday.long"/></strong></td>
							<td align="center" nowrap="nowrap"><strong><i18n:message key="tuesday.long"/></strong></td>
							<td align="center" nowrap="nowrap"><strong><i18n:message key="wednesday.long"/></strong></td>
							<td align="center" nowrap="nowrap"><strong><i18n:message key="thursday.long"/></strong></td>
							<td align="center" nowrap="nowrap"><strong><i18n:message key="friday.long"/></strong></td>
							<td align="center" nowrap="nowrap"><strong><i18n:message key="saturday.long"/></strong></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<%@include file="popups/edit_event_popup.jsp"%>
	<%@include file="popups/edit_task_popup.jsp"%>
	