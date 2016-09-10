package com.webcqs.svs.model;

import java.util.Date;

import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webcqs.common.DateUtil;
import com.webcqs.common.JsUtil;
import com.webcqs.svs.inf.IPMO;
/**
 * 参数
 * @author BUJUN
 *
 */
public final class IParam extends IPMO{
	private Log log = LogFactory.getLog(IParam.class);
	private static String script="(function(o){function setter(k){if(!k)return;return 'set'+(k[0]+'')" +
			".toUpperCase()+k.substring(1);}o=o||{};var wo=typeof WIobj==='undefined' ? {} : " +
			"WIobj;var pm = o.param||{};delete o.param;for(var i in o){if(o.hasOwnProperty(i) && " +
			"typeof o[i]!=='function'){var set = setter(i);if(wo[set]){wo[set](o[i]);}}}" +
			"if(wo.getParam){var param = wo.getParam();for(var i in pm){if(pm.hasOwnProperty" +
			"(i) && typeof pm[i]!=='function'){param.put(i,pm[i]);}}wo.setParam(param);}return wo;})";
	private boolean ok;
	private static final long serialVersionUID = -6288680293303060510L;
	private TMO param = new TMO();
	public IParam(){
		method="GET";
	}
	@Override
	public void fromJSON(String arg0) {
		Bindings vars = new SimpleBindings();
		vars.put("WIobj",this);
		try {
			JsUtil.evel(script+"("+arg0+")", vars);
			ok = true;
		} catch (ScriptException e) {
			log.info("JSON解析失败！",e);
			setDescription("@json_parse_err",e);	
		}
	}
	public TMO getParam() {
		return param;
	}
	public void setParam(TMO param) {
		this.param = param;
	}
	/**
	 * 是否成功解析
	 * @return
	 */
	public boolean parseSuccessfully(){
		return ok;
	}
	public static void main(String[] args){
	}

	public String getDwdm(){
		Object i = param.get("dwdm");
		return i == null ? null : i.toString();
	}
	private Date qsrq;	
	public Date getQsrq(){
		if(qsrq != null )return qsrq;
		Object d = param.get("qsrq");
		if(d ==null)return null;
		if(d instanceof Date){
			qsrq = (Date)d;
			return qsrq;
		}
		if(d instanceof Number){
			Long l =((Number)d).longValue();
			qsrq = new Date();
			qsrq.setTime(l);
			return qsrq;
		}
		if(d instanceof String){
			try {
				qsrq = DateUtil.getDateFromStr((String)d);
			} catch (Exception e) {
				log.info("日期字符串["+d+"]转化为日期对象失败",e);
			}
		}
		return qsrq;
	}
	private Date zzrq;
	public Date getZzrq(){
		if(zzrq!=null)return zzrq;
		Object d = param.get("zzrq");
		if(d ==null)return null;
		if(d instanceof Date){
			zzrq = (Date)d;
			return zzrq;
		}
		if(d instanceof Number){
			Long l =((Number)d).longValue();
			zzrq = new Date();
			zzrq.setTime(l);
			return zzrq;
		}
		if(d instanceof String){
			try {
				zzrq = DateUtil.getDateFromStr((String)d);
			} catch (Exception e) {
				log.info("日期字符串["+d+"]转化为日期对象失败",e);
			}
		}
		return zzrq;
	}
	/**
	 * 获取日期
	 * @param key
	 * @return
	 */
	public Date getDate(String key){		
		Object d = param.get(key);
		if(d == null)return null;
		Date zzrq =null;
		if(d instanceof Date )return (Date)d;
		if(d instanceof Number){
			Long l =((Number)d).longValue();
			zzrq = new Date();
			zzrq.setTime(l);
			return zzrq;
		}
		if(d instanceof String){
			try {
				zzrq = DateUtil.getDateFromStr((String)d);
			} catch (Exception e) {
				log.info("日期字符串["+d+"]转化为日期对象失败",e);
			}
		}
		return zzrq;
	}

	public Object get(String key){
		return param.get(key);
	}
}
