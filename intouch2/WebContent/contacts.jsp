<%@ page contentType="text/html; charset=UTF-8" %>

<div id="contactFolders">
	<div id="contactList">
		<div class="inner">
			<ul id="tools" style="background: transparent;overflow: hidden;margin-top: 5px;margin-right: 10px;overflow: hidden;float: left;">
				<li id="addContact" style="background: none;"><a href="javascript:clearContactForm();Dom.get('contFirstName').focus();"><span style="background: transparent;"><img alt="" src="images/add-contact.gif" id="addContactImg"/></span><i18n:message key="add.contact"/></a></li>
				<!-- <li id="addGroup" style="background: none;"><a href="javascript:addGroup();"><span style="background: transparent;"><img alt="" src="images/add-group.gif" id="addGroupImg"/></span><i18n:message key="add.group"/></a></li>  -->
				<li id="searchContactLi" style="background:none;width: 150px;float: right;">
					<table border="0" cellspacing="0" cellpadding="0" id="searchContact">
						<tr>
							<td width="127"><input type="text" id="searchContactTxt" name="searchContactTxt" onkeyup="searchContact();"/></td>
							<td width="23" align="left" style="float: left;"><a href="javascript:cancelSearchContact();" style="float: left;"><img src="images/search-cancel.gif" border="0" style="margin-top: 6px;margin-right: 9px;margin-left: -10px;"/></a></td>
						</tr>
					</table>
				</li>
			</ul>
			
			<div id="contactListReal">
				<table border="0" cellspacing="0" cellpadding="0" width="370" align="center">
					<thead id="contactListRealHead">
						<tr>
							<th style="width: 1%;">&nbsp;</th>
							<th style="width: 50%;"><i18n:message key="full.name"/>:</th>
							<th style="width: 49%; border-right: none;"><i18n:message key="mail"/>:</th>
						</tr>
					</thead>
					<tbody id="contactListRealBody">
						<tr>
							<td align="center" style="border-bottom: none;" colspan="3">
								<br /><br /><br /><div align="center"><img src="images/chat/loading.gif" width="32" height="32"></div>	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	<ul class="buttons" id="contactActionsBtn">
		<li style="width:120px;overflow:hidden;"><a href="javascript:importContacts();"><span><img alt="" src="images/import-contacts.gif"/></span><i18n:message key="import"/></a></li>
		<li style="width:130px;overflow:hidden;"><a href="javascript:exportAllContacts();"><span><img alt="" src="images/export-contacts.gif"/></span><i18n:message key="export"/></a></li>
	</ul>
</div>

