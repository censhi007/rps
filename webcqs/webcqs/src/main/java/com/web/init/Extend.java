package com.web.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webcqs.common.ComFactory;

/**
 * 自动扩展
 * @author BUJUN
 *
 */
public class Extend {
	private final static Log log = LogFactory.getLog(Extend.class);
	private static List<String> springs=new ArrayList<String>();
	private static List<String> hibernates=new ArrayList<String>();
	/**
	 * 处理jar包
	 * @param jar
	 */
	public static void handel(String jarname,boolean complite){
		JarFile jar = null;
		try {
			log.info("loading...[path="+jarname+"]");
			ComFactory.addJarToClasspath(jarname);
			jar = new JarFile (jarname);
			Manifest mf = jar.getManifest();
			Attributes attr = mf.getMainAttributes();
			String pack = attr.getValue("PackageName");
			
			if(pack==null||"".equals(pack))return;//该包不是符合配置要求的包
			attr=null;
			String cp = pack.replaceAll("\\.", "/");
			//加载handler
			ZipEntry hand = jar.getEntry(cp+"/handler.properties");
			if(hand!=null){
				Properties prop = new Properties();
				prop.load(jar.getInputStream(hand));
				ComFactory.addHandler(prop);
				prop=null;
			}
			//加载语言
			ZipEntry lan = jar.getEntry(cp+"/lang_"+ComFactory.lang+".properties");
			if(lan==null)lan = jar.getEntry(cp+"/lang.properties");
			if(lan!=null){
				Properties prop = new Properties();
				prop.load(jar.getInputStream(lan));
				ComFactory.addMsgTips(prop);
				prop=null;
			}
			//执行模块内部的初始化。默认执行MainInit。可使用initclass配置
			String jdbc = cp+"/jdbc.properties";
			ZipEntry zdbc = jar.getEntry(jdbc);
			String main = pack.replaceAll("/",".")+".MainInit";
			String springroot = cp+"/applicationContext.xml";
			String hibernate = "classpath*:/"+cp+"/hibernate/*.hbm.xml";
			if(zdbc!=null){
				Properties pdbc = new Properties();
				pdbc.load(jar.getInputStream(zdbc));
				String tm = pdbc.getProperty("initclass");
				if(tm!=null)main=tm;
				String sr = pdbc.getProperty("spring.root");
				if(sr!=null&&!"".equals(sr))springroot=sr;
				sr = pdbc.getProperty("hibernate.mapping");
				if(sr!=null&&!"".equals(sr))hibernate=sr;
				pdbc=null;
			}
			ZipEntry sr = jar.getEntry(springroot);
			jar.close();
			jar=null;
			if(sr!=null){
				sr = null;
				//将该路径加入到项目中的spring配置文件中
				springroot="classpath:"+springroot;
				springs.add(springroot);
			}
			String[] hms = hibernate.split(",");
			for(String hbm:hms){
				hibernates.add(hbm);
			}
			if(complite){
				complite();
			}
			try{
				Class<?> cls = Class.forName(main);
				Method m = cls.getMethod("init");
				if(m!=null)m.invoke(null);
			}catch(Throwable ee){
				log.warn("haven`t init-class");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}finally{
			if(jar!=null){
				try {
					jar.close();
					jar=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private static void addxmls(){
		ClassLoader cld = ExcBeforSpring.class.getClassLoader();
		String xml=null;
		URL u = cld.getResource("applicationContext.xml");
		String cp = u.getPath();
		try {
			cp=java.net.URLDecoder.decode(cp, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cp =cp.substring(0, cp.lastIndexOf("/"));
		xml = cp+"/spring/extend.xml";
		File exf = new File(xml);
		OutputStream os =null;
		try{
			if(!exf.exists()){
				exf.createNewFile();
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\r\n<beans>\r\n");
			for(String path:springs)
			sb.append("<import resource=\"").append(path).append("\" />\r\n");
			sb.append("<!-- please do not change this xml,it will be rewritten automatically with the data included by the module jars-->\r\n");
			sb.append("</beans>\r\n");
			
			os = new FileOutputStream(xml);
			os.write(sb.toString().getBytes("utf-8"));
			os.flush();
			os.close();
			os=null;
			
		}catch(Throwable t){
			t.printStackTrace();
		}finally{
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static String hibernatemapping=null;
	private static void addhibernatemappings(){
		ClassLoader cld = ExcBeforSpring.class.getClassLoader();
		String xml=null;
		URL u = cld.getResource("applicationContext.xml");
		String cp = u.getPath();
		try {
			cp=java.net.URLDecoder.decode(cp, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cp =cp.substring(0, cp.lastIndexOf("/"));
		xml = cp+"/spring/hibernate.xml";
		OutputStream os =null;
		InputStream is = null;
		
		try{
			if(hibernatemapping==null){
				is = new FileInputStream(xml);				
				BufferedReader read = new BufferedReader(new InputStreamReader(is));
				String line = null;
				StringBuffer sb = new StringBuffer();
				boolean start = false;
				boolean end = false;
				while((line = read.readLine())!=null){
					if(!start){
						//在出现标记值之前，所有的字符串直接添加到stringBuffer中
						sb.append(line).append("\r\n");
						if(line.indexOf("hibernate generate start")!=-1){
							start=true;							
							continue;
						}
						continue;
					}
					if(!end){
						//在出现结束标记之前的内容丢弃，不添加到stringBuffer中
						if(line.indexOf("hibernate generate end")!=-1){
							end=true;
							sb.append(line).append("\r\n");
							continue;
						}
						continue;
					}
					sb.append(line).append("\r\n");
				}
				is.close();
				is=null;
				hibernatemapping = sb.toString();				
			}
			StringBuffer sb = new StringBuffer();
			for(String hbm:hibernates){
				sb.append("<value>").append(hbm).append("</value>\r\n\t\t\t\t");
			}
			StringBuffer toWrite = new StringBuffer(hibernatemapping);
			Pattern p = Pattern.compile("<!\\-\\-\\s*hibernate generate end\\s*\\-\\->");
			Matcher m = p.matcher(toWrite);
			if(m.find()){
				int i = m.start();
				toWrite=toWrite.insert(i,sb.toString());
			}
			sb=null;
			os = new FileOutputStream(xml);
			os.write(toWrite.toString().getBytes("utf-8"));
			os.flush();
			os.close();
			os=null;
			toWrite=null;
		}catch(Throwable tr){
			tr.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void complite(){
		addxmls();
		addhibernatemappings();
		clear();
	}
	public static void clear(){
		hibernatemapping=null;
		springs.clear();
		hibernates.clear();
	}
}
