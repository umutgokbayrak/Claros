package org.claros.intouch.rss.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.claros.commons.auth.models.AuthProfile;
import org.claros.commons.cache.Cache;
import org.claros.commons.cache.CacheManager;
import org.claros.commons.configuration.PropertyFile;
import org.claros.intouch.preferences.controllers.UserPrefsController;
import org.claros.intouch.rss.models.NewsItem;
import org.gnu.stealthp.rsslib.RSSChannel;
import org.gnu.stealthp.rsslib.RSSException;
import org.gnu.stealthp.rsslib.RSSHandler;
import org.gnu.stealthp.rsslib.RSSItem;
import org.gnu.stealthp.rsslib.RSSParser;

/**
 * @author Umut Gokbayrak
 */
public class NewsController {
	private static Log log = LogFactory.getLog(NewsController.class);
	
	/**
	 * Rss reader
	 * @return
	 */
	private static List fetchRssItems(String newsUrl) {
		ArrayList allNews = new ArrayList();
		try {
			RSSHandler hand = new RSSHandler();
			try {
				URL u = new URL(newsUrl);
				RSSParser.parseXmlFile(u, hand, false);
			} catch(RSSException e) {
				e.printStackTrace();
			}
			RSSChannel ch = hand.getRSSChannel();
			
			
			String channelDescription = ch.getDescription();
			String channelUrl = ch.getLink();
			String channelTitle = ch.getTitle();
			
			LinkedList lst = hand.getRSSChannel().getItems();
			
			for(int i = 0; i < lst.size(); i++) {
				RSSItem itm = (RSSItem)lst.get(i);
				try {
					NewsItem news = new NewsItem();
					news.setChannelDescription(channelDescription);
					news.setChannelTitle(channelTitle);
					news.setChannelUrl(channelUrl);
					news.setTitle(itm.getTitle().replace('\n', ' '));
					news.setDate(itm.getDate());
					news.setDescription(itm.getDescription().replace('\n', ' '));
					news.setLink(itm.getLink());
					allNews.add(news);
				} catch (Exception e1) {
					log.debug("Unable to add news item: " + itm.toString(), e1);
				}
			}
		} catch (Exception e) {
			log.debug("RSS Feed cannot be fetched and parsed.", e);
		}
		return allNews;
	}

	/**
	 * RSS feeds are cached...
	 * 
	 * @param user
	 * @return
	 */
	public static List getRssItems(AuthProfile auth) throws Exception {
		String newsUrl = UserPrefsController.getUserSetting(auth, "newsUrl");
		// if user has no value then get the system default
		if (newsUrl == null) {
			newsUrl = PropertyFile.getConfiguration("/config/config.xml").getString("common-params.rss-feed");
		}
		// if still there is no rss setting, fetch CNN. 
		if (newsUrl == null) {
			newsUrl = "http://rss.cnn.com/rss/cnn_topstories.rss";
//			newsUrl = "http://rss.hurriyet.com.tr/rss.aspx?sectionId=1";
//			newsUrl = "http://rss.e-kolay.net/pages/haber.aspx";
		}

		String key = newsUrl;
		Cache c = CacheManager.getContent(key);
		List result = null;
		if (c == null || c.isExpired()) {
			long ttl = 1000 * 60 * 5;
			result = fetchRssItems(newsUrl);
			CacheManager.putContent(key, result, ttl);
		} else {
			result = (List)c.getValue();
		}
		return result;
	}	
}
