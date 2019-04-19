package com.summer.graduate.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.summer.graduate.entities.RedisLog;
import org.springframework.stereotype.Component;

/**
 * @ClassName com.summer.graduate.util.GsonUtil
 * @Description json工具类
 * @Author summer
 * @Date 2019/4/11 9:55
 * @Version 1.0
 **/
@Component
public class GsonUtil {

	/**
	 * 将json字符串转化成bean
	 */
	public RedisLog jsonToBean(String json) {
		Gson gson = new GsonBuilder().create();
		RedisLog redisLog = gson.fromJson(json, RedisLog.class);
		return redisLog;
	}
}
