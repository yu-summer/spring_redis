package com.summer.graduate.dao;

import com.summer.graduate.entities.User;

public interface UserDao {
	//根据用户名与密码查找用户，用于登录
	User getUser(User user);
}
