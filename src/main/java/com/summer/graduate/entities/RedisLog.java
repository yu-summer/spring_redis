package com.summer.graduate.entities;

import lombok.Data;

/**
 * @ClassName com.summer.graduate.entities.RedisLog
 * @Description 日志实体类
 * @Author summer
 * @Date 2019/4/11 9:50
 * @Version 1.0
 **/
@Data
public class RedisLog {
	private String src_ip;
	private String src_port;
	private String dest_ip;
	private String dest_port;
	private String timestamp;
	private Alert alert;
}
