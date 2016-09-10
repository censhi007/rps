package com.mboots.com.log.model;

import java.util.Date;

import com.mboots.com.inf.Bsi;
/**
 * 记录日志
 * @author BUJUN
 *
 */
public class Lg implements Bsi{
	private static final long serialVersionUID = 2040451810869965474L;
	private String id;
	private String dwdm;
	private String user;
	private String msg;
	private Date vdate;
	private String ip;
	private String browser;
	private String os;
	private String tel;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String userid) {
		this.user = userid;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Date getVdate() {
		return vdate;
	}
	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	
}
