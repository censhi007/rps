package com.mboots.com.util;

import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * web项目中的一些配置数据<br/>
 * {@link com.mboots.com.util.WebConfig#getBean(String)} 本函数返回一个在spring中配置的bean<br/>
 * {@link com.mboots.com.util.WebConfig#get(String)} 本函数返回一个当前服务器配置的参数<br/>
 * @author BUJUN
 *
 */
public class WebConfig {
	private static ApplicationContext context;
	private static Properties pro;
	public static final String LOCAL_CH="zh";//china
	public static final String LOCSL_EN="en";//english
	public static String default_local = "zh";
	public static int currentDB=0;
	
	public static Properties ch;
	public static Properties en;
	
	private static ApplicationContext getContext() {
		if(context == null){
			//
			context=new ClassPathXmlApplicationContext("applicationContext.xml");
		}		
		return context;
	}

	public static void setContext(ApplicationContext context) {
		WebConfig.context = context;
	}
	/**
	 * 返回一个bean。
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		return (T)getContext().getBean(name);
	}
	
	/**
	 * 可以返回任何类型
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <R> R get(String key){
		if(pro==null)return null;
		return (R)pro.get(key);
	}
	/**
	 * 取回配置的字符串
	 * @param code
	 * @return
	 */
	public static String local(String code){
		return local(default_local,code);
	}
	/**
	 * 用于国际化，根据charset显示对应国际的字符串
	 * @param charset
	 * @param code
	 * @return
	 */
	public static String local(String charset,String code){
		Properties pe = getPro(charset);
		if(pe==null)return null;
		return pe.getProperty(code);
	}
	/**
	 * 向本地语言中加入国家化标记
	 * @param pro
	 */
	public static void addLocal(Properties pro){
		Properties p = getDefaulPro();
		if(p==null)return;
		for(Entry<Object,Object> et : pro.entrySet()){
			p.put(et.getKey(),et.getValue());
		}
		p=null;
		pro=null;
	}
	/**
	 * 获取当前的默认语言集合
	 * @return
	 */
	private static Properties getDefaulPro(){
		return getPro(default_local);
	}
	/**
	 * 获取指定的语言集合
	 * @param default_local
	 * @return
	 */
	private static  Properties getPro(String default_local){
		if(LOCAL_CH.equals(default_local)){
			if(ch == null)ch=new Properties();
			return ch;
		}else if(LOCSL_EN.equals(default_local)){
			if(en == null)en=new Properties();
			return en;
		}
		return null;
	}
	
	private static final Pattern p = Pattern.compile("@\\{([^\\}\\@]+)\\}");
	/**
	 * 传入字符串，返回国际化
	 * @param svalue
	 * @return
	 */
	public static String localString(String svalue){
		if(svalue!=null && !"".equals(svalue)){			
			Matcher m = p.matcher(svalue);
			while(m.find()){
				String t = m.group();
				String n = m.group(1);
				String v = local(n);
				v=v==null ? n:v;
				svalue = svalue.replace(t, v);
			}
		}
		return svalue;
	}
}
