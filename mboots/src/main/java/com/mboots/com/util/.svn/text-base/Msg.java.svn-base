package com.mboots.com.util;

import com.mboots.com.inf.Bsi;

/**
 * 返回值
 * @author BUJUN
 *
 */
public class Msg implements Bsi{
	private String id;
	private static final long serialVersionUID = -2915954030708835281L;
	/**
	 * 状态
	 */
	private int state=0;
	/**
	 * 对象
	 */
	private Object ovalue;
	/**
	 * 提示信息
	 */
	private String svalue;
	/**
	 * js调用它
	 */
	private String jscall;
	public int getState() {
		return state;
	}
	public void setState(int stat) {
		this.state = stat;
	}
	public Object getOvalue() {
		return ovalue;
	}
	public void setOvalue(Object ovalue) {
		this.ovalue = ovalue;
	}
	public String getSvalue() {
		return svalue;
	}
	
	/**
	 * 返回的值可能需要进行国际化展示
	 * @param svalue
	 */	
	public void setSvalue(String svalue) {
		this.svalue = WebConfig.localString(svalue);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getJscall() {
		return jscall;
	}
	public void setJscall(String jscall) {
		this.jscall = jscall;
	}
	
}
