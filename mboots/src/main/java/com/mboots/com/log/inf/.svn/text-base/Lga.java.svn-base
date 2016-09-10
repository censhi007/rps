package com.mboots.com.log.inf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lga {
	/**
	 * 具体要记录的日志内容<br/>
	 * 使用EL表达式来解析日志内容<br/>
	 * 其中p+序号表示参数，如：p0,p1<br/>
	 * ret表示函数返回值。<br/>
	 * eg:value="${p0},${ret}"
	 * @return
	 */
	public String value() default "";
	/**
	 * 什么时候记录日志
	 * @return
	 */
	public When when() default When.after;
	public enum When{
        before,
        after
    }
}
