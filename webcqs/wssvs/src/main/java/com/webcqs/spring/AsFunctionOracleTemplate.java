package com.webcqs.spring;

import java.util.List;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class AsFunctionOracleTemplate extends SQLFunctionTemplate{
	
	public AsFunctionOracleTemplate(Type type, String template) {
		super(type, template);
	}

	@SuppressWarnings("rawtypes")
	public String render(List args, SessionFactoryImplementor factory) {
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
