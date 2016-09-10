package com.mboots.com.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mboots.com.util.WebConfig;

/**
 * 用于想web项目提供上下文context
 * @author BUJUN
 *
 */
public class SingleContextListener extends ContextLoaderListener{
	com.fasterxml.jackson.databind.ObjectMapper m;
	 public void contextInitialized(ServletContextEvent event) {
	        super.contextInitialized(event);
	        ServletContext context = event.getServletContext();
	        ApplicationContext ctx = WebApplicationContextUtils
	                .getRequiredWebApplicationContext(context);
	        WebConfig.setContext(ctx);
	    }
}
