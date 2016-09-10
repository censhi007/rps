package com.mboots.com.log.service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.functions.Functions;
import org.apache.taglibs.standard.lang.jstl.ELEvaluator;
import org.apache.taglibs.standard.lang.jstl.ELException;
import org.apache.taglibs.standard.lang.jstl.VariableResolver;
import org.springframework.beans.factory.annotation.Autowired;

import com.mboots.com.inf.Doi;
import com.mboots.com.log.inf.Lga;
import com.mboots.com.log.inf.Lgsi;
import com.mboots.com.log.model.Lg;
import com.mboots.com.util.WebConfig;
/**
 * 对业务逻辑进行记录
 * @author BUJUN
 *
 */
public class LgService implements Lgsi{
	private static Log log = LogFactory.getLog(LgService.class); 
	static{
        System.setProperty("javax.servlet.jsp.functions.allowed", "true");
	}
	@Autowired
	private Doi dao;

	/**
	 * 用于添加日志
	 */
	public Object createLg(MethodInvocation mi) throws Throwable {
		Method m = mi.getMethod();
		Lga lga = m.getAnnotation(Lga.class);
		Lga.When when = lga.when();
		if(Lga.When.before.equals(when)){
			saveLg(mi,lga,null);
		}
		Object result = mi.proceed();
		if(Lga.When.after.equals(when)){
			saveLg(mi,lga,result);
		}
		return result;
	}
	
	private void saveLg(final MethodInvocation mi,final Lga lga,final Object result){
		try{
		if(lga==null)return;
		 MapVariableResolver vr = new MapVariableResolver(mi, result);
		 ELEvaluator eval = new ELEvaluator(vr);    
         //允许包含函数
         String msg = lga.value();
         if(msg!=null){
        	 if(!"".equals(msg)) msg = WebConfig.localString(msg);
             msg = msg.replaceAll("'", "\"");
             Object obj = eval.evaluate(msg, null, String.class, fns, "fn");
             //记录日志
             log.info(obj);
             if(obj instanceof Lg){
            	 dao.save(obj);            	 
             }else{
            	 Lg lg = (Lg)vr.map.get("ctx");
            	 if(lg == null){
            		 lg = new Lg();   
                 }
            	 
            	 lg.setMsg(obj+"");
            	 dao.save(lg);  
             } 
         }
         vr.map.clear();
         vr = null;
		}catch(Throwable t){
			log.warn("日志记录失败！"+t.getLocalizedMessage());
		}
	}
	
	private class MapVariableResolver implements VariableResolver{
        private Map<String, Object> map;
        public MapVariableResolver(MethodInvocation mi, Object result){
            Object[] args = mi.getArguments();
            map = new LinkedHashMap<String, Object>(args.length+2);
            for(int i=0; i<args.length; i++){
                map.put("p"+i, args[i]);
                if((!map.containsKey("ctx")) && args[i] instanceof HttpServletRequest){
                    map.put("ctx", createLg((HttpServletRequest)args[i]));
                }
            }
            map.put("ret", result);
        }
        
        public Object resolveVariable(String arg0, Object arg1)
                throws ELException {
            if(map.containsKey(arg0)){
                return map.get(arg0);
            }
            return "[no named("+arg0+") value]";
        }        
    }

	/**
	 * 从HttpSession中获取当前用户的客户端信息
	 * @param request
	 * @return
	 */
	private Lg createLg(HttpServletRequest request){
		Lg lg = new Lg();
		HttpSession session = request.getSession();
		String  user=null,
				dwdm=null,
				ip=null,
				os=null,
				explorer=null,
				tel=null;
		if(session!=null){
			user = (String)session.getAttribute("username");
			dwdm = (String)session.getAttribute("dwdm");
			ip = (String)session.getAttribute("ip");
			os = (String)session.getAttribute("os");
			explorer = (String)session.getAttribute("explorer");
			tel = (String)session.getAttribute("tel");
		}
		
		lg.setVdate(new Date());
		lg.setDwdm(dwdm);
		lg.setBrowser(explorer);
		lg.setIp(ip);
		lg.setOs(os);
		lg.setTel(tel);
		lg.setUser(user);			
		return lg;
	}
	
	private static Map<String, Method> fns = new HashMap<String, Method>();    
    static{        
        try {            
            //此处添加jstl中的默认方法
            Method[] methods = Functions.class.getMethods();
            for(Method m : methods){
                if((m.getModifiers()&Modifier.STATIC)==Modifier.STATIC){
                    fns.put("fn:"+m.getName(), m);                    
                }
            }
            //还有一些自己定义的方法，也加入进去
            methods = ELFunctions.class.getMethods();
            for(Method m : methods){
                if((m.getModifiers()&Modifier.STATIC)==Modifier.STATIC){
                    fns.put("fn:"+m.getName(), m);                    
                }
            }
            
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
