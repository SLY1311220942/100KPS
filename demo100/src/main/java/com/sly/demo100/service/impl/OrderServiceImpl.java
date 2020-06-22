package com.sly.demo100.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sly.demo100.mapper.OrderMapper;
import com.sly.demo100.model.Order;
import com.sly.demo100.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Resource
	private OrderMapper orderMapper;

	@Override
	public Map<String, Object> findOrder(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Order order = orderMapper.findById(id);
		result.put("status", 200);
		result.put("message", "查询成功！");
		result.put("order", order);
		return result;
	}

	@Override
	public Map<String, Object> insertOrder() {
		Map<String, Object> result = new HashMap<String, Object>();
		Order order = new Order();
		order.setName("order:" + System.currentTimeMillis());
		orderMapper.insertOrder(order);
		result.put("status", 200);
		result.put("message", "插入成功！");
		
		
		return result;
	}

}
