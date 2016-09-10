package com.web.cq.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import com.webcqs.svs.inf.WI;
import com.webcqs.svs.inf.PI;
import com.webcqs.svs.model.Wto;

/**
 * 
 */

public class CqjyCrsq extends PI{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7853039584021186327L;
	private String id;
	private String dwdm;
	private String xmbh;
	private String crfid;
	private String crfmc;
	private String crfdz;
	private Date sqrq;
	private Short jyxmfl;
	private String jyxmflText;
	private Short crfs;
	private String mc;
	private String zlwz;
	private BigDecimal jzmj;
	private BigDecimal zdmj;
	private String ggxh;
	private BigDecimal sl;
	private BigDecimal yz;
	private Integer ysynx;
	private BigDecimal mj;
	private String bz;
	private Short jyfs;
	private BigDecimal dj;
	private BigDecimal zj;
	private String zlqx;
	private String lrr;
	private Date lrrq;
	private String jyzxid;
	private String jyzxmc;
	private Short xmbs;

	private Short step;
	private String op="add";
	private String stype;
	private String jyzxdwdm;
	private String jyfsText;
	private String path;
	// Constructors

	private WOrgz dw;
	private WOrgz jyzx;
	/** default constructor */
	public CqjyCrsq() {
	}

	/** minimal constructor */
	public CqjyCrsq(String id) {
		this.id = id;
		
	}

