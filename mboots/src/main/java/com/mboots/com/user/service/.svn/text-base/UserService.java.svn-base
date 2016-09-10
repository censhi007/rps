package com.mboots.com.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mboots.com.inf.Doi;
import com.mboots.com.model.QueryObject;
import com.mboots.com.user.inf.Usersvi;
import com.mboots.com.user.model.User;

/**
 * 用户操作服务<br/>
 * 所有的*.service.*Service.*都已经加入了spring事务管理，因此不需要手动操作事务
 * @author BUJUN
 *
 */
public class UserService implements Usersvi{
	
	 public User findUser(String name,String pwd){
		 QueryObject qo = (QueryObject)dao.createQuery("select u from User u where u.logname=:name and u.pwd=:pwd ");
		 qo.setParameter("name", name).setParameter("pwd",pwd).setMaxResult(1);		 
		 return qo.uniqueResult();
	 }
	/**
	 * 本项目不支持新建用户
	 */
	public User updateUser(User u){	
		return dao.update(u);
	}
	
	
	@Autowired
	private Doi dao;
	
}
