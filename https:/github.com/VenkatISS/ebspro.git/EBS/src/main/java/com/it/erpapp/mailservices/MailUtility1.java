/*package com.it.erpapp.mailservices;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailUtility {
	public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail,
			String activationCode) {
		String urlParams = "actionId=1004&ai=" + agencyId + "&ac=" + activationCode;
//		String host = "dedrelay.secureserver.net";
		String host = "email.secureserver.net";
		// String host = "192.168.100.52";
		// String host="s198-12-156-132.secureserver.net";

		String from = "info@ilpg.in";
		String subject = "YOUR ACCOUNT WITH www.mylpgbooks.com";
		String messageText = "";
		try {
			messageText = "Dear Dealer,<br><br>Your account has now been created.<br>"
					+ "You have successfully registetred. Thank you for registering with www.mylpgbooks.com <br><br>"
					+ "Please click below link to login your account <br>"
					+ "<a href=https://www.mylpgbooks.com/ebs/RegistrationControlServlet?" + urlParams + ">CLICK HERE</a>"
					+ "<br><br>" + "Regards<br>" + "Your friends at MyLPGBooks TEAM";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMail(host, from, reciepentEmail, subject, messageText);
		System.out.println("Mail Sent...");
	}

	// Reset password mail
	public synchronized void sendResetPasswordMail(long agencyId, String reciepentEmail, long resetPwddate) {
		// String urlParams =
		// "methodId=1005&page=reset_password&dId=0&ac="+activationCode;
		String urlParams = "actionId=1005&ai=" + agencyId + "&fdate=" + resetPwddate;

//		String host = "dedrelay.secureserver.net";
		String host = "email.secureserver.net";
		String from = "info@ilpg.in";
		String subject = "Reset Password Details";
		String messageText = "";
		try {
			messageText = "Dear Dealer,<br><br>You have requested to reset your password.<br><br>"
					+ "Please click below link to reset your account password<br>"
					+ "<a href=https://www.mylpgbooks.com/ebs/login?" + urlParams + ">CLICK HERE</a>"
					+ "<br><br>"
					+ "Please ignore this email if you did not request for a new password. Your current password will remain unchanged."
					+ "Regards<br>" + "Your friends at MyLPGBooks TEAM";
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMail(host, from, reciepentEmail, subject, messageText);
		System.out.println("Mail Sent...");
	}
	
	//forgot security pin mail
    public synchronized void sendOtpThroughMail(long agencyId, String reciepentEmail,int otp) {
    	//String urlParams = "actionId=1004&ai="+agencyId+"&ac="+otp;
    		
//    	String host = "dedrelay.secureserver.net";
		String host = "email.secureserver.net";
    	String from = "info@ilpg.in";
    	String subject = "Reset PIN ";
		
		String mText = "";
		try {
			mText = "Dear Dealer,<br><br>Here is a one-time verification code to reset your PIN.<br>"
                    + "This code ensures that only you can access your account.  <br><br>"
                    + "Your Verification Code:"+otp
                    + "<br><br>"
                    + "Valid for <br>"
                    + "8 minutes <br>"
                    + "Regards<br>" + "Your friends at MyLPGBooks TEAM";
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMail(host, from, reciepentEmail, subject, mText);
		System.out.println("Mail Sent...");
    }
	
	public void sendMail(String host, String from, String reciepentEmail, String subject, String messageText) {
		boolean sessionDebug = false;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		System.out.println(host);
		props.put("mail.transport.protocol", "smtp");
		// Changes Done by Venkat to test mail starts
		// props.put("mail.smtp.port", "25");
		// props.put("mail.smtp.starttls.enable", "true");
		
		 // props.put("mail.smtp.starttls.enable", "true");
		 // props.put("mail.smtp.port", "25"); 
		// props.put("mail.smtp.auth","true");		 
		// Changes Done by Venkat to test mail ends

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(sessionDebug);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(messageText, "text/html");

			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
*/

