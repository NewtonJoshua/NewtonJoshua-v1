package com.newtonjoshua;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;

public class ReCaptcha {
	public static String verify(String res) throws ServletException, IOException {
		URL url = new URL("https://www.google.com/recaptcha/api/siteverify?secret=6LeLMwoTAAAAAPpnEQo4AV4RqH8cWT9wU3zDKDhI&response="+res);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(
		        new InputStreamReader(conn.getInputStream()));
		while ((line = reader.readLine()) != null) {
		    outputString += line;
		}
		System.out.println(outputString);
		String[] splitted=outputString.split("}");
		res=splitted[0];
		String[] split=outputString.split(":");
		res=split[1];
		res=res.trim();
		return res;

	}
}
