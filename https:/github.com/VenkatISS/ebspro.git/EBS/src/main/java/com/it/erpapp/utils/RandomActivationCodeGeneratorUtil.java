package com.it.erpapp.utils;

import java.util.Random;

public class RandomActivationCodeGeneratorUtil {

	private static char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();

	public synchronized static String generateActivationCode() {

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
}
