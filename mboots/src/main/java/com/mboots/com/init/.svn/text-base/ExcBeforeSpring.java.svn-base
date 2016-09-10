package com.mboots.com.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.mboots.com.util.WebConfig;


/**
 * 在加载spring之前，执行本方法
 * @author BUJUN
 *
 */
public class ExcBeforeSpring implements javax.servlet.ServletContextListener{

	public void contextDestroyed(ServletContextEvent arg0) {		
		
	}

	public void contextInitialized(ServletContextEvent xe) {
		ServletContext ctx = xe.getServletContext();
		Enumeration<String> en = ctx.getInitParameterNames();
		
		Properties prop = new Properties();
		InputStream is = null;
		OutputStream os = null;
		try{
			ClassLoader cld = ExcBeforeSpring.class.getClassLoader();
			URL u = cld.getResource("applicationContext.xml");
			String xp;//classpath
			String cp = u.getPath();
			try {
				cp=java.net.URLDecoder.decode(cp, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			cp =cp.substring(0, cp.lastIndexOf("/"));
			xp = cp;
			cp += "/jdbc.properties";
			is = new FileInputStream(cp);
			prop.load(is);
			is.close();
			is = null;
			if(en!=null)
			while(en.hasMoreElements()){
				String key = en.nextElement();
				String v = ctx.getInitParameter(key);
				prop.put(key, v);
			}
			ctx=null;
			checkUrl(prop);
			os = new FileOutputStream(cp);
			prop.store(os, "init params loaded");
			os.close();
			os=null;
			//重新装载。
			prop = new Properties();
			is = new FileInputStream(cp);
			prop.load(is);
			is.close();
			is = null;

			String lang = prop.getProperty("language");
			if(lang==null||"".equals(lang))lang="zh";
			WebConfig.default_local = lang;
			is = cld.getResourceAsStream("lang_"+lang+".properties");			
			if(is==null)is=cld.getResourceAsStream("lang.properties");
			if(is!=null){
				Properties pp = new Properties();
				pp.load(is);
				is.close();
				is = null;
				WebConfig.addLocal(pp);
			}
			
			String lf = prop.getProperty("log_file");
			if(lf!=null && lf.length()>0){				
				changeLogByUser(xp,lf,"A2");
			}
			

			String modules_dir =prop.getProperty("modules.dir");
			
			//加载子模块
			//将子模块jar所在的路劲加入classpath中
			File mdir = modules_dir!=null&&!"".equals(modules_dir.trim()) ? new File(modules_dir) : new File(xp,"../lib/modules");
			if(mdir.exists()){
				String md = mdir.getAbsolutePath();
				try {
					md=java.net.URLDecoder.decode(md, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				String[] ls = mdir.list();
				for(String ln : ls){
					if(!ln.endsWith(".jar"))continue;
					Extend.handel(md+"/"+ln,false);
				}
				Extend.complite();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
	/**
	 * 更改日志路径
	 */
	private void changeLogByUser(String cp,String new_log_file_name,String apperder_name) {
		File xml = new File(cp,"log4j.xml");
		new_log_file_name = new_log_file_name.replace("\\", "/");
		new_log_file_name = new_log_file_name.replace("//", "/");
		File new_log_file = new File(new_log_file_name.substring(0, new_log_file_name.lastIndexOf("/")));
		if(!new_log_file.exists()){
			new_log_file.mkdirs();
		}
		FileInputStream in=null;
		FileOutputStream os = null;
		try {
			Pattern p = Pattern.compile("<param\\s+name=\\\"File\\\"\\s+value=\\\"([^\\\"]+)\\\"");
			in= new FileInputStream(xml);
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String line = null;
			StringBuffer sb = new StringBuffer();
			Matcher m;
			boolean nev = false;
			while((line = read.readLine())!=null){
				if(!nev){					
					m = p.matcher(line);
					if(m.find()){
						line = line.replace(m.group(1), new_log_file_name);
						nev=true;
					}
				}
				sb.append(line).append("\r\n");
			}
			in.close();
			in=null;
			os = new FileOutputStream(xml);
			os.write(sb.toString().getBytes("utf-8"));
			os.close();
			os=null;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void checkUrl(Properties prop){
		String dbtype = prop.getProperty("dbtype");
		String x = prop.getProperty("url");
		if(x==null)return;
		String pref="jdbc:mysql://";
		Pattern p = Pattern.compile("jdbc\\:\\w+\\://");
		Matcher m = p.matcher(x);
		if(m.find()){
			x = x.substring(m.end());
		}
		if("mysql".equals(dbtype)||dbtype==null){
			WebConfig.currentDB=0;
			if(x.indexOf("?")==-1){
				x= x+"?characterEncoding=UTF-8";
			}
		}else if("mssql".equalsIgnoreCase(dbtype)){
			WebConfig.currentDB=1;
			pref="jdbc:sqlserver://";
		}else if("postgres".equalsIgnoreCase(dbtype)){
			WebConfig.currentDB=2;
			pref="jdbc:postgresql://";
		}
		if(!x.startsWith(pref)){
			x= pref+x;
		}
		prop.put("url", x);
	}
	

}
