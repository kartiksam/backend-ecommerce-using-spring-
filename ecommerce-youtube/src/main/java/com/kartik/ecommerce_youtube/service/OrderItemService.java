package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.model.OrderItem;
import org.springframework.stereotype.Service;


public interface OrderItemService {
    public OrderItem createOrderItem(OrderItem orderItem);
}
