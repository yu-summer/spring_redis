package com.summer.graduate.service.impl;

import com.summer.graduate.dao.UserDao;
import com.summer.graduate.entities.User;
import com.summer.graduate.service.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName com.summer.graduate.service.impl.UserServerImpl
 * @Description TODO
 * @Author summer
 * @Date 2019/4/19 10:37
 * @Version 1.0
 **/
@Service
public class UserServerImpl implements UserServer {

	@Autowired
	private UserDao userDao;

	public User getUserByUsernameAndPasswd(User user) {
		return userDao.getUser(user);
	}
}
