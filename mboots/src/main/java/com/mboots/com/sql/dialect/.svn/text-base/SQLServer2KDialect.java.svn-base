package com.mboots.com.sql.dialect;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class SQLServer2KDialect extends SQLServerDialect {
	public SQLServer2KDialect() {
		super();
		registerFunction("bit_and", new SQLFunctionTemplate(StandardBasicTypes.LONG,"(?1&?2)"));
		registerFunction("date_add", new SQLFunctionTemplate( StandardBasicTypes.DATE, "dateadd(day,?2,?1)" ) );
		registerFunction("as", new AsFunctionOracleTemplate( StandardBasicTypes.STRING, "?1" ));
		registerFunction("ifnull", new AsFuctionCommonTemplate( StandardBasicTypes.STRING, "isnull(?1, ?2)" ) );
		registerFunction("op_mod", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "(?1)%(?2)" ) );
		registerFunction("sqldate", new SQLFunctionTemplate( StandardBasicTypes.DATE, "?1" ) );
		registerFunction("int_to_string", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "convert(varchar,?1)") );
		registerFunction("str_concat", new SQLFunctionTemplate( StandardBasicTypes.STRING, "?1+?2" ) );
		registerFunction("bit_and_convert", new SQLFunctionTemplate(StandardBasicTypes.LONG,"(cast(?1 as int) & cast(?2 as int))"));
		registerFunction("op_div", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "(?1)/(?2)" ) );
		registerFunction("dateDiff", new SQLFunctionTemplate( StandardBasicTypes.DATE, "dateDiff(?1,?2,?3)"));
		registerFunction("to_int", new SQLFunctionTemplate( StandardBasicTypes.INTEGER, "(?1)"));
	}
}
