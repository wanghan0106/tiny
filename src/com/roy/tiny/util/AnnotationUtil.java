package com.roy.tiny.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationUtil {
	private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);
	
	private final static String CLASSPATH = "com";
	
	@SuppressWarnings("rawtypes")
	public static List<Class> getClassesByAnnotation(Class annatationClass) {
		List<Class> classList = new ArrayList<Class>();
		try {
			Enumeration<URL> resourceUrls = Thread.currentThread().getContextClassLoader().getResources(CLASSPATH);
			Set<Class> classes = new LinkedHashSet<Class>();
			while (resourceUrls.hasMoreElements()) {
				URL url = resourceUrls.nextElement();
				if("file".equals(url.getProtocol())) {
					findAllResourceByFilePath(url.getFile(),classes);
				}
			}
			for(Class clazz : classes) {
				if(clazz.isAnnotationPresent(annatationClass)) {
					classList.add(clazz);
				}
			}
		} catch (MalformedURLException e) {
			log.error("AnnotationUtil.getClassesByAnnotation():"+e.getMessage());
		} catch (IOException e) {
			log.error("AnnotationUtil.getClassesByAnnotation():"+e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error("AnnotationUtil.getClassesByAnnotation():"+e.getMessage());
		}
		return classList;
	}
	
	private static void findAllResourceByFilePath(String filePath,Set<Class> classes) throws MalformedURLException, ClassNotFoundException {
		File file = new File(filePath);
		if(!file.exists()) {
			return;
		}
		if(file.isDirectory()) {
			File[] dirfiles = file.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return (file.isDirectory() || file.getName().endsWith(".class"));
				}
			});
			for(File f : dirfiles) {
				findAllResourceByFilePath(f.getPath(),classes);
			}
		} else {
			filePath = filePath.replace("\\", "/");
			if(filePath.indexOf("/WEB-INF/classes/") > 0) {
				String packagePath = filePath.substring(filePath.indexOf("/WEB-INF/classes/")+17, filePath.length());
				String className = packagePath.substring(0, packagePath.indexOf(".")).replace("/", ".");
				classes.add(Thread.currentThread().getContextClassLoader().loadClass(className));
			}
		}
		
	}
}
