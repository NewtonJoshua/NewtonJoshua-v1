package com.newtonjoshua;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	private final static Logger LOGGER = Logger.getLogger("SendMail");
	public static String sendMail(String user, String name) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		//String msgBody = user;
		String htmlBody = user;
		Multipart mp = new MimeMultipart();
		try {
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("newton.angelin@gmail.com", "NewtonJoshua.com"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("newton.angelin@gmail.com", "User Notification"));
		    msg.setSubject(name +" visited NewtonJoshua.com");
		    //msg.setText(msgBody);
		    // html message
		       MimeBodyPart htmlPart = new MimeBodyPart();
		        htmlPart.setContent(htmlBody, "text/html");
		        mp.addBodyPart(htmlPart);
		        msg.setContent(mp);
		    Transport.send(msg);
		    
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
		}
		return "Success";
		
	}
	public static String sendException(String Exception) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("newton.angelin@gmail.com", "Error: NewtonJoshua.com"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("newton.angelin@gmail.com", "Error Notification"));
		    msg.setSubject("Critical Error in NewtonJoshua.com");
		    msg.setText(Exception);
		    Transport.send(msg);
		    
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
		}
		return "Success";
	}
	public static String sendCred(String mail, String user) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String htmlBody = "AITAM credentials are requested by <br>"+user+" ("+mail+")";
		Multipart mp = new MimeMultipart();
		try {

		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("newton.angelin@gmail.com", "AITAM viewer"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("newton.angelin@gmail.com","AITAM viewer"));
		    msg.setSubject("AITAM Demo- Login Credentials");
		       MimeBodyPart htmlPart = new MimeBodyPart();
		        htmlPart.setContent(htmlBody, "text/html");
		        mp.addBodyPart(htmlPart);
		        msg.setContent(mp);
		    Transport.send(msg);
	    
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
		}
		return "Success";
	}
}
