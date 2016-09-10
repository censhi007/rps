package com.web.cq.svs.imp;

import java.util.Collection;
import java.util.List;

import com.web.svs.imp.DBO;
import com.web.svs.imp.SQLFactory;
import com.webcqs.svs.inf.WI;
import com.webcqs.common.QueryObject;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;

public class Orgz implements Ti{

	@SuppressWarnings("unchecked")
	@Override
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		try{
			QueryObject qo =new QueryObject("select o from WOrgz o where 1=1 ");
			String dwdm = param.getDwdm();
			Object dw = SQLFactory.dwdmLimit(dwdm);
			if(dw!=null){
				if(dw instanceof String){
					qo.append(" and o.dwdm like :dwdm ").addParam("dwdm",dw+"%");					
				}else{
					String s=SQLFactory.buildDwdmLike(" o.dwdm",(Collection<Object>)dw);
					if(s!=null){
						qo.append(" and ").append(s);
					}
				}
			}
			DBO db = (DBO)Config.getBean("dbi");
			List<WI> oL = db.fetchQuery(qo);
			mpo.setData(oL);
			mpo.setState(1);
			mpo.setDescription("@orgz_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@orgz_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
