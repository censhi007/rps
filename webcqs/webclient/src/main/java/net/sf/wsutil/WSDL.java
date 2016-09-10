/*
 *  create date: 2006-1
 *  author : chenliang
 *  
 */

package net.sf.wsutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
 *  This class reads WSDL document and retrieves web service description 
 *  to generate javascript proxy codes that can be used to access the web service.
 *	The class parses WSDL 1.1,see http://www.w3.org/TR/2001/NOTE-wsdl-20010315.
 *  
 *  @version  0.1
 *  
 */
public class WSDL {
	
	
	// root element of wsdl document.
	private Element wsdlRoot;
	
	// wsdl's property
	private String targetNamespace;
	// web service name
	private String serviceName;

	
	// XPath parser
	private XPath xp ;
	
	
	/**
	 * Default construct function.
	 *
	 */
	public WSDL(){
		xp =  getXPath();
	}
	
	
	public void read(String wsdlUrl) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException{
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		wsdlRoot = dbf.newDocumentBuilder().parse(wsdlUrl).getDocumentElement();
		
		// set targetNamespace
		targetNamespace = wsdlRoot.getAttribute("targetNamespace");
		LogWriter.writeLog("targetNamespace = " + targetNamespace);
		getServiceName();
	
//		NamedNodeMap ra = wsdlRoot.getAttributes();
//		for (int i=0;i<ra.getLength();i++){
//			System.out.println(ra.item(i).getNodeName()+"---"+ra.item(i).getNodeValue());
//		}
	}
	