/*
package com.it.erpapp.mailservices;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailUtility {
	public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail,
			String activationCode) {
		String urlParams = "actionId=1004&ai=" + agencyId + "&ac=" + activationCode;
		String host = "dedrelay.secureserver.net";
		// String host = "192.168.100.52";
		// String host="s198-12-156-132.secureserver.net";

		String from = "info@ilpg.in";
		String subject = "YOUR ACCOUNT WITH www.mylpgbooks.com";
		String messageText = "";
		try {
			messageText = "Dear Dealer,<br><br>Your account has now been created.<br>"
					+ "You have successfully registetred. Thank you for registering with www.mylpgbooks.com <br><br>"
					+ "Please click below link to login your account <br>"
					+ "<a href=https://www.mylpgbooks.com/ebs/RegistrationControlServlet?" + urlParams + ">CLICK HERE</a>"
					+ "<br><br>" + "Regards<br>" + "Your friends at MyLPGBooks TEAM";
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMail(host, from, reciepentEmail, subject, messageText);
		System.out.println("Mail Sent...");
	}

	// Reset password mail
	public synchronized void sendResetPasswordMail(long agencyId, String reciepentEmail, long resetPwddate) {
		// String urlParams =
		// "methodId=1005&page=reset_password&dId=0&ac="+activationCode;
		String urlParams = "actionId=1005&ai=" + agencyId + "&fdate=" + resetPwddate;

		String host = "dedrelay.secureserver.net";
		String from = "info@ilpg.in";
		String subject = "Reset Password Details";
		String messageText = "";
		try {
			messageText = "Dear Dealer,<br><br>You have requested to reset your password.<br><br>"
					+ "Please click below link to reset your account password<br>"
					+ "<a href=https://www.mylpgbooks.com/ebs/login?" + urlParams + ">CLICK HERE</a>"
					+ "<br><br>"
					+ "Please ignore this email if you did not request for a new password. Your current password will remain unchanged."
					+ "Regards<br>" + "Your friends at MyLPGBooks TEAM";
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMail(host, from, reciepentEmail, subject, messageText);
		System.out.println("Mail Sent...");
	}
	
	//forgot security pin mail
    public synchronized void sendOtpThroughMail(long agencyId, String reciepentEmail,int otp) {
    	//String urlParams = "actionId=1004&ai="+agencyId+"&ac="+otp;
    		
    	String host = "dedrelay.secureserver.net";
    	String from = "info@ilpg.in";
    	String subject = "Reset PIN ";
		
		String mText = "";
		try {
			mText = "Dear Dealer,<br><br>Here is a one-time verification code to reset your PIN.<br>"
                    + "This code ensures that only you can access your account.  <br><br>"
                    + "Your Verification Code:"+otp
                    + "<br><br>"
                    + "Valid for <br>"
                    + "8 minutes <br>"
                    + "Regards<br>" + "Your friends at MyLPGBooks TEAM";
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendMail(host, from, reciepentEmail, subject, mText);
		System.out.println("Mail Sent...");
    }
	
	public void sendMail(String host, String from, String reciepentEmail, String subject, String messageText) {
		boolean sessionDebug = false;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		System.out.println(host);
		props.put("mail.transport.protocol", "smtp");
		// Changes Done by Venkat to test mail starts
		// props.put("mail.smtp.port", "25");
		// props.put("mail.smtp.starttls.enable", "true");
		
		 // props.put("mail.smtp.starttls.enable", "true");
		 // props.put("mail.smtp.port", "25");
		// props.put("mail.smtp.auth","true");		 
		// Changes Done by Venkat to test mail ends

		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(sessionDebug);
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(messageText, "text/html");

			Transport.send(msg);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
*/


