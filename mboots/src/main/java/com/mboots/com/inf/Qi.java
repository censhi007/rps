package com.mboots.com.inf;

import java.util.List;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public interface Qi {
	int excuteUpdate();
	<T> T uniqueResult();
	@SuppressWarnings("rawtypes")
	List list();
	void excute();
	void setDao(HibernateDaoSupport dao);
}
