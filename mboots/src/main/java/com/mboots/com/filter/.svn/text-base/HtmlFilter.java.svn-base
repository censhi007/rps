package com.mboots.com.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.mboots.com.model.HTMLEscape;
public class HtmlFilter implements Filter{

	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest) req;	     
	     //一些过滤
	     //---
	     //这里封装
	     request = new HTMLEscape(request);	     
	     //继续过滤链
	     chain.doFilter(request, resp);
	}

	
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
