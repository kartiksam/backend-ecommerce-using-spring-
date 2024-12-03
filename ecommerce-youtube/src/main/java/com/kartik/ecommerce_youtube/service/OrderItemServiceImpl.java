package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.model.OrderItem;
import com.kartik.ecommerce_youtube.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService{

@Autowired
private OrderItemRepository orderItemRepository;


    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
return  orderItemRepository.save(orderItem);
    }
}
