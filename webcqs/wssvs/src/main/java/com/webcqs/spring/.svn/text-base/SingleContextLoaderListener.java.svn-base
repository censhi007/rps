package com.webcqs.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.webcqs.common.ComFactory;
import com.webcqs.svs.inf.CIp;
/**
 * 用于单例context。
 * @author Administrator
 *
 */
public class SingleContextLoaderListener extends ContextLoaderListener{
	 public void contextInitialized(ServletContextEvent event) {
	        super.contextInitialized(event);
	        ServletContext context = event.getServletContext();
	        ApplicationContext ctx = WebApplicationContextUtils
	                .getRequiredWebApplicationContext(context);
	        Config.setCtx(ctx);
	        Config.init();
	        Object o = ctx.getBean("dbi");
	        if(o!=null){    	
	        	ComFactory.setCip((CIp)o);
	        }
	 }
}
