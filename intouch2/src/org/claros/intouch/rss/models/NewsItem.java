package org.claros.intouch.rss.models;

/**
 * @author Umut Gokbayrak
 */
public class NewsItem {
	private String channelUrl;
	private String channelDescription;
	private String channelTitle;
	private String title;
	private String link;
	private String date;
	private String description;
	
	/**
	 * @return
	 */
	public String getChannelDescription() {
		return channelDescription;
	}

	/**
	 * @return
	 */
	public String getChannelTitle() {
		return channelTitle;
	}

	/**
	 * @return
	 */
	public String getChannelUrl() {
		return channelUrl;
	}

	/**
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param string
	 */
	public void setChannelDescription(String string) {
		channelDescription = string;
	}

	/**
	 * @param string
	 */
	public void setChannelTitle(String string) {
		channelTitle = string;
	}

	/**
	 * @param string
	 */
	public void setChannelUrl(String string) {
		channelUrl = string;
	}

	/**
	 * @param string
	 */
	public void setDate(String string) {
		date = string;
	}

	/**
	 * @param string
	 */
	public void setLink(String string) {
		link = string;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

}
