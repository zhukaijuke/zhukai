package com.zhukai.common.zk;

import org.I0Itec.zkclient.IZkStateListener;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IZkStateListenerImpl implements IZkStateListener {

	protected static final Logger log = LoggerFactory.getLogger(IZkStateListenerImpl.class);
	
	/**
	 * Disconnected 表示断开
	 * SyncConnected 表示重连上
	 * Expired session 过期
	 */
	@Override
	public void handleStateChanged(KeeperState state) throws Exception {
		
		if(KeeperState.Disconnected.equals(state)){
			log.info("KeeperState.Disconnected 断开");
		} else if(KeeperState.SyncConnected.equals(state)){
			log.info("KeeperState.SyncConnected 重连上");
		} else if(KeeperState.Expired.equals(state)){
			log.info("KeeperState.Expired 过期");
		}
	}

	@Override
	public void handleNewSession() throws Exception {
		log.info("handleNewSession");
	}

}
