package com.sly.demo100.mapper;

import java.util.List;

import com.sly.demo100.model.Order;

public interface OrderMapper {

	Order findById(Integer id);

	List<Order> findOrders(List<Integer> orderIds);

	int insertOrders(List<Order> orders);

	int insertOrder(Order order);

}
