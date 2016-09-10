package com.webcqs.svs.inf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import com.webcqs.common.Prop;

public abstract class IPMO extends WI{

	private static final long serialVersionUID = 9142555957117055864L;
	//目标，指定是获取还是返回
	protected String method;//GET\PUT
	//类型，指定是什么数据
	protected String type;//crsq...
	//时间，指定返回数据时的系统时间
	protected Long time;//系统时间currentTime
	//状态，指定取数是否成功
	protected int state = 0;//0:失败,1:成功
	//描述，如果失败，显示失败原因
	protected String description;//失败原因
	/**
	 * 从JSON字符串中转化对象
	 */
	@Override
	public void fromJSON(String arg0) {
		
		
	}
	/**
	 * 从输入流中读数据
	 */
	@Override
	public void loadFrom(InputStream arg0) throws IOException {
		
		
	}
	/**
	 * 转化为JSON字符串
	 */
	@Override
	public String toJSON() {
		
		return null;
	}
	/**
	 * 向输出流中写数据
	 */
	@Override
	public void writeTo(OutputStream arg0) throws IOException {
		
		
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		if(description == null){
			this.description=null;
			return;
		}
		if(description.startsWith("@")){
			this.description = Prop.getString(description.substring(1));
			if(this.description ==null){
				this.description = description; 
			}
		}else{			
			this.description = description;
		}
	}
	/**
	 * 设置错误信息
	 * @param str
	 * @param th
	 */
	public void setDescription(String str,Throwable th){
		if(str==null){
			description = th.getLocalizedMessage();
		}else{
			setDescription(str);
			description = "\n"+th.getLocalizedMessage();
		}
	}
	/**
	 * 用户ip
	 */
	protected String ip;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDwdm(){
		return null;
	}
	public Date getQsrq(){return null;}
	public Date getZzrq(){return null;}
	public Date getDate(String key){return null;}
}
