package com.webcqs.svs.imp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.xfire.transport.http.XFireServletController;

import com.webcqs.common.ComFactory;
import com.webcqs.svs.inf.CIp;
import com.webcqs.svs.inf.IPMO;
import com.webcqs.svs.inf.QQi;
import com.webcqs.svs.model.IParam;
import com.webcqs.svs.model.MPO;
import com.webcqs.svs.model.Webservicelog;

public class QQImpl implements QQi{
	private static final String [] hip = new String[]{"x-forwarded-for",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP"};
	/**
	 * 传入JSON字符串<br/>
	 * 返回JSON字符串
	 */
	@Override
	public String fetchData(String json) {
		return fetchHelp(json);
	}
	
	@Override
	public byte[] fetchGzipData(byte[] data) {
		return comparess(fetchHelp(data));
	}
	/**
	 * 真是的业务函数
	 * @param o
	 * @return
	 */
	private String fetchHelp(Object o){
		IPMO msg = new MPO();
		String json = null;
		try{
			String uip =null;
			try{
				uip = getIp();
			}catch(Exception e){
				e.printStackTrace();
				//无法获取ip
			}
			msg.setIp(uip);
			if(uip==null){
				msg.setDescription("@ip_failed");
				return msg.toJSON();
			}
			if(o == null){
				//不处理
			}else if(o instanceof String){
				json = (String)o;
			}else if(o instanceof byte[]){
				json=decomparess((byte[])o);
			}
			
			if(json==null){
				msg.setDescription("@param_failed");
				return msg.toJSON();
			}
			IParam param = new IParam();
			param.setIp(uip);
			param.fromJSON(json);
			if(!param.parseSuccessfully()){
				msg.setDescription(param.getDescription());
				return msg.toJSON();
			}
			msg = DBServer.MAIN(param);
			return msg.toJSON();
		}catch(Throwable e){
			e.printStackTrace();
			return msg.toJSON();
		}finally{
			CIp cip = ComFactory.getCip();
			if(cip!=null){				
				Webservicelog log =Webservicelog.buildLog(msg);
				log.setJson(json);
				cip.saveLog(log);
			}
		}
	}
	/**
	 * 对传入的压缩数据解压
	 * @param bts
	 * @return
	 */
	private String decomparess(byte[] bts){
		ByteArrayInputStream bin = null;
		GZIPInputStream gzip=null;
		ByteArrayOutputStream bos =null; 
		try{
			//解压
			bin=new ByteArrayInputStream(bts);
			gzip = new GZIPInputStream(bin);
			bos=new ByteArrayOutputStream();
			
			byte[] buf = new byte[1024];
			int num = -1;
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
			    bos.write(buf, 0, num);
			}
			bts = bos.toByteArray();
			bos.flush();
			bos.close();
			bos=null; 
			bin.close();
			bin=null;
			gzip.close();
			gzip=null;
			//解压结束			
			return new String(bts,"utf-8");
		}catch(Throwable e){
			e.printStackTrace();
		}finally{
			try{
				if(bin!=null)bin.close();
				if(gzip!=null)gzip.close();
				if(bos!=null)bos.close();
			}catch(IOException e ){
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 将传入的数据压缩
	 * @param str
	 * @return
	 */
	private byte[] comparess(String str){
		if(str==null)return new byte[0];
		ByteArrayOutputStream obj =null;
		GZIPOutputStream gzip =null;
		try {
			byte[] bts = str.getBytes("utf-8");
			obj= new  ByteArrayOutputStream(); 
			gzip=new GZIPOutputStream(obj);
			gzip.write(bts);
			gzip.finish();
			gzip.close();
			gzip=null;
			bts= obj.toByteArray();
			obj.close();
			obj=null;
			return bts;
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			if(obj!=null){				
				try {
					obj.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(gzip!=null){
				try {
					gzip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return  new byte[0];
	}
	/**
	 * 获取访问者的ip
	 * @return
	 */
	private String getIp(){
		HttpServletRequest http = XFireServletController.getRequest(); 
		for(String s : hip){
			String v = http.getHeader(s);
			if(NULL(v))continue;
			String [] ss = v.split(",");
			if(ss.length == 1)return ss[0];
			for(String t : ss){
				if(!NULL(t))return t;
			}
		}
		return http.getRemoteAddr();
	}
	/**
	 * 检查ip是否是空的
	 * @param ip
	 * @return
	 */
	private boolean NULL(String ip){
		return ip ==null || ip.length()==0 || "unknown".equals(ip);
	}
}
