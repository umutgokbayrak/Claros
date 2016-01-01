<%@ page contentType="text/html; charset=UTF-8" %>

<link href="css/chat.css" rel="stylesheet" type="text/css" />

<div id="chatlogin" style="display:none">
	<div id="logincenter" style="width:428px; height:281px; outline:none; position:absolute; left: 153px; top: 76px;">
		<table width="428" height="281" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
			<td height="23" id="loginhandle" onmouseup="dragStop(event);" style="color: #0c4363;font-size: 11px;font-weight: bold;padding-left: 37px;padding-bottom:4px;cursor: pointer;background-image: url('images/chat/bg/login_top.gif');background-repeat: no-repeat;"><i18n:message key="select.server.and.login"/></td>
		  </tr>
		  <tr>
			<td height="234" valign="top" background="images/chat/bg/login_bg.gif"><form id="loginform" name="loginform" method="post" action="" onsubmit="return false"><table width="427" border="0" align="center" cellpadding="5" cellspacing="0">
		          <tr>
		          	<td>&nbsp;</td>
		            <td style="color:red;font-weight: bold" id="loginResult">&nbsp;</td>
		          </tr>
		          <tr>
		            <td width="122"><div align="right"><i18n:message key="user"/> : </div></td>
		            <td width="285"><label>
		              <input type="text" name="username" tabindex="1" onkeydown="return(loginCatchEnter(event))" style="width: 200px;border: 1px solid #A3C6E8;height: 14px;font-family: Arial, Helvetica, sans-serif;font-size:11px;"/>
		            </label></td>
		          </tr>
		          <tr>
		            <td><div align="right"><i18n:message key="password"/> : </div></td>
		            <td><label>
		              <input id="chatPassword" type="password" name="password" tabindex="2" onkeydown="return(loginCatchEnter(event))" style="width: 200px;border: 1px solid #A3C6E8;height: 14px;font-family: Arial, Helvetica, sans-serif;font-size:11px;"/>
		            </label></td>
		          </tr>
		          <tr>
		            <td><div align="right"><i18n:message key="server"/> : </div></td>
		            <td><label>
		              <input type="text" name="server" tabindex="3" onkeydown="return(loginCatchEnter(event))" value="Google Talk" readonly="readonly" style="width: 200px;border: 1px solid #A3C6E8;height: 14px;font-family: Arial, Helvetica, sans-serif;font-size:11px;"/>
		            </label></td>
		          </tr>
		          <tr>
		            <td>&nbsp;</td>
		            <td>
		           		<table border="0" cellspacing="0" cellpadding="0" width="100%">
		           			<tr>
		           				<td width="1%" nowrap="nowrap">
								  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
									<tr>
									  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
									  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="claroslogin();"><i18n:message key="login"/></td>
									  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
									</tr>
								  </table>
		           				</td>
		           				<td width="99%" style="padding-left: 7px;">
						            <img src="images/chat/loading_mini.gif" width="16" height="16" id="loginprogress" style="display: none"/>
						            <span id="logintext" style="color: #333333; font-style: italic"></span>
		           				</td>
		           			</tr>
		           		</table> 
		            </td>
		          </tr>
		          
		          <tr>
		            <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
		              <tr>
		                <td width="204" align="right"><a href="#" onmouseout="if (!gtalkClicked) MM_swapImgRestore()" onmouseover="MM_swapImage('gtalk','','images/chat/buttons/gtalk_clicked.gif',1)" onclick="selectGtalk();"><img src="images/chat/buttons/gtalk.gif" name="gtalk" width="188" height="83" border="0" id="gtalk" /></a></td>
		                <td width="10">&nbsp;</td>
		                <td width="203" align="left"><a href="#" onmouseout="if (!jabberClicked) MM_swapImgRestore()" onmouseover="MM_swapImage('jabber','','images/chat/buttons/jabber_clicked.gif',1)" onclick="selectJabber();"><img src="images/chat/buttons/jabber.gif" name="jabber" width="188" height="83" border="0" id="jabber" /></a></td>
		              </tr>
		            </table></td>
		          </tr>
		        </table></form></td>
		  </tr>
		  <tr>
			<td height="18" background="images/chat/bg/login_bottom.png">&nbsp;</td>
		  </tr>
		</table>
	</div>
