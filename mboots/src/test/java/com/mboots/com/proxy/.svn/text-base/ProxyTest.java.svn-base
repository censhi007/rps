package com.mboots.com.proxy;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProxyTest {
	private Set<Object> set;
	
	@Before
	public void init(){
		set = new MProxy().setTarget(new HashSet<Object>()).get();
	}
	
	@Test
	public void test(){
		set.add("OO add");
		set.add("string 2");
		int l = set.size();		
		Assert.assertEquals(2,l);
	}
	
	@After
	public void end(){
		set=null;
	}
}
