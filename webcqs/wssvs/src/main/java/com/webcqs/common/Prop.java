package com.webcqs.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Prop {
	private static Properties prop;
	
	public static void setProp(Properties prop){
		Prop.prop = prop;
	}
	
	public static String getString(String key){
		return getProp().getProperty(key);
	}
	public static Object get(String key){
		return getProp().get(key);
	}
	
	public static Properties getProp(){
		if(prop==null){
			synchronized(Prop.class){
				if(prop==null){
					Properties prop = new Properties();
					InputStream is = null;
					try{
						is = Prop.class.getClassLoader().getResourceAsStream("com/webcqs/res/lan.properties");
						prop.load(is);
						is.close();
						Prop.prop = prop;
						is = null;
					}catch(Exception e){
						e.printStackTrace();
					}finally{
						if(is !=null){
							try {
								is.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		return prop;
	}
}
