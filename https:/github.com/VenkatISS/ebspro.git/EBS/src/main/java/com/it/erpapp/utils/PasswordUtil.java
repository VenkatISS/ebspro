package com.it.erpapp.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.it.erpapp.framework.managers.AgencyPersistenceManager;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class PasswordUtil {
	static Logger logger = Logger.getLogger(PasswordUtil.class.getName());
	
	public static synchronized String encryptPasscode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = null;
		md = MessageDigest.getInstance("SHA"); //step 2
		
		md.update(str.getBytes("UTF-8")); //step 3	      
	    
		byte raw[] = md.digest(); //step 4
		String hash = (new BASE64Encoder()).encode(raw); //step 5
		return hash; //step 6
	}

	public static void main(String args[]) {
		try {
			System.out.println(encryptPasscode("5555555555"));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}
}
