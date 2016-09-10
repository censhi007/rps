package com.webcqs.common;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
/**
 * 自定义的查询对象<br/>
 * 不支持存储过程
 * @author BJ
 * @version 2.1 添加一个获取当前query是执行数据库修改操作还是数据库查询的功能<br/> 
 */
@SuppressWarnings("serial")
public class QueryObject implements Cloneable,java.io.Serializable{
	private StringBuffer sql=null;
	private Session session=null;
	private Map<String,Object>ParaMap=null;
	private Map<Integer,Object>posMap=null;
	private Query qo=null;
	private Boolean sqlQuery;
	private Integer max=-1;
	private Integer first=-1;
	private Integer fetchsize=-1;
	private ResultTransformer form=null;
	
	private Integer operation=null;//0:查询,1:修改,2:save
	/**
	 * 一个HQL查询对象
	 */
	public QueryObject(){
		this(null,null);
	}
	/**
	 * 获取一个HQL查询对象
	 * @param session
	 */
	public QueryObject(Session session){
		this(null,session);
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
	public QueryObject(String sql,Session session){
		this.sql=new StringBuffer(sql==null?"":sql);
		this.session=session;
		ParaMap=new HashMap<String,Object>();
		sqlQuery=false;
		posMap=new HashMap<Integer,Object>();
	}
	/**
	 * 通过传入的session，获取一个sql查询对象
	 * @param session
	 * @return
	 */
	public static QueryObject getSQLQuery(Session session){
		return getSQLQuery(null,session);
	}
	/**
	 * 获取SQLQuery对象
	 * @param sql
	 * @param session
	 * @return
	 */
	public static QueryObject getSQLQuery(String sql,Session session){
		QueryObject qo=new QueryObject(sql,session);
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
		createQuery();		
		return qo.executeUpdate();
	}
	/**
	 * 获取唯一的结果，当不是唯一结果时抛出异常
	 * @return
	 */
	public Object uniqueResult(){
		createQuery();	
		return qo.uniqueResult();
	}
	/**
	 * 获取查选的结果列表
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List list(){
		createQuery();	
		return qo.list();
	}
	/**
	 * 设置取数限制
	 */
	private void setLimit(){
		if(max>0)
		qo.setMaxResults(max);
		if(first>0)
			qo.setFirstResult(first);
		if(fetchsize>0)
			qo.setFetchSize(fetchsize);
		
		if(form!=null)qo.setResultTransformer(form);
	}
	@SuppressWarnings("rawtypes")
	private void setParameter(){
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
	/**
	 * 构造真正的query对象
	 */
	private void createQuery(){
		if(session!=null && session.isOpen()){			
			if(this.sqlQuery){
				this.qo=session.createSQLQuery(this.sql.toString());
			}else{
				this.qo=session.createQuery(this.sql.toString());
			}
			setParameter();
			setLimit();
		}else{
			throw new RuntimeException("没有能使用的session，请先传入可用session！");
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
	 * 分析当前sql
	 * @return
	 */
	public Integer getOperation() {
		if(operation==null){
			//去掉sql前面的空格以及左括号后，查看sql以什么字符串开头。//如果是insert into ,delete,update 等字符串，那么是修改操作
			if(sql==null || sql.length()<1){
				if(obj!=null)return 2;//save
				return -1;//还没有传入sql
			}								
			Pattern p=Pattern.compile("^\\s*\\(*\\s*(?:insert|delete|update|set|use)\\s+");
			Matcher m=p.matcher(sql.toString());
			
			operation = m.find() ? 1 :0;
		}
		return operation;
	}
	private Object obj;
	public QueryObject setObj(Object obj){
		this.obj=obj;
		return this;
	}
	public Object getObj(Object obj){
		return this.obj;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public Msg save(){
		Msg msg=new Msg();
		if(session!=null && session.isOpen()){
			try{
				int i=1;
				if(obj instanceof java.util.Collection){
					for(Object oi : (java.util.Collection<Object>)obj){
						session.saveOrUpdate(oi);
						if(i++ %20 == 0 ){
							session.flush();
							session.clear();
						}
					}
				}else if(obj instanceof Object[]){
					for(Object oi :(Object[])obj){
						session.saveOrUpdate(oi);
						if(i++ %20 == 0 ){
							session.flush();
							session.clear();
						}
					}
				}else session.saveOrUpdate(obj);
				session.flush();
					msg.setBvalue(true);
				}catch(RuntimeException re){
					msg.getErrs().add("保存"+obj.getClass()+"对象失败");
					throw re;
				}
		}
		return msg;
	}
	
	@SuppressWarnings("deprecation")
	public void excute(){
		try {
			session.connection().createStatement().execute(sql.toString());
		} catch (HibernateException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void clear(){
		sql=new StringBuffer();
		ParaMap.clear();
		posMap.clear();
		max=-1;
		first=-1;
		fetchsize=-1;
		form=null;
		qo=null;
	}
}