<div id="contactDetails">
	<div class="contactsHolder">
		<div class="inner">
			<ul id="tools">
				<li id="saveContact"><a href="javascript:saveCheckContact();"><span><img alt="" src="images/savesender-icon.gif" id="saveContactImg"/></span><i18n:message key="save.contact"/></a></li>
				<li id="sendMailContact"><a href="javascript:contactSendMail();"><span><img alt="" src="images/send-icon.gif" id="sendMailContactImg"/></span><i18n:message key="send.mail"/></a></li>
				<li id="deleteContact"><a href="javascript:showDeleteContact();"><span><img alt="" src="images/delete-icon.gif" id="deleteContactImg"/></span><i18n:message key="delete.contact"/></a></li>
				<li id="vcardContact"><a href="javascript:exportVcardContact();"><span><img alt="" src="images/vcard-ico.gif" id="vcardContactImg"/></span><i18n:message key="save.as.vcard"/></a></li>
			</ul>
			<div><img src="images/space.gif" border=0 alt="" height="34"></div>
			<div id="accordionDiv">
			   <div id="generalContactPanel">
			     <div id="generalContactHeader" class="accordionHeader">
					<div style="color:black;padding-left: 5px;"><i18n:message key="general.information"/>:</div>
			      </div>
			      <div id="generalContactContent" class="accordionTabContentBox" style="background-color: #ffffff;">
			      	<img src="images/new-huge.gif" style="float: left;padding: 10px;padding-right: 15px;" id="seximg"/>
			      	<input type="hidden" id="contId" name="contId">
					<table border="0" cellspacing="1" cellpadding="3" style="margin-top: 10px;">
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="first.name"/> : </strong></td>
					    <td width="99%"><input type="text" name="contFirstName" id="contFirstName" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="middle.name"/> : </strong></td>
					    <td width="99%"><input type="text" name="contMiddleName" id="contMiddleName" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="last.name"/> : </strong></td>
					    <td width="99%"><input type="text" name="contLastName" id="contLastName" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="title"/> : </strong></td>
					    <td width="99%"><input type="text" name="contTitle" id="contTitle" class="txt100" maxlength="50"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="sex"/> : </strong></td>
					    <td width="99%">
					    	<select id="contSex" class="txt100" name="contSex" onchange="changeSexImage();">
								<option value=""><i18n:message key="please.select"/></option>
								<option value="M"><i18n:message key="male"/></option>
								<option value="F"><i18n:message key="female"/></option>
					    	</select>
					    </td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="primary.mail"/> : </strong></td>
					    <td width="99%"><input type="text" name="contEmailPrimary" id="contEmailPrimary" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="alternate.mail"/> : </strong></td>
					    <td width="99%"><input type="text" name="contEmailAlternative" id="contEmailAlternative" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="primary.gsm.no"/> : </strong></td>
					    <td width="99%"><input type="text" name="contGsmNoPrimary" id="contGsmNoPrimary" class="txt200" maxlength="30"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="alternate.gsm.no"/> : </strong></td>
					    <td width="99%"><input type="text" name="contGsmNoAlternative" id="contGsmNoAlternative" class="txt200" maxlength="30"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="url"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWebPage" id="contWebPage" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="nickname"/> : </strong></td>
					    <td width="99%"><input type="text" name="contNickName" id="contNickName" class="txt200" maxlength="50"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="spouse.name"/> : </strong></td>
					    <td width="99%"><input type="text" name="contSpouseName" id="contSpouseName" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="notes"/> : </strong></td>
					    <td width="99%">
					    	<textarea rows="5" cols="50" id="contPersonalNote" name="contPersonalNote" class="txtArea"></textarea>
						</td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="birthday"/> : </strong></td>
					    <td width="99%">
					    	<select id="contBirthMonth" name="contBirthMonth" class="txt75">
					    		<option value=""><i18n:message key="month"/></option>
					    		<option value="01">01</option>
					    		<option value="02">02</option>
					    		<option value="03">03</option>
					    		<option value="04">04</option>
					    		<option value="05">05</option>
					    		<option value="06">06</option>
					    		<option value="07">07</option>
					    		<option value="08">08</option>
					    		<option value="09">09</option>
					    		<option value="10">10</option>
					    		<option value="11">11</option>
					    		<option value="12">12</option>
					    	</select>&nbsp;
					    	<select id="contBirthDay" name="contBirthDay" class="txt75">
					    		<option value=""><i18n:message key="day"/></option>
					    		<option value="01">01</option>
					    		<option value="02">02</option>
					    		<option value="03">03</option>
					    		<option value="04">04</option>
					    		<option value="05">05</option>
					    		<option value="06">06</option>
					    		<option value="07">07</option>
					    		<option value="08">08</option>
					    		<option value="09">09</option>
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
					    	</select>
					    </td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="anniversary"/> : </strong></td>
					    <td width="99%">
					    	<select id="contAnniversaryMonth" name="contAnniversaryMonth" class="txt75">
					    		<option value=""><i18n:message key="month"/></option>
					    		<option value="01">01</option>
					    		<option value="02">02</option>
					    		<option value="03">03</option>
					    		<option value="04">04</option>
					    		<option value="05">05</option>
					    		<option value="06">06</option>
					    		<option value="07">07</option>
					    		<option value="08">08</option>
					    		<option value="09">09</option>
					    		<option value="10">10</option>
					    		<option value="11">11</option>
					    		<option value="12">12</option>
					    	</select>&nbsp;
					    	<select id="contAnniversaryDay" name="contAnniversaryDay" class="txt75">
					    		<option value=""><i18n:message key="day"/></option>
					    		<option value="01">01</option>
					    		<option value="02">02</option>
					    		<option value="03">03</option>
					    		<option value="04">04</option>
					    		<option value="05">05</option>
					    		<option value="06">06</option>
					    		<option value="07">07</option>
					    		<option value="08">08</option>
					    		<option value="09">09</option>
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
					    	</select>
					    </td>
					  </tr>
					</table>
			      </div>
			   </div>
			
			   <div id="homeContactPanel">
			     <div id="homeContactHeader" class="accordionHeader">
					<div style="color:black;padding-left: 5px;"><i18n:message key="home.info"/>:</div>
			      </div>
			      <div id="homeContactContent" class="accordionTabContentBox" style="background-color: #ffffff;">
			      	<img src="images/bighome.gif" style="float: left;padding: 10px;padding-right: 15px;"/>
					<table border="0" cellspacing="1" cellpadding="3" style="margin-top: 10px;">
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="address"/> : </strong></td>
					    <td width="99%">
					    	<textarea rows="5" cols="50" id="contHomeAddress" name="contHomeAddress" class="txtArea" style="height: 50px;width: 60%"></textarea>
						</td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="city"/> : </strong></td>
					    <td width="99%"><input type="text" name="contHomeCity" id="contHomeCity" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="state"/> : </strong></td>
					    <td width="99%"><input type="text" name="contHomeProvince" id="contHomeProvince" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="zip.code"/> : </strong></td>
					    <td width="99%"><input type="text" name="contHomeZip" id="contHomeZip" class="txt100" maxlength="5"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="country"/> : </strong></td>
					    <td width="99%"><input type="text" name="contHomeCountry" id="contHomeCountry" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="home.phone"/> : </strong></td>
					    <td width="99%"><input type="text" name="contHomePhone" id="contHomePhone" class="txt200" maxlength="30"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="home.fax"/> : </strong></td>
					    <td width="99%"><input type="text" name="contHomeFaks" id="contHomeFaks" class="txt200" maxlength="30"/></td>
					  </tr>
					</table>
			      </div>
			   </div>
			
			   <div id="workContactPanel">
			     <div id="workContactHeader" class="accordionHeader">
					<div style="color:black;padding-left: 5px;"><i18n:message key="work.info"/>:</div>
			      </div>
			      <div id="workContactContent" class="accordionTabContentBox" style="background-color: #ffffff;">
			      	<img src="images/office-huge.gif" style="float: left;padding: 10px;padding-right: 15px;"/>
					<table border="0" cellspacing="1" cellpadding="3" style="margin-top: 10px;">
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="company"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkCompany" id="contWorkCompany" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="job.title"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkJobTitle" id="contWorkJobTitle" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="office"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkOffice" id="contWorkOffice" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="department"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkDepartment" id="contWorkDepartment" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="profession"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkProfession" id="contWorkProfession" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="manager.name"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkManagerName" id="contWorkManagerName" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="assistant.name"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkAssistantName" id="contWorkAssistantName" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="address"/> : </strong></td>
					    <td width="99%">
					    	<textarea rows="5" cols="50" id="contWorkAddress" name="contWorkAddress" class="txtArea" style="height: 50px;width: 60%"></textarea>
						</td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="city"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkCity" id="contWorkCity" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="state"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkProvince" id="contWorkProvince" class="txt200" maxlength="255"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="zip.code"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkZip" id="contWorkZip" class="txt100" maxlength="5"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="country"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkCountry" id="contWorkCountry" class="txt200" maxlength="100"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="work.phone"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkPhone" id="contWorkPhone" class="txt200" maxlength="30"/></td>
					  </tr>
					  <tr>
					    <td width="1%" nowrap="nowrap"><strong><i18n:message key="work.fax"/> : </strong></td>
					    <td width="99%"><input type="text" name="contWorkFaks" id="contWorkFaks" class="txt200" maxlength="30"/></td>
					  </tr>
					</table>
			      </div>
			   </div>
			
			</div>

		</div>
	</div>
</div>	

<%@include file="popups/contact_saved_indicator.jsp" %>
<%@include file="popups/delete_contact_question.jsp" %>
<%@include file="contact_import.jsp" %>

<script type="text/javascript">
	var accordion = new Rico.Accordion( $('accordionDiv'), {borderColor: '#B9B9B9'});
	window.onresize = function () {
		accordion.initialize($('accordionDiv'), {borderColor: '#B9B9B9'});
	}
</script>
