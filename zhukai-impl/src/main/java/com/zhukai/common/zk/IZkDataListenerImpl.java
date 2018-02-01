package com.zhukai.common.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class IZkDataListenerImpl implements IZkDataListener {

	protected static final Logger log = LoggerFactory.getLogger(IZkStateListenerImpl.class);

	@Value("${zk.log.change.path}")
    private String logChangePath;
	
	@Override
	public void handleDataChange(String dataPath, Object data) throws Exception {
		// 动态调整日志
		if (logChangePath.equals(dataPath) && data != null) {
			this.changeLogLevel(data);
		}
	}

	@Override
	public void handleDataDeleted(String dataPath) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 动态调整日志
	 * 
	 * @param data
	 */
	private void changeLogLevel(Object data) {
		String strSource = (String) data;

		String[] strTops = strSource.split(";");

		for (int i = 0; i < strTops.length; i++) {
			String[] strs = strTops[i].split("-");

			if (strs.length > 1) {
				// 例 : org.apache.zookeeper-ERROR   使org.apache.zookeeper包下指定为ERROR日志级别
				String packagePath = strs[0];
				String strlevel = strs[1];
				Level level = Level.toLevel(strlevel);
				org.apache.log4j.Logger logger = LogManager.getLogger(packagePath);
				logger.setLevel(level);
			} else if (strs.length == 1) {
				Level level = Level.toLevel(strSource);
				LogManager.getRootLogger().setLevel(level);
			}
		}
		if (log.isInfoEnabled()) {
			log.info(" change log level :" + strSource);
		}

	}

}
