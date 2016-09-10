package com.mboots.com.filter;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LoginFilterTest {
	private LoginFilter lf = null;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MockFilterChain chain;
	private MockFilterConfig conf;
	@Before
	public void init(){
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		chain = new MockFilterChain();
		conf = new MockFilterConfig();
		
		conf.addInitParameter("exclude", "*.html|*.js|*.css|*.png|*.jpg|*.gif|*.md|*.xml|/static/*");
		
		request.setRequestURI("/mboots/userinfo");
		request.addParameter("name", "anyone");
		request.setMethod("get");
	}
	@Test
	public void test(){
		lf = new LoginFilter();
		try {
			lf.init(conf);
			lf.doFilter(request, response, chain);			
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@After
	public void destroy(){
		lf = null;
	}
}
