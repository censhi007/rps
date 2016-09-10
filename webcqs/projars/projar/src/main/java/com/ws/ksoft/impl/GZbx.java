package com.ws.ksoft.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
 * 获取指标项
 * @author BUJUN
 *
 */
public class GZbx implements Ti{
	private static final String PREFIX_ND_MSS ="njsys_";
	@SuppressWarnings("unchecked")
	public IPMO fetch(IParam param) {
		MPO mpo = MPO.copyFrom(param);
		try{
			DBI db = (DBI)Config.getBean("dbi");
			Object o = db.invoke(new UCI(){
				@Override
				public Object useConnection(Connection cnn) {
					try {
						ResultSet rs=cnn.getMetaData().getCatalogs();
						int st=PREFIX_ND_MSS.length();
						int length=st+4;
						while (rs.next()) {
							String n=rs.getString(1);
							if(n.toLowerCase().startsWith(PREFIX_ND_MSS) && length==n.length()){
								try{
									Integer.parseInt(n.substring(st));
									return n;
								}catch(Exception ie){
									continue;
								}
							}

						}
						
					} catch (SQLException e) {
						e.printStackTrace();
						return null;
					}
					return null;
				}
				
			});
			if(o==null){
				throw new Exception("没有农经库");
			}
			QueryObject qo = QueryObject.getSQLQuery();
			qo.append("select z.kjnd,z.reportcode,z.col,z.name,z.code_by,z.code_lj,z.param,z.datacheck from [")
			  .append(o.toString()).append("]..report_zb z where 1=1 ");
			Object rc = param.get("reportcode");
			if(rc!=null && !"".equals(rc)){
				if(rc instanceof String ){
					String [] cs = ((String) rc).split(",");
					List<String> sl = new ArrayList<String>();
					for(String s:cs){
						if(s!=null && !"".equals(s))sl.add(s);
					}
					rc = sl;
				}
				qo.append(" and z.reportcode  in (:rc) ").addParam("rc",rc);
			}
			
			qo.append(" order by z.kjnd,z.reportcode,z.col");
			List<Object[]> fL = db.fetchQuery(qo);
			Wto to;
			for(Object[] ps :fL){
				to = new Wto();
				to.put("ndbs",ps[0]);
				to.put("reportcode",ps[1]);
				to.put("zbx",ps[2]);
				to.put("title",ps[3]);
				to.put("zbdm1",ps[4]);
				to.put("zbdm2",ps[5]);
				to.put("param",ps[6]);
				to.put("datacheck",ps[7]);
				
				mpo.addData(to);
			}
			mpo.setState(1);
			mpo.setDescription("@ksoft_zbx_suc");
			return mpo;
		}catch(Throwable e){
			e.printStackTrace();
			mpo.setDescription("@ksoft_zbx_failed", e);
			mpo.setState(0);
			return mpo;
		}
	}
}
