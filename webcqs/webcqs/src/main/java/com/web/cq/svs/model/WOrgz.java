package com.web.cq.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.webcqs.svs.inf.PI;

public class WOrgz extends PI{

	private Integer id;
	private String dwmc;
	private String dwdm;
	private String parent;
	private String jc;
	private static final long serialVersionUID = 8408159699356419911L;

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
		StringBuffer sb = new StringBuffer("{");
		append(sb, "dwdm",dwdm);
		append(sb, "parent",parent);
		append(sb, "dwmc",dwmc);
		append(sb, "jc",jc);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getJc() {
		return jc;
	}

	public void setJc(String jc) {
		this.jc = jc;
	}

	
}
