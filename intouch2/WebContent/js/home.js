var rssContent = new Array();

function getRssNews() {
	rssContent = new Array();
	var url = "rss/getRssItems.cl";
	Dom.get('rssChannelTitle').innerHTML = please_wait;

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				if (o.responseText != null) {
					if (o.responseXML != null) {
						try {
							var result = getFirstCleanValByTagName(o.responseXML, 'result');
							if (result == '0') {
								var chnTitle = trim(getFirstCleanValByTagName(o.responseXML, 'channelTitle'));
								if (chnTitle == null || chnTitle == '') {
									chnTitle = txtnews;
								}
								Dom.get('rssChannelTitle').innerHTML = chnTitle;
	
								var items = o.responseXML.getElementsByTagName('items');
								if (items != null) {
									var nodes = items[0].childNodes;
									var rssItems = Dom.get('rssItems');
									rssItems.innerHTML = "";
									for (var p=0;p<nodes.length;p++) {
										try {
											var item = nodes[p];
		
											var date = trim(getFirstCleanValByTagName(item, 'date'));
											var desc = trim(getFirstCleanValByTagName(item, 'description'));
											var url = trim(getFirstCleanValByTagName(item, 'url'));
											if (url == null || trim(url) == '') url = "#";
											var titl = trim(getFirstCleanValByTagName(item, 'title'));
											
											// rssContent[p] = desc;
											
											var li = document.createElement("li");
											li.innerHTML = "<a href=\"" + url + "\" target=\"_blank\"><b>" + date + "</b><br/>" + titl + "</a>";
	
											rssItems.appendChild(li);
										} catch (e) {
											alert(e.message);
										}
									}
								}
							} else {
								Dom.get('rssChannelTitle').innerHTML = system_error;
							}
						} finally {
							window.setTimeout(getRssNews, 600000);
						}

					}
				}
			}
	  },
	  failure: 	function(o) {
		window.setTimeout(getRssNews, 600000);
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 30000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function getNewMessagesSummary() {
	// no go make the operations.fetch the headers.
	var url = "webmail/listNewMailHeaders.cl";
	Dom.get('newMailHomeTitle').innerHTML = please_wait;
	var emailItems = Dom.get('emailHomeItems');
	emailItems.innerHTML = "";

	var callback = {
	  success: 	function(o) {
			if(o.responseText !== undefined) {
				try {
					if (o.responseXML != null) {
						var result = getFirstCleanValByTagName(o.responseXML, 'result');
						if (result == '0') {
							var items = o.responseXML.getElementsByTagName('items');
							if (items != null) {
								var nodes = items[0].childNodes;
								for (var p=0;p<nodes.length;p++) {
									var item = nodes[p];
	
									var id = trim(getFirstCleanValByTagName(item, 'id'));
									var from = trim(getFirstCleanValByTagName(item, 'from'));
									var subject = trim(getFirstCleanValByTagName(item, 'subject'));
									
									var li = document.createElement("li");
									li.innerHTML = "<a href=\"javascript:showMailFromHome(" + id + ")\"><b>" + from + "</b><br/>" + subject + "</a>";
	
									emailItems.appendChild(li);
								}
								if (nodes.length == 0) {
									var li = document.createElement("li");
									li.innerHTML = no_new_mail;
									emailItems.appendChild(li);
								}
								Dom.get('newMailHomeTitle').innerHTML = nodes.length + " " + new_messages;
							}
						} else {
							Dom.get('newMailHomeTitle').innerHTML = system_error;
						}
					}
				} catch(e) {
					// do nothing sier
				}
			}
	  },

	  failure: 	function(o) {
		showDialog("defaultError");
	  },
	  argument: [],
	  timeout: 300000
	}
	var request = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function showMailFromHome(id) {
	layoutMail('inbox');
	
	var folders = Dom.get('folders');
	var lis = folders.getElementsByTagName('li');
	if (lis != null) {
		for (var i=0;i<lis.length;i++) {
			if (lis[i].getAttribute('foldertype') == '1') {
				var fold = lis[i].id.substr(10);
				selectMailFolder(fold, id);
			}
		}
	}
}
