package com.webcqs.common;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webcqs.svs.imp.DBServer;
import com.webcqs.svs.imp.NTiException;
import com.webcqs.svs.inf.CIp;
import com.webcqs.svs.inf.Cache;
import com.webcqs.svs.inf.Ti;
/**
 * 通用对象工厂
 * @author BUJUN
 *
 */
public class ComFactory {
	public static String handler_properties=null;
	public static boolean _assert = false;
	private static Log log=null;
	private static CIp cip;
	public static String lang = "zh";
	public static CIp getCip() {		
		return cip;
	}

	public static void setCip(CIp cip) {
		ComFactory.cip = cip;
	}
	/**
	 * 添加处理程序
	 * @param key
	 * @param className
	 * @throws NTiException
	 */
	public static void addHandler(String key,String className) throws NTiException{
		DBServer.putClassName(key, className);
	}
	/**
	 * 添加处理程序
	 * @param key
	 * @param hand
	 */
	public static void addHandler(String key,Class<? extends Ti> hand){
		DBServer.putClass(key, hand);
	}
	/**
	 * 通过prop配置处理程序
	 * @param prop
	 */
	public static void addHandler(Properties prop){
		if(prop==null)return;
		for(Object k : prop.keySet()){
			if(k==null)continue;
			try{
				String kn = k.toString();
				String v = prop.getProperty(kn);
				DBServer.putClassName(kn, v);
			}catch(Exception e){
				getLog().info("配置不正确！",e);
			}
		};
	}
	private static Log getLog(){
		if(log == null) log = LogFactory.getLog(ComFactory.class);
		return log;
	}
	private static Cache<String,?> cache;
	/**
	 * 获取缓存
	 * @return
	 */
	public static Cache<String,?> getCache(){
		return cache;
	}
	/**
	 * 设置缓存
	 * @param cache
	 */
	public static void setCache(Cache<String,?> cache){
		ComFactory.cache = cache;
	}
	/**
	 * 设置提示信息的prop文本
	 * @param prop
	 */
	public static void setMsg(Properties prop){
		Prop.setProp(prop);
	}
	/**
	 * 添加提示信息文本
	 * @param prop
	 */
	public static void addMsgTips(Properties prop){
		if(prop==null)return;
		for(Object k : prop.keySet()){
			if(k==null)continue;
			String kn = k.toString();
			Prop.getProp().put(kn, prop.getProperty(kn));
		}
	}
	/**
	 * 
	 * @param key
	 * @param msg
	 */
	public static void addMsgTip(String key,String msg){
		Prop.getProp().put(key, msg);
	}
	
	/**
	 * 将外部的jar包加载到当前classpath中。
	 * @param jar
	 */
	public static void addJarToClasspath(String jar){
		if(jar==null)return;
		try{
			URL urls = new URL("file:/"+jar); 
			URLClassLoader urlLoader = (URLClassLoader) com.webcqs.svs.inf.Ti.class.getClassLoader(); 
	        Class<URLClassLoader> sysclass = URLClassLoader.class;   
	        Method method = sysclass.getDeclaredMethod("addURL", new Class[]{URL.class});  
	        method.setAccessible(true);  
	        method.invoke(urlLoader, urls);  
		}catch(Throwable t){
			t.printStackTrace();
		}
	}
}
