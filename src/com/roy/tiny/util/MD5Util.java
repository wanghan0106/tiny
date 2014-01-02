package com.roy.tiny.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
	private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

	public static String md5(String str) {
		if (str == null) {
			return null;
		}
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte[] result = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : result) {
				sb.append(String.format("%02X", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5Util.md5(): NoSuchAlgorithmException", e);
			return str;
		} catch (UnsupportedEncodingException e) {
			log.error("MD5Util.md5(): UnsupportedEncodingException", e);
			return str;
		}
	}
}
