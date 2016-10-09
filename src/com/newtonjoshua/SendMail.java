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
	private final static Logger LOGGER = Logger.getLogger(Messages.getString("SendMail.0")); //$NON-NLS-1$

	public static String sendCred(String mail, String user) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		String htmlBody = Messages.getString("SendMail.1") + user + Messages.getString("SendMail.2") + mail + Messages.getString("SendMail.3"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		Multipart mp = new MimeMultipart();
		try {

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(Messages.getString("SendMail.4"), Messages.getString("SendMail.5"))); //$NON-NLS-1$ //$NON-NLS-2$
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(Messages.getString("SendMail.6"), Messages.getString("SendMail.7"))); //$NON-NLS-1$ //$NON-NLS-2$
			msg.setSubject(Messages.getString("SendMail.8")); //$NON-NLS-1$
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, Messages.getString("SendMail.9")); //$NON-NLS-1$
			mp.addBodyPart(htmlPart);
			msg.setContent(mp);
			Transport.send(msg);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("SendMail.10") + e.toString() + Messages.getString("SendMail.11") + e.getStackTrace().toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return Messages.getString("SendMail.12"); //$NON-NLS-1$
	}

	public static String sendException(String Exception) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(Messages.getString("SendMail.13"), Messages.getString("SendMail.14"))); //$NON-NLS-1$ //$NON-NLS-2$
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(Messages.getString("SendMail.15"), Messages.getString("SendMail.16"))); //$NON-NLS-1$ //$NON-NLS-2$
			msg.setSubject(Messages.getString("SendMail.17")); //$NON-NLS-1$
			msg.setText(Exception);
			Transport.send(msg);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("SendMail.18") + e.toString() + Messages.getString("SendMail.19") + e.getStackTrace().toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return Messages.getString("SendMail.20"); //$NON-NLS-1$
	}

	public static String sendMail(String user, String name) throws UnsupportedEncodingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		// String msgBody = user;
		String htmlBody = user;
		Multipart mp = new MimeMultipart();
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(Messages.getString("SendMail.21"), Messages.getString("SendMail.22"))); //$NON-NLS-1$ //$NON-NLS-2$
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(Messages.getString("SendMail.23"), Messages.getString("SendMail.24"))); //$NON-NLS-1$ //$NON-NLS-2$
			msg.setSubject(name + Messages.getString("SendMail.25")); //$NON-NLS-1$
			// msg.setText(msgBody);
			// html message
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(htmlBody, Messages.getString("SendMail.26")); //$NON-NLS-1$
			mp.addBodyPart(htmlPart);
			msg.setContent(mp);
			Transport.send(msg);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("SendMail.27") + e.toString() + Messages.getString("SendMail.28") + e.getStackTrace().toString()); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return Messages.getString("SendMail.29"); //$NON-NLS-1$

	}
}
