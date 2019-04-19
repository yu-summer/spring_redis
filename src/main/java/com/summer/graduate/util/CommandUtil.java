package com.summer.graduate.util;

import org.springframework.stereotype.Component;

/**
 * @ClassName com.summer.graduate.util.CommandUtil
 * @Description 处理Redis命令的工具类,这个工具类是可以扩展的
 * @Author summer
 * @Date 2019/4/12 11:23
 * @Version 1.0
 **/
@Component
public class CommandUtil {

	//处理Redis数据库执行的命令类型
	public String[] judgeType(String command) {
		if (command.contains(" ")) {
			String[] keyword = command.trim().split(" ");
			return clearBlank(keyword);
		} else {
			return new String[] {command};
		}
	}

	private String[] clearBlank(String[] arr) {
		//统计0的个数
		int count = 0;    //定义一个变量记录0的个数
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals("")) {
				count++;
			}
		}

		//创建一个新的数组
		String[] newArr = new String[arr.length - count];

		int index = 0; //新数组使用的索引值
		//把非的数据存储到新数组中。
		for (int i = 0; i < arr.length; i++) {
			if (!arr[i].equals("")) {
				newArr[index] = arr[i];
				index++;
			}
		}
		return newArr;
	}
}
