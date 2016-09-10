package com.web.cq.svs.imp;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.web.cq.svs.model.CqjyHt;
import com.web.svs.imp.DBO;
import com.web.svs.imp.SQLFactory;
import com.webcqs.common.QueryObject;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.inf.WI;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
/**
 * 成交合同
 * @author BUJUN
 *
 */
public class CjggSi implements Ti{

	@SuppressWarnings("unchecked")
	@Override
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		try{
			
			QueryObject qo =new QueryObject("select zb from CqjyHt zb  where 1=1 ");
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
				qo.append(" and zb.cjrq >= :qs ").addParam("qs", qs);
			}
			Date zz = param.getZzrq();
			if(zz!=null){
				qo.append(" and zb.cjrq < :zz ").addParam("zz", zz);
			}
			Object xmbh = param.get("xmbh");
			if(xmbh!=null){
				qo.append(" and c.xmbh like :xmbh ").addParam("xmbh", xmbh+"%");
			}
			qo.append(" order by zb.cjrq desc,xmlx,zb.dwdm,zb.lrrq desc");
			DBO db = (DBO)Config.getBean("dbi");
			List<CqjyHt> fL = db.fetchQuery(qo);
			Iterator<CqjyHt> it = fL.iterator();
			WI wi;
			
			while(it.hasNext()){
				CqjyHt fb = it.next();
				wi = fb.toWI();
				mpo.addData(wi);
				wi = null;
				it.remove();
			}
			mpo.setState(1);
			mpo.setDescription("@cjgg_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@cjgg_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