</div>


<div id="chatcontacts">
<table width="189" border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td width="189" height="34" id="contactshandle" style="background-image:url('images/chat/bg/contact_win_top.gif');background-repeat:no-repeat;background-position:bottom; color: #0c4363;font-size: 11px;font-weight: bold;padding-left:47px;padding-bottom:5px;vertical-align: bottom;"><i18n:message key="buddies"/></td>
  </tr>
  <tr>
	<td valign="top" background="images/chat/bg/contact_win_bg.gif" height="337"><table width="189" border="0" cellspacing="0" cellpadding="0" height="337">
		  <tr>
			<td width="19">&nbsp;</td>
			<td width="154">
				<div id="contactsscroll">
					<br /><br /><br /><div align="center"><img src="images/chat/loading.gif" width="32" height="32"></div>
				</div>
			</td>
			<td width="16">&nbsp;</td>
		  </tr>
		</table></td>
  </tr>
  <tr>
  	<td width="189" height="25" align="right" valign="bottom" background="images/chat/bg/contact_win_bg.gif"><a href="#" style="margin-right: 25px;color: #0c4363;font-size: 10px;font-weight: bold;" onclick="chatAddContact();"><i18n:message key="add.new.buddy"/></a></td>
  </tr>
  <tr>
	<td width="189" height="34" background="images/chat/bg/contact_win_bottom.gif">&nbsp;</td>
  </tr>
</table>
</div>

<div id="infowin" style="visibility:hidden" onmouseover="overInfoWin()" >
 <table width="199" border="0" cellspacing="0" cellpadding="0">
	<tr>
	  <td height="16" background="images/chat/bg/info_bg_top.gif">&nbsp;</td>
	</tr>
	<tr>
	  <td height="60" valign="middle" background="images/chat/bg/info_bg.gif">
		<table width="179" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr>
		  	<td align="center">
		  		<img src="images/chat/avatar_load.gif" id="avatar" /><br/>
		  	</td>
		  </tr>
		  <tr>
			<td>
				<span id="infotexttitle" style="overflow: none;margin-top: 4px;">&nbsp;</span>
				<p id="infotextstatus">&nbsp;</p>
			</td>
		  </tr>
		  <tr>
			<td><img src="images/chat/spacer.gif" width="10" height="8"/></td>
		  </tr>
		  <tr>
			<td align="center">
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr>
						<td align="right" style="padding-right: 3px;" width="50%">
						  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
							<tr>
							  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
							  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="openChat();"><i18n:message key="chat"/></td>
							  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
							</tr>
						  </table>
						</td>
						<td align="left" style="padding-left: 3px;" width="50%">
						  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
							<tr>
							  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
							  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="removeBuddy();"><i18n:message key="remove"/></td>
							  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
							</tr>
						  </table>
						</td>
					</tr>
				</table>
			</td>
		  </tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td height="16" background="images/chat/bg/info_bg_bottom.gif">&nbsp;</td>
	</tr>
 </table>
