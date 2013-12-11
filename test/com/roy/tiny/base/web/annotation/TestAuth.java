package com.roy.tiny.base.web.annotation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.stereotype.Controller;

public class TestAuth {
	
	@Test
	public void testScanAuthMethod() {
		String classpath = "com";
		try {
			Enumeration<URL> resourceUrls = Thread.currentThread().getContextClassLoader().getResources(classpath);
			Set<Class> classes = new LinkedHashSet<Class>();
			while (resourceUrls.hasMoreElements()) {
				URL url = resourceUrls.nextElement();
				if("file".equals(url.getProtocol())) {
					findAllResourceByFilePath(url.getFile(),classes);
				}
			}
			for(Class clazz : classes) {
				if(clazz.isAnnotationPresent(Controller.class)) {
					for(Method method : clazz.getDeclaredMethods()) {
						if(method.isAnnotationPresent(Auth.class)) {
							System.out.println(clazz.getName()+"."+method.getName()+"()");
						}
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void findAllResourceByFilePath(String filePath,Set<Class> classes) throws MalformedURLException, ClassNotFoundException {
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
