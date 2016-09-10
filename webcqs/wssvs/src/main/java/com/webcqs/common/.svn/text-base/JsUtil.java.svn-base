package com.webcqs.common;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsUtil {
	private static ScriptEngineManager manager;
	private static ScriptEngine engine;
	/**
	 * 执行脚本
	 * @param script
	 * @return
	 * @throws ScriptException
	 */
	public static Object evel(String script) throws ScriptException{
		if(engine == null){
			manager = new ScriptEngineManager();
			engine = manager.getEngineByName("JavaScript");
		}
		return engine.eval(script);
	}
	/**
	 * 带参数的情况下执行脚本
	 * @param script
	 * @param vars
	 * @return
	 * @throws ScriptException
	 */
	public static Object evel(String script,Bindings vars) throws ScriptException{
		if(engine == null){
			manager = new ScriptEngineManager();
			engine = manager.getEngineByName("JavaScript");
		}
		return engine.eval(script,vars);
	}
	
}