</div>



 <div id="toolbar" style="height:58px;display:none;">
   <table width="100%" height="58" border="0" cellpadding="0" cellspacing="0" align="center">
     <tr>
       <td width="1%" height="58"><img src="images/chat/bg/toolbar_left.gif" width="23" height="58"/></td>
       <td width="33%" background="images/chat/bg/toolbar_bg.gif">
		  <div id="consolescroll">
			<div id="consoleText" style="overflow:hidden; height:50px; width:95%">&nbsp;</div>
		  </div>
	   </td>
       <td width="1%" height="58" bgcolor="green"><img src="images/chat/bg/toolbar_seperator.gif" width="17" height="58"/></td>
       <td width="33%" align="center" valign="middle" background="images/chat/bg/toolbar_bg.gif" nowrap="nowrap" bgcolor="blue">

		<table border="0" cellpadding="2" cellspacing="0" id="statusTable">
		  <tr>
		    <td rowspan="2" align="left" nowrap>
		    	<img src="images/chat/avatar.png" height="48" width="48" id="avatarme" class="avatarMe" onmouseover="this.className='avatarMeHover';" onmouseout="this.className='avatarMe'" style="margin-right: 10px"/>
		    </td>
			<td nowrap="nowrap" valign="bottom"><strong style="color:#272827"><i18n:message key="welcome"/> <span id="chatFullName">&nbsp;</span></strong></td>
		  </tr>
		  <tr>
			<td nowrap="nowrap" valign="top"><strong style="color:#272827"><i18n:message key="status"/> : </strong><span id="statusnow"><i18n:message key="available"/></span> &nbsp;<img src="images/chat/indicators/green.gif" border="0" id="statusindic"/>&nbsp; <a href="javascript:changeStatus()"><i18n:message key="change"/></a></td>
		  </tr>
		</table>


	   </td>
       <td width="1%" height="58"><img src="images/chat/bg/toolbar_seperator.gif" width="17" height="58" /></td>
       <td valign="top" background="images/chat/bg/toolbar_bg.gif"><table border="0" width="260" align="right" cellpadding="0" cellspacing="0" style="margin-top:20px">
         <tr>
           <td width="13" nowrap="nowrap"><img src="images/chat/ico/logout.gif" width="20" height="20" /></td>
           <td nowrap="nowrap" width="50"><a href="javascript:showChatLogout();" style="color:#0c4363;margin-left:7px;"><i18n:message key="logout"/></a></td>
           <td width="20" nowrap="nowrap"><img src="images/chat/ico/preferences.gif" width="22" height="20" /></td>
           <td nowrap="nowrap" width="65"><a href="javascript:chatPreferences();" style="color:#0c4363;margin-left:7px;"><i18n:message key="preferences"/></a></td>
           <td width="19" nowrap="nowrap"><img src="images/chat/ico/claros.gif" width="21" height="20" /></td>
           <td nowrap="nowrap" width="50"><a href="http://www.claros.org" target="_blank" style="color:#0c4363;margin-left:7px;">claros.org</a></td>
         </tr>
       </table></td>
       <td width="1%" height="58"><img src="images/chat/bg/toolbar_right.gif" width="23" height="58" /></td>
     </tr>
   </table>
 </div>
  

<div id="chatWin" class="chatWin dragme" style="display:none;" onmousedown="moveOnTop(this);">
  <table width="234" height="242" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td height="26" class="handle" ondblclick="tidyDiv(this);"><div id="handle" style="outline:none; height:26px;cursor:move;"><table width="234" border="0" cellspacing="0" cellpadding="0" height="26" onmouseup="dragStop(event);">
        <tr>
          <td width="12">&nbsp;</td>
          <td width="14"><img src="images/chat/spacer.gif" width="10" height="10" id="statusindicator"/></td>
          <td width="187"><div id="chatWinTitleBar">&nbsp;</div></td>
          <td width="21"><img src="images/chat/spacer.gif" width="17" height="17" onclick="hideChatWin(this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode);" style="cursor: pointer;"></td>
        </tr>
      </table></div></td>
    </tr>
    <tr>
      <td height="152" valign="top" background="images/chat/bg/chatwin_bg.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="152" valign="top" id="chatWinText">
		  	<div id="chatScroll">
				<div id="myText" style="outline:hidden;">&nbsp;</div>
			</div>
		  </td>			  
        </tr>
      </table></td>
    </tr>
    <tr>
      <td height="64" align="left" valign="top" background="images/chat/bg/chatwin_bottom.gif"><label>
        <textarea name="chatWinMsg" wrap="physical" id="chatWinMsg" onkeyDown="return(handleKeys(event, this));"></textarea>
      </label></td>
    </tr>
  </table>
