package com.summer.graduate.controller;

import com.google.gson.Gson;
import com.summer.graduate.util.CommandUtil;
import com.summer.graduate.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

/**
 * @ClassName com.summer.graduate.controller.CommandController
 * @Description TODO
 * @Author summer
 * @Date 2019/4/12 11:31
 * @Version 1.0
 **/
@Controller
public class CommandController {
	@Autowired
	private CommandUtil commandUtil;
	@Autowired
	private RedisUtil redisUtil;

	@ResponseBody
	@RequestMapping(value = "operateRedis.do", method = RequestMethod.GET)
	public String operateRedis(String command) {
		String[] strings = commandUtil.judgeType(command);
		Object result = redisUtil.operateRedis(strings);

		if (result == null) {
			return new Gson().toJson("(nil)");
		} else {
			String s = new Gson().toJson(result);
			return s;
		}
	}

	@ResponseBody
	@RequestMapping(value = "getAllKeys.do", method = RequestMethod.GET)
	public String getAllKeys(@RequestParam(defaultValue = "") String type) {
		String newType = "";
		if (type.equals("0")) {
			newType = "string";
		} else if (type.equals("1")) {
			newType = "hash";
		} else if (type.equals("2")) {
			newType = "list";
		} else if (type.equals("3")) {
			newType = "set";
		} else if (type.equals("4")) {
			newType = "zset";
		} else {
			newType = "";
		}

		Set<String> allkeys = redisUtil.getAllkeysByType(newType);

		return new Gson().toJson(allkeys);
	}

	@ResponseBody
	@RequestMapping(value = "getValueByKey.do", method = RequestMethod.GET)
	public String getValueByKey(String key) {
		Object valueByKey = redisUtil.getValueByKey(key);

		return new Gson().toJson(valueByKey);
	}
}
