package com.zhukai.common.redis;

import java.io.Serializable;

public class CacheExpiredCommand<T> implements Serializable {

	private static final long serialVersionUID = -5204458451306562323L;

	private T object;

	/**
	 * 过期时间
	 */
	private long expiredTime;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}
}
