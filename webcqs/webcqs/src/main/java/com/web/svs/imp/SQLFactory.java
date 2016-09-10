package com.web.svs.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 根据数据库获取相应的sql
 * @author BUJUN
 *
 */
public class SQLFactory {
	
	public static final int MYSQL=0;
	public static final int MSSQL=1;
	public static final int POSTGRES=2;
	public static String[] limit_dwdms;
	public static boolean limit_turnon=false;
	public static int dbtype = 0;
	/**
	 * 检查表是否存在
	 */
	public static final int _checkTable=1;
	public static String getSQL(final int i){
		switch(i){
		case _checkTable:
			return ckTable();
		}
		return null;
	}
	/**
	 * 生成对应的table 检查字段。其中表名使用@tableName代替
	 * @return
	 */
	private static String ckTable(){
		switch(dbtype){
		case MYSQL:
			return "show tables like '@tableName'";
		case MSSQL:
			return "select object_id('@tableName')";
		case POSTGRES:
			return "select table_schema from information_schema.tables where table_name='@tableName'";
		}
		return null;
	}
	
	public static Object dwdmLimit(String dwdm){
		if(!limit_turnon || limit_dwdms==null || limit_dwdms.length==0){
			return dwdm;
		}
		dwdm=dwdm==null?"":dwdm;
		List<String> res=new ArrayList<String>(); 
		//if(dwdm instanceof String){
		for(String s:limit_dwdms){
			String t=(String)dwdm;
			if(s.length()>t.length()){
				if( startWith(s,t))
					res.add(s);
			}else if(startWith(t,s)){
				return dwdm;
			}
		}			
		if(res.size()<1)res.add("-----------绝对不匹配");
		return res;
		//}
		/*int last=limit_dwdms.length-1;
		for(String s:(Collection<String>)dwdm){
			
			for(int i=0;i<=last;){
				String ts=limit_dwdms[i];
				if(ts.length()>s.length()){
					if(startWith(ts,s)){//30101001,30101
						limit_dwdms[i]=limit_dwdms[last];//已符合条件的单位不再进行匹配
						limit_dwdms[last]=ts;
						res.add(ts);
						last--;
						continue;//因为将最大值减了1，所以不增加本次的---
					}
					i++;
				}else if(startWith(s,ts)){//跳出循环
					res.add(s);
					break;
				}
			}
		}
		if(res.size()<1)res.add("---------绝对不匹配");
		return res;*/
	}
	private static Boolean startWith(String str1,String str2){
		return str1.startsWith(str2);
	}
	public static String buildDwdmLike(String prefix,Collection<Object> l){
		if(l==null ||l.size()<1)return null;
		Boolean init=true;
		StringBuffer sb=new StringBuffer("(");
		for(Object o:l){
			if(init){
				sb.append(prefix).append(" like '").append(o).append("%' ");
				init=false;
			}else{
				sb.append(" or ").append(prefix).append(" like '").append(o).append("%' ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
	
	public static String def_table_name="ht_jyht";
}
