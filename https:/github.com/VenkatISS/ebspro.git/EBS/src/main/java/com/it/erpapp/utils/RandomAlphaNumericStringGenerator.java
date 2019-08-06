package com.it.erpapp.utils;

public class RandomAlphaNumericStringGenerator {
	public static String getAlphaNumericString() {
		String code = "";
		char[] chars = new char[62];
		int i = 0;

		for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		i = 26;
		for (char c = '0'; c <= '9'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		i = 36;

		for (char c = 'a'; c <= 'z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}

		int numChars = 6 + (int) (Math.random() * 2.2D);
		for (i = 0; i < numChars; i++) {
			char c = chars[((int) (Math.random() * chars.length))];
			code = code + c;
		}

		return code;
	}

	public static String getAlphaString() {
		String code = "";
		char[] chars = new char[62];
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		i = 26;
		for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		i = 36;
		for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		int numChars = 6 + (int) (Math.random() * 2.2D);
		for (i = 0; i < numChars; i++) {
			char c = chars[((int) (Math.random() * chars.length))];
			code = code + c;
		}

		return code;
	}
	
	public static String getCaseSensitiveAlphaString() {
		String code = "";
		char[] chars = new char[62];
		int i = 0;
		for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		i = 26;
		for (char c = 'A'; c <= 'Z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		i = 36;
		for (char c = 'a'; c <= 'z'; c = (char) (c + '\001')) {
			chars[(i++)] = c;
		}
		int numChars = 6 + (int) (Math.random() * 2.2D);
		for (i = 0; i < numChars; i++) {
			char c = chars[((int) (Math.random() * chars.length))];
			code = code + c;
		}

		return code;
	}
	
/*	public static void main(String[] args){
		String code = getCaseSensitiveAlphaString();
		System.out.println("Code: "+code);
	}
*/
}