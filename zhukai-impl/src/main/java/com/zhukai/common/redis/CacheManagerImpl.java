package com.zhukai.common.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.zhukai.util.SerializableUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class CacheManagerImpl implements CacheManager {

	/**
	 * 默认情况下返回的filedName
	 */
	private static final String DEFAULT_FIELD = "default-field";

	/**
	 * 配置不同环境不同项目的key前辍
	 */
	@Value("${dubbo.app.name}")
	private String appName;

	private JedisSentinelPool jedisPool;

	public JedisSentinelPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisSentinelPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 无论是key为null或是配置的key前辍为null,都返回key的值
	 * 
	 * @param key
	 * @return
	 */
	private String processKey(String key) {

		if (StringUtils.isBlank(key) || StringUtils.isBlank(appName))
			return key;

		return appName + "_" + key;

	}

	private void returnResource(Jedis redis) {
		if (redis != null) {
			redis.close();
		}
	}

	public List<String> Keys(String keyPattern) {

		List<String> list = new ArrayList<String>();

		/**
		 * 如果用户传递的表达式为*,则直接返回空串
		 */
		if (StringUtils.isNotBlank(keyPattern) && keyPattern.equals("*"))
			return list;

		keyPattern = processKey(keyPattern);
		Jedis jredis = null;

		try {
			jredis = getJedis();
			Set<String> keySet = jredis.keys(keyPattern);

			Iterator<String> itera = keySet.iterator();

			while (itera.hasNext()) {
				list.add(itera.next());
			}

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}

		return list;
	}

	public boolean remonKeys(String keyPattern) {

		/**
		 * 如果用户传递的表达式为*,则直接返回空串
		 */
		if (StringUtils.isNotBlank(keyPattern) && keyPattern.equals("*"))
			return false;

		keyPattern = processKey(keyPattern);
		Jedis jredis = null;

		try {
			jredis = getJedis();
			Set<String> keySet = jredis.keys(keyPattern);

			Iterator<String> itera = keySet.iterator();

			while (itera.hasNext()) {
				jredis.del(itera.next());
			}
			return true;

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);

		}

	}

	public void setValue(String key, String value, Integer expireSeconds) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.setex(key, expireSeconds, value);

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void setValue(String key, String value) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.set(key, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public Long remove(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.del(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public String getValue(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.get(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}

	}

	public Map<String, String> getAllMap(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.hgetAll(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void pushToListHead(String key, String[] values) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.lpush(key, values);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void pushToListHead(String key, String value) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.lpush(key, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void pushToListFooter(String key, String[] values) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.rpush(key, values);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void pushToListFooter(String key, String value) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.rpush(key, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public String popListHead(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.lpop(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public String popListFooter(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.rpop(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public String findListItem(String key, long index) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.lindex(key, index);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public List<String> findLists(String key, long start, long end) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.lrange(key, start, end);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}

	}

	public long listLen(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.llen(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void addSet(String key, String[] values) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.sadd(key, values);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public String popSet(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.spop(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public boolean existsInSet(String key, String member) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.sismember(key, member);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public Set<String> findSetAll(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.smembers(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public long findSetCount(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.scard(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}

	}

	@Override
	public <T> void setObject(String key, T t) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			String value = SerializableUtil.convert2String((Serializable) t);
			jredis.set(key, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public <T> void setObject(String key, T t, Integer expireSeconds) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			String value = SerializableUtil.convert2String((Serializable) t);
			jredis.setex(key, expireSeconds, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getObject(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			String value = jredis.get(key);
			if (StringUtils.isBlank(value)) {
				return null;
			}
			return (T) SerializableUtil.convert2Object(value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void addSortSet(String key, String value, long sortNo) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.zadd(key, sortNo, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}

	}

	public Set<String> findSortSets(String key, long start, long end) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.zrange(key, start, end);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public long findSortSetCount(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.zcard(key);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public long findSortSetCount(String key, long min, long max) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.zcount(key, min, max);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public void removeMapValue(String key, String field) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			jredis.hdel(key, field);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	public void setMapValue(String key, String field, String value, int seconds) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();

			CacheExpiredCommand<String> cec = new CacheExpiredCommand<String>();

			cec.setObject(value);
			cec.setExpiredTime(System.currentTimeMillis() + seconds * 1000L);

			jredis.hset(key, field, SerializableUtil.convert2String((Serializable) cec));
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@SuppressWarnings("unchecked")
	public String getMapValue(String key, String field) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			String value = jredis.hget(key, field);

			if (StringUtils.isBlank(value)) {
				return null;
			}
			CacheExpiredCommand<String> cec = (CacheExpiredCommand<String>) SerializableUtil.convert2Object(value);

			if (System.currentTimeMillis() < cec.getExpiredTime()) {
				return cec.getObject();
			} else {
				jredis.hdel(key, field);
				return null;
			}
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public <T> void setMapObject(String key, String field, T t, int seconds) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();

			CacheExpiredCommand<T> cec = new CacheExpiredCommand<T>();

			cec.setObject(t);
			cec.setExpiredTime(System.currentTimeMillis() + seconds * 1000L);
			String value = SerializableUtil.convert2String((Serializable) cec);
			jredis.hset(key, field, value);
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getMapObject(String key, String field) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			String value = jredis.hget(key, field);
			if (StringUtils.isBlank(value)) {
				return null;
			}
			CacheExpiredCommand<T> cec = (CacheExpiredCommand<T>) SerializableUtil.convert2Object(value);
			if (System.currentTimeMillis() < cec.getExpiredTime()) {
				return (T) cec.getObject();
			} else {
				jredis.hdel(key, field);
				return null;
			}
		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public String generateMapFieldByDefault(Object... objArray) {
		StringBuffer sb = new StringBuffer();

		for (Object obj : objArray) {
			sb.append(obj.toString() + "-");
		}
		if (sb.length() > 0) {
			sb = sb.delete(sb.length() - 1, sb.length());
		} else {
			sb.append(DEFAULT_FIELD);
		}
		return sb.toString();
	}

	@Override
	public long decrBy(String key, Integer count) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.decrBy(key, count);

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public long decr(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.decr(key);

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public long incrBy(String key, Integer count) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.incrBy(key, count);

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public long incr(String key) {
		key = processKey(key);
		Jedis jredis = null;
		try {
			jredis = getJedis();
			return jredis.incr(key);

		} catch (Exception e) {
			throw new CacheException(e);
		} finally {
			returnResource(jredis);
		}
	}

	@Override
	public String convertMapValue(String mapValueStr) {
		@SuppressWarnings("unchecked")
		CacheExpiredCommand<String> cec = (CacheExpiredCommand<String>) SerializableUtil.convert2Object(mapValueStr);

		if (System.currentTimeMillis() < cec.getExpiredTime()) {
			return cec.getObject();
		}
		return null;
	}

}
