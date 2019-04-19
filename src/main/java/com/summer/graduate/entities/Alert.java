package com.summer.graduate.entities;

import lombok.Data;

/**
 * @ClassName com.summer.graduate.entities.Alert
 * @Description TODO
 * @Author summer
 * @Date 2019/4/11 10:05
 * @Version 1.0
 **/
@Data
public class Alert {
	private String action;
	private String gid;
	private String signature_id;
	private String rev;
	private String signature;
	private String category;
	private String severity;
}
