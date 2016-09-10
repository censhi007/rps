package com.mboots.com.sql.dialect;

import java.util.List;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class AsFunctionOracleTemplate extends SQLFunctionTemplate{

	/**
	 * Constructs a SQLFunctionTemplate
	 *
	 * @param type The functions return type
	 * @param template The function template
	 */
	public AsFunctionOracleTemplate(Type type, String template) {
		this( type, template, true );
	}

	/**
	 * Constructs a SQLFunctionTemplate
	 *
	 * @param type The functions return type
	 * @param template The function template
	 * @param hasParenthesesIfNoArgs If there are no arguments, are parentheses required?
	 */
	public AsFunctionOracleTemplate(Type type, String template, boolean hasParenthesesIfNoArgs) {
		super(type,template,hasParenthesesIfNoArgs);
	}


	@SuppressWarnings("rawtypes")
	public String render(Type argumentType,List args,  SessionFactoryImplementor factory) {
		String sql = "";
		if (args.size() > 0){
			String[] test = args.get(0).toString().split(" as ");
			StringBuffer sqlb = new StringBuffer();
			for (int i = 0; i < test.length; i++) {
				sqlb.append(test[i]);
				if (test[i].indexOf("cast(") > 0)
					sqlb.append(" as ");
				else {
					sqlb.append(" ");
				}
				System.out.println(test[i]);
			}
			sql = sqlb.toString();
		}
		return sql;
	}
}
