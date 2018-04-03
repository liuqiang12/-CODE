package com.JH.dgather.comm;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

public class Util {
	private static Logger logger = Logger.getLogger(Util.class);

	public static String getChineseStr(String ch) {
		StringBuffer sb = new StringBuffer();

		String[] str = ch.split("\\:");

		int intLength = str.length;

		for (int i = 0; i < intLength; i++) {
			sb.append(str[i]);
		}
		if (str.length <= 1) {

			return ch;
		}
		//return toStringHex(specialCharFilter(sb.toString()));
		return toStringHex(sb.toString());
	}

	private static String specialCharFilter(String ch) {// ���˱����еġ�:��

		String[] str = ch.split("\\:");
		int intLength = str.length;

		StringBuffer temp = new StringBuffer();
		for (int j = 0; j < str.length; j++) {
			temp.append(str[j]);
		}

		return temp.toString();
	}

	private static String toStringHex(String bytes) {

		if (bytes.length() % 2 != 0) {

			return bytes;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 
		for (int i = 0; i < bytes.length(); i += 2) {
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		}
		try {
			return new String(baos.toByteArray(), "gbk").trim();
		} catch (Exception e) {
			logger.error("SNMP", e);
		}
		return null;
	}
	private static String hexString = "0123456789abcdef";
	
	
	
	
}
