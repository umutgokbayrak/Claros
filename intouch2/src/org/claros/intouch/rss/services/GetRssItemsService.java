package org.claros.intouch.rss.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.claros.commons.utility.Utility;
import org.claros.intouch.common.services.BaseService;
import org.claros.intouch.rss.controllers.NewsController;
import org.claros.intouch.rss.models.NewsItem;

public class GetRssItemsService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -553212879305214811L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Expires","-1");
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-control","no-cache");
		response.setHeader("Content-Type", "text/xml; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		out.write("<data>");
		out.print("<items>");
		try {
			List news = NewsController.getRssItems(getAuthProfile(request));
			String channelTitle = "";
			String channelDesc = "";
			String channelUrl = "";
			
			if (news != null) {
				NewsItem item = null;
				for (int i=0;i<news.size();i++) {
					item = (NewsItem)news.get(i);
					out.print("<item>");
					
					out.print("<date> " + Utility.htmlSpecialChars(item.getDate()) + "</date>");
					out.print("<description> " + Utility.htmlSpecialChars(item.getDescription()) + "</description>");
					out.print("<url> " + Utility.htmlSpecialChars(item.getLink()) + "</url>");
					out.print("<title> " + Utility.htmlSpecialChars(item.getTitle()) + "</title>");
					if (channelTitle == null || channelTitle.equals("")) {
						channelTitle = Utility.htmlSpecialChars(item.getChannelTitle());
					}
					if (channelDesc == null || channelDesc.equals("")) {
						channelDesc = Utility.htmlSpecialChars(item.getChannelDescription());
					}
					if (channelUrl == null || channelUrl.equals("")) {
						channelUrl = Utility.htmlSpecialChars(item.getChannelUrl());
					}
					out.print("</item>");
				}
			}
			out.print("</items>");
			out.print("<channelTitle> " + channelTitle + "</channelTitle>");
			out.print("<channelDesc> " + channelDesc + "</channelDesc>");
			out.print("<channelUrl> " + channelUrl + "</channelUrl>");
			out.print("<result>0</result>");
		} catch (Exception e) {
			out.print("</items>");
			out.print("<result>1</result>");
		}
		out.print("</data>");
	}

}
