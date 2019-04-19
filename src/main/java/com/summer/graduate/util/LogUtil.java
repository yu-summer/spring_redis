package com.summer.graduate.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @ClassName com.summer.graduate.util.LogUtil
 * @Description redis工具类，从redis数据库中获取数据
 * @Author summer
 * @Date 2019/3/26 15:16
 * @Version 1.0
 **/
public class LogUtil {
	private RedisTemplate<String, Object> redisTemplate;

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 判断缓存中是否有对应的value
	 *
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}


	/**
	 * 获取list
	 *
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 *
	 * @param key 键
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
