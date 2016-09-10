package com.mboots.com.inf;

import java.util.List;

public interface Doi {
	/**
	 * 根据id返回cls类型的对象
	 * @param cls
	 * @param id
	 * @return
	 */
	<T> T findById(Class<T> cls,String id);
	/**
	 * 查询hql语句，其中参数使用?代替，并按顺序传入函数
	 * @param hql
	 * @param values
	 * @return
	 */
	List<?> find(String hql,Object...values);
	/**
	 * 查询Qi
	 * @param qi
	 * @return
	 */
	List<?> find(Qi qi);
	/**
	 * 创建Qi,并返回一个可以独立执行的query对象
	 * @return
	 */
	Qi createQuery();
	/**
	 * 创建一个执native sql 的Qi,并返回一个可以独立执行的query对象
	 * @return
	 */
	Qi createSQLQuery();
	/**
	 * 根据hql创建一个执行hql的Qi
	 * @param sql
	 * @return
	 */
	Qi createQuery(String hql);
	/**
	 * 创建一个执行sql的native sql query对象
	 * @param sql
	 * @return
	 */
	Qi createSQLQuery(String sql);
	/**
	 * 插入t
	 * @param t
	 * @return
	 */
	<T> T save(T t);
	/**
	 * 更新t
	 * @param t
	 * @return
	 */
	<T> T update(T t);
	/**
	 * 删除t
	 * @param t
	 * @return
	 */
	<T> T delete(T t);
}
