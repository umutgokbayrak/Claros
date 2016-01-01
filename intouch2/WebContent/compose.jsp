<%@ page contentType="text/html; charset=UTF-8" %>

<!-- COMPOSE STARTS -->
<div id="compose" style="display: none;">
	<div class="inboxholder">
		<div class="inner">
			<ul id="tools">
				<li id="sendMail"><a href="javascript:sendMail();"><span><img alt="" src="images/send-icon.gif" id="okMailImg"/></span><i18n:message key="send.mail"/></a></li>
				
				<%// BEST UPLOADING SOLUTION (M.O.)%>
				<%// start region%>
				<li id="attachMail">
					<div class="uploader">
						<input onchange="addNewUpload()" class="uploadbox" type="file" id="inputfile" name="inputfile" />
						<img alt="" src="images/new-icon.gif" id="attachMailImg" /><i18n:message key="attach.file" />
					</div>
				<%// end region%>

				<li id="saveMail"><a href="javascript:saveAsDraft();"><span><img alt="" src="images/save-icon.gif" id="saveMailImg"/></span><i18n:message key="save.as.draft"/></a></li>
				<li id="preferencesMail"><a href="javascript:showHidePrefsMail();"><span><img alt="" src="images/message-preferences.gif" id="preferencesMailImg"/></span><i18n:message key="options"/></a></li>
				<li id="cancelMail"><a href="javascript:cancelMail();"><span><img alt="" src="images/delete-icon.gif" id="cancelMailImg"/></span><i18n:message key="cancel"/></a></li>
			</ul>
			<div class="inboxTitle" id="inboxTitle2"><i18n:message key="compose.mail"/></div>
			<div id="composer">
                   <table width="100%" border="0" cellspacing="3" cellpadding="3" align="center">
				  <!-- Begin Compose Table -->
				  <tr>
				  	<td>
			          <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
			            <tr>
			              <td valign="top">
			              	<table width="100%" align="center" cellpadding="0" cellspacing="1">
				                <tr>
				                  <td width="100%">
									<form action="/composeAction" method="post" id="composeForm">
										<input type="hidden" name="action" value=""/>
										<input type="hidden" id="from" name="from" value=""/>
										<input type="hidden" name="htmlEmail" value="false" />
										<table border="0"  cellspacing="1"  cellpadding="3" width="100%">
											<tbody class="tableBody" >
												<tr>
													<td width="1%" nowrap="nowrap"><strong><i18n:message key="to"/>:</strong></td>
													<td width="99%" valign="middle" nowrap="nowrap">
														<input type="text" name="to" size="80" id="to"/>
														<div id="autoCompleteTo" class="autocomplete"></div>
														<!-- 
														&nbsp;
														[ <a href="javascript:showHide('bcctr');" style='color:#5A799E;font-weight:bold;'>bcc</a> ]
														 -->
													</td>
												</tr>
												<tr id="cctr">
													<td width="1%" nowrap="nowrap"><strong><i18n:message key="cc"/>:</strong> </td>
													<td width="99%">
														<input type="text" name="cc" size="80" id="cc"/> 
														<div id="autoCompleteCc" class="autocomplete"></div>
													</td>
												</tr>
												<tr id="bcctr">
													<td width="1%" nowrap="nowrap"><strong><i18n:message key="bcc"/>:</strong> </td>
													<td width="99%" nowrap="nowrap">
														<input type="text" name="bcc" size="80" id="bcc"/>
														<div id="autoCompleteBcc" class="autocomplete"></div>
													</td>
												</tr>
												<tr>
													<td width="1%" nowrap="nowrap"><strong><i18n:message key="subject"/>:</strong> </td>
													<td width="99%" nowrap="nowrap"><input type="text" name="subject" id="subject" size="80" onkeydown="return(subjectJump(event));"/></td>
												</tr>
												<tr id="messageOptions" style="display: none;">
													<td width="1%" nowrap="nowrap">&nbsp;</td>
													<td width="99%" nowrap="nowrap">
														<strong><i18n:message key="priority"/>:</strong>
														<select name="priority" id="priority" style="border: 1px solid #999999;font-size: 9px;" nohide="true">
														  <option value="5"><i18n:message key="priority.low"/></option>
														  <option value="3" selected><i18n:message key="priority.normal"/></option>
														  <option value="1"><i18n:message key="priority.high"/></option>
														</select>&nbsp;&nbsp;&nbsp;&nbsp;
														<strong><i18n:message key="sensitivity"/>:</strong>
														<select name="sensitivity" id="sensitivity" style="border: 1px solid #999999;font-size: 9px;" nohide="true">
														  <option value="1" selected><i18n:message key="sensitivity.normal"/></option>
														  <option value="2"><i18n:message key="sensitivity.personal"/></option>
														  <option value="3"><i18n:message key="sensitivity.private"/></option>
														  <option value="4"><i18n:message key="sensitivity.confidential"/></option>
														</select>&nbsp;&nbsp;&nbsp;&nbsp;
														<strong><i18n:message key="request.read.receipt"/>:</strong>
														<input name="requestReceiptNotification" id="requestReceiptNotification" type="checkbox" value="1" style="border: 1px solid #999999;font-size: 9px;width:15px;">
													</td>
												</tr>
												<tr id="attachmentstr" style="display:none;background-color: #F6F6F6;">
													<td width="100%" nowrap="nowrap" colspan="2" style="padding-right: 10px;">
														<div style="border: 1px solid #d2d2d2;width: 100%;overflow: visible;padding: 4px;">
															<table border="0" cellspacing="0" cellpadding="0" width="100%">
																<tr>
																	<td style="float: left;" width="99%">
																		<div style="float: left;">
																			<ul id="composeAttachmentList">
																				<!-- 
																				<li><img src="images/attachment.gif"/><span>hebelek.jpg (120KB)</span><a href="#" onclick="removeAttach()" attid="1">Remove</a></li>
																				<li><img src="images/uploading.gif"/><span>cabbar.doc (148KB)</span><a href="#" onclick="removeAttach()" attid="2">Remove</a></li>
																				 -->
																			</ul>
																		</div>
																	</td>
																	<td align="right" nowrap="nowrap" width="1%" valign="top">
																		<div style="float: right;font-size: 11px;font-weight:bold;color: #999999;padding-right: 10px;">
																			<i18n:message key="total.size"/>: <span id="totalSize">0K</span>
																		</div>
																	</td>
																</tr>
															</table>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
										<br/>
										<script>
											tinyMCE.init({
												mode : "exact",
												theme : "advanced",
												elements: "",
												plugins : "iespell",
												theme_advanced_toolbar_location : "top",
												theme_advanced_toolbar_align : "left",
												theme_advanced_buttons1 : "separator, newdocument, bold, italic, underline, strikethrough, fontsizeselect, separator, justifyleft, justifycenter, justifyright, justifyfull, separator, bullist, numlist, separator, outdent, indent, separator, link, image, forecolor, backcolor, charmap, separator, iespell, code",
												theme_advanced_buttons2 : "",
												theme_advanced_buttons3 : "",
												content_css : "css/editor.css",
												language : lang,
												force_p_newlines: false,
												force_br_newlines: true,
												auto_resize : false,
												verify_html : "false"
											});
										
										</script>
										<textarea id="composeBody" rows="45" cols="60" style="width:85%;height:345px;" name="composeBody"></textarea>
										<br>
									</form>
				                  </td>
				                </tr>
				              </table>
				            </td>
				          </tr>
				       	</table>
				  	</td>
				  </tr>
				  <!-- End Compose Table -->
			    </table>

			</div>

		</div>
	</div>
</div>	
<!-- COMPOSE ENDS -->
