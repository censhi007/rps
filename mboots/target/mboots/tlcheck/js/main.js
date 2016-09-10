(function(){
var BASE=ctx+"/restful/";
var HTML5 = "getContext" in document.createElement("canvas");
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g, "");}
var _uuid_=function(length){
	var uuidpart = "";
    for (var i=0; i<length; i++) {
        var uuidchar = parseInt((Math.random() * 256), 10).toString(16);
        if (uuidchar.length == 1) {
            uuidchar = "0" + uuidchar;
        }
        uuidpart += uuidchar;
    }
    return uuidpart;
}
function timer(func){
	if(!func || typeof func!="function")return;
	var l = arguments.length>1?arguments[1]:1500;
	return setTimeout(func,l);
}

var hasOwn = {}.hasOwnProperty;
/**
 * 封装回调函数
 */
function wrapper(options,_suc,_err){
	options=options||{};
	var suc = options.success;
	options.success=function(){
		_apply(_suc,this,arguments);
		_apply(suc,this,arguments);
	}
	var err = options.error;
	options.error=function(){
		_apply(_err,this,arguments);
		_apply(err,this,arguments);
	}
	return options;
}
function _once(dst,obj){
	dst=dst||{};
	if(typeof obj === "string"){
		var o = {};
		o[obj]=arguments[2];
		obj=o;
	}
	var key;
	for(key in obj){
		if(hasOwn.call(obj,key) && typeof obj[key]==="function"){
			var tv = obj[key];
			dst[key]=function(){
				_k = dst[key];
				delete dst[key];
				try{						
					return tv.apply(dst,arguments);
				}catch(e){
					if(console)console.trace(e);
					dst[key]=_k;//没有成功执行，重新将它还原回去
				}
			}
		}
	}
}
var _apply=function(fuc,_this,args){
	if(typeof fuc==="function"){
		return fuc.apply(_this,args);
	}
}
define(["jquery","bootstrap","underscore","backbone","authority","../css/common.css"],function(require,exports,module){
	require("jquery");
	require("bootstrap");
	require("underscore");
	require("backbone");
	var au = require("authority");
	
	var navmod = Backbone.Model.extend({
		defaults:{
			path:"#",
			onum:0,
			name:"未命名"			
		}
	});
	
	var navc = Backbone.Collection.extend({
		model:navmod,
		comparator:"onum",
		url:BASE+"navs/",
		sync:function(method, model, options){
			var suc = options.success||function(){};
			options.dataType="json";
			var args = arguments;
			var that = this;
			var op = _.extend({},options);
			op.reset=true;
			op.success=function(json){
				if((json||{}).offline){
					new au().showform(function(){
						that.sync.apply(that,args);//重新请求
					});
				}else{
					suc.apply(this,arguments);
				}
			}
			Backbone.sync.apply(this, [method, model, op]);
		}
	});
	
	var navpp = Backbone.View.extend({
	    tagName:  "li",
	    template: _.template($('#templatelnavitem').html()),
	    initialize: function() {
	    },
	    render: function() {     	     
	    	 this.$el.html(this.template(this.model.toJSON()));
	         return this;
	    },
	    clear: function() {
	      this.model.destroy();
	    }
	  });
	
	var nav = Backbone.View.extend({
		el:$("header nav"),
		templatelnav: _.template($('#templatelnav').html()),
		tpuser:_.template($('#templateluser').html()),
		initialize:function(){
			var obj = {name:"首页"};
			this.$el.html(this.templatelnav(obj));
			this.col = new navc();
			this.listenTo(this.col, 'reset', this.navlist);
			this.col.fetch();
		},navlist:function(){
			this.col.each(function(_model){
				var view = new navpp({model: _model});
				var el = this.$el;
				view.$el.click(function(){
					el.find(".active").removeClass("active");
					$(this).addClass("active");
				}).appendTo(el);
			},this);
			
			this.showUser();//显示当前用户。
		},showUser:function(){
			var op={
				type:"GET",
				url:BASE+"userinfo/currentuser",
				dataType:"json"
			};
			var that = this;
			op['success']=function(json){
				if(json && json.state){
					var user = json.ovalue||{name:"anyone"};
					that.$el.find("#divuserinfo").html(that.tpuser(user));
					if(user.init){
						//进行密码设置
						var option={uname:user.logname,title:"用户处于初始化状态，请修改密码！"};
						option=wrapper(option,function(json){
							//TODO rebuild password success
						});						
						new au().rebuildpassword(option);
					}
				}
			}
			op['error']=function(json){
				
			}
			Backbone.ajax(op);
		}
	});
	
	new nav;//加载导航。
});
define("authority",["jquery","underscore","backbone","m5"],function(require,exports,module){
	var lv;
	var $ = require("jquery");
	 require("underscore");//widnow._
	var Backbone = require("backbone");
	var m5 = require("m5");
	//用户权限，在未登陆的情况下，本模块将弹出登录框

	
	var au=function(){};
	au.fn=au.prototype={};
	_.extend(au.fn,{
		version:"1.0",
		constructor:au,
		online:function(options){
			//检查是否在线
			options = typeof options==="function" ? {success:options} : options||{};
			var suc = options.success||function(){};
			if(options)	delete options.success;
			options=wrapper(options,function(text){
				if((json||{}).offline){
					suc.apply(this,[false]);
				}else{
					suc.apply(this,[true]);
				}
			});
			options.url=BASE+"online";
			options.type="GET";
			options.dataType="json";
			$.ajax(options);
		},offline:function(options){
			options = typeof options==="function" ? {success:options} : options||{};
			var suc = options.success||function(){};
			if(options)	delete options.success;
			options=wrapper(options,function(text){
				if((json||{}).offline){
					suc.apply(this,[true]);
				}else{
					suc.apply(this,[false]);
				}
			});
			options.url=BASE+"offline";
			options.type="GET";
			options.dataType="json";
			$.ajax(options);
		},
		_modal:function(options){
			if(!options ||!options.el)return;
			var el = options.el.appendTo("body").modal({keyboard: false});
			el.find("[data-toggle='tooltip']").tooltip();
			var _el = el.get(0);
			var evts = options.events||{};
			for(var i in evts){
				if(hasOwn.call(evts,i) && typeof i === "string"){
					var n = evts[i];
					n=typeof n==="string"?this[n]:n;
					if(typeof n!="function")continue;
					i=i.split(/\s/);
					if(i.length < 2)continue;
					var tmp = null;
					if("this" === i[1]){
						tmp=el;
					}else{
						tmp=el.find(i[1]);
					}
					tmp.on(i[0],function(_f){return function(){var _t = this;return _apply(_f,_t,arguments);};}(n));
				}
			}
		},
		valipass1:function(pass){
			var v = pass.val();
			var reg = /.{2,32}/;
			if(!reg.test(v)){
				pass.data("content","<strong>密码长度必须是2~32</strong>");
				pass.popover("show");
				timer(function(){pass.popover('destroy');});
				return false;
			}
			var d = this.psdegree(v);
			if(d<2){
				pass.data("content","<strong>密码强度必须为中或以上</strong>");
				pass.popover("show");
				timer(function(){pass.popover('destroy');});
				return false;
			}
			return true;
		},
		valipass2:function(pass){
			var v = pass.val();
			var comp = pass.attr("comparewith");
			var pass1 = $("#"+comp);			
			if(v === pass1.val())return true;
			pass.popover("show");
			timer(function(){pass.popover('destroy');});
			return false;
		},
		_formvalue:function(form){
			var that = this;
			var od = {};
			var cansubmit=true;
			if(!form || form.length==0)return od;
			
			form.find("input,select,textarea").each(function(){
				var t = $(this);
				if(t.is("disabled")||t.is(".disabled"))return;
				if(t.is("[type=button]"))return;
				var n = t.attr("name")||t.attr("id");
				if(!n)return;
				var p = t.attr("pattern");
				var pf = t.attr("patternfunc");
				if(pf){
					var pfc = that[pf];
					if(typeof pfc === "function"){
						var rfc =_apply(pfc,that,[t]);
						if(rfc === false){
							cansubmit = false;
							return false;
						}
					}
				}
				
				var v=null;
				var cvt = t.data("covert");
				
				if(t.is("input")){
					v=t.val();
				}else if(t.is("select")){
					t.find("option:selected").each(function(){
						if(v===null){
							v=$(this).val();
						}else{
							v=[v];
							v.push($(this).val());
						}
						
					});
				}else{
					v = t.val();
				}
				if(p){
					try{p = new RegExp(p);}catch(e){p=null;}
					if(!p){
						od[n]=that.covert(v,cvt);
						return;
					}
					var arr = v instanceof Array ? v : [v];
					for(var i=0;i<arr.length;i++){
						if(!p.test(arr[i])){
							t.popover("show");
							timer(function(){t.popover('destroy');});
							cansubmit = false;
							return false;
						}
					}
				}
				
				od[n]=that.covert(v,cvt);
				return;
			});
			
			if(!cansubmit){
				return false;
			}
			return od;
		},formSubmit:function(form,options){
			
			options=options||{};
			var od = this._formvalue(form);
			if(!od){
				return false;
			}
			options['type']="POST";
			if(!options.dataType)options.dataType="json";
			if(!options.headers)options.headers={"Accept": "application/json"};
			if(!options.url)options.url=form.attr("action");
			options['data']=od;
			
			$.ajax(options);
		}
		,showform:function(options){
			options=wrapper(typeof options==="function" ? {success:options} : options||{});
			var that = this;
			var ht = _.template(this.rebuildHTML($("#templatelform").html()));
			var id = _uuid_(16);
			var vcsrc = ctx+"/restful/userinfo/vcode";
			var _op_={"id":id,"vcode":vcsrc,"ctx":ctx};
			var mhead = "mheader"+id;
			ht=$(ht(_op_));
			var _op = {
				el:ht,
				events:{
					"hidden.bs.modal this":function(){
						 $(this).remove();
					},
					"click button[type=submit]":function(){
						var sbut=$(this);
						sbut.addClass("disabled");
						timer(function(){sbut.removeClass("disabled")},2500);
						var fn = sbut.attr("form");
						if(!fn)fn="lform"+id;
						var op = wrapper({},function(json){							
							if(json && json.state){
								ht.modal("hide");//删除								
								var us = json.ovalue||{};
								return options.success.apply(that,[]);
							}else{
								json=json||{};
								sbut.removeClass("disabled");
								that.toast(json.svalue||"登陆失败","danger",ht.find("#"+mhead));
								reloadVC();//重新加载验证码
							}
						},function(txt){					
							that.toast(txt||"登陆失败","danger",ht.find("#"+mhead));
							reloadVC();//重新加载验证码
						});
						that.formSubmit($("#"+fn),op);
						return false;
					}
				}
			};
			this._modal(_op);
			var vcm = ht.find("img[name='vcrefresh']");
			function reloadVC(){
				vcm.attr("src",vcsrc+"?t="+Math.random());
			}
			ht.find("[name='vcrefresh']").click(reloadVC);
		},encypt:function(){
			if(!arguments.length||arguments[0]==="")return "";
			var str="";
			for(var i=0;i<arguments.length;i++){
				str = m5.HexMd5(str+arguments[i]);
			}
			return str;
		},toast:function(){
			var msg=arguments[0],warn=arguments[1]||"warn",slp = arguments[1]||2000;//2秒后自动删除	
			if(!msg || msg.length==0)return;
			if(typeof warn==="number"){
				slp = warn;
				warn="warn";
			}
			
			if(slp <= 0 || typeof slp!=="number")slp = 2000;
			var tips="";
			switch(warn){
			case "success":
				tips="成功";
				break;
			case "info":
				tips="信息";
				break;
			case "danger":
				tips="错误";
				break;
			case "warn":
			default:
				tips="警告";
				warn = "warning";
				break;
			}
			var rpl = arguments[3]||arguments[2];
			var tos = $("<div class=\"alert alert-"+warn+" alert-dismissable maxzindex\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\"aria-hidden=\"true\"> &times; </button>"+tips+"！"+msg+"</div>");
			var _func;
			if(typeof rpl !== "undefined" && rpl.constructor === $){
				rpl.before(tos).hide();
				_func=function(){tos.remove();rpl.show();};
				
			}else{
				tos.appendTo("body");
				_func=function(){tos.remove();};
			}
			var _cc = setTimeout(_func,slp);
			tos.bind("closed.bs.alert",function(){
				clearTimeout(_cc);
				if(_func)_func();
			});
		},covert:function(v){
			if(!v)return v;
			if(arguments.length===0)return v;
			var covert = arguments[1];
			if(typeof this[covert] === "function"){
				return this[covert].call(this,v);
			}
			return v;
		},rebuildHTML:function(html){
			//根据需要。修改模板	//如将col-sm改为col-xs		
			return html;
		},psdegree:function(ps){
			var rs = 0;
			if(/\d/.test(ps))rs+=1;
			if(/[a-z]/.test(ps))rs+=2;
			if(/[A-Z]/.test(ps))rs+=4;
			if(/[\s\~\!\@\#\$\%\^\&\*\(\)\_\+\.\\\"\'\:\;\/\<\>]/.test(ps))rs+=8;
			
			var res = 0;
			if((rs & 1)==1)res++;
			if((rs & 2)==2)res++;
			if((rs & 4)==4)res++;
			if((rs & 8)==8)res++;
			
			return res;
		}
	});
	
	_.extend(au.fn,{
		rebuildpassword:function(options){
			if(!options ||!options.uname){
				return _apply((options||{}).error,this,[{code:0,msg:"需要传入账号."}]);
			}
			//实现异步功能
			var that = this;
			var _c;
			if(_c)clearTimeout(_c);
			_c = timer(function(){that._rebuild(options)},100);
			return true;
		},lsttips:null,tips:function(parent,id,index){
			if(typeof parent==="string"){
				id=parent;
				index=id;
				parent=$("body");
			}
			if(!id)return;
			index=index||0;
			var tps = "#tips"+index+id;
			if(tps === this.lsttips)return;
			this.lsttips=tps;
			parent.find(tps).show().siblings().hide();
			return this;
		},_rebuild:function(options){
			options=wrapper(typeof options==="function" ? {success:options} : options||{});
			var id = _uuid_(16);
			var ht = _.template(this.rebuildHTML($("#templatelrpassword").html()));
			var that = this;
			var _op_={
					"uname":options.uname||"anyone",
					"title":options.title||"重置密码",
					"ctx":ctx,
					"id":id
			};
			var mhead = "mheader"+id;
			ht=$( ht(_op_));
			var _op = {
				el:ht,
				events:{
					"hidden.bs.modal this":function(){
						 $(this).remove();
					},
					"click button[type=submit]":function(){
						var sbut=$(this);
						sbut.addClass("disabled");
						timer(function(){sbut.removeClass("disabled")},2500);
						var fn = sbut.attr("form");
						if(!fn)fn="lform"+id;
						var op = wrapper({},function(json){							
							if(json && json.state){
								ht.modal("hide");//删除								
								var us = json.ovalue||{};
								return options.success.apply(that,[]);
							}else{
								json=json||{};
								sbut.removeClass("disabled");
								that.toast(json.svalue||"修改失败","danger",ht.find("#"+mhead));
							}
						},function(txt){					
							that.toast(txt||"修改失败","danger",ht.find("#"+mhead));
						});
						that.formSubmit($("#"+fn),op);
						return false;
					}
				}
			};
			_op.events["keyup #pn"+id]=function(){
				var txt = $(this);
				var v = txt.val();
				var id = txt.attr("id");
				if(v===""){
					that.tips(ht,id,0);
					return;
				}
				var d = that.psdegree(v)||0;
				//if(d>2 && v.length>=8)d++;//如果密码至少含有两种字符。并且字符串长度大于8.那么密码强度加1
				that.tips(ht,id,d);
			};
			_op.events["keyup #p2"+id]=function(){compare($("#pn"+id),$(this));}
			var _xx=null;
			function compare(txt1,txt2){
				if(_xx)clearTimeout(_xx);
				_xx = timer(function(){
					var v1 = txt1.val();
					var v2 = txt2.val();
					var id = txt2.attr("id");
					if(!v2 || v2.length === 0){
						that.tips(id,0);
						return;
					}
					that.tips(ht,id,v2 === v1 ? 2 : 1);
					return;
				},100);
			}
			this._modal(_op);
		}
	});
	return au;//返回一个类
});
})();