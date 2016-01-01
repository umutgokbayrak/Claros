<%@ page contentType="text/html; charset=UTF-8" %>

<div id="preferences" style="width: 584px;max-height:650px;">
<table border="0" cellspacing="0" width="576" cellpadding="0">   
	<tr>
		<td class="prefTop">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td width="1%"><img src="images/top-pref.png" style="margin-right: 5px;"/></td>
					<td width="99%"><i18n:message key="preferences"/></td>
				</tr>
			</table>
			
		</td>
	</tr>
	<tr>
		<td class="prefBody">
			<table border="0" cellspacing="0" cellpadding="0" class="prefTblBack" align="center">
				<tr>
					<td>
						<table border="0" cellspacing="0" cellpadding="0" align="center" id="prefTabs">
							<tr>
								<td id="prefTabMail" class="prefTabActive" valign="middle" align="center" onclick="clickPrefTab('Mail');">
									<i18n:message key="mail"/>
								</td>
								<td id="prefTabContacts" class="prefTabNotActive" valign="middle" align="center" onclick="clickPrefTab('Contacts');">
									<i18n:message key="contacts"/>
								</td>
								<td id="prefTabChat" class="prefTabNotActive" valign="middle" align="center" onclick="clickPrefTab('Chat');">
									<i18n:message key="chat"/>
								</td>
								<td id="prefTabRSS" class="prefTabNotActive" valign="middle" align="center" onclick="clickPrefTab('RSS');">
									<i18n:message key="news"/>
								</td>
								<td width="172" id="prefTabSpace">&nbsp;</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td id="prefTabDivs">
						<div id="prefMail">
							<table border="0" cellspacing="1" cellpadding="3" width="100%">
								<tr>
									<td width="1%">
										<i18n:message key="full.name"/> : 
									</td>
									<td width="99%">
										<input type="text" id="prefFullName" class="txt200"/>
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key="email.address"/> : 
									</td>
									<td>
										<input type="text" id="prefEmailAddress" class="txt200"/>
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key="reply.to.address"/> : 
									</td>
									<td>
										<input type="text" id="prefReplyTo" class="txt200"/>
									</td>
								</tr>
								<tr>
									<td><i18n:message key="new.mail.sound.alert"/> : </td>
									<td>
										<select id="prefMailSound" class="txt100" style="border: 1px solid #999999;">
											<option value="yes"><i18n:message key="on"/></option>
											<option value="no"><i18n:message key="off"/></option>
										</select>
									</td>			
								</tr>
								<!-- 
								<tr>
									<td>
										<i18n:message key="spam.analysis"/> : 
									</td>
									<td>
										<select id="prefSpamAnalysis" class="txt150" style="border: 1px solid #999999;">
											<option value="-1">no spam analysis</option>
											<option value="0.9"><i18n:message key="very.light.mode"/></option>
											<option value="0.8"><i18n:message key="light.mode"/></option>
											<option value="0.6"><i18n:message key="moderate"/></option>
											<option value="0.2"><i18n:message key="heavy.mode"/></option>
											<option value="0.1"><i18n:message key="paranoid"/></option>
										</select>
									</td>
								</tr>
								 -->
								<tr>
									<td>
										<i18n:message key="save.sent.mail"/> : 
									</td>
									<td>
										<select id="prefSaveSent" class="txt150" style="border: 1px solid #999999;">
											<option value="yes"><i18n:message key="yes"/></option>
											<option value="no"><i18n:message key="no"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key="signature"/> : 
									</td>
									<td>
										<textarea id="prefSignature" wrap="hard" rows="3" cols="20" style="font-family: Arial, Helvetica, sans-serif;font-size:11px;height:60px;border: 1px solid #999999;width: 200px;"></textarea>
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key="signature.position"/> : 
									</td>
									<td>
										<select id="prefSignaturePos" class="txt150" style="border: 1px solid #999999;">
											<option value="top"><i18n:message key="insert.at.top"/></option>
											<option value="bottom"><i18n:message key="insert.at.bottom"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key="send.read.receipt"/> : 
									</td>
									<td>
										<select id="prefSendReadReceipt" class="txt150" style="border: 1px solid #999999;">
											<option value="prompt"><i18n:message key="send.read.receipt.prompt"/></option>
											<option value="always"><i18n:message key="send.read.receipt.always"/></option>
											<option value="never"><i18n:message key="send.read.receipt.never"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<td><img src="images/space.gif" width="140" height="1"/></td>
									<td><img src="images/space.gif" width="100" height="1"/></td>
								</tr>
							</table>
						</div>
						<div id="prefContacts" style="display:none;">
							<table border="0" cellspacing="1" cellpadding="3" width="100%">
								<!-- 
								<tr>
									<td width="1%">
										<i18n:message key="my.contacts.are.spam.free"/> : 
									</td>
									<td width="99%">
										<select id="prefSafeContacts" class="txt150" style="border: 1px solid #999999;">
											<option value="yes"><i18n:message key="yes"/></option>
											<option value="no"><i18n:message key="no"/></option>
										</select>
									</td>
								</tr>
								 -->
								<tr>
									<td width="1%">
										<i18n:message key="save.recipients"/> : 
									</td>
									<td width="99%">
										<select id="prefSaveSentContacts" class="txt150" style="border: 1px solid #999999;">
											<option value="yes"><i18n:message key="yes"/></option>
											<option value="no"><i18n:message key="no"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key="contact.display"/> : 
									</td>
									<td>
										<select id="prefDisplayType" class="txt150" style="border: 1px solid #999999;">
											<option value="nameFirst"><i18n:message key="first.middle.last"/></option>
											<option value="surnameFirst"><i18n:message key="last.first.middle"/></option>
										</select>
									</td>
								</tr>
								<tr>
									<td><img src="images/space.gif" width="170" height="1"/></td>
									<td><img src="images/space.gif" width="100" height="1"/></td>
								</tr>
							</table>
						</div>
						<div id="prefChat" style="display:none;">
							<table border="0" cellspacing="1" cellpadding="3" width="100%">
								<tr>
									<td width="1%">
										<i18n:message key="set.status.away.after"/> : 
									</td>
									<td width="99%">
										<input type="text" value="15" id="prefChatAwayMins" class="txt50" style="width: 25px;"/> <i18n:message key="mins"/>
									</td>
								</tr>
								<tr>
									<td><i18n:message key="new.msg.sound.alert"/> : </td>
									<td>
										<select id="prefChatSound" class="txt100" style="border: 1px solid #999999;">
											<option value="yes"><i18n:message key="on"/></option>
											<option value="no"><i18n:message key="off"/></option>
										</select>
									</td>			
								</tr>
								<tr>
									<td><img src="images/space.gif" width="140" height="1"/></td>
									<td><img src="images/space.gif" width="100" height="1"/></td>
								</tr>
							</table>
						</div>
						<div id="prefRSS" style="display:none;">
							<table border="0" cellspacing="1" cellpadding="3" width="100%">
								<tr>
									<td width="1%">
										<i18n:message key="rss.feed.url"/> : 
									</td>
									<td width="1%">
										<input type="text" id="prefNewsUrl" style="width: 300px;"/>
									</td>
									<td width="98%">
										<a href="popups/whatis_rss.jsp" target="_blank"><img src="images/info_small.gif" border="0"/></a>
									</td>
								</tr>
								<tr>
									<td><img src="images/space.gif" width="120" height="1"/></td>
									<td><img src="images/space.gif" width="1" height="1"/></td>
									<td><img src="images/space.gif" width="1" height="1"/></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td class="prefBottom"><img src="images/space.gif" height="7" width="7"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="height: 53px;background-image: url('images/pref-back-bottom.gif');background-repeat: no-repeat;" valign="top">
			<table border="0" width="100" cellspacing="1" cellpadding="3" align="right" style="margin-right: 15px;">
				<tr>
					<td width="50%" align="right">
					  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
						<tr>
						  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
						  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="savePreferences();"><i18n:message key="save"/></td>
						  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
						</tr>
					  </table>
					</td>
					<td width="50%">
					  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50" id="prefCancel">
						<tr>
						  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
						  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hidePreferences();"><i18n:message key="cancel"/></td>
						  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
						</tr>
					  </table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
