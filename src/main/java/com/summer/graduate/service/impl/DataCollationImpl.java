package com.summer.graduate.service.impl;

import com.summer.graduate.Comparators.MapValueComparator;
import com.summer.graduate.entities.Alert;
import com.summer.graduate.entities.RedisLog;
import com.summer.graduate.util.GsonUtil;
import com.summer.graduate.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName DataCollationImpl
 * @Description TODO
 * @Author summer
 * @Date 2019/3/26 17:56
 * @Version 1.0
 **/
@Service
public class DataCollationImpl {
	@Autowired
	private LogUtil redisUtil;

	@Autowired
	private GsonUtil gsonUtil;

	public Map<String, Object> dataResult() {
		List<String> lrange = (List<String>) (List) redisUtil.lGet("log", 0, redisUtil.lGetListSize("log"));

		//存放来源IP
		Map<String, Integer> srcIP = new HashMap<>();
		//存放来源port
		Map<String, Integer> srcPort = new HashMap<>();
		//存放目的ip
		Map<String, Integer> destIP = new HashMap<>();
		//存放目的port
		Map<String, Integer> destPort = new HashMap<>();
		//存放目的alert
		Map<Object, Integer> alert = new HashMap<>();

		List<RedisLog> allLogs = new ArrayList<>();

		for (String log : lrange) {
			RedisLog redisLog = gsonUtil.jsonToBean(log);
			//放入来源ip
			if (redisLog.getSrc_ip() != null) {
				String ip = redisLog.getSrc_ip();
				if (!srcIP.containsKey(ip)) {
					srcIP.put(ip, 1);
				} else {
					srcIP.put(ip, srcIP.get(ip) + 1);
				}
			}
			//放入来源port
			if (redisLog.getSrc_port() != null) {
				String port = redisLog.getSrc_port();
				if (!srcPort.containsKey(port)) {
					srcPort.put(port, 1);
				} else {
					srcPort.put(port, srcPort.get(port) + 1);
				}
			}
			//放入目的ip
			if (redisLog.getDest_ip() != null) {
				String ip = redisLog.getDest_ip();
				if (!destIP.containsKey(ip)) {
					destIP.put(ip, 1);
				} else {
					destIP.put(ip, destIP.get(ip) + 1);
				}
			}
			//放入目的port
			if (redisLog.getDest_port() != null) {
				String port = redisLog.getDest_port();
				if (!destPort.containsKey(port)) {
					destPort.put(port, 1);
				} else {
					destPort.put(port, destPort.get(port) + 1);
				}
			}

			//放入alert
			if (redisLog.getAlert() != null) {

				Alert alert1 = redisLog.getAlert();
				//放入alert
				if (!alert.containsKey(alert1.getSignature())) {
					alert.put(alert1.getSignature(), 1);
				} else {
					alert.put(alert1.getSignature(), alert.get(alert1.getSignature()) + 1);
				}

				allLogs.add(redisLog);
			}

		}

		Map<String, Integer> srcIP2 = sortMapByValue(srcIP);
		Map<String, Integer> srcPort2 = sortMapByValue(srcPort);
		Map<String, Integer> descIP2 = sortMapByValue(destIP);
		Map<String, Integer> descPort2 = sortMapByValue(destPort);

		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("fin_allLogs", allLogs);           //所有存在警告的日志
		resultMap.put("fin_srcIP", getTop20(srcIP2));
		resultMap.put("fin_srcPort", getTop20(srcPort2));
		resultMap.put("fin_descIP", getTop20(descIP2));
		resultMap.put("fin_descPort", getTop20(descPort2));
		resultMap.put("allCount", lrange.size());
		resultMap.put("alertType", alert);

		return resultMap;
	}


	/**
	 * 使用 Map按value进行排序
	 *
	 * @param oriMap
	 * @return
	 */
	private static Map<String, Integer> sortMapByValue(Map<String, Integer> oriMap) {
		if (oriMap.isEmpty()) {
			return oriMap;
		}
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		List<Map.Entry<String, Integer>> entryList = new ArrayList<>(
				oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, Integer>> iter = entryList.iterator();
		Map.Entry<String, Integer> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	/**
	 * 获取前20的数据
	 *
	 * @param map
	 * @return
	 */
	private static Map<String, Integer> getTop20(Map<String, Integer> map) {

		if (map.size() > 20) {
			Map<String, Integer> topMap = new HashMap<>();

			int i = 0;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				i++;
				topMap.put("'" + entry.getKey() + "'", entry.getValue());
				if (i >= 20) break;
			}
			return topMap;
		} else {
			Map<String, Integer> topMap = new HashMap<>();
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				topMap.put("'" + entry.getKey() + "'", entry.getValue());
			}
			return topMap;
		}
	}
}
