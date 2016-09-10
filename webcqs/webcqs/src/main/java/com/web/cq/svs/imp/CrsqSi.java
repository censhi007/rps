package com.web.cq.svs.imp;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.web.cq.svs.model.CqjyJyxxfb;
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
 * 获取出让申请
 * @author BUJUN
 *
 */
public class CrsqSi implements Ti{

	@SuppressWarnings("unchecked")
	@Override
	public IPMO fetch(IParam param) {
		//DBO db = (DBO)Config.getBean("DBO");
		MPO mpo = MPO.copyFrom(param);
		try{
			
			QueryObject qo =new QueryObject("select fb from CqjyJyxxfb fb inner join fetch ");
			qo.append("fb.cr as c where 1=1 ");
			String dwdm = param.getDwdm();
			Object dw = SQLFactory.dwdmLimit(dwdm);
			if(dw!=null){
				if(dw instanceof String){
					qo.append(" and c.dwdm like :dwdm ").addParam("dwdm",dw+"%");					
				}else{
					String s=SQLFactory.buildDwdmLike(" c.dwdm",(Collection<Object>)dw);
					if(s!=null){
						qo.append(" and ").append(s);
					}
				}
			}
			Date qs = param.getQsrq();
			if(qs!=null){
				qo.append(" and fb.ggqx_q >= :qs ").addParam("qs", qs);
			}
			Date zz = param.getZzrq();
			if(zz!=null){
				qo.append(" and fb.ggqx_q < :zz ").addParam("zz", zz);
			}
			Object lx = param.get("lx");
			if(lx!=null ){
				if(lx instanceof Number){
					qo.append(" and c.jyxmfl = "+lx);
				}else if(lx instanceof String){
					try{
						Integer.parseInt((String)lx);
						qo.append(" and c.jyxmfl="+lx);
					}catch(Exception e){}
				}
			}
			Object xmbh = param.get("xmbh");
			if(xmbh!=null){
				qo.append(" and c.xmbh like :xmbh ").addParam("xmbh", xmbh+"%");
			}
			Object crfs = param.get("crfs");
			if(crfs!=null){
				int fs = 3;
				if(crfs instanceof Number){
					fs = ((Number)crfs).intValue();
					fs = fs>3||fs <1 ? 3 : fs;
				}
				qo.append(" and c.crfs = :fs ").addParam("fs",fs);
			}
			DBO db = (DBO)Config.getBean("dbi");
			List<CqjyJyxxfb> fL = db.fetchQuery(qo);
			Iterator<CqjyJyxxfb> it = fL.iterator();
			WI wi;
			
			while(it.hasNext()){
				CqjyJyxxfb fb = it.next();
				wi = fb.toWI();
				mpo.addData(wi);
				wi = null;
				it.remove();
				//it.remove(fb);
			}
			mpo.setState(1);
			mpo.setDescription("@jyxm_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@jyxm_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
