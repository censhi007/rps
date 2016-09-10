package com.mboots.com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * 控制器的基类
 * @author BUJUN
 *
 */
public abstract class BAct implements java.io.Serializable{	
	private static final long serialVersionUID = -6041084556463061044L;
	private static final String [] hip = new String[]{"x-forwarded-for",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP"};
	/**
	 * 获取访问者的ip
	 * @return
	 */
	protected String getIp(HttpServletRequest http){
		for(String s : hip){
			String v = http.getHeader(s);
			if(NULL(v))continue;
			String [] ss = v.split(",");
			if(ss.length == 1)return ss[0];
			for(String t : ss){
				if(!NULL(t))return t;
			}
		}
		return http.getRemoteAddr();
	}
	/**
	 * 检查ip是否是空的
	 * @param ip
	 * @return
	 */
	private boolean NULL(String ip){
		return ip ==null || ip.length()==0 || "unknown".equals(ip);
	}
	/**
	 * 向session中放置参数
	 * @param session
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T attr(HttpSession session,String name){
		return (T)session.getAttribute(name);
	}
	/**
	 * 获取session中的参数
	 * @param session
	 * @param name
	 * @param value
	 * @return
	 */
	protected BAct attr(HttpSession session,String name,Object value){
		session.setAttribute(name, value);
		return this;
	}
	/**
	 * 获取form表单或者url中的参数
	 * @param request
	 * @param name
	 * @return
	 */
	protected String param(HttpServletRequest request,String name){
		return request.getParameter(name);
	}
	/**
	 * 获取request中的参数
	 * @param request
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T>T attr(HttpServletRequest request,String name){
		return (T)request.getAttribute(name);
	}
	/**
	 * 想request中设置参数
	 * @param request
	 * @param name
	 * @param value
	 * @return
	 */
	protected BAct attr(HttpServletRequest request,String name,Object value){
		request.setAttribute(name, value);
		return this;
	}
	/**
	 * 从session中删除对象
	 * @param session
	 * @param name
	 * @return
	 */
	protected BAct removeAttr(HttpSession session,String name){
		session.removeAttribute(name);
		return this;
	}
	/**
	 * 从HttpServletRequest中删除对象
	 * @param session
	 * @param name
	 * @return
	 */
	protected BAct removeAttr(HttpServletRequest HttpServletRequest,String name){
		HttpServletRequest.removeAttribute(name);
		return this;
	}

	/**
	 * 记录日志
	 * @param msg
	 * @param session
	 *
	protected void info(String msg,HttpSession session){
		lgs.createLg(msg, session);
	}
	
	@Autowired
	protected Lgsi lgs;
	*/
}
