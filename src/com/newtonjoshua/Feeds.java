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
	URL url = new URL("https://github.com/NewtonJoshua.atom");
	HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
	// Reading the feed
	SyndFeedInput input = new SyndFeedInput();
	SyndFeed feed = input.build(new XmlReader(httpcon));
	@SuppressWarnings("unchecked")
	List<SyndEntry> entries = feed.getEntries();
	Iterator<SyndEntry> itEntries = entries.iterator();
	JSONObject json      = new JSONObject();
	JSONArray  jsonArray = new JSONArray();
	JSONObject jsonEle;
	while (itEntries.hasNext()) {
		jsonEle = new JSONObject();
		SyndEntry entry = itEntries.next();
		jsonEle.put("Title", entry.getTitle());
		jsonEle.put("Link", entry.getLink());
		//Date
		String contents= entry.getContents().toString();
		int begin=contents.indexOf("is=\"relative-time\">");
		contents=contents.substring(begin+19);
		int end=contents.indexOf("<");
		contents=contents.substring(0,end);
		contents=contents.trim();
		String Date=contents;
		jsonEle.put("Date", Date);
		//Activity
		contents= entry.getContents().toString();
		begin=contents.indexOf("<blockquote>\n");
		contents=contents.substring(begin+14);
		end=contents.indexOf("\n");
		contents=contents.substring(0,end);
		contents=contents.trim();
		String Activity= contents;
		jsonEle.put("Activity", Activity);
		//Repository
		contents= entry.getContents().toString();
		begin=contents.indexOf("target:repo");
		contents=contents.substring(begin+13);
		end=contents.indexOf("<");
		contents=contents.substring(0,end);
		contents=contents.trim();
		String Repository= contents;
		jsonEle.put("Repository", "@" + Repository);
		
		if(!entry.getContents().toString().contains("<blockquote>\n")){
			jsonEle.put("Activity", entry.getTitle());
			jsonEle.put("Repository", "");
		}
		
//		System.out.println("Title: " + entry.getTitle());
//		System.out.println("Link: " + entry.getLink());
//		System.out.println("Author: " + entry.getAuthor());
//		System.out.println("Publish Date: " + entry.getPublishedDate());
//		if(entry.getDescription() != null){
//			System.out.println("Description: " + entry.getDescription().getValue());
//		}
//		System.out.println();
		jsonArray.put(jsonEle);
	}
	json.put("GitHub", jsonArray);
	//System.out.println(json.toString());
	return json;
}


}
