/*
 * 
 *  Program start entry file.This tool runs in command mode. 
 *  Command line parameters are parsed by apache common cli. 
 *  (http://jakarta.apache.org/commons/cli/).
 * 
 *  create date: 2006-1-18
 *  author:  chenliang [mail:chen7115@hotmail.com]
 *  copyright (c) 2006 chenliang
 *  
 *  This program's License uses GNU GENERAL PUBLIC LICENSE,version 2.0 or later.
 *  
 *  update log:
 *  	2006-2-27  encapsulate invoking of web service as a class.
 *  				add option of asynchronous or synchronous invoking of web service in js. 
 *  
 */

package net.sf.wsutil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.xml.sax.SAXException;

/**
 * 
 *  Program entry class. 
 *
 *	@version 0.1
 *
 */

public class Main {

	
	/*
	 * define validating parameters of wsdl2js
	 * 
	 */
	private final static Options opt = new Options();
	static {
		opt.addOption("u",true,"<address of WSDL> location of WSDL,the url may be file path or network URL. For example, http://localhost/webservice/ws1.jws?WSDL");
		opt.addOption("p",true,"<soap|httppost|httpget> protocol of sending and receiving SOAP message. Default is soap,httppost or httpget is optional, decided by the implemention of web service.");
		opt.addOption("o",true,"<filename>  output file location.");
		opt.addOption("s",false,"synchronous invoking the web service in js,optional. default is asynchronous");
		opt.addOption("h",false,"show this usage information.");
	}
	
	
	
	/**
	 * @param args  command line parameters
	 * @throws XPathExpressionException 
	 */
	public static void main(String[] args)  {

		//parse command parameters
		final CommandLineParser clp = new BasicParser();
		CommandLine line = null;
		try {
			line = clp.parse(opt,args);
		} catch (ParseException e) {
			e.printStackTrace();
			usage();
		}
		
		
		
		if (line.hasOption("h") || args.length < 1){
			usage();
		}
		
		
		// protocol default value is soap
		String protocol = JSGenerator.PROTOCOL_SOAP;
		if (line.hasOption("p")){
			protocol = line.getOptionValue('p');
			if (!JSGenerator.PROTOCOL_SOAP.equalsIgnoreCase(protocol) && 
				!JSGenerator.PROTOCOL_HTTPPOST.equalsIgnoreCase(protocol) &&
				!JSGenerator.PROTOCOL_HTTPGET.equalsIgnoreCase(protocol)){
				System.out.println("Parameter p value must be soap or httppost or httpget.");
				System.exit(1);
			}
		}
		
		
		String url = "";
		if (!line.hasOption("u")){
			System.out.println("parameter -u must be exist!");
			usage();
			System.exit(1);
		}else{
			url = line.getOptionValue("u");
		}
		
		// don't output log information 
		LogWriter.setLogWriter(null);

		boolean asynch = true;
		if (line.hasOption("s")){
			// synchronous invoking
			asynch = false;
		}
		
		try {
			WSDL wsdl = new WSDL();
			wsdl.read(url);
			//wsdl.read("http://localhost:7001/myws1/ws1.jws?WSDL=");

			// default output file name is webservice name adds .js
			String outFileName = wsdl.getServiceName() + ".js";
			if (line.hasOption("o")){
				outFileName = line.getOptionValue("o");
			}
			
			OutputStream ofile =  new FileOutputStream(outFileName);
			
			JSGenerator jsg = new JSGenerator(ofile,wsdl,protocol,asynch);
			jsg.writeCode();
			System.out.println("Javascript code has been written into file: " + outFileName + ".");
			ofile.close();
			
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
	}

	
	/**
	 * Usage description about wsdl2js. 
	 *
	 */
	private static void usage(){
		String desc = "wsdl2js is a tool to generate javascript code that can call web service.\n" + 
				 "wsdl2js supports wsdl 1.1 and soap 1.1.\n" +
						"wsdl2js uses GNU GENERAL PUBLIC LICENSE.\n";
		System.out.println(desc);
		String os = System.getProperty("os.name");
		String cmd = "";
		if (os.toLowerCase().startsWith("windows")){
			cmd = "wsdl2js";
		}else{
			cmd = "wsdl2js.sh";
		}
		 final HelpFormatter hf = new HelpFormatter();
	        hf.printHelp(
	            cmd
	                + " [options] ",
	            opt);
	        System.exit(1);
	}
	
}