// Local mail
package com.it.erpapp.mailservices;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailUtility1 {
	public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail,
			String activationCode) {
		String urlParams = "actionId=1004&ai=" + agencyId + "&ac=" + activationCode;
		final String username = "do-not-reply@wearberryworld.com";
		final String password = "Test@321";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "wearberryworld.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			String mText = "Dear Dealer,<br><br>Your account has now been created.<br>"
					+ "You have successfully registetred. Thank you for registering with www.mylpgbooks.com <br><br>"
					+ "Please click below link to login your account <br>"
					+ "<a href=http://localhost:8080/ebs/RegistrationControlServlet?" + urlParams + ">CLICK HERE</a>"
					+ "<br><br>" + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			// String mText="Dear Dealer,<br><br>Your account has now been
			// created.<br>"
			// + "You have successfully registetred. Thank you for registering
			// with www.mylpgbooks.com <br><br>"
			// + "Please click below link to login your account <br>"
			// + "<a
			// href=http://192.168.100.211:8080/ebs/RegistrationControlServlet?"+urlParams+">CLICK
			// HERE</a>"
			// + "<br><br>"
			// + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			// String mText="Dear Dealer,<br><br>Your account has now been
			// created.<br>"
			// + "You have successfully registetred. Thank you for registering
			// with www.mylpgbooks.com <br><br>"
			// + "Please click below link to login your account <br>"
			// + "<a
			// href=http://www.mylpgbooks.com/ebs/RegistrationControlServlet?"+urlParams+">CLICK
			// HERE</a>"
			// + "<br><br>"
			// + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@wearberryworld.com"));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			message.setRecipients(Message.RecipientType.TO, address);
			// message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("venkat.mvr4u@gmail.com"));
			message.setSubject("YOUR ACCOUNT WITH www.mylpgbooks.com");
			message.setContent(mText, "text/html");

			// message.setText(text);

			Transport.send(message);

			System.out.println("Mail Sent ....");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	// update password mail
	public synchronized void sendResetPasswordMail(long agencyId, String reciepentEmail, long resetPwddate) {
		// String urlParams =
		// "methodId=1005&page=reset_password&dId=0&ac="+activationCode;

		String urlParams = "actionId=1005&ai=" + agencyId + "&fdate=" + resetPwddate;

		final String username = "do-not-reply@wearberryworld.com";
		final String password = "Test@321";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "wearberryworld.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		// String host = "wearberryworld.com";
		// String host="localhost";

		// String from = "do-not-reply@wearberryworld.com";
		// String subject = "Reset Password Details";
		String messageText = "";
		try {
			// messageText = "Dear Dealer,<br><br>You have requested to reset
			// your password.<br><br>"
			// + "Please click below link to reset your account password<br>"
			// + "<a href=http://localhost:8080/ebs/login?"+urlParams+"'>CLICK
			// HERE</a>"
			// + "<br><br>"
			// +"Please ignore this email if you did not request for a new
			// password. Your current password will remain unchanged."
			// + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			messageText = "Dear Dealer,<br><br>You have requested to reset your password.<br><br>"
					+ "Please click below link to reset your account password<br>"
					+ "<a href=http://localhost:8080/ebs/login?" + urlParams + ">CLICK HERE</a>" + "<br><br>"
					+ "Please ignore this email if you did not request for a new password. Your current password will remain unchanged."
					+ "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			// messageText = "Dear Dealer,<br><br>You have requested to reset
			// your password.<br><br>"
			// + "Please click below link to reset your account password<br>"
			// + "<a
			// href=http://192.168.100.211:8080/ebs/login?"+urlParams+"'>CLICK
			// HERE</a>"
			// + "<br><br>"
			// +"Please ignore this email if you did not request for a new
			// password. Your current password will remain unchanged."
			// + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@wearberryworld.com"));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			message.setRecipients(Message.RecipientType.TO, address);
			// message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("venkat.mvr4u@gmail.com"));
			message.setSubject("YOUR MyLPGBooks DEALER ACCOUNT PASSWORD");
			message.setContent(messageText, "text/html");

			// message.setText(text);

			Transport.send(message);

			System.out.println("Mail Sent ....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// sendMail(host, from, reciepentEmail, subject, messageText);
		// System.out.println("Mail Sent...");

	}

	// forgot security pin mail
	public synchronized void sendOtpThroughMail(long agencyId, String reciepentEmail, int otp) {
		// String urlParams = "actionId=1004&ai="+agencyId+"&ac="+otp;
		final String username = "do-not-reply@wearberryworld.com";
		final String password = "Test@321";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "wearberryworld.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			String mText = "Dear Dealer,<br><br>Here is a one-time verification code to reset your password.<br>"
					+ "This code ensures that only you can access your account.  <br><br>" + "Your Verification Code:"
					+ otp + "<br><br>" + "Valid for <br>" + "8 minutes <br>" + "Regards<br>"
					+ "Your friends at MyLPGBooks TEAM";

			// String mText="Dear Dealer,<br><br>Your account has now been
			// created.<br>"
			// + "You have successfully registetred. Thank you for registering
			// with www.mylpgbooks.com <br><br>"
			// + "Please click below link to login your account <br>"
			// + "<a
			// href=http://www.mylpgbooks.com/ebs/RegistrationControlServlet?"+urlParams+">CLICK
			// HERE</a>"
			// + "<br><br>"
			// + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@wearberryworld.com"));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			message.setRecipients(Message.RecipientType.TO, address);
			// message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("venkat.mvr4u@gmail.com"));
			message.setSubject("YOUR ACCOUNT WITH www.mylpgbooks.com");
			message.setContent(mText, "text/html");

			// message.setText(text);

			Transport.send(message);

			System.out.println("OTP Mail Sent ....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}




// Server mail 
/*package com.it.erpapp.mailservices;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

@Component
public class MailUtility {
	public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail,
			String activationCode) {
		String urlParams = "actionId=1004&ai=" + agencyId + "&ac=" + activationCode;
		final String username = "do-not-reply@wearberryworld.com";
		final String password = "Test@321";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "wearberryworld.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			String mText = "Dear Dealer,<br><br>Your account has now been created.<br>"
					+ "You have successfully registetred. Thank you for registering with www.mylpgbooks.com <br><br>"
					+ "Please click below link to login your account <br>"
					+ "<a href=https://www.mylpgbooks.com/ebs/RegistrationControlServlet?" + urlParams + ">CLICK HERE</a>"
					+ "<br><br>" + "Regards<br>" + "Your friends at MyLPGBooks TEAM";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@wearberryworld.com"));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("YOUR ACCOUNT WITH www.mylpgbooks.com");
			message.setContent(mText, "text/html");

			Transport.send(message);

			System.out.println("Mail Sent ....");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	// update password mail
	public synchronized void sendResetPasswordMail(long agencyId, String reciepentEmail, long resetPwddate) {
		String urlParams = "actionId=1005&ai=" + agencyId + "&fdate=" + resetPwddate;

		final String username = "do-not-reply@wearberryworld.com";
		final String password = "Test@321";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "wearberryworld.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		String messageText = "";
		try {
			messageText = "Dear Dealer,<br><br>You have requested to reset your password.<br><br>"
					+ "Please click below link to reset your account password<br>"
					+ "<a href=https://www.mylpgbooks.com/ebs/login?" + urlParams + ">CLICK HERE</a>" + "<br><br>"
					+ "Please ignore this email if you did not request for a new password. Your current password will remain unchanged."
					+ "Regards<br>" + "Your friends at MyLPGBooks TEAM";	


			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@wearberryworld.com"));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("YOUR MyLPGBooks DEALER ACCOUNT PASSWORD");
			message.setContent(messageText, "text/html");

			Transport.send(message);

			System.out.println("Mail Sent ....");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// forgot security pin mail
	public synchronized void sendOtpThroughMail(long agencyId, String reciepentEmail, int otp) {
		final String username = "do-not-reply@wearberryworld.com";
		final String password = "Test@321";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "wearberryworld.com");
		props.put("mail.smtp.port", "25");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			String mText = "Dear Dealer,<br><br>Here is a one-time verification code to reset your password.<br>"
					+ "This code ensures that only you can access your account.  <br><br>" + "Your Verification Code:"
					+ otp + "<br><br>" + "Valid for <br>" + "8 minutes <br>" + "Regards<br>"
					+ "Your friends at MyLPGBooks TEAM";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("do-not-reply@wearberryworld.com"));
			InternetAddress[] address = { new InternetAddress(reciepentEmail) };
			message.setRecipients(Message.RecipientType.TO, address);
			message.setSubject("YOUR ACCOUNT WITH www.mylpgbooks.com");
			message.setContent(mText, "text/html");

			Transport.send(message);

			System.out.println("OTP Mail Sent ....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
*/













//server mail

/*@Component
public class MailUtility {
public synchronized void sendRegistrationActivationMail(long agencyId, String reciepentEmail,
              String activationCode) {
      //ylpgbooks.com./45.127.101.199
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
                              + "You have successfully registetred. Thank you for registering with www.ilpg.in <br><br>"
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
}*/