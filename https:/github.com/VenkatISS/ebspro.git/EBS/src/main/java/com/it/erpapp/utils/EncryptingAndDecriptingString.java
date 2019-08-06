package com.it.erpapp.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptingAndDecriptingString {

	byte[] encrypted;
	private final Logger logger = LoggerFactory.getLogger(EncryptingAndDecriptingString.class);

	public void encryption() {
		try {
			String text = "Admin ";
			String key = "Bar12345Bar12345";

			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			encrypted = cipher.doFinal(text.getBytes());
			logger.error(new String(encrypted));
		} catch (Exception e) {
			logger.error("Exception Occurred Is:", e);
		}
	}

	public void decryption(byte[] encrypted) {
		try {
			String key = "Bar12345Bar12345";

			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			String decrypted = new String(cipher.doFinal(encrypted));
			logger.error(decrypted);
		} catch (Exception e) {
			logger.error("Exception Occurred Is:", e);
		}
	}

	public static void main(String[] args) {
		EncryptingAndDecriptingString app = new EncryptingAndDecriptingString();
		app.encryption();
		app.decryption(app.encrypted);
	}
}