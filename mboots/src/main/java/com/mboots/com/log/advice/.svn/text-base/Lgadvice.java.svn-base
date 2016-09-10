package com.mboots.com.log.advice;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import com.mboots.com.log.inf.Lga;
import com.mboots.com.log.inf.Lgsi;
/**
 * 用于处理切片的拦截器。
 * @author BUJUN
 *
 */
public class Lgadvice implements MethodInterceptor{

	/**
	 * 执行具体的方法
	 */
	public Object invoke(MethodInvocation arg0) throws Throwable {
		if(lgs==null)return arg0.proceed();
		Method m = arg0.getMethod();
		if(m.getAnnotation(Lga.class)==null){
			System.out.println("Nerver here!");
			return arg0.proceed();		
		}
		
		return lgs.createLg(arg0);
	}

	@Autowired
	protected Lgsi lgs;

	public Lgsi getLgs() {
		return lgs;
	}

	public void setLgs(Lgsi lgs) {
		this.lgs = lgs;
	}
	
}
