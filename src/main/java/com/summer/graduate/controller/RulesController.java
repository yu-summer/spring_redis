package com.summer.graduate.controller;

import com.summer.graduate.loginInterceptor.IsCheckUserLogin;
import com.summer.graduate.util.FileOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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

	private String dirPath = "E:\\rules";

	/**
	 * 罗列出所有rules文件
	 *
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@RequestMapping("rulesList.do")
	@ResponseBody
	public ModelAndView rulesList() {
		ModelAndView modelAndView = new ModelAndView("rulesList");
		Map<String, Object> resultMap = new HashMap<>();
		List<String> list = fileOperation.readDir(dirPath);
		modelAndView.addObject("rules", list);
		return modelAndView;
	}

	/**
	 * 编辑rules文件
	 *
	 * @param fileName
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@RequestMapping(value = "edit.do", method = RequestMethod.GET)
	public ModelAndView edit(String fileName) {
		ModelAndView modelAndView = new ModelAndView("edit");
		StringBuffer stringBuffer = fileOperation.readFile(dirPath + "\\" + fileName);

		modelAndView.addObject("title", fileName);
		modelAndView.addObject("text", new String(stringBuffer));

		System.out.println(stringBuffer);
		return modelAndView;
	}

	/**
	 * 删除rules
	 *
	 * @param fileName
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@RequestMapping(value = "delete.do", method = RequestMethod.GET)
	public ModelAndView deleteRules(String fileName) {
		fileOperation.deleteFile(dirPath + "\\" + fileName);
		ModelAndView modelAndView = new ModelAndView("rulesList");
		return modelAndView;
	}

	/**
	 * 更新rules
	 *
	 * @param fileName
	 * @return
	 */
	@IsCheckUserLogin(check = true)
	@RequestMapping(value = "update.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> updateRules(String fileName, StringBuffer content) {
		String resultMesg = "success";
		try {
			fileOperation.writeFile(dirPath + "\\" + fileName, content);
		} catch (Exception e) {
			e.printStackTrace();
			resultMesg = "error";
		}
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("result", resultMesg);
		return resultMap;
	}

	@IsCheckUserLogin(check = true)
	@RequestMapping(value = "add.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> addRules(String fileName) {
		String resultMesg = "success";
		Map<String, String> resultMap = new HashMap<>();
		try {
			fileOperation.addRules(dirPath + "\\" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			resultMesg = "error";
		}
		resultMap.put("result", resultMesg);
		return resultMap;
	}
}
