package com.web.cq.svs.imp;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.webcqs.svs.inf.WI;
import com.web.cq.svs.model.CqjyZbgs;
import com.web.svs.imp.DBO;
import com.web.svs.imp.SQLFactory;
import com.webcqs.common.QueryObject;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;

public class ZbgsSi implements Ti{

	@SuppressWarnings("unchecked")
	@Override
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		try{
			
			QueryObject qo =new QueryObject("select zb from CqjyZbgs zb inner join fetch zb.cr as c ")
			.append("left join fetch zb.sr as s left join fetch s.fb as f  where f.crsqid=c.id ");
			String dwdm = param.getDwdm();
			Object dw = SQLFactory.dwdmLimit(dwdm);
			if(dw!=null){
				if(dw instanceof String){
					qo.append(" and zb.dwdm like :dwdm ").addParam("dwdm",dw+"%");					
				}else{
					String s=SQLFactory.buildDwdmLike(" zb.dwdm",(Collection<Object>)dw);
					if(s!=null){
						qo.append(" and ").append(s);
					}
				}
			}
			Date qs = param.getQsrq();
			if(qs!=null){
				qo.append(" and zb.gsrq >= :qs ").addParam("qs", qs);
			}
			Date zz = param.getZzrq();
			if(zz!=null){
				qo.append(" and zb.gsrq < :zz ").addParam("zz", zz);
			}
			
			qo.append(" order by zb.pzsj desc,c.jyxmfl,c.jyzxdwdm,zb.dwdm ");
			DBO db = (DBO)Config.getBean("dbi");
			List<CqjyZbgs> fL = db.fetchQuery(qo);
			Iterator<CqjyZbgs> it = fL.iterator();
			WI wi;
			
			while(it.hasNext()){
				CqjyZbgs fb = it.next();
				wi = fb.toWI();
				mpo.addData(wi);
				wi = null;
				it.remove();
			}
			mpo.setState(1);
			mpo.setDescription("@zbgs_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@zbgs_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
