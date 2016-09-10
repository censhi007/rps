package com.web.svs.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.transform.Transformers;

import com.webcqs.common.DBI;
import com.webcqs.common.QueryObject;
import com.webcqs.spring.Config;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
import com.webcqs.svs.model.Wto;
/**
 * 查询需要删除的数据<br/>
 * 只在SQLserver中获取数据
 * @author BUJUN
 *
 */
public class ToDSi_SQLServer implements Ti{

	@SuppressWarnings("unchecked")
	@Override
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		Object tbn = param.get("tbname");
		if(tbn==null || "".equals(tbn)){
			tbn = SQLFactory.def_table_name;
		}
		
		try{
			DBI db = (DBI)Config.getBean("dbi");
			QueryObject no = QueryObject.getSQLQuery("select name from [databaselog]..sysobjects where xtype='u' and name like :tbn order by name");
			no.addParam("tbn","%_"+tbn);
			List<String> tbns= db.fetchQuery(no);
			for(String name : tbns){
				QueryObject qo =QueryObject.getSQLQuery("select * from [databaselog]..");
				qo.append(name).append(" where 1=1  ");				
				String dwdm = param.getDwdm();
				Object dw = SQLFactory.dwdmLimit(dwdm);
				if(dw!=null){
					if(dw instanceof String){
						qo.append(" and _sql_post_dwdm like :dwdm ").addParam("dwdm",dw+"%");					
					}else{
						String s=SQLFactory.buildDwdmLike(" _sql_post_dwdm",(Collection<Object>)dw);
						if(s!=null){
							qo.append(" and ").append(s);
						}
					}
				}

				Object qxq = param.getDate("ltimeq");
				if(qxq!=null && qxq instanceof Date){
					qo.append(" and _sql_post_lasttime >= :ltimeq ").addParam("ltimeq", qxq);
				}
				qxq = param.getDate("ltimez");
				if(qxq!=null && qxq instanceof Date){
					qo.append(" and _sql_post_lasttime < :ltimez ").addParam("ltimez", qxq);
				}
				Object state = param.get("state");
				Short sta = 1;
				try{
					sta = Short.parseShort(state+"");
				}catch(Exception e){}
				qo.append(" and _sql_post_sta = :sta ").addParam("sta",sta);
				
				qo.append(" order by _sql_post_lasttime ").setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				
				List<Map<String,Object>> hL = db.fetchQuery(qo);
				Iterator<Map<String,Object>> it = hL.iterator();
				Wto wi;
				List<String>cs=null;
				while(it.hasNext()){
					Map<String,Object> fb = it.next();
					if(cs==null){
						Set<String> ks = fb.keySet();
						if(ks==null)break;
						cs = new ArrayList<String>();
						for(String k :ks){
							if(k!=null && !k.startsWith("_sql_post_")){
								cs.add(k);
							}
						}
					}
					wi = new Wto();
					for(String k :cs){
						wi.put(k.toLowerCase(), fb.get(k));
					}
					mpo.addData(wi);
					wi = null;
					it.remove();
				}
			}
			
			mpo.setState(1);
			mpo.setDescription("@todel_suc");
			return mpo;
		}catch(Exception e){
			e.printStackTrace();
			mpo.setDescription("@todel_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}

}
