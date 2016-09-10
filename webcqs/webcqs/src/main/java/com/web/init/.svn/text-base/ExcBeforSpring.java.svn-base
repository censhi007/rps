package com.web.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;

import com.web.svs.imp.SQLFactory;
import com.webcqs.common.ComFactory;
/**
 * 在spring加载之前执行
 * @author BUJUN
 *
 */
public class ExcBeforSpring implements javax.servlet.ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(ServletContextEvent xe) {
		ServletContext ctx = xe.getServletContext();
		Enumeration<String> en = ctx.getInitParameterNames();
		
		Properties prop = new Properties();
		InputStream is = null;
		OutputStream os = null;
		try{
			ClassLoader cld = ExcBeforSpring.class.getClassLoader();
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
			while(en.hasMoreElements()){
				String key = en.nextElement();
				String v = ctx.getInitParameter(key);
				prop.put(key, v);
			}
			ctx=null;
			checkUrl(prop);
			checkHibMapping(prop);//处理映射文件
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
			String hdp = prop.getProperty("handler_path");
			if(hdp!=null && !"".equals(hdp)){
				ComFactory.handler_properties = hdp;
			}
			String lang = prop.getProperty("language");
			if(lang==null||"".equals(lang))lang="zh";
			ComFactory.lang = lang;
			is = cld.getResourceAsStream("lang_"+lang+".properties");			
			if(is==null)is=cld.getResourceAsStream("lang.properties");
			if(is!=null){
				Properties pp = new Properties();
				pp.load(is);
				is.close();
				is = null;
				ComFactory.addMsgTips(pp);
			}
			String lf = prop.getProperty("log_file");
			if(lf!=null && lf.length()>0){				
				changeLogByUser(lf,"A2");
			}
			
			String tu = prop.getProperty("limit.turnon");
			SQLFactory.limit_turnon = "1".equals(tu)||"true".equalsIgnoreCase(tu);
			String dl = prop.getProperty("limit.dwdms");
			if(dl!=null && !"".equals(dl)){
				SQLFactory.limit_dwdms = dl.split(",");
			}
			dl = prop.getProperty("def_table_name");
			if(dl!=null && !"".equals(dl)){
				SQLFactory.def_table_name = dl;
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
	private void changeLogByUser(String new_log_file_name,String apperder_name) {
		new_log_file_name = new_log_file_name.replace("\\", "/");
		new_log_file_name = new_log_file_name.replace("//", "/");
		File new_log_file = new File(new_log_file_name.substring(0, new_log_file_name.lastIndexOf("/")));
		if(!new_log_file.exists()){
			new_log_file.mkdirs();
		}
		@SuppressWarnings("static-access")
		DailyRollingFileAppender  drfa = (DailyRollingFileAppender) Logger.getLogger(ExcBeforSpring.class)
		.getRootLogger().getAppender(apperder_name);
		String log_file_name = drfa.getFile();
		try {
			drfa.setFile(new_log_file_name,true,drfa.getBufferedIO(),drfa.getBufferSize());
			drfa.activateOptions();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (log_file_name != null){
			File log_file = new File(log_file_name);
			if(log_file.exists()){
				log_file.delete();
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
			SQLFactory.dbtype=0;
			if(x.indexOf("?")==-1){
				x= x+"?characterEncoding=UTF-8";
			}
		}else if("mssql".equalsIgnoreCase(dbtype)){
			SQLFactory.dbtype=1;
			pref="jdbc:sqlserver://";
		}else if("postgres".equalsIgnoreCase(dbtype)){
			SQLFactory.dbtype=2;
			pref="jdbc:postgresql://";
		}
		if(!x.startsWith(pref)){
			x= pref+x;
		}
		prop.put("url", x);
	}
	
	private void checkHibMapping(Properties prop){
		String dbtype = prop.getProperty("dbtype");
		if("mysql".equals(dbtype)||dbtype==null){
			prop.put("hibernate_mapping", "classpath*:/hibernate/mysql/*.hbm.xml");
			return;
		}
		if("mssql".equalsIgnoreCase(dbtype)){
			prop.put("hibernate_mapping", "classpath*:/hibernate/mssql/*.hbm.xml");
			return;
		}
		if("postgres".equalsIgnoreCase(dbtype)){
			prop.put("hibernate_mapping", "classpath*:/hibernate/postgres/*.hbm.xml");
			return;
		}
	}
}
