package net.sf.wsutil;

import javax.xml.xpath.XPathExpressionException;


/**
 * 
 *  This class generate javascript code  to web service.
 *	Generated code uses http GET protocol to connect web service.
 *
 *	@version	0.1
 *
 */
public class HttpGetGenerator implements ICodeGenerator {

	// save code in memory,at last save into destination stream.
	private StringBuilder codes = new StringBuilder();
	
	// line seperator
	private String newLine = System.getProperty("line.separator","\r\n");

	WSDL wsdl = null;
	
	// operations come from wsdl
	Operation[] ops = null;
	
	// invoke type in js, default is asynchronous
	private boolean asynch = true;
	
	
	
	/**
	 * Construct function.
	 * 
	 * @param wsdl 
	 * @throws XPathExpressionException 
	 */
	public HttpGetGenerator(WSDL wsdl) {
		this.wsdl = wsdl;
		try {
			this.ops = wsdl.getOperations();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	
	public HttpGetGenerator(WSDL wsdl,boolean asynch) {
		this.wsdl = wsdl;
		this.asynch = asynch;
		try {
			this.ops = wsdl.getOperations();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see net.sf.wsutil.ICodeGenerator#writeCode()
	 */
	public String writeCode() {
		for(Operation op : ops){
			codes.append(writeFunction(op));
			codes.append(writeCallback(op));
		}
		
		return codes.toString();
	}

	/**
	 * generate javascript code of sending url message to web service by GET method.
	 * 
	 * @param op
	 * @return
	 */
	private String writeFunction(Operation op){
		StringBuilder codes = new StringBuilder();
		String funName = op.name  ;
		String callbackName = funName + "_callback";
		codes.append(newLine + newLine + "\t// web service method");
		codes.append(newLine+"\tfunction " + funName
				+ "(" + paramList(op.params) + ") {" + newLine
				// function body
				+ "\t\tvar method_url = this.url" + " + \"" + op.httpGetInfo.location + "\"" +  newLine
				// convert  parameter to urlencode message
				+ "\t\tmessage=\"\"" + newLine
				+  paramNodeList(op.params) 
				+ "\t\txmlHttpRequest = getHttpRequest()" + newLine
				+ "\t\txmlHttpRequest.onreadystatechange = " + callbackName + newLine
				+ "\t\txmlHttpRequest.open(\"" + op.httpGetInfo.method + "\",method_url+\"?\"+message," + asynch + ");" + newLine
				//+ "\t\txmlHttpRequest.setRequestHeader(\"Content-Type\",\"" + op.httpGetInfo.contentType + "\");" + newLine
				+ "\t\txmlHttpRequest.send();" + newLine
				+ "\t}" + newLine + newLine	// function define end	
			);	// end of append method
		
		return codes.toString();
	}
	
	/**
	 * Generate javascript code of callback function of request web service.
	 * 
	 * @param op
	 * @return
	 */
	private String writeCallback(Operation op){
		StringBuilder codes = new StringBuilder();
		
		// XMLHttpRequest call back function name
		String callbackName = op.name + "_callback";
		// handle the return value function name
		String handlerFunction = op.name + "_handler";
		
		codes.append("\t// this function will be called when result return from web service." + newLine);
		codes.append("\tfunction " + callbackName + "(){" + newLine
				+ "\t// return value from web service is an xml document." + newLine
				+ "\t\tvar rawData;" + newLine
				+ "\t\tif (xmlHttpRequest.readyState == 4){" + newLine
				+ "\t\t\tif (xmlHttpRequest.status == 200){" + newLine
				+ "\t\t\t\trawdata = xmlHttpRequest.responseXML;" + newLine);
				// retrieve return value, if return value is single value,
				// it is the first child node of root node.
				// if return value is array,it is child nodes of root node,
				// around with type name node, such as <string>value1</string>
		
				if (op.returnType.name == ""){
					// no return value
					codes.append("\t\t\t\tvar resultValue = \"\"" + newLine);
				}else if (op.returnType.typeName.startsWith("Array")){
					codes.append("\t\t\t\tvar resultValue = new Array();" + newLine);
					codes.append("\t\t\t\tvar resultNode = rawdata.documentElement;" + newLine);
					codes.append("\t\t\t\tfor(i = 0;i<resultNode.childNodes.length;i++){" + newLine);
					codes.append("\t\t\t\t\tresultValue[i] = resultNode.childNodes.item(i).firstChild.nodeValue;" + newLine);
					codes.append("\t\t\t\t}" + newLine);
				}else{
					codes.append("\t\t\t\tvar resultNode = rawdata.documentElement;" + newLine);
					// for simple types:string,int,float,get value from node
					if (op.returnType.typeName.matches("int|float|string")){
						codes.append("\t\t\t\tvar resultValue = resultNode.firstChild.nodeValue" + newLine);
					}else{					
						codes.append("\t\t\t\tvar resultValue = resultNode" + newLine);
					}
				}
				codes.append("\t\t\t\t// Now,you can process the returnValue in function " + handlerFunction + newLine);
				codes.append("\t\t\t\t" + handlerFunction +"(resultValue);" + newLine);
				codes.append("\t\t\t}else{" + newLine
				+ "\t\t\t\talert(\"web service response error:\" + xmlHttpRequest.status + \",\" + xmlHttpRequest.statusText);" + newLine
				+ "\t\t\t}" + newLine
				+ "\t\t}" + newLine
				+ "\t}" + newLine + newLine
				+ "\t// process result value of method " + op.name +  newLine
				+ "\tfunction " + handlerFunction + "(" +
					"/*" + op.returnType.typeName + "*/ " +
					"resultValue) {" + newLine + newLine
				+ "\t}" + newLine + newLine
				);
		
		
		return codes.toString();
	}
	
	
	
	/**
	 * Convert parameter array to parameter string compart by comma.
	 * @param ps
	 * @return
	 */
	private String paramList(Parameter[] ps){
		StringBuilder rst = new StringBuilder();
		for (Parameter p : ps){
			// add parameter types
			rst.append("/*"+ p.typeName +"*/ ");
			rst.append(p.name+",");
		}
		// delete last comma if it exists
		if (ps.length > 0){
			rst.delete(rst.length()-1,rst.length());
		}
		return rst.toString();
	}
	
	
	/**
	 * Convert parameter array to node list included in urlencode message.
	 * @param ps
	 * @return
	 */
	private String paramNodeList(Parameter[] ps){
		
		StringBuilder rst = new StringBuilder();
		for (Parameter p : ps){
			if (p.typeName.startsWith("Array")){
				// the parameter type is array,cycle every element of array
				rst.append("\t\tfor(i = 0;i < " + p.name + ".length; i++){" + newLine);
				rst.append("\t\t\tmessage += \"&" + p.name + "=\";" + newLine);
				rst.append("\t\t\tmessage += escape(" + p.name + "[i]);" + newLine);
				rst.append("\t\t}" + newLine);
			}else{
				// other type
				rst.append("\t\tmessage += \"&" + p.name+"=\" + escape(");
				rst.append(p.name);
				rst.append(")");
			}
			rst.append(newLine);
		}
		return rst.toString();
		
	}	
	
		
	
}
