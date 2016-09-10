package com.mboots.com.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mboots.com.util.BAct;
import com.mboots.com.util.Msg;

/**
 * 不需要登陆，通用方法<br/>
 * 这里没有使用control注解<br/>
 * 因为没有用到日志,且本控制层直接在xml中进行了配置
 * @author BUJUN
 *
 */

@Scope("session")
@RequestMapping(value="/nlogin")
public class NLogin  extends BAct{
	private static final long serialVersionUID = 7608563707620448899L;

	@RequestMapping(value = "/client", method = RequestMethod.POST)
	@ResponseBody
	public Msg clientMsg(String browser,String os,String tel,HttpServletRequest request){
		Msg m = new Msg();
		HttpSession session = request.getSession();
		attr(session,"browser",browser);
		attr(session,"os",os);
		attr(session,"tel",tel);
		m.setState(1);
		return m;
	}
}
