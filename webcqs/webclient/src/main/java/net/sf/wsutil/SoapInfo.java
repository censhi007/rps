package net.sf.wsutil;

/**
 *	Http SOAP information of an operation. 
 *	
 *	@version	0.1
 */
class SoapInfo {
	/**
	 * soapAction string.
	 */
	public String soapAction;
	
	/**
	 * Http address of web service
	 */
	public String address;
	
	/**
	 *  HTTP Content-Type of the SOAP request or SOAP response.
	 *
	 */
	public String contentType;
	
	/**
	 * send data method,default value is POST
	 */
	public String method;
	
}
