package net.sf.wsutil;

/**
 *	Http post information of an operation. 
 *
 *	@version	0.1
 *
 */
class HttpPostInfo {
	/**
	 * message send method from client to web service
	 */
	public String method ;
	/**
	 * web service method's url location
	 */
	public String location;
	/**
	 * content type in request header
	 */
	public String contentType;
	/**
	 * web service address
	 */
	public String address;
}
