package com.summer.graduate.util;

import com.summer.graduate.myEnum.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Slowlog;

import java.util.*;

/**
 * @ClassName com.summer.graduate.util.RedisUtil
 * @Description TODO
 * @Author summer
 * @Date 2019/4/11 11:38
 * @Version 1.0
 **/
@Component
public class RedisUtil {
	@Autowired
	private JedisPool jedisPool;

	//获取redis服务器信息
	public String getRedisInfo() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Client client = jedis.getClient();
			client.info();
			String info = client.getBulkReply();
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			jedis.close();
		}
	}

	// 获取日志列表
	public List<Slowlog> getLogs(long entries) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<Slowlog> logList = jedis.slowlogGet(entries);
			return logList;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取日志条数
	public Long getLogsLen() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long logLen = jedis.slowlogLen();
			return logLen;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 清空日志
	public String logEmpty() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.slowlogReset();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取占用内存大小
	public Long dbSize() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Client client = jedis.getClient();
			client.dbSize();
			return client.getIntegerReply();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}


	//使用Java来直接操控Redis数据库 start
	//Redis数据库一共有string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)五种数据库

	/**
	 * Redis操控
	 */
	public Object operateRedis(String[] command) {
		Jedis jedis = jedisPool.getResource();
		String include = "";
		if (command.length == 1) {
			include = isInclude(command[0].toUpperCase());
		} else {
			include = isInclude(command[0].toUpperCase(), command[1].toUpperCase());
		}

		Object result = null;
		if (include.equals("0")) {
			result = "(error) ERR unknown command '" + command[0] + "'";
		} else if (include.equals("string")) {
			result = operateString(command, jedis);
		} else if (include.equals("hash")) {
			result = operateHash(command, jedis);
		} else if (include.equals("list")) {
			result = operateList(command, jedis);
		} else if (include.equals("set")) {
			result = operateSet(command, jedis);
		} else if (include.equals("sortedSet")) {
			result = operateSortedSet(command, jedis);
		} else if (include.equals("system")) {
			result = operateSystem(command, jedis);
		}
		return result;
	}

	/**
	 * 执行与string类型相关的命令
	 *
	 * @param command
	 * @param jedis
	 * @return
	 */
	private Object operateString(String[] command, Jedis jedis) {
		RedisString redisString = RedisString.valueOf(command[0].toUpperCase());

		Object result = null;

		try {
			switch (redisString) {
				case GET:
					result = jedis.get(command[1]);
					break;
				case SET:
					result = jedis.set(command[1], command[2]);
					break;
				case DECR:
					result = jedis.decr(command[1]);
					break;
				case INCR:
					result = jedis.incr(command[1]);
					break;
				case MGET:
					result = jedis.mget(excludeElement(1, command));
					break;
				case MSET:
					result = jedis.mset(excludeElement(1, command));
					break;
				case SETEX:
					result = jedis.setex(command[1], Integer.valueOf(command[2]), command[3]);
					break;
				case APPEND:
					result = jedis.append(command[1], command[2]);
					break;
				case DECRBY:
					result = jedis.decr(command[1]);
					break;
				case GETBIT:
					result = jedis.getbit(command[1], Long.valueOf(command[2]));
					break;
				case GETSET:
					result = jedis.getSet(command[1], command[2]);
					break;
				case INCRBY:
					result = jedis.incrBy(command[1], Long.valueOf(command[2]));
					break;
				case MSETNX:
					result = jedis.msetnx(excludeElement(1, command));
					break;
				case PSETEX:
					result = jedis.psetex(command[1], Long.valueOf(command[2]), command[3]);
					break;
				case SETBIT:
					result = jedis.setbit(command[1], Long.valueOf(command[2]), command[3]);
					break;
				case SETNX:
					result = jedis.setnx(command[1], command[2]);
					break;
				case STRLEN:
					result = jedis.strlen(command[1]);
					break;
				case GETRANGE:
					result = jedis.getrange(command[1], Long.valueOf(command[2]), Long.valueOf(command[3]));
					break;
				case INCRBYFLOAT:
					result = jedis.incrByFloat(command[1], Double.valueOf(command[2]));
					break;
				default: {
					result = "未知错误";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			System.out.println(e.getMessage());
		}
		return result;
	}

	/**
	 * 执行与hash类型相关的命令
	 *
	 * @param command
	 * @param jedis
	 * @return
	 */
	private Object operateHash(String[] command, Jedis jedis) {
		RedisHash redisHash = RedisHash.valueOf(command[0].toUpperCase());
		Object result = null;

		try {
			switch (redisHash) {
				case HDEL:
					result = jedis.hdel(command[1], excludeElement(2, command));
					break;
				case HGET:
					result = jedis.hget(command[1], command[2]);
					break;
				case HLEN:
					result = jedis.hlen(command[1]);
					break;
				case HSET:
					result = jedis.hset(command[1], command[2], command[3]);
					break;
				case HKEYS:
					result = jedis.hkeys(command[1]);
					break;
				case HMGET:
					result = jedis.hmget(command[1], excludeElement(2, command));
					break;
				case HMSET:
					result = jedis.hmset(command[1], listToMap(command));
					break;
				case HVALS:
					result = jedis.hvals(command[1]);
					break;
				case HSETNX:
					result = jedis.hsetnx(command[1], command[2], command[3]);
					break;
				case HEXISTS:
					result = jedis.hexists(command[1], command[2]);
					break;
				case HGETALL:
					result = jedis.hgetAll(command[1]);
					break;
				case HINCRBY:
					result = jedis.hincrBy(command[1], command[2], Long.valueOf(command[3]));
					break;
				case HINCRBYFLOAT:
					result = jedis.hincrByFloat(command[1], command[2], Double.valueOf(command[3]));
					break;
				default: {
					result = "未知错误";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			System.out.println(e.getMessage());
		}

		return result;
	}

	/**
	 * 执行与list类型相关的命令
	 *
	 * @param command
	 * @param jedis
	 * @return
	 */
	private Object operateList(String[] command, Jedis jedis) {
		RedisList redisList = RedisList.valueOf(command[0].toUpperCase());
		Object result = null;

		try {
			switch (redisList) {
				case LLEN:
					result = jedis.llen(command[1]);
					break;
				case LPOP:
					result = jedis.lpop(command[1]);
					break;
				case LREM:
					result = jedis.lrem(command[1], Long.valueOf(command[2]), command[3]);
					break;
				case LSET:
					result = jedis.lset(command[1], Long.valueOf(command[2]), command[3]);
					break;
				case RPOP:
					result = jedis.rpop(command[1]);
					break;
				case BLPOP:
					result = jedis.blpop(excludeElement(1, command));
					break;
				case BRPOP:
					result = jedis.brpop(excludeElement(1, command));
					break;
				case LPUSH:
					result = jedis.lpush(command[1], excludeElement(2, command));
					break;
				case LTRIM:
					result = jedis.ltrim(command[1], Long.valueOf(command[2]), Long.valueOf(command[3]));
					break;
				case RPUSH:
					result = jedis.rpush(command[1], excludeElement(2, command));
					break;
				case LINDEX:
					result = jedis.lindex(command[1], Long.valueOf(command[2]));
					break;
				case LPUSHX:
					result = jedis.lpushx(command[1], excludeElement(2, command));
					break;
				case LRANGE:
					result = jedis.lrange(command[1], Long.valueOf(command[2]), Long.valueOf(command[3]));
					break;
				case RPUSHX:
					result = jedis.rpushx(command[1], excludeElement(2, command));
					break;
				case LINSERT:
					result = jedis.linsert(command[1], BinaryClient.LIST_POSITION.valueOf(command[2]), command[3], command[4]);
					break;
				case RPOPLPUSH:
					result = jedis.rpoplpush(command[1], command[2]);
					break;
				case BRPOPLPUSH:
					result = jedis.brpoplpush(command[1], command[2], Integer.valueOf(command[3]));
					break;
				default: {
					result = "未知错误";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			System.out.println(e.getMessage());
		}
		return result;
	}

	private Object operateSet(String[] command, Jedis jedis) {
		RedisSet redisSet = RedisSet.valueOf(command[0].toUpperCase());
		Object result = null;

		try {
			switch (redisSet) {
				case SADD:
					result = jedis.sadd(command[1], excludeElement(2, command));
					break;
				case SPOP:
					result = jedis.spop(command[1]);
					break;
				case SREM:
					result = jedis.srem(command[1], excludeElement(2, command));
					break;
				case SCARD:
					result = jedis.scard(command[1]);
					break;
				case SDIFF:
					result = jedis.sdiff(excludeElement(1, command));
					break;
				case SMOVE:
					result = jedis.smove(command[1], command[2], command[3]);
					break;
				case SSCAN:
					result = jedis.sscan(command[1], command[2]);
					break;
				case SINTER:
					result = jedis.sinter(excludeElement(1, command));
					break;
				case SUNION:
					result = jedis.sunion(excludeElement(1, command));
					break;
				case SMEMBERS:
					result = jedis.smembers(command[1]);
					break;
				case SISMEMBER:
					result = jedis.sismember(command[1], command[2]);
					break;
				case SDIFFSTORE:
					result = jedis.sdiffstore(command[1], excludeElement(2, command));
					break;
				case SINTERSTORE:
					result = jedis.sinterstore(command[1], excludeElement(2, command));
					break;
				case SRANDMEMBER:
					result = jedis.srandmember(command[1]);
					break;
				case SUNIONSTORE:
					result = jedis.sunionstore(command[1], excludeElement(2, command));
					break;
				default: {
					result = "未知错误";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			System.out.println(e.getMessage());
		}
		return result;
	}

	private Object operateSortedSet(String[] command, Jedis jedis) {
		RedisSortedSet redisSortedSet = RedisSortedSet.valueOf(command[0].toUpperCase());
		Object result = null;

		try {
			switch (redisSortedSet) {
				case ZADD:
					result = jedis.zadd(command[1], Double.valueOf(command[2]), command[3]);
					break;
				case ZREM:
					result = jedis.zrem(command[1], excludeElement(2, command));
					break;
				case ZCARD:
					result = jedis.zcard(command[1]);
					break;
				case ZRANK:
					result = jedis.zrank(command[1], command[2]);
					break;
				case ZSCAN:
					result = jedis.zscan(command[1], command[2]);
					break;
				case ZCOUNT:
					result = jedis.zcount(command[1], Double.valueOf(command[2]), Double.valueOf(command[3]));
					break;
				case ZRANGE:
					result = jedis.zrange(command[1], Long.valueOf(command[1]), Long.valueOf(command[3]));
					break;
				case ZSCORE:
					result = jedis.zscore(command[1], command[2]);
					break;
				case ZINCRBY:
					result = jedis.zincrby(command[1], Double.valueOf(command[2]), command[3]);
					break;
				case ZREVRANK:
					result = jedis.zrevrank(command[1], command[2]);
					break;
				case ZLEXCOUNT:
					result = jedis.zlexcount(command[1], command[2], command[3]);
					break;
				case ZREVRANGE:
					result = jedis.zrevrange(command[1], Long.valueOf(command[2]), Long.valueOf(command[3]));
					break;
				case ZINTERSTORE:
					result = jedis.zinterstore(command[1], excludeElement(2, command));
					break;
				case ZRANGEBYLEX:
					result = jedis.zrangeByLex(command[1], command[2], command[3]);
					break;
				case ZUNIONSTORE:
					result = jedis.zunionstore(command[1], excludeElement(2, command));
					break;
				case ZRANGEBYSCORE:
					result = jedis.zrangeByScore(command[1], Double.valueOf(command[2]), Double.valueOf(command[3]));
					break;
				case ZREMRANGEBYLEX:
					result = jedis.zremrangeByLex(command[1], command[2], command[3]);
					break;
				case ZREMRANGEBYRANK:
					result = jedis.zremrangeByRank(command[1], Long.valueOf(command[2]), Long.valueOf(command[3]));
					break;
				case ZREMRANGEBYSCORE:
					result = jedis.zremrangeByScore(command[1], command[2], command[3]);
					break;
				case ZREVRANGEBYSCORE:
					result = jedis.zrevrangeByScore(command[1], command[2], command[3]);
					break;
				default: {
					result = "未知错误";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			System.out.println(e.getMessage());
		}
		return result;
	}

	private Object operateSystem(String[] command, Jedis jedis) {

		RedisSystem redisSystem = null;
		if (command[0].equalsIgnoreCase("CLIENT") || command[0].equalsIgnoreCase("CONFIG")
				|| command[0].equalsIgnoreCase("CLUSTER")) {
			redisSystem = RedisSystem.valueOf(command[0].toUpperCase() + command[1].toUpperCase());
		} else {
			redisSystem = RedisSystem.valueOf(command[0].toUpperCase());
		}

		Object result = null;

		try {
			switch (redisSystem) {
				case DEL:
					result = jedis.del(command[1]);
					break;
				case KEYS:
					result = jedis.keys(command[1]);
					break;
				case INFO:
					result = jedis.info();
					break;
				case SAVE:
					result = jedis.save();
					break;
				case SYNC:
					jedis.sync();
					break;
				case TIME:
					result = jedis.time();
					break;
				case BGSAVE:
					result = jedis.bgsave();
					break;
				case DBSIZE:
					result = jedis.dbSize();
					break;
				case SLAVEOF:
					result = jedis.slaveof(command[1], Integer.valueOf(command[2]));
					break;
				case SLOWLOG:
					result = jedis.slowlogGet(Long.valueOf(command[2]));
					break;
				case FLUSHALL:
					result = jedis.flushAll();
					break;
				case LASTSAVE:
					result = jedis.lastsave();
					break;
				case SHUTDOWN:
					result = jedis.shutdown();
					break;
				case CONFIGGET:   //
					result = jedis.configGet(command[2]);
					break;
				case CONFIGSET: //
					result = jedis.configSet(command[2], command[3]);
					break;
				case CLIENTKILL: //
					result = jedis.clientKill(command[2]);
					break;
				case CLIENTLIST: //
					result = jedis.clientList();
					break;
				case CLUSTERSLOTS:
					result = jedis.clusterSlots();
					break;
				case CLIENTGETNAME:
					result = jedis.clientGetname();
					break;
				case CLIENTSETNAME:
					result = jedis.clientSetname(command[2]);
					break;
				case CONFIGRESETSTAT:
					result = jedis.configResetStat();
					break;
				case TYPE:
					result = jedis.type(command[1]);
					break;
				default: {
					result = "未知错误";
				}
			}
		} catch (Exception e) {
			result = e.getMessage();
			System.out.println(e.getMessage());
		}
		return result;
	}
	// end


	/**
	 * 排除数组的前index个元素，第一个元素是命令关键字
	 *
	 * @param command
	 * @return
	 */
	private String[] excludeElement(int index, String[] command) {
		String[] newCommand = new String[command.length - index];

		for (int i = index; i < command.length; i++) {
			newCommand[i - index] = command[i];
		}

		return newCommand;
	}

	/**
	 * 将一个list集合转换为map集合，转换规则是list中两两配对组合成map的键值对
	 * @param command
	 * @return
	 */
	private Map<String, String> listToMap(String[] command) {

		String[] strings = excludeElement(2, command);
		Map<String, String> map = new HashMap<>();

		for (int i = 0; i < strings.length; i+=2) {
			map.put(strings[i], strings[i+1]);
		}
		return map;
	}



	/**
	 * 判断命令是否包含在枚举中
	 *
	 * @param key
	 * @return
	 */
	private String isInclude(String key, String key2) {
		String include = "0";

		for (RedisString e : RedisString.values()) {
			if (e.toString().equals(key)) {
				include = "string";
				break;
			}
		}

		for (RedisHash e : RedisHash.values()) {
			if (e.toString().equals(key)) {
				include = "hash";
				break;
			}
		}

		for (RedisList e : RedisList.values()) {
			if (e.toString().equals(key)) {
				include = "list";
				break;
			}
		}

		for (RedisSet e : RedisSet.values()) {
			if (e.toString().equals(key)) {
				include = "set";
				break;
			}
		}

		for (RedisSortedSet e : RedisSortedSet.values()) {
			if (e.toString().equals(key)) {
				include = "sortedSet";
				break;
			}
		}

		if (key.equalsIgnoreCase("CLIENT") || key.equalsIgnoreCase("CONFIG")
		|| key.equalsIgnoreCase("CLUSTER")) {
			for (RedisSystem e : RedisSystem.values()) {
				if (e.toString().equals(key+key2)) {
					include = "system";
					break;
				}
			}
		} else {
			for (RedisSystem e : RedisSystem.values()) {
				if (e.toString().equals(key)) {
					include = "system";
					break;
				}
			}
		}
		return include;
	}

	private String isInclude(String key) {
		return "system";
	}


	/**
	 * 获取所有的keys
	 * @return
	 */
	private Set<String> getAllKeys() {
		Jedis jedis = jedisPool.getResource();
		return jedis.keys("*");
	}

	/**
	 * 根据类型获取值
	 * @param type
	 * @return
	 */
	public Set<String> getAllkeysByType(String type) {
		Jedis jedis = jedisPool.getResource();
		Set<String> allKeys = getAllKeys();

		Set<String> string = new HashSet<>();
		Set<String> list = new HashSet<>();
		Set<String> set = new HashSet<>();
		Set<String> hash = new HashSet<>();
		Set<String> zset = new HashSet<>();
		for (String key: allKeys) {
			switch (jedis.type(key)) {
				case "string":
					string.add(key);
					break;
				case "list":
					list.add(key);
					break;
				case "set":
					set.add(key);
					break;
				case "hash":
					hash.add(key);
					break;
				case "zset":
					zset.add(key);
					break;
			}
		}

		switch (type) {
			case "string":
				return string;
			case "list":
				return list;
			case "set":
				return set;
			case "hash":
				return hash;
			case "zset":
				return zset;
			default:
				return allKeys;
		}
	}


	public Object getValueByKey(String key) {
		Jedis jedis = jedisPool.getResource();
		String type = jedis.type(key);
		Object result = null;

		switch (type) {
			case "string":
				result = jedis.get(key);
				break;
			case "list":
				result = jedis.lrange(key, 0, jedis.llen(key));
				break;
			case "set":
				result = jedis.smembers(key);
				break;
			case "hash":
				result = jedis.hgetAll(key);
				break;
			case "zset":
				result = jedis.zrange(key, 0, jedis.zcard(key));
				break;
		}

		return result;
	}
}
