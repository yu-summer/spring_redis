package com.summer.graduate.controller;

import com.summer.graduate.service.DataCollationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName com.summer.graduate.controller.RedisController
 * @Description TODO
 * @Author summer
 * @Date 2019/3/26 15:23
 * @Version 1.0
 **/
@Controller
public class RedisController {
	@Autowired
	private DataCollationImpl dataCollation;

	Map<String, Object> dataResult = null;    //显示的数据

	/**
	 * 跳转主页面
	 *
	 * @return
	 */
	@RequestMapping(value = "index.do")
	public ModelAndView toIndex() {
		dataResult = dataCollation.dataResult();
		ModelAndView modelAndView = setData();
		modelAndView.setViewName("main");
		modelAndView.addObject("top20Logs", dataResult.get("fin_allLogs"));
		modelAndView.addObject("allCount", dataResult.get("allCount"));
		modelAndView.addObject("alertType", dataResult.get("alertType"));

		return modelAndView;
	}

	@RequestMapping("chart.do")
	public ModelAndView toChart() {
		ModelAndView modelAndView = setData();
		modelAndView.setViewName("chart");
		return modelAndView;
	}

	@RequestMapping("pdf.do")
	public ModelAndView toPDF() {
		ModelAndView modelAndView = setData();
		modelAndView.setViewName("pdf");
		modelAndView.addObject("top20Logs", dataResult.get("fin_allLogs"));
		modelAndView.addObject("allCount", dataResult.get("allCount"));
		modelAndView.addObject("alertType", dataResult.get("alertType"));
		return modelAndView;
	}

	/**
	 * 将数据转化为图表需要的数据格式
	 *
	 * @return
	 */
	private static List<String> transformData(Map<String, Integer> map) {
		List<String> result = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			result.add("{value: " + entry.getValue() + ", name: " + entry.getKey() + "}");
		}
		return result;
	}

	/**
	 * 填充数据
	 *
	 * @return
	 */
	private ModelAndView setData() {
		ModelAndView modelAndView = new ModelAndView();
		Map<String, Integer> log = null;

		//来源ip数据
		log = (Map<String, Integer>) dataResult.get("fin_srcIP");
		modelAndView.addObject("top20scrIP_key", log.keySet());
		modelAndView.addObject("top20scrIP", transformData(log));

		//来源port数据
		log = (Map<String, Integer>) dataResult.get("fin_srcPort");
		modelAndView.addObject("top20scrPort_key", log.keySet());
		modelAndView.addObject("top20scrPort", transformData(log));

		//目的IP数据
		log = (Map<String, Integer>) dataResult.get("fin_descIP");
		modelAndView.addObject("top20descIP_key", log.keySet());
		modelAndView.addObject("top20descIP", transformData(log));

		//目的port数据
		log = (Map<String, Integer>) dataResult.get("fin_descPort");
		modelAndView.addObject("top20descPort_key", log.keySet());
		modelAndView.addObject("top20descPort", transformData(log));

		return modelAndView;
	}
}
