package com.web.cq.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import com.webcqs.svs.inf.WI;
import com.webcqs.common.DateUtil;
import com.webcqs.svs.inf.PI;
import com.webcqs.svs.model.Wto;

/**
 * CqjyZbgs entity. @author MyEclipse Persistence Tools
 */

public class CqjyZbgs extends PI {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7868206899812797358L;
	private String id;
	private String dwdm;
	private String crsqid;
	private String srsqid;
	private String zbdw;
	private BigDecimal zbjg;
	private Date pzsj;
	private String pzdd;
	private String bz;
	private String lrr;
	private Date lrrq;
	private String gsr;
	private Date gsrq;
	private Date gszzrq;
	private Short state;
	private String op;

	private CqjyCrsq  cr;
	private CqjySrsq sr;
	
	private Date gsqxq;
	private Date gsqxz;
	// Constructors

	/** default constructor */
	public CqjyZbgs() {
	}

	/** minimal constructor */
	public CqjyZbgs(String id, String dwdm) {
		this.id = id;
		this.dwdm = dwdm;
	}

	/** full constructor */
	public CqjyZbgs(String id, String dwdm, String crsqid, String srsqid,
			String zbdw, BigDecimal zbjg, Date pzsj, String pzdd, String bz,
			String lrr, Date lrrq, String gsr, Date gsrq,
			Date gszzrq, Short state,Date gsqxq,Date gsqxz,String op) {
		this.id = id;
		this.dwdm = dwdm;
		this.crsqid = crsqid;
		this.srsqid = srsqid;
		this.zbdw = zbdw;
		this.zbjg = zbjg;
		this.pzsj = pzsj;
		this.pzdd = pzdd;
		this.bz = bz;
		this.lrr = lrr;
		this.lrrq = lrrq;
		this.gsr = gsr;
		this.gsrq = gsrq;
		this.gszzrq = gszzrq;
		this.state = state;
		this.gsqxq=gsqxq;
		this.gsqxz=gsqxz;
		this.setOp(op);
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDwdm() {
		return this.dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getCrsqid() {
		return this.crsqid;
	}

	public void setCrsqid(String crsqid) {
		this.crsqid = crsqid;
	}

	public String getSrsqid() {
		return this.srsqid;
	}

	public void setSrsqid(String srsqid) {
		this.srsqid = srsqid;
	}

	public String getZbdw() {
		return this.zbdw;
	}

	public void setZbdw(String zbdw) {
		this.zbdw = zbdw;
	}

	public BigDecimal getZbjg() {
		return this.zbjg;
	}

	public void setZbjg(BigDecimal zbjg) {
		this.zbjg = zbjg;
	}

	public Date getPzsj() {
		return this.pzsj;
	}

	public void setPzsj(Date pzsj) {
		this.pzsj = pzsj;
	}

	public String getPzdd() {
		return this.pzdd;
	}

	public void setPzdd(String pzdd) {
		this.pzdd = pzdd;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getLrr() {
		return this.lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}

	public Date getLrrq() {
		return this.lrrq;
	}

	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}

	public String getGsr() {
		return this.gsr;
	}

	public void setGsr(String gsr) {
		this.gsr = gsr;
	}

	public Date getGsrq() {
		return this.gsrq;
	}

	public void setGsrq(Date gsrq) {
		if(gszzrq==null && gsrq!=null){
			gszzrq=DateUtil.getDateAddDays(gsrq, 5);
		}
		this.gsrq = gsrq;
	}

	public Date getGszzrq() {
		if(gszzrq==null && gsrq!=null){
			gszzrq=DateUtil.getDateAddDays(gsrq, 5);
		}
		return this.gszzrq;
	}

	public void setGszzrq(Date gszzrq) {
		this.gszzrq = gszzrq;
	}

	public Short getState() {
		return this.state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public CqjyCrsq getCr() {
		return cr;
	}

	public void setCr(CqjyCrsq cr) {
		this.cr = cr;
	}

	public CqjySrsq getSr() {
		return sr;
	}

	public void setSr(CqjySrsq sr) {
		this.sr = sr;
	}

	public Date getGsqxq() {
		return gsqxq;
	}

	public void setGsqxq(Date gsqxq) {
		this.gsqxq = gsqxq;
	}

	public Date getGsqxz() {
		if(gsqxz==null){
			gsqxz=getGszzrq();
		}
		return gsqxz;
	}

	public void setGsqxz(Date gsqxz) {
		this.gsqxz = gsqxz;
	}

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


	public WI toWI(){
		Wto to =new Wto();
		WOrgz jyzx = cr.getJyzx();
		if(jyzx!=null){
			String jc =jyzx.getJc();
			to.put("crfmc",jc!=null ? jc+cr.getCrfmc() : cr.getCrfmc());
		}else{
			to.put("crfmc",cr.getCrfmc());
		}
		if(pzsj==null && sr!=null && sr.getFb()!=null){
			to.put("pzsj",sr.getFb().getGgqxZ());
		}else{			
			to.put("pzsj",pzsj);
		}
		to.put("pzdd",pzdd);
		to.put("lx",cr.getJyxmfl());
		to.put("lxText",cr.getJyxmflText());
		//to.put("zlqx",cr.getZlqx());
		to.put("gsqxq",gsqxq);
		to.put("gsqxz",gsqxz);
		to.put("mc",cr.getMc());
		to.put("ggxh",cr.getGgxh());
		to.put("jzmj",cr.getJzmj());
		to.put("mj",cr.getMj());
		to.put("zlqx",cr.getZlqx());
		to.put("zbjg",zbjg);
		to.put("zbdw",zbdw);
		return to;
	}
	
}