</div>

<div id="changeStatus" class="dialogBox" style="display:none;width: 400px;height: 250px;">
	<div class="dialogHeader"><i18n:message key="change.status"/></div>
	<div class="dialogBody">
		<div class="dialogText">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr>
				<td><i18n:message key="current.status"/> : </td>
				<td id="userStatus"></td>
			  </tr>
			  <tr>
				<td><i18n:message key="new.status"/> : </td>
				<td>
					<select name="newstatus" style="border: 1px solid #999999;font-size: 11px;" id="newstatus" nohide="true">
					  <option value="available"><i18n:message key="available"/></option>
					  <option value="away"><i18n:message key="away"/></option>
					  <option value="disturb"><i18n:message key="do.not.disturb"/></option>
					</select>
				</td>
			  </tr>
			  <tr>
				<td valign="top" style="padding-top: 10px;"><i18n:message key="status.message"/> : </td>
				<td>
					<textarea id="newstatusmsg" name="newstatusmsg" cols="30" rows="6" style="width:200px; height:70px;border: 1px solid #999999;	font-family: Arial, Helvetica, sans-serif;font-size:11px;"></textarea>
				</td>
			  </tr>
			</table>
		</div>
		<table border="0" width="100%" cellspacing="1" cellpadding="3">
			<tr>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('changeStatus');saveStatus();"><i18n:message key="save"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td width="50%">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50" id="chatPrefCancel">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('changeStatus');"><i18n:message key="cancel"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="logoutChat" class="dialogBox" style="display:none;width: 400px;height: 120px;">
	<div class="dialogHeader"><i18n:message key="confirmation.needed"/></div>
	<div class="dialogBody">
		<img src="images/question.gif" class="dialogBoxIcon"/>
		<div class="dialogText">
			<i18n:message key="sure.logout.chat"/><br/><br/>
		</div>

		<table border="0" cellspacing="0" cellpadding="0" align="right">
			<tr>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('logoutChat');doLogoutChat();"><i18n:message key="yes"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td width="50%" align="left" style="padding-left: 20px;">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('logoutChat');"><i18n:message key="no"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="removeBuddy" class="dialogBox" style="display:none;width: 400px;height: 150px;">
	<div class="dialogHeader"><i18n:message key="confirmation.needed"/></div>
	<div class="dialogBody">
		<img src="images/question.gif" class="dialogBoxIcon"/>
		<div class="dialogText">
			<span id="removeUserName">&nbsp;</span><i18n:message key="are.you.sure.remove"/><br/><br/>
		</div>

		<table border="0" cellspacing="0" cellpadding="0" align="right">
			<tr>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('removeBuddy');doRemoveBuddy();"><i18n:message key="yes"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td width="50%" align="left" style="padding-left: 20px;">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('removeBuddy');"><i18n:message key="no"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>

<div id="addBuddy" class="dialogBox" style="display:none;width: 400px;height: 170px;">
	<div class="dialogHeader"><i18n:message key="add.new.buddy"/></div>
	<div class="dialogBody">
		<div class="dialogText">
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr>
				<td><i18n:message key="contact.address"/> : </td>
				<td><input name="newBuddy" id="newBuddy" type="text" style="border:solid 1px #666666; width: 200px;"/></td>
			  </tr>
			  <tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td colspan="2"><i18n:message key="add.buddy.info"/></td>
			  </tr>
			</table><br/><br/>
		</div>
		<table border="0" cellspacing="1" cellpadding="3" align="right">
			<tr>
				<td width="50%" align="right">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="75">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('addBuddy');doAddContact();"><i18n:message key="send.request"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
				<td width="50%">
				  <table height="23" border="0" cellspacing="0" cellpadding="0" width="50" id="prefCancel">
					<tr>
					  <td width="1%"><img src="images/button-left-bg.gif" width="9" height="23"/></td>
					  <td nowrap="nowrap" style="background-image: url(images/button-bg.gif);text-align:center;padding-left:15px;padding-right:15px;cursor:pointer" width="98%" height="23" onclick="hideDialog('addBuddy');"><i18n:message key="cancel"/></td>
					  <td width="1%"><img src="images/button-right-bg.gif" width="9" height="23"/></td>
					</tr>
				  </table>
				</td>
			</tr>
		</table>
	</div>
