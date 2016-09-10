package com.bj.svs;

import java.io.FileOutputStream;
import java.io.OutputStream;

import net.sf.wsutil.JSGenerator;
import net.sf.wsutil.WSDL;

public class Main {

	public static void main(String args[]) throws Exception{
		String url ="http://202.75.217.152/wsgs/services/ServiceInfo?wsdl";//"http://192.168.0.248:10002/cq/services/QQi?wsdl";
		WSDL wsdl = new WSDL();
		wsdl.read(url);
		//wsdl.read("http://localhost:7001/myws1/ws1.jws?WSDL=");

		// default output file name is webservice name adds .js
		String outFileName = wsdl.getServiceName() + ".js";
		
		
		OutputStream ofile =  new FileOutputStream(outFileName);
		
		JSGenerator jsg = new JSGenerator(ofile,wsdl,"soap",true);
		jsg.writeCode();
		System.out.println("Javascript code has been written into file: " + outFileName + ".");
		ofile.close();
	}
}
