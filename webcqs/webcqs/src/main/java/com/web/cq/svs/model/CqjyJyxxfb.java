package com.web.cq.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.webcqs.common.DateUtil;
import com.webcqs.svs.inf.PI;
import com.webcqs.svs.inf.WI;
import com.webcqs.svs.model.Wto;

/**
 * CqjyJyxxfb entity. @author MyEclipse Persistence Tools
 */

public class CqjyJyxxfb extends PI {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1671857205168255853L;
	private String id;
	private String dwdm;
	private String jyzxdwdm;
	private Date fbrq;
	private String crfid;
	private String crfdwmc;
	private String crffzr;
	private String crfdz;
	private BigDecimal crfzczb;
	private String jyzxid;
	private String jyzxlxr;
	private String jyzxlxdh;
	private String jyzxcz;
	private String crsqid;
	private String xxnr;
	private Short bjfs;
	private String bjfsmc;
	private String bz;
	private String lrr;
	private String shr;
	private Date shrq;
	private Date lrrq;
	private Short state;
	private CqjyCrsq cr;
	private Date fbjzrq;
	private Date ggqxQ;
	private Date ggqxZ;
	private String crfdh;
	private String op;
	// Constructors

	/** default constructor */
	public CqjyJyxxfb() {
	}

	/** minimal constructor */
	public CqjyJyxxfb(String id, String dwdm) {
		this.id = id;
		this.dwdm = dwdm;
	}

