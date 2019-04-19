package com.summer.graduate.entities;

import lombok.Data;

/**
 * @ClassName com.summer.graduate.entities.Operate
 * @Description TODO
 * @Author summer
 * @Date 2019/4/11 11:47
 * @Version 1.0
 **/
@Data
public class Operate {
	private long id;//slowlog唯一编号id
	private String executeTime;// 查询的时间戳
	private String usedTime;// 查询的耗时（微妙），如表示本条命令查询耗时47微秒
	private String args;// 查询命令，完整命令为 SLOWLOG GET，slowlog最多保存前面的31个key和128字符
}
