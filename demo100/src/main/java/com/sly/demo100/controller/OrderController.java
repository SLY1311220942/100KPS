package com.sly.demo100.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sly.demo100.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Resource
	private OrderService orderService;
	
	private Long nowThreadId;
	
	private Lock lock = new ReentrantLock();
	
	private static Map<Long, Condition> conditionMap = new HashMap<>(16);

	/**
	 * 根据id查询订单
	 * 
	 * @param id
	 * @return
	 * @author sly
	 * @time 2020年5月23日
	 */
	@ResponseBody
	@RequestMapping("/findOrder")
	public Object findOrder(Integer id) {
		Map<String, Object> result = new HashMap<>(16);
		try {
			lock.lock();
			Thread currentThread = Thread.currentThread();
			long threadId = currentThread.getId();
			nowThreadId = threadId;
			System.out.println("挂起线程ID:" + threadId);
			Condition condition = lock.newCondition();
			conditionMap.put(threadId, condition);
			condition.await();
			result = orderService.findOrder(id);
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
			result.put("message", "查询订单错误！");
			return result;
		} finally {
			lock.unlock();
		}
	}
	
	@ResponseBody
	@RequestMapping("/notifyOrder")
	public Object notifyOrder() {
		Map<String, Object> result = new HashMap<>(16);
		try {
			
			System.out.println("激活线程ID:" + nowThreadId);
			lock.lock();
			Condition condition = conditionMap.get(nowThreadId);
			condition.signal();
			
			result.put("status", 200);
			result.put("message", "唤醒订单成功！");
			return result;
		} catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			result.put("status", 400);
			result.put("message", "唤醒订单错误！");
			return result;
		} finally {
			lock.unlock();
		}
	}


}