	/** full constructor */
	public CqjyCrsq(String id, String dwdm, String xmbh, String crfid,
			String crfmc, String crfdz, Date sqrq, Short jyxmfl,
			String jyxmflText, Short crfs, String mc, String zlwz, BigDecimal jzmj,
			BigDecimal zdmj, String ggxh, BigDecimal sl, BigDecimal yz, Integer ysynx,
			BigDecimal mj, String bz, Short jyfs, BigDecimal dj, BigDecimal zj,
			String zlqx, String lrr, Date lrrq, String jyzxid,
			String jyzxmc, Short xmbs,Short step,String jyzxdwdm,String path,String op) {
		this.id = id;
		this.dwdm = dwdm;
		this.xmbh = xmbh;
		this.crfid = crfid;
		this.crfmc = crfmc;
		this.crfdz = crfdz;
		this.sqrq = sqrq;
		this.jyxmfl = jyxmfl;
		this.jyxmflText = jyxmflText;
		this.crfs = crfs;
		this.mc = mc;
		this.zlwz = zlwz;
		this.jzmj = jzmj;
		this.zdmj = zdmj;
		this.ggxh = ggxh;
		this.sl = sl;
		this.yz = yz;
		this.ysynx = ysynx;
		this.mj = mj;
		this.bz = bz;
		this.jyfs = jyfs;
		this.dj = dj;
		this.zj = zj;
		this.zlqx = zlqx;
		this.lrr = lrr;
		this.lrrq = lrrq;
		this.jyzxid = jyzxid;
		this.jyzxmc = jyzxmc;
		this.xmbs = xmbs;
		this.jyzxdwdm=jyzxdwdm;
		this.step=step;
		this.path=path;
		this.op=op;
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

	public String getXmbh() {
		return this.xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getCrfid() {
		return this.crfid;
	}

	public void setCrfid(String crfid) {
		this.crfid = crfid;
	}

	public String getCrfmc() {
		return this.crfmc;
	}

	public void setCrfmc(String crfmc) {
		this.crfmc = crfmc;
	}

	public String getCrfdz() {
		return this.crfdz;
	}

	public void setCrfdz(String crfdz) {
		this.crfdz = crfdz;
	}

	public Date getSqrq() {
		return this.sqrq;
	}

	public void setSqrq(Date sqrq) {
		this.sqrq = sqrq;
	}

	public Short getJyxmfl() {
		return this.jyxmfl;
	}

	public void setJyxmfl(Short jyxmfl) {
		this.jyxmfl = jyxmfl;
	}

	public String getJyxmflText() {
		return this.jyxmflText;
	}

	public void setJyxmflText(String jyxmflText) {
		this.jyxmflText = jyxmflText;
	}

	public Short getCrfs() {
		return this.crfs;
	}

	public void setCrfs(Short crfs) {
		this.crfs = crfs;
	}

	public String getMc() {
		return this.mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getZlwz() {
		return this.zlwz;
	}

	public void setZlwz(String zlwz) {
		this.zlwz = zlwz;
	}

	public BigDecimal getJzmj() {
		return this.jzmj;
	}

	public void setJzmj(BigDecimal jzmj) {
		this.jzmj = jzmj;
	}

	public BigDecimal getZdmj() {
		return this.zdmj;
	}

	public void setZdmj(BigDecimal zdmj) {
		this.zdmj = zdmj;
	}

	public String getGgxh() {
		return this.ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public BigDecimal getSl() {
		return this.sl;
	}

	public void setSl(BigDecimal sl) {
		this.sl = sl;
	}

	public BigDecimal getYz() {
		return this.yz;
	}

	public void setYz(BigDecimal yz) {
		this.yz = yz;
	}

	public Integer getYsynx() {
		return this.ysynx;
	}

	public void setYsynx(Integer ysynx) {
		this.ysynx = ysynx;
	}

	public BigDecimal getMj() {
		return this.mj;
	}

	public void setMj(BigDecimal mj) {
		this.mj = mj;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Short getJyfs() {
		return this.jyfs;
	}

	public void setJyfs(Short jyfs) {
		this.jyfs = jyfs;
	}

	public BigDecimal getDj() {
		return this.dj;
	}

	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}

	public BigDecimal getZj() {
		return this.zj;
	}

	public void setZj(BigDecimal zj) {
		this.zj = zj;
	}

	public String getZlqx() {
		return this.zlqx;
	}

	public void setZlqx(String zlqx) {
		this.zlqx = zlqx;
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

	public String getJyzxid() {
		return this.jyzxid;
	}

	public void setJyzxid(String jyzxid) {
		this.jyzxid = jyzxid;
	}

	public String getJyzxmc() {
		return this.jyzxmc;
	}

	public void setJyzxmc(String jyzxmc) {
		this.jyzxmc = jyzxmc;
	}

	public Short getXmbs() {
		return this.xmbs;
	}

	public void setXmbs(Short xmbs) {
		this.xmbs = xmbs;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public Short getStep() {
		return step;
	}

	public void setStep(Short step) {
		this.step = step;
	}

	public String getJyzxdwdm() {
		return jyzxdwdm;
	}

	public void setJyzxdwdm(String jyzxdwdm) {
		this.jyzxdwdm = jyzxdwdm;
	}

	public WOrgz getJyzx() {
		return jyzx;
	}

	public void setJyzx(WOrgz jyzx) {
		this.jyzx = jyzx;
	}

	public WOrgz getDw() {
		return dw;
	}

	public void setDw(WOrgz dw) {
		this.dw = dw;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getJyfsText() {
		return jyfsText;
	}

	public void setJyfsText(String jyfsText) {
		this.jyfsText = jyfsText;
	}

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

	public WI toWI(){
		Wto to = new Wto();
		to.put("id",id);
		String jc =null;
		
		if(jyzx!=null){
			jc = jyzx.getJc();
		}
		String dwmc = jc==null ? "": jc;
		if(dw!=null){
			dwmc += dw.getJc();
		}
		to.put("dwdm",dwdm);
		to.put("dwmc",dwmc);
		to.put("mc",mc);
		to.put("xmmc",dwmc+mc);
		to.put("xmbh", xmbh);
		to.put("crqx", zlqx);
		to.put("crfs", crfs);
		to.put("lx", jyxmfl);
		to.put("lxText", jyxmflText);
		if(jc!=null){
			to.put("crfmc", jc+"-"+crfmc);	
		}else{
			to.put("crfmc", crfmc);
		}		
		to.put("dj", dj);
		to.put("zj", zj);
		to.put("zlwz", zlwz);
		to.put("jzmj", jzmj);
		to.put("zdmj", zdmj);
		to.put("mj", mj);
		to.put("ggxh", ggxh);
		to.put("path", path);
		to.put("sl", sl);
		to.put("yz", yz);
		if(jyfsText == null){
			to.put("jyfs", jyfs == 1 ? "村组织招标" : jyfs == 2 ? "委托中介招标" : "公开协商");
		}else{			
			to.put("jyfs", jyfsText);
		}
		to.put("bz", bz);
		to.put("crfs", crfs);
		to.put("crfsText", crfs==1 ? "租赁" : crfs == 2 ? "转让" : "土地流转");
		to.put("jyzxmc",jyzxmc);
		
		return to;
	}
	
	
}