</div>
<!-- 
<div id="chatPreferences" class="curtain">
	&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br />&nbsp;<br /><form id="chatPreferencesForm">
	<table width="400 border="0" cellspacing="0" cellpadding="4" align="center"  height="315">
	  <tr>
		<td colspan="2">
			<strong style="font-size:18px;color:#999999"><i18n:message key="preferences"/></strong><hr size="1" />
		</td>
	  </tr>
	  <tr>
		<td><i18n:message key="full.name"/> : </td>
		<td><input name="preffullname" id="preffullname" type="text" style="border:solid 1px #666666; width: 150px;"/></td>
	  </tr>
	  <tr>
		<td><i18n:message key="set.status.away.after"/> : </td>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
				<tr>
					<td rowspan="2" width="70" nowrap="nowrap">
						<span id="mins">15</span> <i18n:message key="mins"/>
					</td>
					<td>
						<a href="javascript:prefArrUp();"><img src="images/chat/buttons/arr_up.gif" border="0" width="17" height="17"/></a>
					</td>
				</tr>
				<tr>
					<td>
						<a href="javascript:prefArrDown();"><img src="images/chat/buttons/arr_down.gif" border="0" width="17" height="17"/></a>
					</td>
				</tr>
			</table>
		</td>
	  </tr>
	  <tr>
		<td><i18n:message key="effects"/> : </td>
		<td><input name="anims" id="anims" type="radio" value="on" checked="checked"/> <i18n:message key="on"/> <input name="anims" id="anims" type="radio" value="off" /> <i18n:message key="off"/></td>
	  </tr>
	  <tr>
		<td>Sound Alert : </td>
		<td><input name="sndalert" id="sndalert" type="radio" value="on" checked="checked"/> <i18n:message key="on"/> <input name="sndalert" id="sndalert" type="radio" value="off" /> <i18n:message key="off"/></td>
	  </tr>
	  <tr>
		<td colspan="2" align="center" style="font-size:16px; font-weight:bold">
			<hr size="1"/>
			<br />
	 
			[ <a href="javascript:saveChatPreferences();" style="color:#EBEBEB"><i18n:message key="save"/></a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;<a href="javascript:closeCurtain('chatPreferences');" style="color:#EBEBEB"><i18n:message key="cancel"/></a> ]					
		</td>
	  </tr>
	</table>
  </form>
</div>
 -->

<div id="askWin">&nbsp;</div>

<div id="player" style="visibility: hidden;width: 1px;height: 1px;">
<object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	width="1"
	height="1"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"
	id="playa2"
	style="position:absolute">
	<param name="movie" value="resources/playa2.swf">
	<param name="quality" value="high">
	<param name="flashvars" value="autoplay=false&playlist=resources/newmsg.xml">
	<param name="loop" value="false">
	<param name="loopsong" value="false">
	<embed
		name="playa2"
		src="resources/playa2.swf"
		width="1"
		height="1"
		quality="high"
		swLiveConnect="true"
		flashvars="autoplay=false&playlist=resources/newmsg.xml"
		pluginspage="http://www.macromedia.com/go/flashplayer/"
		loop="false" 
		style="position:absolute">
	</embed>
</object>
</div>
</body>
</html>
