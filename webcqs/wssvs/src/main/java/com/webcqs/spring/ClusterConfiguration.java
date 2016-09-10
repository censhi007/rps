package com.webcqs.spring;

import java.util.Iterator;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Settings;
import org.hibernate.engine.Mapping;
import org.hibernate.event.EventListeners;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.util.PropertiesHelper;

public class ClusterConfiguration extends Configuration {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3400316215216525894L;
	private transient Mapping mapping = buildMapping();
	
	@SuppressWarnings("rawtypes")
	private void validate() throws MappingException {
		Iterator iter = classes.values().iterator();
		while ( iter.hasNext() ) {
			( (PersistentClass) iter.next() ).validate( mapping );
		}
		iter = collections.values().iterator();
		while ( iter.hasNext() ) {
			( (Collection) iter.next() ).validate( mapping );
		}
	}
	
	/**
	 * Instantiate a new <tt>SessionFactory</tt>, using the properties and
	 * mappings in this configuration. The <tt>SessionFactory</tt> will be
	 * immutable, so changes made to the <tt>Configuration</tt> after
	 * building the <tt>SessionFactory</tt> will not affect it.
	 *
	 * @return a new factory for <tt>Session</tt>s
	 * @see org.hibernate.SessionFactory
	 */
	public SessionFactory buildSessionFactory() throws HibernateException {
		secondPassCompile();
		validate();
		Environment.verifyProperties( super.getProperties());
		Properties copy = new Properties();
		copy.putAll( super.getProperties() );
		PropertiesHelper.resolvePlaceHolders( copy );
		Settings settings = buildSettings( copy );

		return new SessionFactoryImpl(
				this,
				mapping,
				settings,
				getInitializedEventListeners(),
				super.getSessionFactoryObserver()
			);
	}
	
	private EventListeners getInitializedEventListeners() {
		EventListeners result = (EventListeners) super.getEventListeners().shallowCopy();
		result.initializeListeners( this );
		return result;
	}	
}
