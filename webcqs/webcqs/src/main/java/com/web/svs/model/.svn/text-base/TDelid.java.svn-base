package com.web.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.webcqs.svs.inf.PI;
import com.webcqs.svs.inf.WI;
import com.webcqs.svs.model.Wto;
/**
 * 需要删除的对象的id
 * @author BUJUN
 *
 */
public class TDelid extends PI{
	private String id;
	private String tableName;
	private String dwdm;
	private Date lasttime;
	private Short sta;
	private OKeys keys;
	private static final long serialVersionUID = 5166099291429653401L;

	@Override
	public void fromJSON(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadFrom(InputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public OKeys getKeys() {
		if(keys==null)keys=new OKeys();
		return keys;
	}

	public void setKeys(OKeys keys) {
		this.keys = keys;
	}

	public Short getSta() {
		return sta;
	}

	public void setSta(Short sta) {
		this.sta = sta;
	}
	/**
	 * 目前未使用，<br/>
	 * sqlserver端的todel是直接使用map组装的。
	 */
	public WI toWI(){
		Wto to = new Wto();	
		to.put("id", id);
		to.put("keys",parseKeys());
		to.put("lasttime", lasttime);
		to.put("state", sta);
		return to;
	}
	
	
	private Object parseKeys(){
		Wto to = new Wto();	
		if("ht_jyht".equalsIgnoreCase(tableName)){
			to.put("uid", getKeys().getKeys().replace("ht_jyht_",""));
			return to;
		}
		return getKeys().getKeys();
	}
}
