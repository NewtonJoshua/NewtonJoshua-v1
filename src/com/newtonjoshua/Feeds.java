package com.newtonjoshua;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class Feeds {
	public static JSONObject getFeed() throws Exception {
		URL url = new URL(Messages.getString("Feeds.0")); //$NON-NLS-1$
		HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
		// Reading the feed
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(httpcon));
		@SuppressWarnings("unchecked")
		List<SyndEntry> entries = feed.getEntries();
		Iterator<SyndEntry> itEntries = entries.iterator();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonEle;
		while (itEntries.hasNext()) {
			jsonEle = new JSONObject();
			SyndEntry entry = itEntries.next();
			jsonEle.put(Messages.getString("Feeds.1"), entry.getTitle()); //$NON-NLS-1$
			jsonEle.put(Messages.getString("Feeds.2"), entry.getLink()); //$NON-NLS-1$
			// Date
			String contents = entry.getContents().toString();
			int begin = contents.indexOf(Messages.getString("Feeds.3")); //$NON-NLS-1$
			contents = contents.substring(begin + 19);
			int end = contents.indexOf(Messages.getString("Feeds.4")); //$NON-NLS-1$
			contents = contents.substring(0, end);
			contents = contents.trim();
			String Date = contents;
			jsonEle.put(Messages.getString("Feeds.5"), Date); //$NON-NLS-1$
			// Activity
			contents = entry.getContents().toString();
			begin = contents.indexOf(Messages.getString("Feeds.6")); //$NON-NLS-1$
			contents = contents.substring(begin + 14);
			end = contents.indexOf(Messages.getString("Feeds.7")); //$NON-NLS-1$
			contents = contents.substring(0, end);
			contents = contents.trim();
			String Activity = contents;
			jsonEle.put(Messages.getString("Feeds.8"), Activity); //$NON-NLS-1$
			// Repository
			contents = entry.getContents().toString();
			begin = contents.indexOf(Messages.getString("Feeds.9")); //$NON-NLS-1$
			contents = contents.substring(begin + 13);
			end = contents.indexOf(Messages.getString("Feeds.10")); //$NON-NLS-1$
			contents = contents.substring(0, end);
			contents = contents.trim();
			String Repository = contents;
			jsonEle.put(Messages.getString("Feeds.11"), Messages.getString("Feeds.12") + Repository); //$NON-NLS-1$ //$NON-NLS-2$

			if (!entry.getContents().toString().contains(Messages.getString("Feeds.13"))) { //$NON-NLS-1$
				jsonEle.put(Messages.getString("Feeds.14"), entry.getTitle()); //$NON-NLS-1$
				jsonEle.put(Messages.getString("Feeds.15"), Messages.getString("Feeds.16")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// System.out.println("Title: " + entry.getTitle());
			// System.out.println("Link: " + entry.getLink());
			// System.out.println("Author: " + entry.getAuthor());
			// System.out.println("Publish Date: " + entry.getPublishedDate());
			// if(entry.getDescription() != null){
			// System.out.println("Description: " +
			// entry.getDescription().getValue());
			// }
			// System.out.println();
			jsonArray.put(jsonEle);
		}
		json.put(Messages.getString("Feeds.17"), jsonArray); //$NON-NLS-1$
		// System.out.println(json.toString());
		return json;
	}

}
