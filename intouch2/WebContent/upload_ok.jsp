<%@ page contentType="text/html; charset=UTF-8" %>
<%
	response.setHeader("Expires","-1");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-control","no-cache");
%>
<%@page import ="java.util.Locale" %>
<%@page import ="org.claros.commons.utility.Utility" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/i18n-1.0" prefix="i18n" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	String lang = request.getParameter("lang");

	String defaultLang = org.claros.commons.configuration.PropertyFile.getConfiguration("/config/config.xml").getString("common-params.default-lang"); 
	if (lang == null) lang = defaultLang;
	Locale loc = new Locale(defaultLang);
	try {
		loc = new Locale(lang);
	} catch (Exception e) {}

%>
<i18n:bundle baseName="org.claros.intouch.i18n.lang" locale="<%= loc %>"/>
<%@ include file="js_messages.jsp" %>
<script>
	var size = '<%= request.getParameter("size") %>';
	var totalSize = '<%= request.getParameter("totalSize") %>';
	var id = '<%= request.getParameter("id") %>';
	var fileName = "<%= org.claros.commons.utility.Utility.convertTRCharsToHtmlSafe(java.net.URLDecoder.decode(java.net.URLDecoder.decode(request.getParameter("fileName"),"UTF-8"),"UTF-8")) %>";
	var result = '<%= request.getParameter("result") %>';
	var maxAttSize = '<%= request.getParameter("maxAttSize") %>';
	var maxMailSize = '<%= request.getParameter("maxMailSize") %>';
	
	switch (result) {
		case '0':
			// everything is ok 
			parent.document.getElementById('totalSize').innerHTML = totalSize;
		
			var outAtt = fileName + " (" + size + ")";
			var li = parent.document.getElementById('uploadli' + id);
			var span = li.getElementsByTagName("span")[0];
			span.innerHTML = outAtt;

			var a = li.getElementsByTagName("a")[0];
			a.style.display = '';
			
			var img = li.getElementsByTagName("img")[0];
			img.src = "images/attachment.gif";
			
			break;
		case '1':
			// attachment exceeded the max att size limit
			alert(size_exceeds_att_limit + maxAttSize);
			
			var attMail = parent.document.getElementById('composeAttachmentList');

			var lis = attMail.getElementsByTagName("li");
			
			for (var i=0;i<lis.length;i++) {
				var li = lis[i];

				if (li.getElementsByTagName("img")[0].src.indexOf("images/uploading.gif") > 0) {
					li.style.display = "none";
					
					var actCount = 0;
					if (lis.length > 1) {
						for (var j=0;j<lis.length;j++) {
							if (lis[j].style.display != 'none') {
								actCount++;
							}
						}
					}
					if (actCount == 0) {
						parent.document.getElementById('attachmentstr').style.display = 'none';
					}
				}
			}
			break;

		case '2':
			// mail size exceed the mail size limit
			alert(size_exceeds_mail_limit + maxMailSize);

			var attMail = parent.document.getElementById('composeAttachmentList');
			var lis = attMail.getElementsByTagName("li");
			
			for (var i=0;i<lis.length;i++) {
				var li = lis[i];

				if (li.innerHTML.indexOf(fileName) >= 0) {
					li.style.display = "none";
					
					var actCount = 0;
					if (lis.length > 1) {
						for (var j=0;j<lis.length;j++) {
							if (lis[j].style.display != 'none') {
								actCount++;
							}
						}
					}
					if (actCount == 0) {
						parent.document.getElementById('attachmentstr').style.display = 'none';
					}
				}
			}

			break;
		case '3':
			// attachment exceeded the max att size limit
			alert(system_error);
			
			var attMail = parent.document.getElementById('composeAttachmentList');

			var lis = attMail.getElementsByTagName("li");
			
			for (var i=0;i<lis.length;i++) {
				var li = lis[i];

				if (li.getElementsByTagName("img")[0].src.indexOf("images/uploading.gif") > 0) {
					li.style.display = "none";
					
					var actCount = 0;
					if (lis.length > 1) {
						for (var j=0;j<lis.length;j++) {
							if (lis[j].style.display != 'none') {
								actCount++;
							}
						}
					}
					if (actCount == 0) {
						parent.document.getElementById('attachmentstr').style.display = 'none';
					}
				}
			}
			break;
	}
</script>
OK
