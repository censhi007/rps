(function(){
	/**
	 * This script named QQi.js<br/>
	 * It was the an interface mapped to the WSDL http://127.0.0.1/cq/services/QQi?wsdl<br/>
	 * This interface has several functions with which you can access the server.<br/>
	 * Notice!!!The Ajax was limited by the same origin policy.If you can not solve that question.<br/>
	 * Please offer a proxy-url,which accepts two params:url|data. And the proxy-url will return the result from webservice-server<br/>
	 * Like this:<br/>
	 * <script>var b = QQi(url);</script><br/>
	 * <script>b.proxy='<i>http//127.0.0.1/proxy</i>';</script>
	 * They are:<br/>
	 * @FunctionNameList
	 * @version 1.0
	 * @author BUJUN
	 * @since 2014-08-29
	 * @created 2014/09/16 13:22:02
	 * @copyright net.bujun
	 */
	var win = window;
	win['console']&&(win['console']={log:function(){}});
	var b=win.QQi=function(url){	
		return new b.fn.init(url);
	}
	b.fn=b.prototype={
		o:{},
		url:'services/QQi',
		proxy:null,
		asynch:true,
		init:function(url){
			if(url)this.url = url;
			this.initFunc();
			return this;
		},
		set:function(k,v){
			this.o[k]=v;
		},
		get:function(k){
			return this.o[k];
		},
		initFunc:function(){
			var that = this;
			that.set('fetchData_callback',function(/**string*/o){
				/**
				 * You can write code here to complete the handler,which is Not-Recommended .<br/>
				 * Instead, we strongly recommend you use obj.set(k,func) outsite,which will never change the core code.<br/>
				 * Notice that no matter which one you chose,you should guarantee the code run correctly
				 */
			});

		},
		call:function(e){
			e=e||{};
			var msg = e.msg||"";
			var callback = e.callback||function(){};
			var hd = e.header||{};
			var method = e.method;
			var that = this;
			var xht = this.getXHT();
			xht.onreadystatechange=function(){
				var rawData;
				if (xht.readyState == 4){
					if (xht.status == 200){
						rawdata = xht.responseXML;
						var resultNode = rawdata.documentElement.firstChild.firstChild.firstChild;						
						if(typeof callback==='function'){
							callback.apply(that,[resultNode]);
							return;
						}
						if(typeof callback === 'string' && typeof win[callback] === 'function'){
							win[callback].apply(that,[resultNode]);
							return;
						}
					}else{
						console.log("web service response error:" + xht.status + "," + xht.statusText);
					}
				}
			};
			var u = that.proxy ? that.proxy+"?url="+that.url : that.url;
			xht.open(method,u,that.asynch);
			for(var i in hd){
				if(hd.hasOwnProperty(i) && typeof hd[i]!=="function")				
				xht.setRequestHeader(i,hd[i]);
			}
			xht.send(msg);
			
		},
		/**
		 *The http request object<br/>
		 *It was used to send http request.
		 */
		getXHT:function(){
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
		,fetchData : function(/*string*/ in0){
			/**
			 * Function fetchData is the mapper to the interface in remote server.<br/>
			 * You can call it like this:<br/>
			 * var obj = QQi(url);
			 * obj.fetchData( in0);
			 */
			var that = this;
			var soapMess = '<?xml version=\"1.0" encoding=\"utf-8\"?>\r\n'+
			'<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" '+
			'xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n'+
			'<soap:Body>\r\n'+
			'<fetchData xmlns=\"http://inf.svs.webcqs.com\">';
				soapMess+='<in0>\r\n'+in0.replace(/</g,'&lt;')+'\r\n</in0>\r\n'
			soapMess+='</fetchData>\r\n</soap:Body>\r\n</soap:Envelope>';
			var o={msg:soapMess};
			o['method']='POST';
			o['callback']=function(resultNode){
				var resultValue = resultNode.firstChild.nodeValue;
				var cbk = that.get('fetchData_callback');
				if(typeof cbk === 'function')cbk.apply(that,[resultValue]);
			};
			o['header']={'SOAPAction':'null','Content-Type':'text/xml; charset=utf-8'};
			that.call(o);
		}

	}
	b.fn.init.prototype=b.fn;
})();
