<%@ page contentType="text/html; charset=UTF-8" %>
<!-- HOME STARTS -->
<div id="home">
	<div class="homeTitle">
		<em>&nbsp;</em>
		<i18n:message key="home"/>
		<span><img alt="" src="images/bighome.gif"/></span>
	</div>
	<div id="newMessages" class="box">
		<div class="tl"><div class="br"><div class="bl"><div class="tr"><div class="inner" style="height: 450px;">
			<div class="title" id="newMailHomeTitle"><i18n:message key="please.wait"/></div>
			<div style="height: 305px;overflow: auto;overflow-x: hidden;">
				<ul id="emailHomeItems">
				</ul>
			</div>
			<div><a href="javascript:layoutMail();" class="goto"><i18n:message key="go.to.mail"/><span><img alt="" src="images/goto-arrow.gif"/></span></a></div>
		</div></div></div></div></div>
		<div><div class="icon"><span><img alt="" src="images/newmessages-icon.gif"/></span></div></div>
	</div>
	<div class="homeholder">
		<!-- 
		<div id="events" class="box">
			<div class="tl"><div class="br"><div class="bl"><div class="tr"><div class="inner">
				<div class="title">There are 2 events for the next 3 days</div>
				<ul>
					<li><a href="#"><b>28.11.2006 Saturday</b><br/>Hebelek yapilacak ve ard?ndan de hodelek yapilacak. Doymazsan ard?ndan da dandini ve dindini yapilacak.</a></li>
					<li><a href="#"><b>29.11.2006 Sunday</b><br/>Hebelek yapilacak ve ard?ndan de hodelek yapilacak. Doymazsan ard?ndan da dandini ve dindini yapilacak.</a></li>
				</ul>
				<div><a href="javascript:layoutCalendar();" class="goto">Go to Calendar<span><img alt="" src="images/goto-arrow.gif"/></span></a></div>
			</div></div></div></div></div>
			<div><div class="icon"><span><img alt="" src="images/events-icon.gif"/></span></div></div>
		</div>
		 -->
		<div id="news" class="box">
			<div class="tl"><div class="br"><div class="bl"><div class="tr"><div class="inner" style="height: 450px;">
				<div class="title" style="padding-left: 20px;"><span id="rssChannelTitle"><i18n:message key="please.wait"/></span></div>
				<div style="padding-left:50px;height: 305px;overflow: auto;min-width: 270px;width: 93%;overflow-x:hidden;">
				<ul id="rssItems">
				</ul>
				</div>
				<div><a href="javascript:showRssPreferences();" class="goto"><i18n:message key="configure.rss"/><span><img alt="" src="images/rss-icon.gif"/></span></a></div>
			</div></div></div></div></div>
			<div><div class="icon"><span><img alt="" src="images/news-icon.png"/></span></div></div>
		</div>
	</div>
</div>
<!-- HOME ENDS -->

<div id="balloonRSS" style="position: absolute;top:0px;left: 0px;z-index: 100;background-color: #fafafa;border: 1px solid #dadada;min-height: 50px;min-width: 200px;display:none;">&nbsp;</div>
