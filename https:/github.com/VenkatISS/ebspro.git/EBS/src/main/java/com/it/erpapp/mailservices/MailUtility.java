package com.it.erpapp.mailservices;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;



/*
//server mail

@Component
public class MailUtility {
	public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail, String activationCode) {
        //mylpgbooks.com./45.127.101.199
        String urlParams = "actionId=1004&ai=" + agencyId + "&ac=" + activationCode;
        final String SMTP_HOST_NAME = "smtpout.secureserver.net";
        final int SMTP_HOST_PORT = 465; //port number
        final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
        final String SMTP_AUTH_PWD = "Welcome@321";

        try {
        	Properties props = new Properties();
        	props.put("mail.transport.protocol", "smtps");
        	props.put("mail.smtps.host", SMTP_HOST_NAME);
        	props.put("mail.smtps.auth", "true");

        	Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);

            String mText = "Dear Dealer,<br><br>Your account has now been created.<br>"
                                + "You have successfully registetred. Thank you for registering with www.mylpgbooks.com <br><br>"
                                + "Please click below link to login your account <br>"
                                + "<a href=https://www.mylpgbooks.com/ebs/RegistrationControlServlet?" + urlParams + ">CLICK HERE</a>"
                                + "<br><br>" + "Regards<br>" + "Your friends at MYLPGBOOKS TEAM";

            message.setSubject("YOUR ACCOUNT WITH mylpgbooks.com");
            message.setContent(mText, "text/html");
            Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
            message.addFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)

            transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
	}

	//update password mail
	public synchronized void sendResetPasswordMail(long agencyId, String reciepentEmail, long resetPwddate) {

        String urlParams = "actionId=1005&ai=" + agencyId + "&fdate=" + resetPwddate;

        final String SMTP_HOST_NAME = "smtpout.secureserver.net";
        final int SMTP_HOST_PORT = 465; //port number
        final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
        final String SMTP_AUTH_PWD = "Welcome@321";

        try {
        	Properties props = new Properties();
        	props.put("mail.transport.protocol", "smtps");
        	props.put("mail.smtps.host", SMTP_HOST_NAME);
        	props.put("mail.smtps.auth", "true");

        	Session mailSession = Session.getDefaultInstance(props);
        	mailSession.setDebug(true);
        	Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            String messageText = "";


            messageText = "Dear Dealer,<br><br>You have requested to reset your password.<br><br>"
            		+ "Please click below link to reset your account password<br>"
            		+ "<a href=https://www.mylpgbooks.com/ebs/login?" + urlParams + ">CLICK HERE</a>" + "<br><br>"
            		+ "Please ignore this email if you did not request for a new password. Your current password will remain unchanged."
            		+ "Regards<br>" + "Your friends at MYLPGBOOKS TEAM";

            message.setSubject("YOUR ACCOUNT WITH mylpgbooks.com");
            message.setContent(messageText, "text/html");
            Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
            message.addFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)

            transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            System.out.println("Mail Sent ....");
        } catch (Exception e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        // sendMail(host, from, reciepentEmail, subject, messageText);
        // System.out.println("Mail Sent...");
	}

	//forgot security pin mail
	public synchronized void sendOtpThroughMail(long agencyId, String reciepentEmail, int otp) {

		final String SMTP_HOST_NAME = "smtpout.secureserver.net";
        final int SMTP_HOST_PORT = 465; //port number
        final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
        final String SMTP_AUTH_PWD = "Welcome@321";

        try {
        	Properties props = new Properties();
        	props.put("mail.transport.protocol", "smtps");
        	props.put("mail.smtps.host", SMTP_HOST_NAME);
        	props.put("mail.smtps.auth", "true");

        	Session mailSession = Session.getDefaultInstance(props);
            mailSession.setDebug(true);
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);

            String mText = "Dear Dealer,<br><br>Here is a one-time verification code to reset your password.<br>"
            		+ "This code ensures that only you can access your account.  <br><br>" + "Your Verification Code:"
            		+ otp + "<br><br>" + "Valid for <br>" + "8 minutes <br>" + "Regards<br>"
            		+ "Your friends at MYLPGBOOKS TEAM";
            
            message.setSubject("YOUR ACCOUNT WITH mylpgbooks.com");
            message.setContent(mText, "text/html");
            Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
            message.addFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)

            transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
                    
            System.out.println("OTP Mail Sent ....");
        } catch (MessagingException e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
        
	}
	
	//	SENDING CONFIRMATION EMAILS TO OLD AND NEW EMAIL IDS - FOR EMAILID UPDATE
	public synchronized String sendingIntimationEmailsForEmailIdChange(long agencyId, String reciepentEmail, String flag, String oldEmailId, String newEmailId) {
		String mstatus = "ERROR";
		final String SMTP_HOST_NAME = "smtpout.secureserver.net";
		final int SMTP_HOST_PORT = 465; //port number
		final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
		final String SMTP_AUTH_PWD = "Welcome@321";

		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtps");
			props.put("mail.smtps.host", SMTP_HOST_NAME);
			props.put("mail.smtps.auth", "true");

			Session mailSession = Session.getDefaultInstance(props);
			mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			String mText = "";
			String color = "#2196F3";
			if(flag.equals("new")){
				mText = "<b>Request For Changing Email Address </b><br><br>"
						+ "Dear Dealer,<br><br>We have received a request to add an Email Id <font color="+color+">"+newEmailId+"</font><br> with your "
						+ "dealer MyLPGBooks Account linked with <font color="+color+">"+ agencyId + "</font>.  <br><br>"
						
						+ "Please confirm this request by clicking on CONFIRM EMAIL ADDRESS .<br><br>"
						+ "<a href=https://www.mylpgbooks.com/ebs/AgencyDataControlServlet?actionId=9011&agencyId="+agencyId+"&oldEmailId="+oldEmailId +"&newEmailId="+newEmailId+" >Confirm Email Address</a>" + "<br><br>"
//						+ "<a href=http://localhost:8080/ebs/AgencyDataControlServlet?actionId=9011&agencyId="+agencyId+"&oldEmailId="+oldEmailId +"&newEmailId="+newEmailId+">Confirm Email Address</a>" + "<br><br>"
						+ "If you did not made this request, please contact us immediately."
						+ "<br><br><br>" + "Regards<br>"
						+ "Your friends at MYLPGBOOKS TEAM";
				
				message.setSubject("Email ID change request for your dealer account with mylpgbooks.com");
			}else if(flag.equals("old")) {
				mText = "<b>Request For Changing Email Address </b><br><br>"
						+ "Dear Dealer,<br><br>We have received a request to change your current email address associated with <br>"
						+ "dealer MyLPGBooks account of <font color="+color+">"+agencyId+"</font> to <font color="+color+">"+newEmailId+"</font>.<br><br>"
						+ "If you did not made this request, please contact us immediately."
						+ "<br><br><br>" + "Regards<br>"
						+ "Your friends at MYLPGBOOKS TEAM";
				
				message.setSubject("Email ID change request for your dealer account with mylpgbooks.com");
			}
			
//			message.setSubject("Email ID change request for your dealer account with mylpgbooks.com");
			message.setContent(mText, "text/html");
			Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
			message.addFrom(from);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)
			
			transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
			System.out.println("OTP Mail Sent ....");
			mstatus = "SUCCESS";
		} catch (MessagingException e) {
			mstatus = "ERROR";
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return mstatus;
	}
}

*/


