function getContacts() {
	var url = "contacts/getList.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				var list = Dom.get('contactListReal');
				var listHead = Dom.get('contactListRealHead');
				list.innerHTML = 
					'<TABLE cellSpacing=0 cellPadding=0 width=370 align=center border=0>'
					+ '<THEAD id="contactListRealHead">' + listHead.innerHTML + '</THEAD>'
					+ '<TBODY id="contactListRealBody">' + o.responseText + '</TBODY>'
					+ '</TABLE>';
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

function showContactDetails(contId) {
	var trs = Dom.get('contactListRealBody').getElementsByTagName('tr');
	if (trs != null) {
		for (var i=0;i<trs.length;i++) {
			trs[i].style.backgroundColor = C_WHITE;
			trs[i].style.color = C_BLACK;
			trs[i].clicked = false;
		}
	}
	var el = Dom.get('contact' + contId)
	el.style.backgroundColor = C_ROYALBLUE;
	el.style.color = C_WHITE;
	el.clicked = true;

	var paramData = "id=" + contId;
	var url = "contacts/getContactDetails.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseXML != null) {
					try {
						clearContactForm();
						var cemailAlternate = getFirstCleanValByTagName(o.responseXML, 'emailAlternate');
						var cemailPrimary = getFirstCleanValByTagName(o.responseXML, 'emailPrimary');
						var cfirstName = getFirstCleanValByTagName(o.responseXML, 'firstName');
						var cgsmNoAlternate = getFirstCleanValByTagName(o.responseXML, 'gsmNoAlternate');
						var cgsmNoPrimary = getFirstCleanValByTagName(o.responseXML, 'gsmNoPrimary');
						var chomeAddress = getFirstCleanValByTagName(o.responseXML, 'homeAddress');
						var chomeCity = getFirstCleanValByTagName(o.responseXML, 'homeCity');
						var chomeCountry = getFirstCleanValByTagName(o.responseXML, 'homeCountry');
						var chomeFaks = getFirstCleanValByTagName(o.responseXML, 'homeFaks');
						var chomePhone = getFirstCleanValByTagName(o.responseXML, 'homePhone');
						var chomeProvince = getFirstCleanValByTagName(o.responseXML, 'homeProvince');
						var chomeZip = getFirstCleanValByTagName(o.responseXML, 'homeZip');
						var clastName = getFirstCleanValByTagName(o.responseXML, 'lastName');
						var cmiddleName = getFirstCleanValByTagName(o.responseXML, 'middleName');
						var cnickName = getFirstCleanValByTagName(o.responseXML, 'nickName');
						var cpersonalNote = getFirstCleanValByTagName(o.responseXML, 'personalNote');
						var csex = getFirstCleanValByTagName(o.responseXML, 'sex');
						var cspouseName = getFirstCleanValByTagName(o.responseXML, 'spouseName');
						var ctitle = getFirstCleanValByTagName(o.responseXML, 'title');
						var cusername = getFirstCleanValByTagName(o.responseXML, 'username');
						var cwebPage = getFirstCleanValByTagName(o.responseXML, 'webPage');
						var cworkAddress = getFirstCleanValByTagName(o.responseXML, 'workAddress');
						var cworkAssistantName = getFirstCleanValByTagName(o.responseXML, 'workAssistantName');
						var cworkCity = getFirstCleanValByTagName(o.responseXML, 'workCity');
						var cworkCompany = getFirstCleanValByTagName(o.responseXML, 'workCompany');
						var cworkCountry = getFirstCleanValByTagName(o.responseXML, 'workCountry');
						var cworkDepartment = getFirstCleanValByTagName(o.responseXML, 'workDepartment');
						var cworkFaks = getFirstCleanValByTagName(o.responseXML, 'workFaks');
						var cworkJobTitle = getFirstCleanValByTagName(o.responseXML, 'workJobTitle');
						var cworkManagerName = getFirstCleanValByTagName(o.responseXML, 'workManagerName');
						var cworkOffice = getFirstCleanValByTagName(o.responseXML, 'workOffice');
						var cworkPhone = getFirstCleanValByTagName(o.responseXML, 'workPhone');
						var cworkProfession = getFirstCleanValByTagName(o.responseXML, 'workProfession');
						var cworkProvince = getFirstCleanValByTagName(o.responseXML, 'workProvince');
						var cworkZip = getFirstCleanValByTagName(o.responseXML, 'workZip');
						var cid = getFirstCleanValByTagName(o.responseXML, 'id');
						var canniversaryDay = getFirstCleanValByTagName(o.responseXML, 'anniversaryDay');
						var canniversaryMonth = getFirstCleanValByTagName(o.responseXML, 'anniversaryMonth');
						var cbirthDay = getFirstCleanValByTagName(o.responseXML, 'birthDay');
						var cbirthMonth = getFirstCleanValByTagName(o.responseXML, 'birthMonth');

						if ((cemailPrimary == null || cemailPrimary == '') && (cemailAlternate == null || cemailAlternate == '')) {
							disableButton('sendMailContact');
						} else {
							enableButton('sendMailContact');
						}
						enableButton('deleteContact');
						enableButton('vcardContact');

						Dom.get('contBirthDay').value = cbirthDay;
						Dom.get('contBirthMonth').value = cbirthMonth;
						Dom.get('contAnniversaryDay').value = canniversaryDay;
						Dom.get('contAnniversaryMonth').value = canniversaryMonth;
		
						Dom.get('contFirstName').value = cfirstName;
						Dom.get('contMiddleName').value = cmiddleName;
						Dom.get('contLastName').value = clastName;
						Dom.get('contTitle').value = ctitle;
						Dom.get('contSex').value = csex;
						Dom.get('contEmailPrimary').value = cemailPrimary;
						Dom.get('contEmailAlternative').value = cemailAlternate;
						Dom.get('contGsmNoPrimary').value = cgsmNoPrimary;
						Dom.get('contGsmNoAlternative').value = cgsmNoAlternate;
						Dom.get('contWebPage').value = cwebPage;
						Dom.get('contNickName').value = cnickName;
						Dom.get('contSpouseName').value = cspouseName;
						Dom.get('contPersonalNote').value = cpersonalNote;
						Dom.get('contHomeAddress').value = chomeAddress;
						Dom.get('contHomeCity').value = chomeCity;
						Dom.get('contHomeProvince').value = chomeProvince;
						Dom.get('contHomeZip').value = chomeZip;
						Dom.get('contHomeCountry').value = chomeCountry;
						Dom.get('contHomePhone').value = chomePhone;
						Dom.get('contHomeFaks').value = chomeFaks;
						Dom.get('contWorkCompany').value = cworkCompany;
						Dom.get('contWorkJobTitle').value = cworkJobTitle;
						Dom.get('contWorkOffice').value = cworkOffice;
						Dom.get('contWorkDepartment').value = cworkDepartment;
						Dom.get('contWorkProfession').value = cworkProfession;
						Dom.get('contWorkManagerName').value = cworkManagerName;
						Dom.get('contWorkAssistantName').value = cworkAssistantName;
						Dom.get('contWorkAddress').value = cworkAddress;
						Dom.get('contWorkCity').value = cworkCity;
						Dom.get('contWorkProvince').value = cworkProvince;
						Dom.get('contWorkZip').value = cworkZip;
						Dom.get('contWorkCountry').value = cworkCountry;
						Dom.get('contWorkPhone').value = cworkPhone;
						Dom.get('contWorkFaks').value = cworkFaks;
						Dom.get('contId').value = cid;
						
						changeSexImage();
						// show details in the accordion
						layoutContacts();
					} catch (e) {
						
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

function clearContactForm() {
	Dom.get('contFirstName').value = "";
	Dom.get('contMiddleName').value = "";
	Dom.get('contLastName').value = "";
	Dom.get('contTitle').value = "";
	Dom.get('contSex').value = "";
	Dom.get('contEmailPrimary').value = "";
	Dom.get('contEmailAlternative').value = "";
	Dom.get('contGsmNoPrimary').value = "";
	Dom.get('contGsmNoAlternative').value = "";
	Dom.get('contWebPage').value = "";
	Dom.get('contNickName').value = "";
	Dom.get('contSpouseName').value = "";
	Dom.get('contPersonalNote').value = "";
	Dom.get('contHomeAddress').value = "";
	Dom.get('contHomeCity').value = "";
	Dom.get('contHomeProvince').value = "";
	Dom.get('contHomeZip').value = "";
	Dom.get('contHomeCountry').value = "";
	Dom.get('contHomePhone').value = "";
	Dom.get('contHomeFaks').value = "";
	Dom.get('contWorkCompany').value = "";
	Dom.get('contWorkJobTitle').value = "";
	Dom.get('contWorkOffice').value = "";
	Dom.get('contWorkDepartment').value = "";
	Dom.get('contWorkProfession').value = "";
	Dom.get('contWorkManagerName').value = "";
	Dom.get('contWorkAssistantName').value = "";
	Dom.get('contWorkAddress').value = "";
	Dom.get('contWorkCity').value = "";
	Dom.get('contWorkProvince').value = "";
	Dom.get('contWorkZip').value = "";
	Dom.get('contWorkCountry').value = "";
	Dom.get('contWorkPhone').value = "";
	Dom.get('contWorkFaks').value = "";
	Dom.get('contId').value = "";
	Dom.get('contBirthDay').value = "";
	Dom.get('contBirthMonth').value = "";
	Dom.get('contAnniversaryDay').value = "";
	Dom.get('contAnniversaryMonth').value = "";

	disableButton('sendMailContact');
	disableButton('deleteContact');
	disableButton('vcardContact');

	changeSexImage();
}

function myEncode(str) {
	str = encodeURI(str);
	str = str.replace(new RegExp("&",'g'), "%26");
	str = str.replace(new RegExp(";",'g'), "%3B");
	str = str.replace(new RegExp("[?]",'g'), "%3F");
	return str;
}

/**
 * Checks if a contact has been previously saved. gives a warning if found
 */
function saveCheckContact() {
	var contEmailPrimary = myEncode(Dom.get('contEmailPrimary').value);
	var contEmailAlternative = myEncode(Dom.get('contEmailAlternative').value);

	if (contEmailPrimary == null || trim(contEmailPrimary) == '') {
		showDialog('contactSaveNoMailError');
	} else {
		var contId = Dom.get('contId').value;
		if (contId != null && trim(contId) != '') {
			saveContact();
		} else {
			var paramData = "contEmailPrimary=" + contEmailPrimary;
			paramData += "&contEmailAlternative=" + contEmailAlternative;
		
			var url = "contacts/saveCheckContact.cl";
		
			var callback = {
			  success: 	function(o) {
					if(o.responseText !== undefined) {
						if (o.responseText == 'none') {
							saveContact();
						} else {
							showDialog('saveContactDuplicateQuestion');
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
	}
}

function saveContact() {
	var contId = myEncode(Dom.get('contId').value);
	var contFirstName = myEncode(Dom.get('contFirstName').value);
	var contMiddleName = myEncode(Dom.get('contMiddleName').value);
	var contLastName = myEncode(Dom.get('contLastName').value);
	var contTitle = myEncode(Dom.get('contTitle').value);
	var contSex = myEncode(Dom.get('contSex').value);
	var contEmailPrimary = myEncode(Dom.get('contEmailPrimary').value);
	var contEmailAlternative = myEncode(Dom.get('contEmailAlternative').value);
	var contGsmNoPrimary = myEncode(Dom.get('contGsmNoPrimary').value);
	var contGsmNoAlternative = myEncode(Dom.get('contGsmNoAlternative').value);
	var contWebPage = myEncode(Dom.get('contWebPage').value);
	var contNickName = myEncode(Dom.get('contNickName').value);
	var contSpouseName = myEncode(Dom.get('contSpouseName').value);
	var contPersonalNote = myEncode(Dom.get('contPersonalNote').value);
	var contHomeAddress = myEncode(Dom.get('contHomeAddress').value);
	var contHomeCity = myEncode(Dom.get('contHomeCity').value);
	var contHomeProvince = myEncode(Dom.get('contHomeProvince').value);
	var contHomeZip = myEncode(Dom.get('contHomeZip').value);
	var contHomeCountry = myEncode(Dom.get('contHomeCountry').value);
	var contHomePhone = myEncode(Dom.get('contHomePhone').value);
	var contHomeFaks = myEncode(Dom.get('contHomeFaks').value);
	var contWorkCompany = myEncode(Dom.get('contWorkCompany').value);
	var contWorkJobTitle = myEncode(Dom.get('contWorkJobTitle').value);
	var contWorkOffice = myEncode(Dom.get('contWorkOffice').value);
	var contWorkDepartment = myEncode(Dom.get('contWorkDepartment').value);
	var contWorkProfession = myEncode(Dom.get('contWorkProfession').value);
	var contWorkManagerName = myEncode(Dom.get('contWorkManagerName').value);
	var contWorkAssistantName = myEncode(Dom.get('contWorkAssistantName').value);
	var contWorkAddress = myEncode(Dom.get('contWorkAddress').value);
	var contWorkCity = myEncode(Dom.get('contWorkCity').value);
	var contWorkProvince = myEncode(Dom.get('contWorkProvince').value);
	var contWorkZip = myEncode(Dom.get('contWorkZip').value);
	var contWorkCountry = myEncode(Dom.get('contWorkCountry').value);
	var contWorkPhone = myEncode(Dom.get('contWorkPhone').value);
	var contWorkFaks = myEncode(Dom.get('contWorkFaks').value);
	var contBirthDay = myEncode(Dom.get('contBirthDay').value);
	var contBirthMonth = myEncode(Dom.get('contBirthMonth').value);
	var contAnniversaryDay = myEncode(Dom.get('contAnniversaryDay').value);
	var contAnniversaryMonth = myEncode(Dom.get('contAnniversaryMonth').value);

	var paramData = "contId=" + contId;
	paramData += "&contFirstName=" + contFirstName;
	paramData += "&contMiddleName=" + contMiddleName;
	paramData += "&contLastName=" + contLastName;
	paramData += "&contTitle=" + contTitle;
	paramData += "&contSex=" + contSex;
	paramData += "&contEmailPrimary=" + contEmailPrimary;
	paramData += "&contEmailAlternative=" + contEmailAlternative;
	paramData += "&contGsmNoPrimary=" + contGsmNoPrimary;
	paramData += "&contGsmNoAlternative=" + contGsmNoAlternative;
	paramData += "&contWebPage=" + contWebPage;
	paramData += "&contNickName=" + contNickName;
	paramData += "&contSpouseName=" + contSpouseName;
	paramData += "&contPersonalNote=" + contPersonalNote;
	paramData += "&contBirthDay=" + contBirthDay;
	paramData += "&contBirthMonth=" + contBirthMonth;
	paramData += "&contAnniversaryDay=" + contAnniversaryDay;
	paramData += "&contAnniversaryMonth=" + contAnniversaryMonth;
	paramData += "&contHomeAddress=" + contHomeAddress;
	paramData += "&contHomeCity=" + contHomeCity;
	paramData += "&contHomeProvince=" + contHomeProvince;
	paramData += "&contHomeZip=" + contHomeZip;
	paramData += "&contHomeCountry=" + contHomeCountry;
	paramData += "&contHomePhone=" + contHomePhone;
	paramData += "&contHomeFaks=" + contHomeFaks;
	paramData += "&contWorkCompany=" + contWorkCompany;
	paramData += "&contWorkJobTitle=" + contWorkJobTitle;
	paramData += "&contWorkOffice=" + contWorkOffice;
	paramData += "&contWorkDepartment=" + contWorkDepartment;
	paramData += "&contWorkProfession=" + contWorkProfession;
	paramData += "&contWorkManagerName=" + contWorkManagerName;
	paramData += "&contWorkAssistantName=" + contWorkAssistantName;
	paramData += "&contWorkAddress=" + contWorkAddress;
	paramData += "&contWorkCity=" + contWorkCity;
	paramData += "&contWorkProvince=" + contWorkProvince;
	paramData += "&contWorkZip=" + contWorkZip;
	paramData += "&contWorkCountry=" + contWorkCountry;
	paramData += "&contWorkPhone=" + contWorkPhone;
	paramData += "&contWorkFaks=" + contWorkFaks;

	var url = "contacts/saveContact.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText == 'ok') {
					showContactSavedIndicator();
					getContacts();
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

function changeSexImage() {
	var sex = Dom.get('contSex').value;
	var img = "new-huge";
	if (sex == 'M') {
		img = "male-huge";
	} else if (sex == 'F') {
		img = "female-huge";
	}
	Dom.get('seximg').src = "images/" + img + ".gif";
}

function searchContact() {
	var prefix = myEncode(Dom.get('searchContactTxt').value);

	var paramData = "prefix=" + prefix;
	var url = "contacts/getList.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				var list = Dom.get('contactListRealBody');
				list.innerHTML = o.responseText;
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

function cancelSearchContact() {
	Dom.get('searchContactTxt').value = "";
	getContacts();
}

function contactSendMail() {
	var emailPrimary = trim(Dom.get('contEmailPrimary').value);
	var emailAlternative = trim(Dom.get('contEmailAlternative').value);
	var myMail = null;
	if (emailPrimary != null && emailPrimary != '') {
		myMail = emailPrimary;
	}
	
	if (myMail == null) {
		if (emailAlternative != null && emailAlternative != '') {
			myMail = emailAlternative;
		}
	}
	if (myMail != null) {
		clearComposeForm();
		Dom.get('to').value = myMail;
		layoutMail('compose');

		var eid = tinyMCE.getEditorId('composeBody');
		var te = Dom.get(eid);
		var html = "<br/>" + prefSignature;
		te.contentWindow.document.body.innerHTML = html;
		moveCursorPosition(0);
		Dom.get('subject').focus();
	}
}

function showContactSavedIndicator() {
	var d = Dom.get('contactSavedIndicator');
	d.style.left = ((windowwidth - getWidth(d)) / 2) + "px";
	d.style.top = "22px";
	d.style.display = "block";
	setTimeout(hideContactSavedIndicator, 8000);
}

function hideContactSavedIndicator() {
	var d = Dom.get('contactSavedIndicator');
	d.style.display = "none";
}

function showDeleteContact() {
	showDialog('deleteContactQuestion');
}

function deleteContact() {
	var contId = trim(Dom.get('contId').value);

	var paramData = "id=" + contId;
	var url = "contacts/deleteContact.cl";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				clearContactForm();
				getContacts();
				hideDialog('deleteContactQuestion');
			}
	  },

	  failure: 	function(o) {
		hideDialog('deleteContactQuestion');
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('POST', url, callback, paramData);
}

function exportVcardContact() {
	var contId = trim(Dom.get('contId').value);
	Dom.get('downloader').src = "contacts/exportVcard.service?id=" + contId;
}

function exportAllContacts() {
	Dom.get('downloader').src = "contacts/exportAllContacts.cl";
}

function importContacts() {
	Dom.get('contactImportIframe').src = "contact_import_iframe.jsp";
	showDialog("contactImportWin");
}

function uploadImportCsv() {
	var iframe = window.frames['contactImportIframe'];
	if (iframe.document.forms[0].inputfile.value == '') {
		alert(select_file_error);
	} else {
		iframe.document.forms[0].submit();
	}
}
