package com.web.jyht.svs.imp;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.webcqs.common.DBI;
import com.webcqs.common.QueryObject;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
import com.webcqs.svs.model.Wto;

public class HthzSi implements Ti{

	@SuppressWarnings("unchecked")
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		
		try{
			DBI db = (DBI)Config.getBean("dbi");
			QueryObject qo = new QueryObject().append("select ht.dwdm,count(ht.id),sum(ht.jkze) from HtJyht ht where 1=1 ");
			String dwdm = param.getDwdm();
			Object dw = db.dwdmlimit(dwdm);
			if(dw!=null){
				if(dw instanceof String){
					qo.append(" and ht.dwdm like :dwdm ").addParam("dwdm",dw+"%");					
				}else{
					Object s=db.buildDwdmLike(" ht.dwdm",(Collection<Object>)dw);
					if(s!=null){
						qo.append(" and ").append(s.toString());
					}
				}
			}
			Object qxq = param.get("qdrqq");
			if(qxq!=null && qxq instanceof Date){
				qo.append(" and ht.qdrq >= :qdq ").addParam("qdq", qxq);
			}
			qxq = param.get("qdrqz");
			if(qxq!=null && qxq instanceof Date){
				qo.append(" and ht.qdrq < :qdz ").addParam("qdz", qxq);
			}
			qxq = param.getDate("ltimeq");
			if(qxq!=null && qxq instanceof Date){
				qo.append(" and ht.lasttime >= :ltimeq ").addParam("ltimeq", qxq);
			}
			qxq = param.getDate("ltimez");
			if(qxq!=null && qxq instanceof Date){
				qo.append(" and ht.lasttime < :ltimez ").addParam("ltimez", qxq);
			}
			Object wcqk = param.get("wcqk");
			if(wcqk!=null && wcqk instanceof Number){
				qo.append(" and ht.zzhtwcqk = :zzhtwcqk ").addParam("zzhtwcqk", wcqk);
			}
			Object xqht = param.get("xqht");
			if(xqht!=null && xqht instanceof Number){
				qo.append(" and ht.xqht = :xqht ").addParam("xqht", xqht);
			}
			Object htlxqk = param.get("lxqk");
			if(htlxqk!=null && htlxqk instanceof Number){
				qo.append(" and ht.htlxqk = :htlxqk ").addParam("htlxqk", htlxqk);
			}
			qo.append(" group by ht.dwdm order by ht.dwdm");
			List<Object[]> hL = db.fetchQuery(qo);
			Iterator<Object[]> it = hL.iterator();
			Wto wi;
			while(it.hasNext()){
				Object[] fb = it.next();
				wi = new Wto();
				wi.put("dwdm", fb[0]);
				wi.put("count", fb[1]);
				wi.put("jkze", fb[2]);
				mpo.addData(wi);
				wi = null;
				it.remove();
			}
			mpo.setState(1);
			mpo.setDescription("@jyhhzt_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@jyhthz_failed", e);
			mpo.setState(0);			
		}
		return null;
	}

}
