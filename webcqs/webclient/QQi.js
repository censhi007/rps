
//------------------------------------------------------------------------------
// <autogenerated>
//     This code was generated by wsdl2js tool.
//     Runtime Version: 0.1
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </autogenerated>
//------------------------------------------------------------------------------
	//remote web server request object
	var xmlHttpRequest;
	function getHttpRequest(){
		var http_request;
		if (window.XMLHttpRequest) { // Mozilla, Safari, ...
			 http_request = new XMLHttpRequest();
		}else if (window.ActiveXObject) { // IE
			 http_request = new ActiveXObject("Microsoft.XMLHTTP");
		 }else{
			 alert("Your browser doesn't support to create XMLHttp Object,Some content on this page can't show.");
			 return null;
		}
		 return http_request;
	}

	// define a class to encapsulate invoking of web service
	// the class name is the name of web service
	// url -  location of access web service.
	function QQi(url){
		// url=http://192.168.0.248:10002/cq/services/QQi
		this.url = url
		this.fetchData=fetchData
	}


	// web service method
	function fetchData(/*string*/ in0) {
		soapMess ="<?xml version=\"1.0\" encoding=\"utf-8\"?>"
		+"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
		+"<soap:Body>"
		+"<fetchData xmlns=\"http://inf.svs.webcqs.com\">"
		soapMess += "<in0>"+in0+"</in0>"
		soapMess +="</fetchData>"
		soapMess +="</soap:Body></soap:Envelope>"
		xmlHttpRequest = getHttpRequest()
		xmlHttpRequest.onreadystatechange = fetchData_callback
		xmlHttpRequest.open("POST",this.url,true);
		xmlHttpRequest.setRequestHeader("SOAPAction","");
		xmlHttpRequest.setRequestHeader("Content-Type","text/xml; charset=utf-8");
		xmlHttpRequest.send(soapMess);
	}

	// this function will be called when result return from web service.
	function fetchData_callback(){
	// return value from web service is an xml document.
		var rawData;
		if (xmlHttpRequest.readyState == 4){
			if (xmlHttpRequest.status == 200){
				rawdata = xmlHttpRequest.responseXML;
				var resultNode = rawdata.documentElement.firstChild.firstChild.firstChild;
				var resultValue = resultNode.firstChild.nodeValue
				// Now,you can process the returnValue in function fetchData_handler
				fetchData_handler(resultValue);
			}else{
				alert("web service response error:" + xmlHttpRequest.status + "," + xmlHttpRequest.statusText);
			}
		}
	}

	// process result value of method fetchData
	function fetchData_handler(/*string*/ resultValue) {

	}



