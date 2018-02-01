package com.zhukai.common;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContextException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.zhukai.common.zk.IZkStateListenerImpl;
import com.zhukai.common.zk.ZkSerializerImpl;

public class PropertiesConfig extends PropertyPlaceholderConfigurer implements Watcher {

	protected static final Logger log = LoggerFactory.getLogger(IZkStateListenerImpl.class);

	// 重写zookeeper中存储的配置
	private Resource[] overrideLocaltions;

	private final static Properties sysProp = new Properties();

	/**
	 * 获取所有系统属性
	 * 
	 * @return
	 */
	public static Properties getProps() {
		return sysProp;
	}

	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties zkprops)
			throws BeansException {

		String zkhost = zkprops.getProperty("zk.host");
		String znodes = zkprops.getProperty("zk.config.root");
		log.info("zkhost:" + zkhost);
		log.info("znodes:" + znodes);
		ZkClient zk = null;
		try {
			zk = new ZkClient(zkhost, 30000, 30000, new ZkSerializerImpl());
		} catch (Exception e) {
			log.error("Failed to connect to zk server" + zkhost, e);
			throw new ApplicationContextException("Failed to connect to zk server" + zkhost, e);
		}

		try {
			for (String znode : znodes.split(",")) {
				List<String> children = zk.getChildren(znode.trim());
				for (String child : children) {
					try {
						String value = zk.readData(znode + "/" + child);

						log.info("Zookeeper key:{}\t value:{}", child, value);
						zkprops.setProperty(child, value);
					} catch (Exception e) {
						log.error("Read property(key:{}) error", child);
						log.error("Exception:", e);
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed to get property from zk server" + zkhost, e);
			throw new ApplicationContextException("Failed to get property from zk server" + zkhost, e);
		} finally {
			try {
				zk.close();
			} catch (Exception e) {
				log.error("Error found when close zookeeper connection.", e);
			}
		}

		Properties overProps = queryOverrideLocation();

		// 将扩展的配置信息覆盖至zookeeper获取的属性
		copyProperties(zkprops, overProps);
		// 保存到静态属性中，供其它非spring托管理的类获取配置信息
		saveProperties(zkprops);
		// 与spring进行结合
		super.processProperties(beanFactoryToProcess, zkprops);
	}

	/**
	 * 将source中的属性覆盖到dest属性中
	 * 
	 * @param dest
	 * @param source
	 */
	private void copyProperties(Properties dest, Properties source) {

		Enumeration<?> enums = source.propertyNames();

		while (enums.hasMoreElements()) {
			String key = (String) enums.nextElement();
			dest.put(key, source.get(key));
		}

	}

	/**
	 * 保存到本地内存中，可作为非spring bean代码中获取配置的方式
	 * 
	 * @param props
	 */
	private void saveProperties(Properties props) {

		Enumeration<?> enums = props.propertyNames();

		while (enums.hasMoreElements()) {
			String key = (String) enums.nextElement();
			sysProp.put(key, props.get(key));
		}

	}

	// 获取扩展配置的properties
	private Properties queryOverrideLocation() {

		Properties props = new Properties();

		if (overrideLocaltions != null) {

		}
		for (Resource resource : overrideLocaltions) {
			
			// 下面PathMatchingResourcePatternResolver类可以用于路径通配符找到Resource
			// 例如location="classpath*:ext.properties"
			// PathMatchingResourcePatternResolver pmrpr = new PathMatchingResourcePatternResolver();
			// Resource[] resource = pmrpr.getResources(location);
			try {
				Properties prop = PropertiesLoaderUtils.loadProperties(resource);

				copyProperties(props, prop);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

		return props;

	}

	public void process(WatchedEvent event) {
	}

	public Resource[] getOverrideLocaltions() {
		return overrideLocaltions;
	}

	public void setOverrideLocaltions(Resource[] overrideLocaltions) {
		this.overrideLocaltions = overrideLocaltions;
	}

}