	/** full constructor */
	public CqjyJyxxfb(String id, String dwdm, Date fbrq, String crfid,
			String crfdwmc, String crffzr, String crfdz, BigDecimal crfzczb,
			String jyzxid, String jyzxlxr, String jyzxlxdh, String jyzxcz,
			String crsqid, String xxnr, Short bjfs, String bjfsmc, String bz,
			String lrr, String shr, Date shrq, Date lrrq,String jyzxdwdm,Date ggqxQ,Date ggqxZ,
			Short state,String crfdh,String op) {
		this.id = id;
		this.dwdm = dwdm;
		this.fbrq = fbrq;
		this.crfid = crfid;
		this.crfdwmc = crfdwmc;
		this.crffzr = crffzr;
		this.crfdz = crfdz;
		this.crfzczb = crfzczb;
		this.jyzxid = jyzxid;
		this.jyzxlxr = jyzxlxr;
		this.jyzxlxdh = jyzxlxdh;
		this.jyzxcz = jyzxcz;
		this.crsqid = crsqid;
		this.xxnr = xxnr;
		this.bjfs = bjfs;
		this.bjfsmc = bjfsmc;
		this.bz = bz;
		this.lrr = lrr;
		this.shr = shr;
		this.shrq = shrq;
		this.lrrq = lrrq;
		this.jyzxdwdm=jyzxdwdm;
		this.state=state;
		this.ggqxQ=ggqxQ;
		this.ggqxZ=ggqxZ;
		this.crfdh=crfdh;
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

	public Date getFbrq() {
		if(fbrq==null && lrrq!=null){
			this.setFbrq(lrrq);
		}
		return this.fbrq;
	}

	public void setFbrq(Date fbrq) {
		this.fbrq = fbrq;
		if(fbrq!=null){
			this.setFbjzrq(DateUtil.getDateAddDays(fbrq, 15));//推迟15天
		}
	}

	public String getCrfid() {
		return this.crfid;
	}

	public void setCrfid(String crfid) {
		this.crfid = crfid;
	}

	public String getCrfdwmc() {
		return this.crfdwmc;
	}

	public void setCrfdwmc(String crfdwmc) {
		this.crfdwmc = crfdwmc;
	}

	public String getCrffzr() {
		return this.crffzr;
	}

	public void setCrffzr(String crffzr) {
		this.crffzr = crffzr;
	}

	public String getCrfdz() {
		return this.crfdz;
	}

	public void setCrfdz(String crfdz) {
		this.crfdz = crfdz;
	}

	public BigDecimal getCrfzczb() {
		return this.crfzczb;
	}

	public void setCrfzczb(BigDecimal crfzczb) {
		this.crfzczb = crfzczb;
	}

	public String getJyzxid() {
		return this.jyzxid;
	}

	public void setJyzxid(String jyzxid) {
		this.jyzxid = jyzxid;
	}

	public String getJyzxlxr() {
		return this.jyzxlxr;
	}

	public void setJyzxlxr(String jyzxlxr) {
		this.jyzxlxr = jyzxlxr;
	}

	public String getJyzxlxdh() {
		return this.jyzxlxdh;
	}

	public void setJyzxlxdh(String jyzxlxdh) {
		this.jyzxlxdh = jyzxlxdh;
	}

	public String getJyzxcz() {
		return this.jyzxcz;
	}

	public void setJyzxcz(String jyzxcz) {
		this.jyzxcz = jyzxcz;
	}

	public String getCrsqid() {
		return this.crsqid;
	}

	public void setCrsqid(String crsqid) {
		this.crsqid = crsqid;
	}

	public String getXxnr() {
		return this.xxnr;
	}

	public void setXxnr(String xxnr) {
		this.xxnr = xxnr;
	}

	public Short getBjfs() {
		return this.bjfs;
	}

	public void setBjfs(Short bjfs) {
		this.bjfs = bjfs;
	}

	public String getBjfsmc() {
		return this.bjfsmc;
	}

	public void setBjfsmc(String bjfsmc) {
		this.bjfsmc = bjfsmc;
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

	public String getShr() {
		return this.shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	public Date getShrq() {
		return this.shrq;
	}

	public void setShrq(Date shrq) {
		this.shrq = shrq;
	}

	public Date getLrrq() {
		return this.lrrq;
	}

	public void setLrrq(Date lrrq) {
		this.lrrq = lrrq;
	}


	public String getJyzxdwdm() {
		return jyzxdwdm;
	}

	public void setJyzxdwdm(String jyzxdwdm) {
		this.jyzxdwdm = jyzxdwdm;
	}

	public CqjyCrsq getCr() {
		return cr;
	}

	public void setCr(CqjyCrsq cr) {
		this.cr = cr;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public Date getFbjzrq() {		
		if(fbjzrq==null && getFbrq()!=null){
			this.setFbjzrq(DateUtil.getDateAddDays(fbrq, 15));//推迟15天
		}
		return fbjzrq;
	}

	public void setFbjzrq(Date fbjzrq) {
		this.fbjzrq = fbjzrq;
	}

	public Date getGgqxQ() {
		return ggqxQ;
	}

	public void setGgqxQ(Date ggqxQ) {
		this.ggqxQ = ggqxQ;
	}

	public Date getGgqxZ() {
		return ggqxZ;
	}

	public void setGgqxZ(Date ggqxZ) {
		this.ggqxZ = ggqxZ;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getCrfdh() {
		return crfdh;
	}

	public void setCrfdh(String crfdh) {
		this.crfdh = crfdh;
	}

	private int outdate;

	public int getOutdate() {
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		if(ggqxZ==null){
			outdate=0;
			return outdate;
		}
		//修改为在当前日期是最后一天不算过期。
		outdate=c.getTimeInMillis() > ggqxZ.getTime() ? 1:0;
		return outdate;
	}

	public void setOutdate(int outdate) {
		this.outdate = outdate;
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
		Wto to = (Wto)cr.toWI();
		//to.put("id",id);
		to.put("qsrq",ggqxQ);
		to.put("zzrq",ggqxZ);
		to.put("crfdz",crfdz == null ? cr.getCrfdz() : crfdz);
		to.put("crffzr",crffzr);
		to.put("lxdh",crfdh);
		to.put("jyzxlxr",jyzxlxr);		
		return to;
	}
}