package com.summer.graduate.service;

import com.summer.graduate.Comparators.MapValueComparator;
import com.summer.graduate.util.RedisUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName com.summer.graduate.service.impl.DataCollationImpl
 * @Description TODO
 * @Author summer
 * @Date 2019/3/26 17:56
 * @Version 1.0
 **/
@Service
public class DataCollationImpl {
	@Autowired
	private RedisUtil redisUtil;


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

		List<JSONObject> allLogs = new ArrayList<>();

		for (String log : lrange) {
			JSONObject jsonObject = new JSONObject(log);
			//放入来源ip
			if (jsonObject.has("src_ip")) {
				String ip = jsonObject.getString("src_ip");
				if (!srcIP.containsKey(ip)) {
					srcIP.put(ip, 1);
				} else {
					srcIP.put(ip, srcIP.get(ip) + 1);
				}
			}
			//放入来源port
			if (jsonObject.has("src_port")) {
				String port = jsonObject.get("src_port").toString();
				if (!srcPort.containsKey(port)) {
					srcPort.put(port, 1);
				} else {
					srcPort.put(port, srcPort.get(port) + 1);
				}
			}
			//放入目的ip
			if (jsonObject.has("dest_ip")) {
				String ip = jsonObject.getString("dest_ip");
				if (!destIP.containsKey(ip)) {
					destIP.put(ip, 1);
				} else {
					destIP.put(ip, destIP.get(ip) + 1);
				}
			}
			//放入目的port
			if (jsonObject.has("dest_port")) {
				String port = jsonObject.get("dest_port").toString();
				if (!destPort.containsKey(port)) {
					destPort.put(port, 1);
				} else {
					destPort.put(port, destPort.get(port) + 1);
				}
			}

			//放入alert
			if (jsonObject.has("alert")) {
				if (jsonObject.has("alert")) {
					JSONObject jsonAlert = new JSONObject(jsonObject.get("alert").toString());
					//放入alert
					if (!alert.containsKey(jsonAlert.get("signature"))) {
						alert.put(jsonAlert.get("signature").toString(), 1);
					} else {
						alert.put(jsonAlert.get("signature").toString(), alert.get(jsonAlert.get("signature").toString()) + 1);
					}
				}
				allLogs.add(jsonObject);
			}

		}

		Map<String, Integer> srcIP2 = sortMapByValue(srcIP);
		Map<String, Integer> srcPort2 = sortMapByValue(srcPort);
		Map<String, Integer> descIP2 = sortMapByValue(destIP);
		Map<String, Integer> descPort2 = sortMapByValue(destPort);

		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("fin_allLogs", allLogs);
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
