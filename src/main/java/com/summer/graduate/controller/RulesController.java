package com.summer.graduate.controller;

import com.summer.graduate.util.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName com.summer.graduate.controller.RulesController
 * @Description TODO
 * @Author summer
 * @Date 2019/3/27 9:57
 * @Version 1.0
 **/
@Controller
public class RulesController {

	@Autowired
	private FileOperation fileOperation;

	@RequestMapping("rulesList.do")
	@ResponseBody
	public ModelAndView editRules() {
		ModelAndView modelAndView = new ModelAndView("rules");

		return modelAndView;
	}

	@RequestMapping(value = "rulesList.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> rulesList() {
		Map<String, Object> resultMap = new HashMap<>();
		List<String> list = fileOperation.readDir("E:\\rules");
		resultMap.put("result", list);
		return resultMap;
	}

	@RequestMapping("test.do")
	public void test() {
		fileOperation.readDir("E:\\rules");
	}
}
