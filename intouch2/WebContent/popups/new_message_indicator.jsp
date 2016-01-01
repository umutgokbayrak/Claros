<%@ page contentType="text/html; charset=UTF-8" %>

 <div id="newMessageIndicator" class="roundedcornr_box_171934" style="display:none;width: 260px;overflow: visible;z-index: 5999;position: absolute;">
   <div class="roundedcornr_top_171934"><div></div></div>
      <div class="roundedcornr_content_171934" align="center" style="font-weight: bold;white-space: nowrap;">
		<i18n:message key="you.got.mail"/>
      </div>
   <div class="roundedcornr_bottom_171934"><div></div></div>
</div>
<div id="alertNewMail" style="visibility: hidden;width: 1px;height: 1px;">
<object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
	width="1"
	height="1"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab"
	id="playa"
	style="position:absolute">
	<param name="movie" value="resources/playa2.swf">
	<param name="quality" value="high">
	<param name="flashvars" value="autoplay=false&playlist=resources/newmail.xml">
	<param name="loop" value="false">
	<param name="loopsong" value="false">
	<embed
		name="playa"
		src="resources/playa2.swf"
		width="1"
		height="1"
		quality="high"
		swLiveConnect="true"
		flashvars="autoplay=false&playlist=resources/newmail.xml"
		pluginspage="http://www.macromedia.com/go/flashplayer/"
		loop="false" 
		style="position:absolute">
	</embed>
</object>
</div>
