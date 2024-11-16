package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.OrderException;
import com.kartik.ecommerce_youtube.model.Addres;
import com.kartik.ecommerce_youtube.model.Order;
import com.kartik.ecommerce_youtube.model.User;

import java.util.List;

public interface OrderService {

    public Order createOrder(User user, Addres shippingAddress) throws OrderException;

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long OrderId) throws OrderException;

    public Order cancellledOrder(Long orderId) throws  OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;

    User getUserById(Long userId);
}
