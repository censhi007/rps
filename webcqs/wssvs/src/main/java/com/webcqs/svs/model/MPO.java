package com.webcqs.svs.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.WI;

public class MPO extends IPMO{
	public MPO(){
		method="POST";
	}
	private static final long serialVersionUID = 1977975708413314663L;
	private List<WI> data = new ArrayList<WI>();
	@Override
	public String toJSON() {
		StringBuffer sb = new StringBuffer("{");
		append(sb,"method",method);
		append(sb,"type",type);
		append(sb,"time",time);
		append(sb,"ip",ip);
		append(sb,"state",state);
		append(sb,"description",description);
		append(sb,"data",data);
		sb.append("}");
		return sb.toString();
	}
	public List<WI> getData() {
		return data;
	}
	public void setData(List<WI> data) {
		this.data = data;
	}
	
	public void addData(WI wo){
		data.add(wo);
	}
	
	public static MPO copyFrom(IPMO ipo){
		MPO po = new MPO();
		if(ipo!=null){
			po.setIp(ipo.getIp());
			po.setType(ipo.getType());
			po.setTime(System.currentTimeMillis());
			po.setState(0);
			po.setDescription(ipo.getDescription());
			po.setDwdm(ipo.getDwdm());
			po.setQsrq(ipo.getQsrq());
			po.setZzrq(ipo.getZzrq());
		}
		return po;
	}
	private String dwdm;
	private Date qsrq;
	private Date zzrq;
	public String getDwdm() {
		return dwdm;
	}
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	public Date getQsrq() {
		return qsrq;
	}
	public void setQsrq(Date qsrq) {
		this.qsrq = qsrq;
	}
	public Date getZzrq() {
		return zzrq;
	}
	public void setZzrq(Date zzrq) {
		this.zzrq = zzrq;
	}
	
}
