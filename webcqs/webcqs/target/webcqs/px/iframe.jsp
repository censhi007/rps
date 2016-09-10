<%	String x=request.getParameter("get");
	if("js".equals(x)){
		java.io.OutputStream os = response.getOutputStream();
		String url = request.getRequestURL().toString();
		String cpth = request.getContextPath();
		java.util.regex.Pattern p =java.util.regex.Pattern.compile("(\\w{3,5}\\://[^/]+"+cpth+")");
		java.util.regex.Matcher m = p.matcher(url);
		String proxy=url;
		if(m.find()){
			proxy = m.group();
			proxy+="/proxy";
		}
		String script="(function(){\r\n\tvar win = window;\r\n\twin[\'$proxy\']=function(o,call_back){\r\n\t\tcall_back = call_back||o.call_back||function(){};\r\n\t\tif(!o.url){\r\n\t\t\to.url=\"#\";\r\n\t\t}\r\n\t\tif(o.call_back)delete o.call_back;\r\n\t\tvar doc = document;\r\n\t\tvar msg = JSON.stringify(o);\r\n\t\tvar bd = document.body;\r\n\t\t	ajax(bd,doc,msg,call_back);\r\n\t\t}\r\n\tfunction ajax(bd,doc,msg,fn){\r\n\t\tvar fm = doc.createElement(\"iframe\");\r\n\t\tbd.appendChild(fm);\r\n\t\tfm.setAttribute(\"width\",1);\r\n\t\tfm.setAttribute(\"height\",1);\r\n\t\tfm.contentWindow.name=msg;\r\n\t\tfm.state=0;\r\n\t\tfm.onload=function(){\r\n\t\t\tif(!fm.state){\r\n\t\t\tfm.state=1;\r\n\t\t\t	fm.contentWindow.location.replace(\"#\");\r\n\t\t\tfm.contentWindow.close();\r\n\t\t\treturn;\r\n\t\t}\r\n\t\tif(fm.state == 1){\r\n\t\t\tvar d = fm.contentWindow.name;\r\n\t\t\tfm.contentWindow.document.write(\"\");\r\n\t\t\tbd.removeChild(fm);\r\n\t\t\tif(typeof fn === \"function\"){\r\n\t\t\tfn.apply(null,[d]);\r\n\t\t}\r\n\t\t}\r\n\t}\r\n\t\tfm.setAttribute(\'src\',\'"+url+"\');\r\n\t}\r\n})();";
		script+=("\r\n\r\nfunction proxya(a){\r\n\tvar hf = a.getAttribute(\"href\");\r\n\tif(hf && hf!=\"#\" && hf.trim().length>0){\r\n\t\thf=\""+proxy+"?_url_=\"+hf;\r\n\t\ta.setAttribute(\"href\",hf);\r\n\t}\r\n}");
		try{
			os.write(script.getBytes("utf-8"));
			os.flush();
			os.close();
			os=null;
		}catch(Exception e){}
		finally{
			if(os!=null){
				try{
					os.close();
				}catch(Exception xe){}
				
			}
			
		}
	}else{
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FRAME_PROXY</title>
<script type="text/javascript">
	var script=false;
	try{
		script = JSON.parse(window.name);
	}catch(e){}
	function getXHT(){
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
	if(script && script.url){		
		var u = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/proxy?_url_=";
		u += script.url;
		console.log(u);
		var xht = getXHT();
		xht.onreadystatechange=function(){
			var rawData;
			if (xht.readyState == 4){
				if (xht.status == 200){
					rawdata = xht.response;
					window.name = rawdata;
				}else{
					console.log("web service response error:" + xht.status + "," + xht.statusText);
				}
			}
		};
		xht.open(script.method||"POST",u||"",false);
		if(script.msg && script.msg.length>0){
			xht.send(script.msg);
		}		
	}

</script>
</head>
<body>

</body>
</html>
<%}%>