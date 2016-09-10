package com.webcqs.spring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

 
public class Config {
	public static ApplicationContext ctx = null;
	/**
	 * 初始化.在spring加载时执行
	 */
	public static void init(){
		if(ctx==null){			
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		
	}

	public static void setCtx(ApplicationContext ctx){
		Config.ctx=ctx;
	}
	public static Object getBean(String id) {
		if(ctx==null)init();
		return ctx.getBean(id);
	}
	

	
}
