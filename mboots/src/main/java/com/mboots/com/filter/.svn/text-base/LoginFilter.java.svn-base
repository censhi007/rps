package com.mboots.com.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class LoginFilter implements Filter{
	private Pattern ep;
	
	public void destroy() {
	}

	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		arg1.setCharacterEncoding("utf-8");
		String u =request.getRequestURL().toString();
		Object log = request.getSession().getAttribute("logged");
		
		if(Boolean.TRUE.equals(log)||check(u)){
			if(u.endsWith("/online")){
				return;//直接结束。本名词仅仅用于检测是否在线，并在不在线时提示offline
			}
			if(u.endsWith("/offline")){//为了速度，直接在登陆检查中处理。并且本项目中，将这两个词当作关键字。不允许改写
				request.getSession().invalidate();
				OutputStream os = arg1.getOutputStream();
				os.write("{\"offline\":true}".getBytes("utf-8"));
				os.flush();
				os.close();
				return;
			}
			arg2.doFilter(request, arg1);
			return;
		}
		
		OutputStream os = arg1.getOutputStream();
		os.write("{\"offline\":true}".getBytes("utf-8"));
		os.flush();
		os.close();
	}

	
	public void init(FilterConfig arg0) throws ServletException {
		String ostr = arg0.getInitParameter("exclude");
		if(ostr!=null && !"".equals(ostr)){
			ostr=ostr.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*").replaceAll("\\|", "\\$\\|");
			ostr+="$";
			ep = Pattern.compile(ostr);
		}
	}

	/**
	 * 检查当前连接是否不需检测登陆状态
	 * @param path
	 * @return
	 */
	boolean check(String path){
		if(ep==null)return false;
		return ep.matcher(path).find();
	}
	
}
