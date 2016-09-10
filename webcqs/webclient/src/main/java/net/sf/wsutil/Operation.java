package net.sf.wsutil;


/**
 *	Operation information comes from WSDL. 
 *
 */
class Operation {
	
	/**
	 * Operation name
	 */
	public String name;
	/**
	 * Parameter list of operation.
	 * If no parameter,params is an array with zero's element.
	 */
	public Parameter[] params;
	/**
	 * Return type parameter of operation. 
	 * If no return value,returnType is void.
	 */
	public Parameter returnType;
	
	
	public SoapInfo soapInfo;
	
	public HttpPostInfo httpPostInfo;
	
	public HttpGetInfo httpGetInfo;
	
	
	
}
