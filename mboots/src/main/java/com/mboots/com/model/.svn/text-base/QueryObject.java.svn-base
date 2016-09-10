package com.mboots.com.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.mboots.com.inf.Qi;


public class QueryObject implements Cloneable,java.io.Serializable,Qi{	
	private static final long serialVersionUID = -3724766321919715398L;
	private StringBuffer sql=null;
	private HibernateDaoSupport dao=null;
	private Map<String,Object>ParaMap=null;
	private Map<Integer,Object>posMap=null;
	private Boolean sqlQuery;
	private Integer max=-1;
	private Integer first=-1;
	private Integer fetchsize=-1;
	private ResultTransformer form=null;
	public static final String ROW_COUNT = "select count(*) ";
	public static final String FROM = "from";
	public static final String DISTINCT = "distinct";
	public static final String HQL_FETCH = "fetch";
	public static final String ORDER_BY = "order";
	/**
	 * 一个HQL查询对象
	 */
	public QueryObject(){
		this(null,null);
	}
	/**
	 * 获取一个HQL查询对象
	 * @param dao
	 */
	public QueryObject(HibernateDaoSupport dao){
		this(null,dao);
	}
	/**
	 * 获取一个HQL查询对象
	 * @param sql
	 */
	public QueryObject(String sql){
		this(sql,null);
	}
	/**
	 * 生成Hql查询对象
	 * @param sql
	 * @param session
	 */
	public QueryObject(String sql,HibernateDaoSupport dao){
		this.sql=new StringBuffer(sql==null?"":sql);
		this.dao=dao;
		ParaMap=new HashMap<String,Object>();
		sqlQuery=false;
		posMap=new HashMap<Integer,Object>();
	}
	/**
	 * 通过传入的dao，获取一个sql查询对象
	 * @param dao
	 * @return
	 */
	public static QueryObject getSQLQuery(HibernateDaoSupport dao){
		return getSQLQuery(null,dao);
	}
	/**
	 * 获取SQLQuery对象
	 * @param sql
	 * @param session
	 * @return
	 */
	public static QueryObject getSQLQuery(String sql,HibernateDaoSupport dao){
		QueryObject qo=new QueryObject(sql,dao);
		qo.sqlQuery=true;
		return qo;
	}
	public static QueryObject getSQLQuery(String sql){
		return getSQLQuery(sql,null);
	}
	public static QueryObject getSQLQuery(){
		return getSQLQuery(null,null);
	}
	
	public QueryObject append(String sql){
		this.sql.append(sql);
		return this;
	}
	
	public QueryObject setSql(String sql){
		this.sql=new StringBuffer(sql);
		return this;
	}
	
	public QueryObject setSql(StringBuffer sql){
		this.sql=sql;
		return this;
	}

	public QueryObject addParam(String str,Object obj){
		this.ParaMap.put(str, obj);
		return this;
	}
	public QueryObject setParameter(String str,Object obj){
		this.ParaMap.put(str, obj);
		return this;
	}
	/**
	 * 请注意，不支持集合对象。
	 * @param pos
	 * @return
	 */
	public QueryObject setParameter(Integer pos,Object obj){
		this.posMap.put(pos, obj);
		return this;
	}
	/**
	 * 设置最大的结果位置<br/>
	 * 从1开始。小于1时表示不限制
	 * @param Max
	 * @return
	 */
	public QueryObject setMaxResult(Integer Max){
		this.max=Max;
		return this;
	}
	/**
	 * 设置第一个结果位置<br/>
	 * 下标从1开始,小于1时取默认。
	 * @param first
	 * @return
	 */
	public QueryObject setFirstResult(Integer first){
		this.first=first;
		return this;
	}
	public QueryObject setFetchSize(Integer fetch){
		this.fetchsize=fetch;
		return this;
	}
	/**
	 * 执行更新操作.<br/>
	 * (更新，删除)
	 * @return
	 */
	public int excuteUpdate(){
		return call(new tquery(tquery.update));
	}
	/**
	 * 获取唯一的结果，当不是唯一结果时抛出异常
	 * @return
	 */
	public <T> T uniqueResult(){
		return call(new tquery(tquery.uniq));
	}
	/**
	 * 获取查选的结果列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List list(){
		return call(new tquery(tquery.list));
	}
	
	@SuppressWarnings("unchecked")
	private <T> T call(tquery query){
		if(dao==null){
			throw new RuntimeException("执行查询语句需要传递dao参数");
		}
		return (T)dao.getHibernateTemplate().execute(query);
	}
	/**
	 * 设置取数限制
	 */
	private void setLimit(Query qo){
		if(max>0)
		qo.setMaxResults(max);
		if(first>0)
			qo.setFirstResult(first);
		if(fetchsize>0)
			qo.setFetchSize(fetchsize);
		
		if(form!=null)qo.setResultTransformer(form);
	}
	@SuppressWarnings("rawtypes")
	private void setParameter(Query qo){
		if(!this.ParaMap.isEmpty()){
			for(String key:this.ParaMap.keySet()){
				Object o=this.ParaMap.get(key);
				if(o instanceof java.util.Collection){
					qo.setParameterList(key, (Collection)o);
				}else if(o instanceof Object[]){
					qo.setParameterList(key, (Object[])o);
				}else{
					qo.setParameter(key, o);
				}
			}
		}
		
		if(!this.posMap.isEmpty()){
			for(Integer i:this.posMap.keySet()){
				Object o=this.posMap.get(i);
				qo.setParameter(i, o);
			}
		}
	}

	
	public QueryObject union(QueryObject o){
		QueryObject ro=this.clone();
		ro.sql.append(" union all ").append(o.sql);
		ro.fetchsize=ro.fetchsize<o.fetchsize?o.fetchsize:ro.fetchsize;
		ro.first=ro.first<o.first?ro.first:o.first;		
		ro.max=ro.max>o.max?ro.max:o.max;
		
		ro.form=ro.form==null?o.form:ro.form;
		
		for(String key:o.ParaMap.keySet()){
			if(!ro.ParaMap.containsKey(key))
				ro.ParaMap.put(key,o.ParaMap.get(key));
		}
		
		for(Integer key:o.posMap.keySet()){
			if(!ro.posMap.containsKey(key))
				ro.posMap.put(key, o.posMap.get(key));
		}
		return ro;
	}
	
