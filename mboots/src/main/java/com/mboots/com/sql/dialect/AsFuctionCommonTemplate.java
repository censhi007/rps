package com.mboots.com.sql.dialect;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.Type;

public class AsFuctionCommonTemplate extends SQLFunctionTemplate {

	public AsFuctionCommonTemplate(Type type, String template) {
		super(type, template);
	}

}
