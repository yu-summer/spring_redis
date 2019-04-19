package com.summer.graduate.controller;

import com.google.gson.Gson;
import com.summer.graduate.entities.Operate;
import com.summer.graduate.entities.RedisInfoDetail;
import com.summer.graduate.loginInterceptor.IsCheckUserLogin;
import com.summer.graduate.service.impl.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName com.summer.graduate.controller.RedisController
 * @Description TODO
 * @Author summer
 * @Date 2019/4/11 11:52
 * @Version 1.0
 **/
@Controller
public class RedisController {
	@Autowired
	private RedisService redisService;

	//跳转到监控页面
	@IsCheckUserLogin(check = true)
	@RequestMapping(value="redisMonitor.do")
	public String redisMonitor(Model model) {
		//获取redis的info
		List<RedisInfoDetail> ridList = redisService.getRedisInfo();
		//获取redis的日志记录
		List<Operate> logList = redisService.getLogs(1000);
		//获取日志总数
		long logLen = redisService.getLogLen();
		model.addAttribute("infoList", ridList);
		model.addAttribute("logList", logList);
		model.addAttribute("logLen", logLen);
		return "redisMonitor";
	}
	//清空日志按钮
	@RequestMapping(value="logEmpty.do")
	@ResponseBody
	public String logEmpty(){
		return redisService.logEmpty();
	}

	//获取当前数据库中key的数量
	@RequestMapping(value="getKeysSize.do")
	@ResponseBody
	public String getKeysSize(){
		return new Gson().toJson(redisService.getKeysSize());
	}

	//获取当前数据库内存使用大小情况
	@RequestMapping(value="getMemeryInfo.do")
	@ResponseBody
	public String getMemeryInfo(){
		return new Gson().toJson(redisService.getMemeryInfo());
	}

	@RequestMapping(value="command.do")
	public String command() {
		return "command";
	}
}
