package com.web.jyht.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.webcqs.svs.inf.PI;
import com.webcqs.svs.inf.WI;
import com.webcqs.svs.model.Wto;
/**
 * 
 * @author BUJUN
 *
 */
public class OrgTree extends PI{
	private String id;
	private String dwdm;
	private String dwmc;
	private String parentId;
	private Integer level;
	private static final long serialVersionUID = -5296565131617230478L;

	@Override
	public void fromJSON(String arg0) {
		
	}

	@Override
	public void loadFrom(InputStream arg0) throws IOException {
		
	}

	@Override
	public String toJSON() {
		return null;
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		
	}

	public String getId() {
		return id;
	}
	
	public WI toWI(){
		Wto to = new Wto();
		to.put("id", id);
		to.put("parentId", parentId);
		to.put("dwdm",dwdm);
		to.put("dwmc",dwmc);
		to.put("level",level);
		return to;
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

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
