package com.ws.ksoft.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.webcqs.common.DBI;
import com.webcqs.common.QueryObject;
import com.webcqs.common.UCI;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
import com.webcqs.svs.model.Wto;
/**
 * 查询在某个时间段内，对应单位下有多少条数据
 * @author BUJUN
 *
 */
public class GNum implements Ti{
	private static final String PREFIX_ND_MSS ="njsys_";
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		try{
			int sty=-1;
			int stm=-1;
			int edy=-1;
			int edm=-1;
			int ty = 0;
			try{
				Object to = param.get("useLasttime");
				if(to==null || "undefined".equals(to)){
					//用户没有传入该标签。自动猜测默认值。
					//如果用户传入的其实日期和终止日期含有时分数据，说明用户希望查询lasttime，如果用户传入的是日期型数据，说明用户期望查询的是会计时间。
					Date qsrq = param.getQsrq();
					boolean ok = false;
					if(qsrq!=null && (qsrq.getHours()!=0 || qsrq.getMinutes()!=0))ok=true;
					qsrq = param.getZzrq();
					if(qsrq!=null && (qsrq.getHours()!=0 || qsrq.getMinutes()!=0))ok=true;
					ty = ok ? 1 : 0;
					
				}else if(to instanceof Number){
					ty = ((Number)to).intValue();
				}else if(to instanceof String){
					ty = "1".equals(to)?1:0;
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			Date qsrq = param.getQsrq();
			Date zzrq = param.getZzrq();
			if(ty == 0){
				if(qsrq!=null){
					sty = qsrq.getYear()+1900;
					stm = qsrq.getMonth()+1;
				}
				if(zzrq!=null){
					edy = zzrq.getYear()+1900;
					edm = zzrq.getMonth()+1;
					int d = zzrq.getDate();
					if(d==1){
						//终止日期传入x月1日。说明客户希望不包含终止月份
						edm--;
						if(edm == 0){
							//例如。终止日期用户传入2015-01-01;表示用户希望查询2015年以前的数据
							edm=12;
							edy--;
						}
					}
				}
				if(sty>edy){//年度差
					int t=sty;
					sty=edy;
					edy=t;
					t=stm;
					stm=edm;
					edm=t;
				}else if(sty==edy){//月差
					if(stm>edm){
						int t=stm;
						stm=edm;
						edm=t;
					}
				}
			}			
			final int _sty = sty;
			final int _edy = edy;
			DBI db = (DBI)Config.getBean("dbi");
			Object o = db.invoke(new UCI(){
				@Override
				public Object useConnection(Connection cnn) {
					List<String> nddb = new ArrayList<String>();
					try {
						ResultSet rs=cnn.getMetaData().getCatalogs();
						int st=PREFIX_ND_MSS.length();
						int length=st+4;
						while (rs.next()) {
							String n=rs.getString(1);
							if(n.toLowerCase().startsWith(PREFIX_ND_MSS) && length==n.length()){
								try{
									Integer y=Integer.parseInt(n.substring(st));
									//在查询期间内的年度库
									if(y>=_sty && (_edy==-1 || _edy>=y)){
										nddb.add(n);
									}
								}catch(Exception ie){
									continue;
								}
							}

						}
						
					} catch (SQLException e) {
						e.printStackTrace();
						return null;
					}
					return nddb;
				}
				
			});
			List<String> ndbs = null;
			if(o==null || !(o instanceof List)){
				ndbs = new ArrayList<String>();
			}else{
				ndbs = (List)o;
			}
			QueryObject qo = QueryObject.getSQLQuery();
			String dwdm = param.getDwdm();
			Object dw = db.dwdmlimit(dwdm);
			String dsp=null;
			if(dw!=null){
				if(dw instanceof String){
					dsp = dw+"%";
					dw = "m.dwdm like :dwdm";				
				}else{
					dw=db.buildDwdmLike(" m.dwdm",(Collection<Object>)dw);
				}
			}
			Object rcode = param.get("reportcode");
			if(rcode!=null){
				if(rcode instanceof String){
					List<String> tl = new ArrayList<String>();
					for(String s:((String) rcode).split(",")){
						if(s!=null &&!"".equals(s)){
							tl.add(s);
						}
					}
					rcode=tl;
				}
			}
			Wto to;
			for(String s:ndbs){
				Integer cy=Integer.parseInt(s.substring(6));
				qo.clear();
				qo.append(" select count(dataid),")
				  .append(cy.toString())
				  .append(" as ndbs,reportcode,dwdm  from [")
				  .append(s).append("]..gk_v_data_main m where 1=1 ");
				if(1 == ty){
					//按包含起始时间，不包含终止时间的情况来查询数据。
					if(qsrq!=null){
						qo.append(" and m.lasttime>=:stime").addParam("stime", qsrq);
					}
					if(zzrq!=null){
						qo.append(" and m.lasttime <:etime").addParam("etime", zzrq);
					}
				}else if(0==ty){				
					if(sty!=edy){//开始时间与结束时间不在同一年
						if(edy==-1){//查询大于起始时间的数据
							qo.append(" and m.qskjqj>=:stm").addParam("stm", stm);							
						}else if(sty==-1){//查询小于终止时间的数据
							qo.append(" and m.zjkjqj<=:edm").setParameter("edm", edm);
						}else{
							if(cy==sty){//开始时间是当前轮询年度//查询当前年度起始月份以后的数据
								qo.append(" and m.qskjqj>=:stm").addParam("stm", stm);
							}else if (cy==edy){
								qo.append(" and m.zjkjqj<=:edm").setParameter("edm", edm);
							}else{
								//取全年数据
								//...
							}
						}
						
					}else{
						if(sty==-1){//styy==edy==-1
							//取全部年度全部数据
						}else{
							//取但年度某期间数据
							qo.append(" and m.zjkjqj<=:edm").setParameter("edm", edm).append(" and m.qskjqj>=:stm").addParam("stm", stm);
						}
					}
				}
				if(dw!=null)qo.append(" and ").append(dw.toString());
				if(dsp!=null)qo.addParam("dwdm",dsp);
				
				if(rcode!=null){
					qo.append(" and m.reportcode in (:reportcode)").addParam("reportcode", rcode);
				}
				
				qo.append(" group by reportcode,dwdm");
				List<Object[]> oos = db.fetchQuery(qo);
				for(Object[] os:oos){
					to = new Wto();
					to.put("count", os[0]);
					to.put("ndbs", os[1]);
					to.put("reportcode", os[2]);
					to.put("dwdm", os[3]);
					mpo.addData(to);
				}
			}
			mpo.setState(1);
			mpo.setDescription("@ksoft_count_suc");
			return mpo;
		}catch(Throwable e){
			e.printStackTrace();
			mpo.setDescription("@ksoft_count_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
