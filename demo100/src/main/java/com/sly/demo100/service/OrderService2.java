package com.sly.demo100.service;

import java.util.Map;

public interface OrderService2 {

	Map<String, Object> findOrder(Integer id);
	
	Map<String, Object> insertOrder();

}
