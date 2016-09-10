package com.web.svs.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.weather.inf.WI;
import com.webcqs.common.DBI;
import com.webcqs.common.QueryObject;
import com.webcqs.common.UCI;
import com.webcqs.svs.inf.CIp;
import com.webcqs.svs.model.Webservicelog;

public class DBO extends HibernateDaoSupport implements CIp,DBI{
	private static final String IP_DB="ipconfig";
	private static final String LG_DB="_webservicelog";
	/**
	 * 通过HQL查询
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WI> fetchHQL(String hql){
		Session s = this.getSession(true);
		try{
			List<WI> wL = s.createQuery(hql).list();
			return wL;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	/**
	 * 通过SQL查询
	 * @param Sql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List fetchSQL(String Sql){
		Session s = this.getSession(true);
		try{
			List wL = s.createSQLQuery(Sql).list();
			return wL;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public List fetchQuery(QueryObject qo){
		Session s = this.getSession(true);
		try{
			qo.setSession(s);
			List wL = qo.list();
			return wL;
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	/**
	 * 添加ipconfig<br/>
	 * id,ip,permit
	 * @param s
	 */
	@SuppressWarnings("deprecation")
	private void preConfig(){
		Session s=null;
		Transaction tran=null;
		try{
			s = this.getSession(true);
			Connection con = s.connection();
			String sql = SQLFactory.getSQL(SQLFactory._checkTable);
			sql=sql.replaceFirst("@tableName", IP_DB);
			PreparedStatement tp = con.prepareStatement(sql);
			ResultSet res = tp.executeQuery();
			Object o = null;
			if(res.next()){
				o = res.getObject(1);
			}
			res.close();
			tp.close();
			s.close();
			s = getSession(true);
			QueryObject qo = QueryObject.getSQLQuery(s);
			if(o == null){
				tran = s.beginTransaction();
				qo.clear();
				qo.append("CREATE TABLE "+IP_DB+" (id varchar(32),ip varchar(32),permit smallint " +
						"DEFAULT '0',PRIMARY KEY (id))");
				qo.excute();
				qo.clear();
				qo.append("create index i_ip_ip on "+IP_DB+" (ip)").excute();
				s.flush();
				s.clear();
				tran.commit();
			}
		}catch(Exception e){
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			if(s!=null)s.close();
		}		
		
	}
	/**
	 * 添加访问记录表<br/>
	 * id,ip,stype,dw,qs,zz,sta,cnt,tm,json
	 * @param s
	 */
	@SuppressWarnings("deprecation")
	private void preLog(){
		Session s=null;
		Transaction tran=null;
		try{
			s = this.getSession(true);
			Connection con = s.connection();
			String sql = SQLFactory.getSQL(SQLFactory._checkTable);
			sql=sql.replaceFirst("@tableName", LG_DB);
			PreparedStatement tp = con.prepareStatement(sql);
			ResultSet res = tp.executeQuery();
			Object o = null;
			if(res.next()){
				o = res.getObject(1);
			}
			res.close();
			tp.close();
			s.close();
			s = getSession(true);
			QueryObject qo = QueryObject.getSQLQuery(s);
			if(o == null){
				tran = s.beginTransaction();
				qo.clear();
				qo.append(" create table "+LG_DB+"(id varchar(32),ip varchar(32),stype varchar(20),dw varchar(20)" +
						",qs datetime,zz datetime,sta smallint,cnt int,tm datetime,json text,PRIMARY KEY (id))");
				qo.excute();
				qo.clear();
				qo.append("create index i_dw_wlg on "+LG_DB+" (dw)").excute();
				s.flush();
				s.clear();
				tran.commit();
			}
		}catch(Exception e){
			if(tran!=null){
				tran.rollback();
			}
			e.printStackTrace();
		}finally{
			if(s!=null)s.close();
		}
	}
	@SuppressWarnings("rawtypes")
	public boolean CKIP(String ip){
		preConfig();
		preLog();
		Session s = this.getSession(true);
		try{
			
			List ll = s.createSQLQuery("select ip from "+IP_DB+" where ip=:ip and permit=1")
			 .setParameter("ip", ip).list();
			if(ll!=null && ll.size()>0)return true;
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	/**
	 * 保存访问记录
	 * @param log
	 */
	public  void saveLog(Webservicelog log){
		Session s = this.getSession(true);
		Transaction tran=null;
		try{
			tran = s.beginTransaction();
			s.save(log);
			tran.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(tran!=null){
				tran.rollback();
			}
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public Object invoke(UCI uci){
		Session s = this.getSession(true);
		Connection con = s.connection();
		try{
			Object o = uci.useConnection(con);
			con.close();
			con = null;
			if(s.isConnected()||s.isOpen()){
				s.close();
				s=null;
			}
			return o;
		}catch(Throwable e){
			e.printStackTrace();
			return null;
		}finally{
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(s!=null && s.isOpen()){
				s.close();
			}
		}
	}
	@Override
	public Object buildDwdmLike(String arg0, Collection<Object> arg1) {		
		return SQLFactory.buildDwdmLike(arg0, arg1);
	}
	@Override
	public Object dwdmlimit(String arg0) {
		return SQLFactory.dwdmLimit(arg0);
	}
}
