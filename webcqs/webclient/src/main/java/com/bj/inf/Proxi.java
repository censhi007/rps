package com.bj.inf;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "QQiPortType", targetNamespace = "http://inf.svs.webcqs.com")
public interface Proxi {
	
	@WebMethod
	@WebResult(name = "out", targetNamespace = "http://inf.svs.webcqs.com")
	@RequestWrapper(localName = "fetchData", targetNamespace = "http://inf.svs.webcqs.com", className = "com.bj.inf.Para1")
	@ResponseWrapper(localName = "fetchDataResponse", targetNamespace = "http://inf.svs.webcqs.com", className = "com.bj.inf.Return1")
	public String fetchData(@WebParam(name = "in0", targetNamespace = "http://inf.svs.webcqs.com")String json);
}
