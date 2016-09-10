package com.webcqs.svs.inf;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.webcqs.common.DateUtil;

public abstract class PI extends WI{
	
	private static final long serialVersionUID = -57927229228889112L;

	@SuppressWarnings("rawtypes")
	protected StringBuffer append(StringBuffer sb,Object k){
		if(k==null){
			sb.append("null");
			return sb;
		}
		if(k instanceof String){
			sb.append("'").append(k).append("'");
			return sb;
		}
		if(k instanceof Long || k instanceof Integer || 
		   k instanceof Short||k instanceof Boolean){
			sb.append("").append(k);
			return sb;
		}
		if(k instanceof Number){
			sb.append("").append(((Number)k).doubleValue());
			return sb;
		}
		if(k instanceof Date){
			sb.append("'").append(DateUtil.getDateStr(DateUtil.formart10, (Date)k)).append("'");
			return sb;
		}
		if(k instanceof WI){
			sb.append("").append(((WI)k).toJSON());
			return sb;
		}
		if(k instanceof Collection){
			sb.append("[");
			int i=0;
			for(Object o:(Collection)k){
				if(i==0){
					append(sb,o);					
				}else{
					sb.append(",");
					append(sb,o);	
				}
				i++;
			}
			sb.append("]");
		}
		if(k instanceof Map){
			Map xm = (Map)k;
			sb.append("{");
			for(Object ks : xm.keySet()){
				if(ks==null)continue;
				append(sb,ks.toString(),xm.get(ks));
			}
			sb.append("}");
		}
		return sb;
	}
	
	public WI toWI(){
		return null;
	}
}
