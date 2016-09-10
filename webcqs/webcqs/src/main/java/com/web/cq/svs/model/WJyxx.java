package com.web.cq.svs.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

import com.webcqs.svs.inf.PI;

/**
 * 交易项目
 * @author BUJUN
 *
 */
public class WJyxx extends PI{
	private String id;
	private static final long serialVersionUID = -7127010848250474357L;
	private String dwmc;
	private String xmbh;
	private String xmmc;
	private Integer crfs;
	private String crqx;
	private String crfmc;
	private BigDecimal dj;
	private BigDecimal zj;
	private Date qsrq;
	private Date zzrq;
	private String path;
	private String mc;
	private String zlwz;
	private BigDecimal jzmj;
	private BigDecimal mj;
	private BigDecimal zdmj;
	private String ggxh;
	private BigDecimal sl;
	private BigDecimal yz;
	private Integer ysynx;
	private String jyfs;
	private String bz;
	private String crfdz;
	private String crffzr;
	private String lxdh;
	private String jyzxmc;
	private String jyzxlxr;
	
	@Override
	public void fromJSON(String arg0) {
		
	}

	@Override
	public void loadFrom(InputStream arg0) throws IOException {
		
	}

	@Override
	public String toJSON() {
		if(json!=null)return json.toString();
		StringBuffer sb = new StringBuffer("{");
		append(sb,"dwmc",dwmc);
		append(sb,"xmbh",xmbh);
		append(sb,"xmmc",xmmc);
		append(sb,"crfs",crfs);
		append(sb,"crqx",crqx);
		append(sb,"crfmc",crfmc);
		append(sb,"dj",dj);
		append(sb,"zj",zj);
		append(sb,"qsrq",qsrq);
		append(sb,"zzrq",zzrq);
		append(sb,"path",path);
		append(sb,"mc",mc);
		append(sb,"zlwz",zlwz);
		append(sb,"jzmj",jzmj);
		append(sb,"mj",mj);
		append(sb,"zdmj",zdmj);
		append(sb,"ggxh",ggxh);
		append(sb,"sl",sl);
		append(sb,"yz",yz);
		append(sb,"ysynx",ysynx);
		append(sb,"jyfs",jyfs);
		append(sb,"bz",bz);
		append(sb,"crfdz",crfdz);
		append(sb,"crffzr",crffzr);
		append(sb,"lxdh",lxdh);
		append(sb,"jyzxmc",jyzxmc);
		append(sb,"jyzxlxr",jyzxlxr);
		append(sb,"id",id);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getXmmc() {
		return xmmc;
	}

	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}

	public Integer getCrfs() {
		return crfs;
	}

	public void setCrfs(Integer crfs) {
		this.crfs = crfs;
	}

	public String getCrqx() {
		return crqx;
	}

	public void setCrqx(String crqx) {
		this.crqx = crqx;
	}

	public String getCrfmc() {
		return crfmc;
	}

	public void setCrfmc(String crfmc) {
		this.crfmc = crfmc;
	}

	public BigDecimal getDj() {
		return dj;
	}

	public void setDj(BigDecimal dj) {
		this.dj = dj;
	}

	public BigDecimal getZj() {
		return zj;
	}

	public void setZj(BigDecimal zj) {
		this.zj = zj;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public String getZlwz() {
		return zlwz;
	}

	public void setZlwz(String zlwz) {
		this.zlwz = zlwz;
	}

	public BigDecimal getJzmj() {
		return jzmj;
	}

	public void setJzmj(BigDecimal jzmj) {
		this.jzmj = jzmj;
	}

	public BigDecimal getMj() {
		return mj;
	}

	public void setMj(BigDecimal mj) {
		this.mj = mj;
	}

	public BigDecimal getZdmj() {
		return zdmj;
	}

	public void setZdmj(BigDecimal zdmj) {
		this.zdmj = zdmj;
	}

	public String getGgxh() {
		return ggxh;
	}

	public void setGgxh(String ggxh) {
		this.ggxh = ggxh;
	}

	public BigDecimal getSl() {
		return sl;
	}

	public void setSl(BigDecimal sl) {
		this.sl = sl;
	}

	public BigDecimal getYz() {
		return yz;
	}

	public void setYz(BigDecimal yz) {
		this.yz = yz;
	}

	public Integer getYsynx() {
		return ysynx;
	}

	public void setYsynx(Integer ysynx) {
		this.ysynx = ysynx;
	}

	public String getJyfs() {
		return jyfs;
	}

	public void setJyfs(String jyfs) {
		this.jyfs = jyfs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCrfdz() {
		return crfdz;
	}

	public void setCrfdz(String crfdz) {
		this.crfdz = crfdz;
	}

	public String getCrffzr() {
		return crffzr;
	}

	public void setCrffzr(String crffzr) {
		this.crffzr = crffzr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getJyzxmc() {
		return jyzxmc;
	}

	public void setJyzxmc(String jyzxmc) {
		this.jyzxmc = jyzxmc;
	}

	public String getJyzxlxr() {
		return jyzxlxr;
	}

	public void setJyzxlxr(String jyzxlxr) {
		this.jyzxlxr = jyzxlxr;
	}
	
	private StringBuffer json;
	public static WJyxx Wrap(CqjyJyxxfb fb){
		StringBuffer sb = new StringBuffer("{");
		WJyxx w = new WJyxx();
		w.id = fb.getId();
		CqjyCrsq cr = fb.getCr();
		WOrgz jyzx = cr.getJyzx();
		WOrgz dw = cr.getDw();
		String jc =null;
		if(jyzx!=null){
			jc = jyzx.getJc();
		}
		w.dwmc = jc==null ? "": jc;
		if(dw !=null){
			w.dwmc+=dw.getJc();
		}
		w.mc = cr.getMc();
		w.xmmc = w.dwmc+w.mc;
		w.xmbh = cr.getXmbh();
		w.crqx = cr.getZlqx();
		w.crfs = cr.getCrfs()+0;
		if(jc!=null){
			w.crfmc = jc+"-"+cr.getCrfmc();
		}else{
			w.crfmc = cr.getCrfmc();
		}
		w.dj = cr.getDj();
		
		sb.append("}");
		w.json=sb;
		return w;
	}
}
