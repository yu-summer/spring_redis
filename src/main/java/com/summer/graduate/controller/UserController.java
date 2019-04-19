package com.summer.graduate.controller;

import com.google.gson.Gson;
import com.summer.graduate.entities.User;
import com.summer.graduate.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName com.summer.graduate.controller.UserController
 * @Description TODO
 * @Author summer
 * @Date 2019/4/19 10:40
 * @Version 1.0
 **/
@Controller
public class UserController {
	@Autowired
	private UserServer userServer;
	@Resource
	private HttpServletRequest request;

	@ResponseBody
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String login(String username, String password) {
		Map<String, String> result = new HashMap<>();
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		User isLogin = userServer.getUserByUsernameAndPasswd(user);
		if (isLogin == null) {
			result.put("result", "error");
		} else {
			request.getSession().setAttribute("user", user);
			result.put("result", "success");
		}

		return new Gson().toJson(result);
	}

	@RequestMapping("index.do")
	public String index() {
		return "login";
	}
}
