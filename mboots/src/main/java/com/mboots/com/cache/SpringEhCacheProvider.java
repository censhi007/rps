package com.mboots.com.cache;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.ObjectExistsException;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.ehcache.EhCacheRegionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public final class SpringEhCacheProvider extends EhCacheRegionFactory {
	
	private static final long serialVersionUID = -4630887168803279377L;

	private static final Logger log = LoggerFactory
			.getLogger(SpringEhCacheProvider.class);

	private Resource configLocation;
	private Resource diskStoreLocation;

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setDiskStoreLocation(Resource diskStoreLocation) {
		this.diskStoreLocation = diskStoreLocation;
	}
	/**
	 * Callback to perform any necessary initialization of the underlying cache
	 * implementation during SessionFactory construction.
	 * <p/>
	 * 
	 * @param properties
	 *            current configuration settings.
	 */
	public final void start(Properties properties) throws CacheException {
		if (manager != null) {
			String s = "Attempt to restart an already started EhCacheProvider. Use sessionFactory.close() "
					+ " between repeated calls to buildSessionFactory. Using previously created EhCacheProvider."
					+ " If this behaviour is required, consider using SingletonEhCacheProvider.";
			log.warn(s);
			return;
		}
		Configuration config = null;
		try {
			if (configLocation != null) {
				config = ConfigurationFactory.parseConfiguration(configLocation
						.getInputStream());
				if (this.diskStoreLocation != null) {
					DiskStoreConfiguration dc = new DiskStoreConfiguration();
					dc.setPath(this.diskStoreLocation.getFile()
							.getAbsolutePath());
					try {
						config.addDiskStore(dc);
					} catch (ObjectExistsException e) {
						String s = "if you want to config distStore in spring,"
								+ " please remove diskStore in config file!";
						log.warn(s, e);
					}
				}
			}
		} catch (IOException e) {
			log.warn("create ehcache config failed!", e);
		}
		if (config != null) {
			config.setName(UUID.randomUUID().toString());
			manager = new CacheManager(config);
		} else {
			manager = new CacheManager();
		}
		
		mbeanRegistrationHelper.registerMBean( manager, properties );
	}	
	/**
	 * Callback to perform any necessary cleanup of the underlying cache
	 * implementation during SessionFactory.close().
	 */
	public final void stop() {
		if (manager != null) {
			manager.shutdown();
			manager = null;
		}
	}

	/**
	 * Not sure what this is supposed to do.
	 * 
	 * @return false to be safe
	 */
	public final boolean isMinimalPutsEnabledByDefault() {
		return false;
	}
}
