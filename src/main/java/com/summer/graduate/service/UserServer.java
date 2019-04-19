package com.summer.graduate.service;

import com.summer.graduate.entities.User;

public interface UserServer {
	User getUserByUsernameAndPasswd(User user);
}
