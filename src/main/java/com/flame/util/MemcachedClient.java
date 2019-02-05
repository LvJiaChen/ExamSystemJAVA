package com.flame.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import net.rubyeye.xmemcached.XMemcachedClient;

/**
 * memcached缓存
 * 
 * @author biejunbo
 * @date 2014-6-4
 * 
 */
public class MemcachedClient {

	/** 缓存时效 1天 */
	public static final int CACHE_EXP_DAY = 3600 * 24;

	/** 缓存时效 1周 */
	public static final int CACHE_EXP_WEEK = 3600 * 24 * 7;

	/** 缓存时效 1月(最大只能为30天) */
	public static final int CACHE_EXP_MONTH = 3600 * 24 * 30;

	/** 缓存时效 永久 */
	public static final int CACHE_EXP_FOREVER = 0;

	// 默认缓存时效 1月
	private int expired = CACHE_EXP_MONTH;

	private XMemcachedClient memcachedClient;

	public MemcachedClient(String string, Integer port) {
		try {
			this.memcachedClient = new XMemcachedClient(string, port);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public MemcachedClient() {
		super();
	}

	/**
	 * 设置数据到MemCached中，如果已经存在则覆盖
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public boolean set(String key, Object value) {
		return set(key, value, expired);
	}

	/**
	 * 设置数据到MemCached中，如果已经存在则覆盖
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param expire
	 *            过期时长
	 * @return
	 */
	public boolean set(String key, Object value, int expire) {
		try {
			return this.memcachedClient.set(key, expire, value);
		} catch (Exception e) {
			this.checkedException(e);
		}
		return false;
	}

	/**
	 * 从MemCached中获取数据
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public <T> T get(String key) {
		try {
			return this.memcachedClient.get(key);
		} catch (Exception e) {
			this.checkedException(e);
		}
		return null;
	}

	/**
	 * 从MemCached中批量获取数据
	 * 
	 * @param keys
	 *            键列表
	 * @return
	 */
	public <T> Map<String, T> get(Collection<String> keys) {
		try {
			return this.memcachedClient.get(keys);
		} catch (Exception e) {
			this.checkedException(e);
		}
		return Collections.emptyMap();
	}

	/**
	 * 删除MemCached中的数据
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public boolean delete(String key) {
		try {
			return this.memcachedClient.delete(key);
		} catch (Exception e) {
			this.checkedException(e);
		}
		return false;
	}

	/**
	 * 与MemCached交互中的异常处理。
	 * 
	 * @param e
	 */
	protected void checkedException(Exception e) {
		e.printStackTrace();
	}

	public void setExpired(int expired) {
		this.expired = expired;
	}

	public XMemcachedClient getMemcachedClient() {
		return memcachedClient;
	}

	public void setMemcachedClient(XMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}
}
