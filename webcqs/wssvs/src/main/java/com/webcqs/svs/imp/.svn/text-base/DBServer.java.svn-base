package com.webcqs.svs.imp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webcqs.common.ComFactory;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.Ti;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;

public class DBServer {
	private static Log log=null;
	/**
	 * 主入口
	 * @param param
	 * @return
	 */
	public static IPMO MAIN(IParam param){
		IPMO msg = MPO.copyFrom(param);
		IPMO ipo = null;
		try{
			String type = param.getType();
			msg.setIp(param.getIp());
			if(type ==null || "".equals(type)){
				msg.setDescription("@type_null");
				return msg;
			}
			ipo = getVali().fetch(param);
			if(ipo!=null)return ipo;
			Ti ti = findTi(type);
			if(ti ==null){
				msg.setDescription("@type_err");
				return msg;
			}
			ipo = ti.fetch(param);
			return ipo;
		}catch(Exception e){
			e.printStackTrace();
			msg.setDescription("@server_failed",e);
			return msg;
		}
	}
	
	public static Ti findTi(String t){
		Class<? extends Ti> tc = cMap.get(t);
		if(tc == null)return null;
		try{
			return tc.newInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private static Map<String,Class<? extends Ti>> cMap=null;
	private static Ti vali;
	public static Ti getVali(){
		if(vali==null)vali=new Vali();
		return vali;
	}
	public static void putClass(String key,Class<? extends Ti> tc){
		cMap.put(key, tc);
	}

	@SuppressWarnings("unchecked")
	public static void putClassName(String key,String tc)throws NTiException{
		try{
			Class<? extends Ti> cs = (Class<? extends Ti>)Class.forName(tc);
			putClass(key,cs);
		}catch(Exception e){
			throw new NTiException(e);
		}
	}
	
	static{
		cMap = new ConcurrentHashMap<String,Class<? extends Ti>>();
		InputStream is = null;
		try{
			if(ComFactory._assert){
				getLog().info(DBServer.class.getClassLoader().getResource(".").getPath());
			}
			if(ComFactory.handler_properties!=null){
				is = new FileInputStream(ComFactory.handler_properties);
			}else{				
				is = DBServer.class.getClassLoader().getResourceAsStream("handlers.properties");
			}
			if(is!=null){
				Properties prop = new Properties();
				prop.load(is);
				ComFactory.addHandler(prop);
				is.close();
				is = null;
			}else{
				if(ComFactory._assert){
					getLog().info("没有配置处理程序");
				}
			}
		}catch(Exception e){
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					
				}
			}
		}
	}
	private static Log getLog(){
		if(log == null) log = LogFactory.getLog(ComFactory.class);
		return log;
	}
}
