<%@ page contentType="text/html; charset=UTF-8" %>

<div id="createEvent" class="dialogBox" style="display:none;width: 500px;height: 365px;z-index: 6001">
	<div class="dialogHeader" id="eventEditDialogHeader"><i18n:message key="edit.event"/></div>
	<div class="dialogBody">
		<img src="images/event.gif" class="dialogBoxIcon" style="padding-bottom: 100px;"/>
		<div class="dialogText">
			<table border="0" cellspacing="1" cellpadding="3" width="75%">
				<tr>
					<td><strong><i18n:message key="date"/>: </strong></td>
					<td id="eventEditDateDisplayed">
						<select id="eventDateDay" nohide="true" class="txt50">
							<option value="01">1</option>
							<option value="02">2</option>
							<option value="03">3</option>
							<option value="04">4</option>
							<option value="05">5</option>
							<option value="06">6</option>
							<option value="07">7</option>
							<option value="08">8</option>
							<option value="09">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
						</select>&nbsp;
						
						<select id="eventDateMonth" nohide="true" class="txt100">
							<option value="01"><i18n:message key="january.long"/></option>
							<option value="02"><i18n:message key="february.long"/></option>
							<option value="03"><i18n:message key="march.long"/></option>
							<option value="04"><i18n:message key="april.long"/></option>
							<option value="05"><i18n:message key="may.long"/></option>
							<option value="06"><i18n:message key="june.long"/></option>
							<option value="07"><i18n:message key="july.long"/></option>
							<option value="08"><i18n:message key="august.long"/></option>
							<option value="09"><i18n:message key="september.long"/></option>
							<option value="10"><i18n:message key="october.long"/></option>
							<option value="11"><i18n:message key="november.long"/></option>
							<option value="12"><i18n:message key="december.long"/></option>
						</select>&nbsp;

						<select id="eventDateYear" nohide="true" class="txt50">
						</select>
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="duration"/>: </strong></td>
					<td>
						<input type="hidden" id="eventId" value=""/>
						<select id="eventStartTime" nohide="true" class="txt50">
							<option value="00:00">00:00</option>
							<option value="00:30">00:30</option>
							<option value="01:00">01:00</option>
							<option value="01:30">01:30</option>
							<option value="02:00">02:00</option>
							<option value="02:30">02:30</option>
							<option value="03:00">03:00</option>
							<option value="03:30">03:30</option>
							<option value="04:00">04:00</option>
							<option value="04:30">04:30</option>
							<option value="05:00">05:00</option>
							<option value="05:30">05:30</option>
							<option value="06:00">06:00</option>
							<option value="06:30">06:30</option>
							<option value="07:00">07:00</option>
							<option value="07:30">07:30</option>
							<option value="08:00">08:00</option>
							<option value="08:30">08:30</option>
							<option value="09:00">09:00</option>
							<option value="09:30">09:30</option>
							<option value="10:00">10:00</option>
							<option value="10:30">10:30</option>
							<option value="11:00">11:00</option>
							<option value="11:30">11:30</option>
							<option value="12:00">12:00</option>
							<option value="12:30">12:30</option>
							<option value="13:00">13:00</option>
							<option value="13:30">13:30</option>
							<option value="14:00">14:00</option>
							<option value="14:30">14:30</option>
							<option value="15:00">15:00</option>
							<option value="15:30">15:30</option>
							<option value="16:00">16:00</option>
							<option value="16:30">16:30</option>
							<option value="17:00">17:00</option>
							<option value="17:30">17:30</option>
							<option value="18:00">18:00</option>
							<option value="18:30">18:30</option>
							<option value="19:00">19:00</option>
							<option value="19:30">19:30</option>
							<option value="20:00">20:00</option>
							<option value="20:30">20:30</option>
							<option value="21:00">21:00</option>
							<option value="21:30">21:30</option>
							<option value="22:00">22:00</option>
							<option value="22:30">22:30</option>
							<option value="23:00">23:00</option>
							<option value="23:30">23:30</option>
						</select>
						&nbsp;-&nbsp;
						<select id="eventEndTime" nohide="true" class="txt50">
							<option value="00:00">00:00</option>
							<option value="00:30">00:30</option>
							<option value="01:00">01:00</option>
							<option value="01:30">01:30</option>
							<option value="02:00">02:00</option>
							<option value="02:30">02:30</option>
							<option value="03:00">03:00</option>
							<option value="03:30">03:30</option>
							<option value="04:00">04:00</option>
							<option value="04:30">04:30</option>
							<option value="05:00">05:00</option>
							<option value="05:30">05:30</option>
							<option value="06:00">06:00</option>
							<option value="06:30">06:30</option>
							<option value="07:00">07:00</option>
							<option value="07:30">07:30</option>
							<option value="08:00">08:00</option>
							<option value="08:30">08:30</option>
							<option value="09:00">09:00</option>
							<option value="09:30">09:30</option>
							<option value="10:00">10:00</option>
							<option value="10:30">10:30</option>
							<option value="11:00">11:00</option>
							<option value="11:30">11:30</option>
							<option value="12:00">12:00</option>
							<option value="12:30">12:30</option>
							<option value="13:00">13:00</option>
							<option value="13:30">13:30</option>
							<option value="14:00">14:00</option>
							<option value="14:30">14:30</option>
							<option value="15:00">15:00</option>
							<option value="15:30">15:30</option>
							<option value="16:00">16:00</option>
							<option value="16:30">16:30</option>
							<option value="17:00">17:00</option>
							<option value="17:30">17:30</option>
							<option value="18:00">18:00</option>
							<option value="18:30">18:30</option>
							<option value="19:00">19:00</option>
							<option value="19:30">19:30</option>
							<option value="20:00">20:00</option>
							<option value="20:30">20:30</option>
							<option value="21:00">21:00</option>
							<option value="21:30">21:30</option>
							<option value="22:00">22:00</option>
							<option value="22:30">22:30</option>
							<option value="23:00">23:00</option>
							<option value="23:30">23:30</option>
						</select>
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="color"/></strong></td>
					<td>
						<select id="eventColor" nohide="true" class="txt150">
							<option value="Blue" style="background-color: #0E56A0;color:white;"><i18n:message key="blue"/></option>
							<option value="Red" style="background-color: #B93636;color:white;"><i18n:message key="red"/></option>
							<option value="Orange" style="background-color: #ED8600;color:white;"><i18n:message key="orange"/></option>
							<option value="Green" style="background-color: #61B544;color:white;"><i18n:message key="green"/></option>
						</select>
					</td>
				</tr>
				<tr class="tablelines">
					<td><strong><i18n:message key="repeating"/>: </strong></td>
					<td>
						<select id="eventRepeatType" nohide="true" class="txt150">
							<option value="1"><i18n:message key="no.repeat"/></option>
							<option value="4"><i18n:message key="every.week"/></option>
							<option value="5"><i18n:message key="every.two.weeks"/></option>
							<option value="2"><i18n:message key="every.month"/></option>
							<option value="3"><i18n:message key="every.year"/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="notes"/>: </strong></td>
					<td>
						<textarea id="eventDescription" cols="40" rows="6" class="txtArea" style="height: 60px;width: 250px;"></textarea>
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="location"/>: </strong></td>
					<td>
						<input type="text" id="eventLocation" class="txt250"/>
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="reminder"/>: </strong></td>
					<td>
						<select id="eventReminderDays" nohide="true" class="txt150">
							<option value="0" selected="selected"><i18n:message key="on.time"/></option>
							<option value="300">5 <i18n:message key="minutes"/></option>
							<option value="900">15 <i18n:message key="minutes"/></option>
							<option value="1800">30 <i18n:message key="minutes"/></option>
							<option value="3600">1 <i18n:message key="hour"/></option>
						</select>
						 <!-- <input type="hidden" id="eventReminderDays" value="0"/>  -->
					</td>
				</tr>
				<tr>
					<td><strong><i18n:message key="alert.by"/>: </strong></td>
					<td>
						<select id="eventReminderMethod" nohide="true" class="txt150">
							<option value="-1"><i18n:message key="no.alert"/></option>
							<option value="1"><i18n:message key="popup"/></option>
							<option value="2"><i18n:message key="email"/></option>
						</select>
					</td>
				</tr>
			</table>
			<br/><br/>
		</div>

		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="saveEvent();"><i18n:message key="save.event"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td align="right" id="deleteEventButton">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="deleteEvent();"><i18n:message key="delete.event"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td>
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('createEvent');"><i18n:message key="cancel"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