	public QueryObject unionSelect(QueryObject o){
		QueryObject qo=this.union(o);
		qo.sql.insert(0, "select * from (");
		return qo.append(") as _nT");
	}
	public QueryObject clone(){
		QueryObject o=null;
		try {
			o=(QueryObject) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public QueryObject setResultTransformer(ResultTransformer form){
		this.form=form;
		return this;
	}
	
	/**
	 * 获得原始hql语句
	 * 
	 * @return
	 */
	public String getOrigHql() {
		return sql.toString();
	}

	/**
	 * 获得查询数据库记录数的hql语句。
	 * 
	 * @return
	 */
	public String getRowCountHql() {
		String hql = sql.toString();

		int fromIndex = hql.toLowerCase().indexOf(FROM);
		String projectionHql = hql.substring(0, fromIndex);

		hql = hql.substring(fromIndex);
		String rowCountHql = hql.replace(HQL_FETCH, "");

		int index = rowCountHql.indexOf(ORDER_BY);
		if (index > 0) {
			rowCountHql = rowCountHql.substring(0, index);
		}
		return wrapProjection(projectionHql) + rowCountHql;
	}
	
	private String wrapProjection(String projection) {
		if (projection.indexOf("select") == -1) {
			return ROW_COUNT;
		} else {
			return projection.replace("select", "select count(") + ") ";
		}
	}
	
	/**
	 * 执行无返回的sql脚本。如create table
	 */
	public void excute(){
		dao.getHibernateTemplate().execute(new HibernateCallback<Object>(){
			public Object doInHibernate(Session arg0) throws HibernateException {
				arg0.doWork(new twork(sql.toString()));
				return null;
			}
			
		});
	}	
	
	public void clear(){
		sql=new StringBuffer();
		ParaMap.clear();
		posMap.clear();
		max=-1;
		first=-1;
		fetchsize=-1;
		form=null;
	}
	
	/**
	 * 执行本地方法
	 * @author BUJUN
	 *
	 */
	private final class tquery implements HibernateCallback<Object>{
		static final int uniq = 0;
		static final int list = 1;
		static final int update = 2;
		int method = 1;
		tquery(int m){
			method = m;
		}
		Query qo;
		public Object doInHibernate(Session session) throws HibernateException {
			if(sqlQuery){
				qo = session.createSQLQuery(sql.toString());
			}else{
				qo = session.createQuery(sql.toString());
			}
			setParameter(qo);
			setLimit(qo);
			
			switch(method){
			case uniq:
				return qo.uniqueResult();
			case update:
				return qo.executeUpdate();
			case list:
				return qo.list();
			}
			return null;
		}
		
	}
	/**
	 * 执行无返回sql
	 * @author BUJUN
	 *
	 */
	private static final class twork implements Work{
		String sql;
		twork(String sql){
			this.sql=sql;
		}
		public void execute(Connection connection) throws SQLException {			
			connection.createStatement().execute(sql);
		}
		
	}
	
	public void setDao(HibernateDaoSupport spt){
		this.dao = spt;
	}
}
