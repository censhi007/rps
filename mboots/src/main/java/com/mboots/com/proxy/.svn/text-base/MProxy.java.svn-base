package com.mboots.com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MProxy implements InvocationHandler{
	private Object target;
	private Object bean;
	private String name;
	public MProxy setTarget(Object target) {
		this.target = target;
		return this;
	}
	public void afterPropertySet(){
		if(target!=null){
			bean = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
			name = target.getClass().getName();
		}
	}
	@SuppressWarnings("unchecked")
	public <t> t get(){
		if(bean==null)afterPropertySet();
		return (t)bean;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("run "+name+"`s method:"+method.getName());
		return method.invoke(target, args);
	}

}
