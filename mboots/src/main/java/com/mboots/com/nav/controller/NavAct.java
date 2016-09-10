package com.mboots.com.nav.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Ehcache;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mboots.com.nav.model.Nav;
import com.mboots.com.util.BAct;
import com.mboots.com.util.Msg;
/**
 * 用于处理导航
 * @author BUJUN
 *
 */
@Scope("session")
@Controller
@ResponseBody
@RequestMapping(value="/navs")
public class NavAct extends BAct{	
	private static final long serialVersionUID = 6889063363586119538L;

	private Ehcache cache;

	public Msg list(){
		
		return null;
	}
	
	
	public Ehcache getCache() {
		return cache;
	}
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}
	/**
	 * 列出用户权限内的菜单<br/>
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public List<Nav> nav(HttpServletRequest request,HttpServletResponse response){
		List<Nav> nL = new ArrayList<Nav>();
		
		return nL;
	}
	@RequestMapping(value="/{id}")
	public List<Nav> nav(@PathVariable String id,HttpServletRequest request,HttpServletResponse response){
		List<Nav> nL = new ArrayList<Nav>();
		
		return nL;
	}
}
