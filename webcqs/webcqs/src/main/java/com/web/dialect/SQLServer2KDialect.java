package com.web.dialect;

import org.hibernate.Hibernate;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

import com.webcqs.spring.AsFuctionCommonTemplate;
import com.webcqs.spring.AsFunctionOracleTemplate;

public class SQLServer2KDialect extends SQLServerDialect {
	public SQLServer2KDialect() {
		super();
		registerFunction("bit_and", new SQLFunctionTemplate(Hibernate.LONG,"(?1&?2)"));
		registerFunction("date_add", new SQLFunctionTemplate( Hibernate.DATE, "dateadd(day,?2,?1)" ) );
		registerFunction("as", new AsFunctionOracleTemplate( Hibernate.STRING, "?1" ));
		registerFunction("ifnull", new AsFuctionCommonTemplate( Hibernate.STRING, "isnull(?1, ?2)" ) );
		registerFunction("op_mod", new SQLFunctionTemplate( Hibernate.INTEGER, "(?1)%(?2)" ) );
		registerFunction("sqldate", new SQLFunctionTemplate( Hibernate.DATE, "?1" ) );
		registerFunction("int_to_string", new SQLFunctionTemplate( Hibernate.INTEGER, "convert(varchar,?1)") );
		registerFunction("str_concat", new SQLFunctionTemplate( Hibernate.STRING, "?1+?2" ) );
		registerFunction("bit_and_convert", new SQLFunctionTemplate(Hibernate.LONG,"(cast(?1 as int) & cast(?2 as int))"));
		registerFunction("op_div", new SQLFunctionTemplate( Hibernate.INTEGER, "(?1)/(?2)" ) );
		registerFunction("dateDiff", new SQLFunctionTemplate( Hibernate.DATE, "dateDiff(?1,?2,?3)"));
		registerFunction("to_int", new SQLFunctionTemplate( Hibernate.INTEGER, "(?1)"));
		registerHibernateType(-1, Hibernate.STRING.getName()); 
		registerHibernateType(-9, Hibernate.STRING.getName()); 
	}
}
