package com.summer.graduate.entities;

import lombok.Data;

/**
 * @ClassName com.summer.graduate.entities.User
 * @Description 用户实体
 * @Author summer
 * @Date 2019/4/19 10:17
 * @Version 1.0
 **/
@Data
public class User {
	private Integer id;
	private String username;
	private String password;
	private String ts;
}
