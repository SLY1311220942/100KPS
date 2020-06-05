package com.sly.demo100.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sly.demo100.mapper.OrderMapper;
import com.sly.demo100.model.Order;
import com.sly.demo100.service.OrderService2;

@Service
public class OrderService2Impl implements OrderService2 {
	@Resource
	private OrderMapper orderMapper;

	class Request {
		Integer orderId;
		String serialNo;
		CompletableFuture<Map<String, Object>> future;

	}

	LinkedBlockingQueue<Request> queue = new LinkedBlockingQueue<>();

	// 线程调用方法
	public Map<String, Object> findOrder(Integer id) {
		try {

			Long start = System.currentTimeMillis();
			// orderCode;
			String serialNo = UUID.randomUUID().toString();
			Request request = new Request();
			request.orderId = id;
			request.serialNo = serialNo;

			CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();
			request.future = future;
			queue.add(request);

			Map<String, Object> result = future.get();
			Long end = System.currentTimeMillis();

			System.out.println("====cost:" + (end - start) + " , new service：" + JSON.toJSONString(result));
			// 不断监听自己的线程有没有返回值： 阻塞
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@PostConstruct
	public void init() {
		ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
		scheduled.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

				int size = queue.size();
				if (size == 0) {
					return;
				}

				System.out.println("批量调用：" + size);
				// 如果队列不为空，则批量调用
				List<Integer> orderIds = new ArrayList<>();
				List<Request> requests = new ArrayList<>();
				for (int i = 0; i < size; i++) {
					Request request = queue.poll();
					// 组装参数
					orderIds.add(request.orderId);
					requests.add(request);
				}

				List<Order> orders = orderMapper.findOrders(orderIds);

				for (Request request : requests) {
					for (Order order : orders) {
						Integer orderId = request.orderId;
						if (order.getId().equals(orderId)) {
							Map<String, Object> result = new HashMap<>(16);
							result.put("status", 200);
							result.put("message", "查询成功！");
							result.put("order", order);
							// 通知返回结果
							request.future.complete(result);
						}
					}
				}
			}
		}, 0, 10, TimeUnit.MILLISECONDS);
	}

}