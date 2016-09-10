package com.mboots.com.util;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.mboots.com.inf.Doi;
import com.mboots.com.inf.Qi;
import com.mboots.com.model.QueryObject;

public class BDao extends HibernateDaoSupport implements Doi{
	
	public List<?> find(String hql,Object...values) {
		return this.getHibernateTemplate().find(hql, values);		
	}

	public List<?> find(Qi qi) {
		qi.setDao(this);
		return qi.list();
	}

	public QueryObject createQuery() {
		return new QueryObject(this);
	}

	public QueryObject createSQLQuery() {
		return QueryObject.getSQLQuery(this);
	}

	public QueryObject createQuery(String sql) {		
		return new QueryObject(sql,this);
	}

	public QueryObject createSQLQuery(String sql) {
		return QueryObject.getSQLQuery(sql, this);
	}

	@SuppressWarnings("unchecked")
	public <T> T save(T t) {
		return (T)this.getHibernateTemplate().save(t);
	}

	public <T> T update(T t) {
		this.getHibernateTemplate().update(t);
		return t;
	}

	public <T> T delete(T t) {
		this.getHibernateTemplate().delete(t);
		return t;
	}

	/**
	 * 根据id返回对应的对象
	 * @param cls
	 * @param id
	 * @return
	 */
	public <T> T findById(Class<T> cls,String id){
		return this.getHibernateTemplate().get(cls, id);
	}
	
	
}
