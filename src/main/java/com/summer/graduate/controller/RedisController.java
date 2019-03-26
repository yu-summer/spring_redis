package com.summer.graduate.controller;

import com.summer.graduate.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName com.summer.graduate.controller.RedisController
 * @Description TODO
 * @Author summer
 * @Date 2019/3/26 15:23
 * @Version 1.0
 **/
@Controller
public class RedisController {
	@Autowired
	private RedisUtil redisUtil;

	@RequestMapping("test.do")
	public void test() {
		System.out.println(redisUtil.lGetListSize("log"));
		long len = redisUtil.lGetListSize("log");
		System.out.println(redisUtil.lGet("log", 0, 0));
	}

}
