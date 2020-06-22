package com.sly.demo100.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sly.demo100.service.OrderService;
import com.sly.demo100.service.OrderService2;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Resource
	private OrderService orderService;
	@Resource
	private OrderService2 orderService2;

	@Resource
	RedisTemplate<String, Object> redisTemplate;

	/**
	 * 根据id查询订单
	 * 
	 * @param id
	 * @return
	 * @author sly
	 * @time 2020年5月23日
	 */
	@ResponseBody
	@RequestMapping("/findOrder1")
	public Object findOrder1(Integer id) {
		Map<String, Object> result = new HashMap<>(16);
		try {
			result = orderService.findOrder(id);
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
			result.put("message", "查询订单错误！");
			return result;
		}
	}

	/**
	 * 根据id查询订单
	 * 
	 * @param id
	 * @return
	 * @author sly
	 * @time 2020年5月23日
	 */
	@ResponseBody
	@RequestMapping("/findOrder2")
	public Object findOrder2(Integer id) {
		Map<String, Object> result = new HashMap<>(16);
		try {
			result = orderService2.findOrder(id);
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
			result.put("message", "查询订单错误！");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping("/insertOrder1")
	public Object insertOrder1() {
		Map<String, Object> result = new HashMap<>(16);
		try {
			result = orderService.insertOrder();
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
			result.put("message", "保存订单错误！");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping("/insertOrder2")
	public Object insertOrder2() {
		Map<String, Object> result = new HashMap<>(16);
		try {
			result = orderService2.insertOrder();
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
			result.put("message", "保存订单错误！");
			return result;
		}
	}

}