	/**
	 * Retrieve operation parameter and return value info.
	 * 
	 * @return Array of All operations in WSDL document.
	 * @throws XPathExpressionException
	 */
	public Operation[] getOperations() throws XPathExpressionException{
		
		// define a list to contain operation object parsed from WSDL.
		List<Operation> operationList = new ArrayList<Operation>();
		
		// First get binding name from service/port/@binding,
		// then get operations from binding/operation.
		
		String bindingName = xp.evaluate("/definitions/service/port/@binding",wsdlRoot);
		// delete namespace name at front of bindingName
		bindingName = deleteSpaceNamePrefix(bindingName);
		
		
		// get binding element
		Element bindingElement = (Element) xp.evaluate("/definitions/binding[@name='"+bindingName+"']",wsdlRoot,XPathConstants.NODE);
		// get operation elements under bindingElement
		NodeList operations = (NodeList) xp.evaluate("operation",bindingElement,XPathConstants.NODESET);
		
		for(int i = 0; i < operations.getLength(); i++){
			Element opt = (Element) operations.item(i);
			Operation operation = new Operation();
			operation.name = opt.getAttribute("name");
			operationList.add(operation);
			LogWriter.writeLog("operation name = " + operation.name);
			
			// assign operation's parameter and return types
			setOperationTypes(operation);
			// assign soap from wsdl.
			setOperationSoap(operation);
			// assign http post from wsdl.
			setOperationHttpPost(operation);
			// assign http get from wsdl.
			setOperationHttpGet(operation);
		}
		
		return (Operation[]) operationList.toArray(new Operation[0]);
	}
	
	
	/**
	 * Set paramerets and return types for an operation.
	 * Path of getting types is portType-->operation-->input|output-->message-->part-->(types)
	 * @param operation  
	 * @throws XPathExpressionException 
	 */
	private void setOperationTypes(Operation operation) throws XPathExpressionException{
		// get parameters and return type from definitions/types/s:elements
		
		String optName = operation.name;
		
		Parameter[] params = null;
		Parameter returnValue = new Parameter();
		Port port = getPortInfo();
		String ptype="ServiceInfoPortType";
		
		// get message from portType node
		String path = "/definitions/portType[@name='" + ptype+ "']/" +
				"operation[@name='"+ optName +"']/input/@message";
		String message = deleteSpaceNamePrefix(xp.evaluate(path,wsdlRoot));
		
		// get parameter info from message node
		path = "/definitions/message[@name='"+ message +"']/*";
		NodeList paramList = (NodeList) xp.evaluate(path,wsdlRoot,XPathConstants.NODESET);
		// for part element, if contains type attribute, part is parameter, otherwise,
		// parameter info is in types node.
		params = new Parameter[paramList.getLength()];
		for(int j=0; j < paramList.getLength(); j++){
			Element param = (Element) paramList.item(j);
			String paraName = param.getAttribute("name");
			if (param.getAttribute("type") != "" ){
				String paramType = deleteSpaceNamePrefix(param.getAttribute("type"));
				params[j] = new Parameter();
				params[j].name = paraName;
				params[j].typeName = paramType;
			}else{
				// get parameter info from types node
				String elementName = deleteSpaceNamePrefix(param.getAttribute("element"));
				path = "/definitions/types/schema/element[@name='"+ elementName +"']/complexType";
				Element pElement = (Element) xp.evaluate(path,wsdlRoot,XPathConstants.NODE);
				if (pElement.hasChildNodes()){
					// contains parameter
					paramList = (NodeList) xp.evaluate("*/element",pElement,XPathConstants.NODESET);
					params = new Parameter[paramList.getLength()];
					for(int k=0; k < paramList.getLength(); k++){
						param = (Element) paramList.item(k);
						params[k] = new Parameter();
						params[k].name = param.getAttribute("name");
						params[k].typeName = deleteSpaceNamePrefix(param.getAttribute("type"));
						LogWriter.writeLog("parname="+params[k].name+" type="+params[k].typeName);
					}
				}else{
					// none parameter
					params = new Parameter[0];
				}
				break;
			}
		}
		
		// assign parameter to operaton
		operation.params = params;
		
		// get return type
		path = "/definitions/portType[@name='" +ptype + "']/" +
		"operation[@name='"+ optName +"']/output/@message";
		message = deleteSpaceNamePrefix(xp.evaluate(path,wsdlRoot));

		// get parameter info from message node
		path = "/definitions/message[@name='"+ message +"']";
		Node msgNode = (Node) xp.evaluate(path,wsdlRoot,XPathConstants.NODE);
		if (msgNode.hasChildNodes()){
			Element returnElm = (Element) xp.evaluate("part",msgNode,XPathConstants.NODE);
			if (returnElm.getAttribute("type") != ""){
				// return types in part node
				returnValue.name = returnElm.getAttribute("name");
				returnValue.typeName = deleteSpaceNamePrefix(returnElm.getAttribute("type"));
				
			}else{
				// return type in types node
				String elementName = deleteSpaceNamePrefix(returnElm.getAttribute("element"));
				path = "/definitions/types/schema/element[@name='" + elementName +"']/complexType";
				Element rElement = (Element) xp.evaluate(path,wsdlRoot,XPathConstants.NODE);
				if (rElement.hasChildNodes()){
					// return value
					returnElm = (Element) xp.evaluate("*/element",rElement,XPathConstants.NODE);
					returnValue.name = returnElm.getAttribute("name");
					returnValue.typeName = deleteSpaceNamePrefix(returnElm.getAttribute("type"));
					LogWriter.writeLog(returnValue.name+"<--->"+returnValue.typeName);
				}else{
					// none return value
					returnValue.name = "";
					returnValue.typeName = "void";
				}
			}
			
		}else{
			// none return value
			returnValue.name = "";
			returnValue.typeName = "void";
		}

		
		// assign parameter to operaton
		operation.returnType = returnValue;
		
		
	}
	
	
	/**
	 * Retrieve soap information of designate operation from wsdl and assign to operation object.
	 * 
	 * @param operation		operation to query.
	 * @throws XPathExpressionException
	 */
	private void setOperationSoap(Operation operation) throws XPathExpressionException{
		
		Port port = getPortInfo();
		String soapBinding = port.binding;
		SoapInfo soapInfo = new SoapInfo();
		// path to soapAction node
		String path = "/definitions/binding[@name='" + soapBinding + "']"
				+ "/operation[@name='" + operation.name + "']/operation/@soapAction";
		
		String soapAction = xp.evaluate(path,wsdlRoot);
		LogWriter.writeLog("soapAction="+soapAction);
		// path to soap address of web service
		soapInfo.address = port.localtion;
		soapInfo.soapAction = soapAction;
		soapInfo.contentType = "text/xml; charset=utf-8";
		soapInfo.method = "POST";
		operation.soapInfo = soapInfo;
		LogWriter.writeLog("soap address = "+soapInfo.address);
	}
	
	
	/**
	 * Retrieve httppost information from wsdl and assign to operation object.
	 * 
	 * @param operation		
	 * @throws XPathExpressionException
	 */
	private void setOperationHttpPost(Operation operation) throws XPathExpressionException{
		
		String httpPostBinding = this.serviceName + "HttpPost";
		HttpPostInfo httpPostInfo = new HttpPostInfo();
		// path to binding node
		String path = "/definitions/binding[@name='" + httpPostBinding + "']";
		Element bindingElement = (Element)getXPath().evaluate(path,wsdlRoot,XPathConstants.NODE);

		
		if (bindingElement == null){
			// some wsdl does't contain HttpPost binding,set default value
			httpPostInfo.contentType = "application/x-www-form-urlencoded";
			httpPostInfo.location = "/" + operation.name;
			httpPostInfo.method = "POST";
			// use soap address as httppost address
			httpPostInfo.address = operation.soapInfo.address;
			
		}else{
			// path to httppost address of web service
			path = "/definitions/service/port[@name='"+ httpPostBinding +"']/address/@location";
			httpPostInfo.address = xp.evaluate(path,wsdlRoot);
			
			// path to location of operation from binding element
			path = "operation[@name='" + operation.name + "']/operation/@location";
			httpPostInfo.location = xp.evaluate(path,bindingElement);
			
			// path to content-type from binding element
			path = "operation[@name='" + operation.name + "']/input/content/@type";
			httpPostInfo.contentType = xp.evaluate(path,bindingElement);
			
			httpPostInfo.method = "POST";
			
		}
		
		operation.httpPostInfo = httpPostInfo;
		
	}
	
	
	/**
	 * Retrieve http get inforamtion from wsdl and assign to operation object.
	 * 
	 * @param operation
	 * @throws XPathExpressionException
	 */
	private void setOperationHttpGet(Operation operation) throws XPathExpressionException{
		
		HttpGetInfo httpGetInfo = new HttpGetInfo();
		String httpGetBinding = this.serviceName + "HttpGet";
		String path = "/definitions/binding[@name='" + httpGetBinding + "']";
		Element bindingElement = (Element)getXPath().evaluate(path,wsdlRoot,XPathConstants.NODE);

		
		if (bindingElement == null){
			// some wsdl does't contain HttpPost binding,set default value
			httpGetInfo.contentType = "urlEncoded";
			httpGetInfo.location = "/" + operation.name;
			httpGetInfo.method = "GET";
			// use soap address as httpget address
			httpGetInfo.address = operation.soapInfo.address;
			
		}else{
			// path to httppost address of web service
			path = "/definitions/service/port[@name='"+ httpGetBinding +"']/address/@location";
			httpGetInfo.address = xp.evaluate(path,wsdlRoot);
			
			// path to location of operation from binding element
			path = "operation[@name='" + operation.name + "']/operation/@location";
			httpGetInfo.location = xp.evaluate(path,bindingElement);
			
			httpGetInfo.contentType = "urlEncoded";
			
			httpGetInfo.method = "GET";
			
		}
		
		operation.httpGetInfo = httpGetInfo;		
		
	}
	
	
	/**
	 * Retrieve web service name and assign to serviceName variable.
	 *  
	 * @throws XPathExpressionException
	 */
	public String getServiceName() throws XPathExpressionException{
		String path = "/definitions/service/@name";
		serviceName = xp.evaluate(path,wsdlRoot);
		return serviceName;
		
	}
	
	/**
	 * Retrieve port information in /definitions/service node.
	 * 
	 * @return
	 * @throws XPathExpressionException
	 */
	private Port getPortInfo() throws XPathExpressionException {
		String path = "/definitions/service/port";
		Element portNode = (Element) xp.evaluate(path,wsdlRoot,XPathConstants.NODE);
		Port port = new Port();
		port.binding = deleteSpaceNamePrefix(portNode.getAttribute("binding"));
		port.name = portNode.getAttribute("name");
		port.localtion = xp.evaluate("address/@location",portNode);
		
		return port;
		
	}
	
	/**
	 * Retrieve the location of web service.
	 * 
	 * @return
	 */
	public String getLocation(){
		try {
			return getPortInfo().localtion;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Retrieved a XPath object use to parse xml document by path
	 * 
	 * @return  XPath object.
	 */
	private XPath getXPath(){
		return XPathFactory.newInstance().newXPath();
	}

	private String deleteSpaceNamePrefix(String preSN){
		String[] its = preSN.split(":");
		return its[its.length-1];
	}
	
	
	public String getTargetNamespace() {
		return targetNamespace;
	}

	
}
