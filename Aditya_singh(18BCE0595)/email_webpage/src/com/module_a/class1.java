package com.module_a;


import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class class1 {
	
	static void Mail(String username,String password,String toEmail)
	{

	
		// TODO Auto-generated method stub
		
		String fromEmail = "alphatest21121999@gmail.com";
		
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
			msg.setSubject("Request to take action against the defaulters");
			Multipart emailContent = new MimeMultipart();
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Respected sir;"
					+ "I Satish c.j,Bank manager of the following bank branch - request you to take appropriate action on the defaulters from the pdf attached below.");
			
			
			MimeBodyPart Attachment =new MimeBodyPart();
			Attachment.attachFile("Z:\\Reviews-export.xlsx");
			
			emailContent.addBodyPart(textBodyPart);
			emailContent.addBodyPart(Attachment);
			
			msg.setContent(emailContent);
			Transport.send(msg);
			System.out.println("message sent");
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}

