package com.webcqs.common;

import java.util.Collection;
import java.util.List;


public interface DBI {
	@SuppressWarnings("rawtypes")
	public List fetchHQL(String hql);
	@SuppressWarnings("rawtypes")
	public List fetchSQL(String Sql);
	@SuppressWarnings("rawtypes")
	public List fetchQuery(QueryObject qo);
	/**
	 * 执行uci
	 * @param uci
	 * @return
	 */
	public Object invoke(UCI uci);
	/**
	 * 生成dwdm过滤字段
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public Object buildDwdmLike(String arg0, Collection<Object> arg1);
	/**
	 * dwdm限定条件
	 * @param arg0
	 * @return
	 */
	public Object dwdmlimit(String arg0);
}
