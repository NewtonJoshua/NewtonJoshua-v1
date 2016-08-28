package com.newtonjoshua;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class FB_Posts {
	public static JSONObject facebook() throws Exception {
		String access_token="EAAK9ZBbCHjT8BAOqRQEGOTMGPrEkpC6t5wowBtF8ZB35dDhKFnx9ffiZCbyLr7lfL74twAuiiIl9US3tNrfvCE7kmvKifZABh2bOzaBPdBvSBAilKfxFPdGXMhcDuPtglCJbWcUNliynsje6LIQP0W9sNFcMnpMZD";
		URL obj = new URL("https://graph.facebook.com/me/feed?access_token="+access_token);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty( "Content-Type", "application/json");
		con.setDoOutput(true);
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject FB      = new JSONObject();
		JSONArray  FBArray = new JSONArray();
		JSONObject FBEle;
		
		JSONObject res      = new JSONObject(response.toString());
		JSONArray  jsonArray = new JSONArray(res.get("data").toString());
		for (int i=0; i< jsonArray.length(); i++ ){
			JSONObject jsonEle=new JSONObject(jsonArray.getJSONObject(i).toString());
			JSONObject jsonFrom= new JSONObject(jsonEle.get("from").toString());
			if(jsonFrom.get("id").toString().equals("1008531222504381")){
				FBEle = new JSONObject();
				FBEle.put("story", "");
				if(jsonEle.has("story")){
					FBEle.put("story", jsonEle.get("story").toString());
				}
				FBEle.put("message", "");
				if(jsonEle.has("message")){
					
					FBEle.put("message", jsonEle.get("message").toString());
				}
				FBEle.put("date", "");
				if(jsonEle.has("created_time")){
					String date=jsonEle.get("created_time").toString().substring(0, 9);
					DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
					Date Ddate = srcDf.parse(date);
					DateFormat destDf = new SimpleDateFormat("MMM dd,yyyy");
					date = destDf.format(Ddate);

					FBEle.put("date", date);
				}
				FBEle.put("picture", "");
				if(jsonEle.has("picture")){
					
					FBEle.put("picture", jsonEle.get("picture").toString().replace("https", "http"));
				}
				FBEle.put("link", "");
				if(jsonEle.has("actions")){
					JSONArray  jsonActions = new JSONArray(jsonEle.get("actions").toString());
					JSONObject jsonLink=new JSONObject(jsonActions.getJSONObject(0).toString());
					
					FBEle.put("link", jsonLink.get("link").toString());
				}
				FBArray.put(FBEle);
			}
			
		}
		FB.put("Facebook", FBArray);
		return FB;
	}
}
