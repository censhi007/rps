package com.web.jyht.svs.imp;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.web.jyht.svs.model.OrgTree;
import com.webcqs.common.DBI;
import com.webcqs.common.QueryObject;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.inf.WI;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
/**
 * 获取组织结构树
 * @author BUJUN
 *
 */
public class OTreeSi implements Ti{

	@SuppressWarnings("unchecked")
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		try{
			DBI db = (DBI)Config.getBean("dbi");
			QueryObject qo =new QueryObject().append("select o from OrgTree o where 1=1 ");			
			String dwdm = param.getDwdm();
			Object dw = db.dwdmlimit(dwdm);
			if(dw!=null){
				if(dw instanceof String){
					qo.append(" and o.dwdm like :dwdm ").addParam("dwdm",dw+"%");					
				}else{
					Object s=db.buildDwdmLike(" o.dwdm",(Collection<Object>)dw);
					if(s!=null){
						qo.append(" and ").append(s.toString());
					}
				}
			}
			Object id = param.get("id");
			if(id!=null){
				qo.append(" and id like :id ").addParam("id",id+"%");
			}
			Object parent =param.get("parentId");
			if(parent!=null && !"".equals(parent)){
				qo.append(" and parentId=:parent ").addParam("parent",parent);
			}			
			qo.append(" order by o.id ");
			List<OrgTree> hL = db.fetchQuery(qo);
			Iterator<OrgTree> it = hL.iterator();
			WI wi;
			while(it.hasNext()){
				OrgTree fb = it.next();
				wi = fb.toWI();
				mpo.addData(wi);
				wi = null;
				it.remove();
			}
			mpo.setState(1);
			mpo.setDescription("@otree_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@otree_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
