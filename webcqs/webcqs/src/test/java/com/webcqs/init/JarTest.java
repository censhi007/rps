package com.webcqs.init;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import com.webcqs.common.ComFactory;


public class JarTest {
	public static void main(String[] a){
		String jn = "D:\\my work\\webcqs\\src\\main\\webapp\\WEB-INF\\classes\\..\\lib\\modules\\com.ws.ksoft.jar";//"d:/test/com.ws.ksoft.jar";
		try {
			JarFile jar = new JarFile(jn);
			Manifest mt = jar.getManifest();
			Attributes ppt = mt.getMainAttributes();
			System.out.println(ppt);
			if(ppt!=null)System.out.println(ppt.getValue("Name"));
			
			//System.out.println(System.getProperty("java.class.path"));
			//System.out.println(System.getProperty("user.dir"));
			System.out.println(Class.forName("com.webcqs.svs.inf.Ti"));
			ComFactory.addJarToClasspath(jn);
            // 输入类名  
            String className = "com.ws.ksoft.impl.GZbx";  
            Class<?> tidyClass = Class.forName(className);  
            Object obj = tidyClass.newInstance();  
			System.out.println(obj);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
