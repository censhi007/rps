package com.webcqs.svs.inf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Date;

public abstract class WI implements java.io.Serializable,Cloneable{	
	private static final long serialVersionUID = 1L;

	public WI clone(){
		try {
			return (WI)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	/**
	 * 灏嗗璞¤浆鍖栦负JSON瀛楃涓�
	 * @return
	 */
	public abstract String toJSON();
	public abstract void fromJSON(String json);
	protected StringBuffer append(StringBuffer sb,String name,Object k){
		sb.trimToSize();
		int s =sb.length();
		if(s>0){
			char c = sb.charAt(s-1);
			if(c!='{'&&c!='['&&c!='('){
				sb.append(",");
			}
		}
		sb.append(name).append(":");
		append(sb,k);
		return sb;		
	}
	
	@SuppressWarnings("rawtypes")
	protected StringBuffer append(StringBuffer sb,Object k){
		if(k==null){
			sb.append("null");
			return sb;
		}
		if(k instanceof String){
			sb.append("'").append(((String) k).replaceAll("'","\\\\'")).append("'");
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
			sb.append("").append(((Date)k).getTime());
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
		return sb;
	}
	
	public abstract void writeTo(OutputStream os)throws IOException;
	public abstract void loadFrom(InputStream is)throws IOException;
}
