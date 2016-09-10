package com.webcqs.svs.model;

import java.util.Date;

import com.webcqs.svs.inf.IPMO;
/**
 * web服务访问记录
 * @author BUJUN
 *
 */
public class Webservicelog implements java.io.Serializable{	
	private static final long serialVersionUID = 4192111326671401575L;
	private String json;
	private String dw;
	private String id;
	private String ip;
	private short sta;
	private String stype;
	private Date qs;
	private Date zz;
	private Date tm;
	private int cnt = 0;
	
	public Webservicelog(){
		tm = new Date();
	}
	
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public short getSta() {
		return sta;
	}
	public void setSta(short sta) {
		this.sta = sta;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public Date getQs() {
		return qs;
	}
	public void setQs(Date qs) {
		this.qs = qs;
	}
	public Date getZz() {
		return zz;
	}
	public void setZz(Date zz) {
		this.zz = zz;
	}
	public Date getTm() {
		return tm;
	}
	public void setTm(Date tm) {
		this.tm = tm;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	private String desc;
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 从结果对象中转化访问记录
	 * @param mpo
	 * @return
	 */
	public static Webservicelog buildLog(IPMO mpo){		
		Webservicelog log = new Webservicelog();
		log.setDw(mpo.getDwdm());
		log.setIp(mpo.getIp());
		log.setSta((short)mpo.getState());
		log.setQs(mpo.getQsrq());
		log.setZz(mpo.getZzrq());
		log.setStype(mpo.getType());
		if(mpo instanceof MPO){			
			log.setCnt(((MPO)mpo).getData().size());
		}
		log.setDesc(mpo.getDescription());
		return log;
	}
}
