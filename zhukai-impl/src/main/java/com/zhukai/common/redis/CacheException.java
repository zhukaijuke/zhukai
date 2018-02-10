package com.zhukai.common.redis;

public class CacheException extends RuntimeException {

	private static final long serialVersionUID = -2872031076742395597L;

	public CacheException() {
		super("cache operator exception");
	}

	public CacheException(Exception e) {
		super("cache operator exception", e);
	}

	public CacheException(String str) {
		super("cache operator exception:" + str);
	}
}
