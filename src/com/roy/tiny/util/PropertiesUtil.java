package com.roy.tiny.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
	private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

	public static String getProperty(String key, String resource) {
		ResourceBundle boudle = null;

		try {
			boudle = ResourceBundle.getBundle(resource);
		} catch (MissingResourceException e) {
			log.warn("can not find bundled resource: " + resource);
			return null;
		}

		try {
			return boudle.getString(key);
		} catch (MissingResourceException e) {
			log.warn("can not find bundled resource key: " + key);
			return null;
		}
	}

	public static boolean getBooleanProperty(String key, String resource) {
		String value = getProperty(key, resource);
		return value == null ? false : Boolean.valueOf(value);
	}

	public static int getIntProperty(String key, String resource) {
		String value = getProperty(key, resource);
		return value == null ? 0 : Integer.valueOf(value);
	}

	public static long getLongProperty(String key, String resource) {
		String value = getProperty(key, resource);
		return value == null ? 0 : Long.valueOf(value);
	}

	public static double getDoubleProperty(String key, String resource) {
		String value = getProperty(key, resource);
		return value == null ? 0d : Double.valueOf(value);
	}

	public static float getFloatProperty(String key, String resource) {
		String value = getProperty(key, resource);
		return value == null ? 0f : Float.valueOf(value);
	}
}