//local mail
@Component
public class MailUtility {
	public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail, String activationCode) {
      //mylpgbooks.com./45.127.101.199
      String urlParams = "actionId=1004&ai=" + agencyId + "&ac=" + activationCode;
      final String SMTP_HOST_NAME = "smtpout.secureserver.net";
      final int SMTP_HOST_PORT = 465; //port number
      final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
      final String SMTP_AUTH_PWD = "Welcome@321";

      try {
      	Properties props = new Properties();
      	props.put("mail.transport.protocol", "smtps");
      	props.put("mail.smtps.host", SMTP_HOST_NAME);
      	props.put("mail.smtps.auth", "true");

      	Session mailSession = Session.getDefaultInstance(props);
          mailSession.setDebug(true);
          Transport transport = mailSession.getTransport();
          MimeMessage message = new MimeMessage(mailSession);

          String mText = "Dear Dealer,<br><br>Your account has now been created.<br>"
                              + "You have successfully registetred. Thank you for registering with www.mylpgbooks.com <br><br>"
                              + "Please click below link to login your account <br>"
                              + "<a href=http://localhost:8080/ebs/RegistrationControlServlet?" + urlParams + ">CLICK HERE</a>"
                              + "<br><br>" + "Regards<br>" + "Your friends at MYLPGBOOKS TEAM";

          message.setSubject("YOUR ACCOUNT WITH mylpgbooks.com");
          message.setContent(mText, "text/html");
          Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
          message.addFrom(from);
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)

          transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
          transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
          transport.close();
      } catch (Exception e) {
      	e.printStackTrace();
      	throw new RuntimeException(e);
      }
	}

	//update password mail
	public synchronized void sendResetPasswordMail(long agencyId, String reciepentEmail, long resetPwddate) {

      String urlParams = "actionId=1005&ai=" + agencyId + "&fdate=" + resetPwddate;

      final String SMTP_HOST_NAME = "smtpout.secureserver.net";
      final int SMTP_HOST_PORT = 465; //port number
      final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
      final String SMTP_AUTH_PWD = "Welcome@321";

      try {
      	Properties props = new Properties();
      	props.put("mail.transport.protocol", "smtps");
      	props.put("mail.smtps.host", SMTP_HOST_NAME);
      	props.put("mail.smtps.auth", "true");

      	Session mailSession = Session.getDefaultInstance(props);
      	mailSession.setDebug(true);
      	Transport transport = mailSession.getTransport();
          MimeMessage message = new MimeMessage(mailSession);
          String messageText = "";


          messageText = "Dear Dealer,<br><br>You have requested to reset your password.<br><br>"
          		+ "Please click below link to reset your account password<br>"
          		+ "<a href=http://localhost:8080/ebs/login?" + urlParams + ">CLICK HERE</a>" + "<br><br>"
          		+ "Please ignore this email if you did not request for a new password. Your current password will remain unchanged."
          		+ "Regards<br>" + "Your friends at MYLPGBOOKS TEAM";

          message.setSubject("YOUR ACCOUNT WITH mylpgbooks.com");
          message.setContent(messageText, "text/html");
          Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
          message.addFrom(from);
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)

          transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
          transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
          transport.close();
          System.out.println("Mail Sent ....");
      } catch (Exception e) {
      	e.printStackTrace();
      	throw new RuntimeException(e);
      }
      // sendMail(host, from, reciepentEmail, subject, messageText);
      // System.out.println("Mail Sent...");
	}

	//forgot security pin mail
	public synchronized void sendOtpThroughMail(long agencyId, String reciepentEmail, int otp) {

		final String SMTP_HOST_NAME = "smtpout.secureserver.net";
      final int SMTP_HOST_PORT = 465; //port number
      final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
      final String SMTP_AUTH_PWD = "Welcome@321";

      try {
      	Properties props = new Properties();
      	props.put("mail.transport.protocol", "smtps");
      	props.put("mail.smtps.host", SMTP_HOST_NAME);
      	props.put("mail.smtps.auth", "true");

      	Session mailSession = Session.getDefaultInstance(props);
          mailSession.setDebug(true);
          Transport transport = mailSession.getTransport();
          MimeMessage message = new MimeMessage(mailSession);

          String mText = "Dear Dealer,<br><br>Here is a one-time verification code to reset your password.<br>"
          		+ "This code ensures that only you can access your account.  <br><br>" + "Your Verification Code:"
          		+ otp + "<br><br>" + "Valid for <br>" + "8 minutes <br>" + "Regards<br>"
          		+ "Your friends at MYLPGBOOKS TEAM";

          message.setSubject("YOUR ACCOUNT WITH mylpgbooks.com");
          message.setContent(mText, "text/html");
          Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
          message.addFrom(from);
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)

          transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
          transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
          transport.close();
                  
          System.out.println("OTP Mail Sent ....");
      } catch (MessagingException e) {
      	e.printStackTrace();
      	throw new RuntimeException(e);
      }
      
	}
	
	//	SENDING CONFIRMATION EMAILS TO OLD AND NEW EMAIL IDS - FOR EMAILID UPDATE
	public synchronized String sendingIntimationEmailsForEmailIdChange(long agencyId, String reciepentEmail, String flag, String oldEmailId, String newEmailId) {
		String mstatus = "ERROR";
		final String SMTP_HOST_NAME = "smtpout.secureserver.net";
		final int SMTP_HOST_PORT = 465; //port number
		final String SMTP_AUTH_USER = "do-not-reply@mylpgbooks.com";
		final String SMTP_AUTH_PWD = "Welcome@321";

		try {
			Properties props = new Properties();
			props.put("mail.transport.protocol", "smtps");
			props.put("mail.smtps.host", SMTP_HOST_NAME);
			props.put("mail.smtps.auth", "true");

			Session mailSession = Session.getDefaultInstance(props);
			mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();
			MimeMessage message = new MimeMessage(mailSession);
			String mText = "";
			String color = "#2196F3";
			if(flag.equals("new")){
				mText = "<b>Request For Changing Email Address </b><br><br>"
						+ "Dear Dealer,<br><br>We have received a request to add an Email Id <font color="+color+">"+newEmailId+"</font><br> with your "
						+ "dealer MyLPGBooks Account linked with <font color="+color+">"+ agencyId + "</font>.  <br><br>"
						
						+ "Please confirm this request by clicking on CONFIRM EMAIL ADDRESS .<br><br>"
						+ "<a href=http://localhost:8080/ebs/AgencyDataControlServlet?actionId=9011&agencyId="+agencyId+"&oldEmailId="+oldEmailId +"&newEmailId="+newEmailId+">Confirm Email Address</a>" + "<br><br>"
						+ "If you did not made this request, please contact us immediately."
						+ "<br><br><br>" + "Regards<br>"
						+ "Your friends at MYLPGBOOKS TEAM";
				
				message.setSubject("Email ID change request for your dealer account with mylpgbooks.com");
			}else if(flag.equals("old")) {
				mText = "<b>Request For Changing Email Address </b><br><br>"
						+ "Dear Dealer,<br><br>We have received a request to change your current email address associated with <br>"
						+ "dealer MyLPGBooks account of <font color="+color+">"+agencyId+"</font> to <font color="+color+">"+newEmailId+"</font>.<br><br>"
						+ "If you did not made this request, please contact us immediately."
						+ "<br><br><br>" + "Regards<br>"
						+ "Your friends at MYLPGBOOKS TEAM";
				
				message.setSubject("Email ID change request for your dealer account with mylpgbooks.com");
			}
			
//			message.setSubject("Email ID change request for your dealer account with mylpgbooks.com");
			message.setContent(mText, "text/html");
			Address[] from = InternetAddress.parse("do-not-reply@mylpgbooks.com");//Your domain email
			message.addFrom(from);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(reciepentEmail)); //Send email To (Type email ID that you want to send)
			
			transport.connect(SMTP_HOST_NAME,SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();
			
			System.out.println("OTP Mail Sent ....");
			mstatus = "SUCCESS";
		} catch (MessagingException e) {
			mstatus = "ERROR";
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return mstatus;
	}

}

