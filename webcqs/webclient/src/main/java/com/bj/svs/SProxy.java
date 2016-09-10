package com.bj.svs;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;

import com.bj.inf.Proxi;

public class SProxy extends Service{
	public static String SPACENAME="http://inf.svs.webcqs.com";
	public static String SEVNAME="QQi";
	protected SProxy(URL wsdlDocumentLocation, QName serviceName) {
		super(wsdlDocumentLocation, serviceName);
	}
	private Proxi px;
	@WebEndpoint(name = "QQiHttpPort")
	public Proxi getPort() {
		if(px!=null)return px;
		px= super.getPort(new QName(SPACENAME,
				"QQiHttpPort"), Proxi.class);
		return px;
	}
	
	public String fetch(String json){
		return getPort().fetchData(json);
	}
	
	public static SProxy getInstance(String wsdl,String spname,String svname){
		URL uri = null;
		URL baseUrl;
		QName q=null;
		try{
			baseUrl = SProxy.class.getResource(".");
			uri = new URL(baseUrl,wsdl);
			q = new QName(spname,svname);
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new SProxy(uri,q);
	}
	
	public static SProxy getInstance(String wsdl){
		return getInstance(wsdl,SPACENAME,SEVNAME);
	}
	public static void main(String [] args){
		String json = SProxy.getInstance("http://127.0.0.1/cq/services/QQi?wsdl").fetch("{type:'ORGZ',param:{dwdm:'33020101001'}}");
		System.out.println(json);
	}
}
