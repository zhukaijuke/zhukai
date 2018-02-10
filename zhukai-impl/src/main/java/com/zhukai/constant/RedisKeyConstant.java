package com.zhukai.constant;

public class RedisKeyConstant {
	
	private RedisKeyConstant() {}
	
	public static final int CACHE_ONE_SECOND = 1;
    public static final int CACHE_ONE_MINUTE = 60;
    public static final int CACHE_THIRTY_MINUTE = 30 * 60;
    public static final int CACHE_ONE_HOUR = 60 * 60;
    public static final int CACHE_ONE_DAY = 1 * 24 * 60 * 60;
    public static final int CACHE_ONE_WEEK = 7 * 24 * 60 * 60;
    public static final int CACHE_ONE_MONTH = 30 * 24 * 60 * 60;
    public static final int CACHE_ONE_YEAR = 365 * 24 * 60 * 60;
	
	public final static String CACHE_USER = "USER";
	
}
