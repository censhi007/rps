package com.mboots.com.user.model;

import com.mboots.com.inf.Bsi;

public class User implements Bsi{	
	private static final long serialVersionUID = 5709763469576221184L;
	private String id;
	private String logname;
	private String name;
	private String pwd;
	/**
	 * 初始化状态
	 */
	private Integer init;
	
	public String getId() {
		return id;
	}

	public String getName() {
		if(name==null)return logname;
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getInit() {
		return init;
	}

	public void setInit(Integer init) {
		this.init = init;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}
	/**
	 * 是否是初始状态
	 * @return
	 */
	public boolean initstate(){
		return init==null||init==1;
	}
	
}
