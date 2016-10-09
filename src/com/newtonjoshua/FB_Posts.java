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
		String access_token = Messages.getString("FB_Posts.0"); //$NON-NLS-1$
		URL obj = new URL(Messages.getString("FB_Posts.1") + access_token); //$NON-NLS-1$
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod(Messages.getString("FB_Posts.2")); //$NON-NLS-1$
		con.setRequestProperty(Messages.getString("FB_Posts.3"), Messages.getString("FB_Posts.4")); //$NON-NLS-1$ //$NON-NLS-2$
		con.setDoOutput(true);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		JSONObject FB = new JSONObject();
		JSONArray FBArray = new JSONArray();
		JSONObject FBEle;

		JSONObject res = new JSONObject(response.toString());
		JSONArray jsonArray = new JSONArray(res.get(Messages.getString("FB_Posts.5")).toString()); //$NON-NLS-1$
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonEle = new JSONObject(jsonArray.getJSONObject(i).toString());
			JSONObject jsonFrom = new JSONObject(jsonEle.get(Messages.getString("FB_Posts.6")).toString()); //$NON-NLS-1$
			if (jsonFrom.get(Messages.getString("FB_Posts.7")).toString().equals(Messages.getString("FB_Posts.8"))) { //$NON-NLS-1$ //$NON-NLS-2$
				FBEle = new JSONObject();
				FBEle.put(Messages.getString("FB_Posts.9"), Messages.getString("FB_Posts.10")); //$NON-NLS-1$ //$NON-NLS-2$
				if (jsonEle.has(Messages.getString("FB_Posts.11"))) { //$NON-NLS-1$
					FBEle.put(Messages.getString("FB_Posts.12"), jsonEle.get(Messages.getString("FB_Posts.13")).toString()); //$NON-NLS-1$ //$NON-NLS-2$
				}
				FBEle.put(Messages.getString("FB_Posts.14"), Messages.getString("FB_Posts.15")); //$NON-NLS-1$ //$NON-NLS-2$
				if (jsonEle.has(Messages.getString("FB_Posts.16"))) { //$NON-NLS-1$

					FBEle.put(Messages.getString("FB_Posts.17"), jsonEle.get(Messages.getString("FB_Posts.18")).toString()); //$NON-NLS-1$ //$NON-NLS-2$
				}
				FBEle.put(Messages.getString("FB_Posts.19"), Messages.getString("FB_Posts.20")); //$NON-NLS-1$ //$NON-NLS-2$
				if (jsonEle.has(Messages.getString("FB_Posts.21"))) { //$NON-NLS-1$
					String date = jsonEle.get(Messages.getString("FB_Posts.22")).toString().substring(0, 9); //$NON-NLS-1$
					DateFormat srcDf = new SimpleDateFormat(Messages.getString("FB_Posts.23")); //$NON-NLS-1$
					Date Ddate = srcDf.parse(date);
					DateFormat destDf = new SimpleDateFormat(Messages.getString("FB_Posts.24")); //$NON-NLS-1$
					date = destDf.format(Ddate);

					FBEle.put(Messages.getString("FB_Posts.25"), date); //$NON-NLS-1$
				}
				FBEle.put(Messages.getString("FB_Posts.26"), Messages.getString("FB_Posts.27")); //$NON-NLS-1$ //$NON-NLS-2$
				if (jsonEle.has(Messages.getString("FB_Posts.28"))) { //$NON-NLS-1$

					FBEle.put(Messages.getString("FB_Posts.29"), jsonEle.get(Messages.getString("FB_Posts.30")).toString().replace(Messages.getString("FB_Posts.31"), Messages.getString("FB_Posts.32"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
				FBEle.put(Messages.getString("FB_Posts.33"), Messages.getString("FB_Posts.34")); //$NON-NLS-1$ //$NON-NLS-2$
				if (jsonEle.has(Messages.getString("FB_Posts.35"))) { //$NON-NLS-1$
					JSONArray jsonActions = new JSONArray(jsonEle.get(Messages.getString("FB_Posts.36")).toString()); //$NON-NLS-1$
					JSONObject jsonLink = new JSONObject(jsonActions.getJSONObject(0).toString());

					FBEle.put(Messages.getString("FB_Posts.37"), jsonLink.get(Messages.getString("FB_Posts.38")).toString()); //$NON-NLS-1$ //$NON-NLS-2$
				}
				FBArray.put(FBEle);
			}

		}
		FB.put(Messages.getString("FB_Posts.39"), FBArray); //$NON-NLS-1$
		return FB;
	}
}
