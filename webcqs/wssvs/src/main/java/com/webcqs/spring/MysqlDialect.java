package com.webcqs.spring;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class MysqlDialect extends MySQL5Dialect{
	public MysqlDialect() {
		super();
		registerFunction("bit_and", new SQLFunctionTemplate(Hibernate.LONG,"?1&?2"));
		registerFunction("bit_and_convert", new SQLFunctionTemplate(Hibernate.LONG,"?1&?2"));
		registerFunction("date_add", new SQLFunctionTemplate( Hibernate.DATE, "date_add(?1, INTERVAL ?2 ?3)" ) ); 
		registerFunction("str_concat", new SQLFunctionTemplate( Hibernate.STRING, "concat(?1,?2)" ) );
		registerFunction("op_div", new SQLFunctionTemplate( Hibernate.INTEGER, "?1 div ?2" ) );
		registerFunction("as", new AsFuctionCommonTemplate( Hibernate.INTEGER, "?1 " ) );
		registerFunction("ifnull", new AsFuctionCommonTemplate( Hibernate.OBJECT, "ifnull(?1, ?2)" ) );
		registerFunction("if", new AsFuctionCommonTemplate( Hibernate.OBJECT, "if(?1, ?2, ?3)" ) );
		registerFunction("int_to_string", new AsFuctionCommonTemplate( Hibernate.INTEGER, "?1" ) );
		registerFunction("sqldate", new SQLFunctionTemplate( Hibernate.DATE, "?1" ) );
		registerFunction("op_mod", new SQLFunctionTemplate( Hibernate.INTEGER, "mod(?1,?2)" ) );
		registerFunction("to_int", new SQLFunctionTemplate( Hibernate.INTEGER, "(?1)"));
	}
}
