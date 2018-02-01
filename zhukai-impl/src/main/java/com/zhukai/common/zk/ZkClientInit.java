package com.zhukai.common.zk;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 在spring-zk中加载此类, 配置需要监听的路径
 * 
 * @author Juke
 */
public class ZkClientInit {
	
	@Autowired
	private ZkClient zkClient;
	@Autowired(required=false)
	private IZkDataListener iZkDataListener;
	@Autowired(required=false)
	private IZkChildListener iZkChildListener;
	@Autowired(required=false)
	private IZkStateListener iZkStateListener;
	
	private boolean isSubscribeStateChanges = false;
	/** 需要设置监听的path */
	private List<String> dataChangePathList;
	/** 需要设置监听的path */
	private List<String> dataChildPathList;
	
	public void init() {
		// 监听State
		if (isSubscribeStateChanges) {
			zkClient.subscribeStateChanges(iZkStateListener);
		}
		// 监听dataChange
		if (dataChangePathList != null) {
			for (String path : dataChangePathList) {
				zkClient.subscribeDataChanges(path, iZkDataListener);
			}
		}
		// 监听dataChild
		if (dataChildPathList != null) {
			for (String path : dataChildPathList) {
				zkClient.subscribeChildChanges(path, iZkChildListener);
			}
		}
	}

	public boolean getIsSubscribeStateChanges() {
		return isSubscribeStateChanges;
	}

	public void setIsSubscribeStateChanges(boolean isSubscribeStateChanges) {
		this.isSubscribeStateChanges = isSubscribeStateChanges;
	}

	public List<String> getDataChangePathList() {
		return dataChangePathList;
	}

	public void setDataChangePathList(List<String> dataChangePathList) {
		this.dataChangePathList = dataChangePathList;
	}

	public List<String> getDataChildPathList() {
		return dataChildPathList;
	}

	public void setDataChildPathList(List<String> dataChildPathList) {
		this.dataChildPathList = dataChildPathList;
	}
	
}
