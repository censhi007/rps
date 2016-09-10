package com.webcqs.common;

import javax.script.Bindings;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * JSTest
 * @author Administrator
 *
 */
public class JSUTst {
	private String script;
	@Test(groups = { "test" }) 
	public void eval1(){
		try {
			Object o = JsUtil.evel(script);
			Assert.assertEquals(o, 3);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	@Test(groups = { "test" }) 
	public void eval2(){
		try {
			Bindings obj = new SimpleBindings();
			obj.put("obj", 1);
			Object o = JsUtil.evel(script,obj);
			Assert.assertEquals(o, 1);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	@BeforeClass(alwaysRun=true)
	public void before(){
		script="function(){println('Start script \r\nhello world!\r\n');println('End script\r\n');return obj||3;}();";
	}
	@AfterClass(alwaysRun=true)
	public void after(){
		
	}
}
