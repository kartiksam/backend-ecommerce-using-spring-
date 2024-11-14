package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.OrderException;
import com.kartik.ecommerce_youtube.model.*;
import com.kartik.ecommerce_youtube.repository.CartRepository;
import com.kartik.ecommerce_youtube.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImple implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImple(CartRepository cartRepository, OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(User user, Addres shippingAddress) {
        // Fetch the cart for the user
        Cart cart = cartRepository.findByUserId(user.getId());

        // Create a new Order instance
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        order.setDiscount(cart.getDiscount());
        order.setOrderStatus("PLACED");
        order.setTotalItems(cart.getTotalItem());

        // Map CartItems to OrderItems
        List<OrderItem> orderItems = cart.getCartItems().stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setSize(cartItem.getSize());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
                    orderItem.setUserId(user.getId());
                    return orderItem;
                })
                .toList();

        // Set the mapped OrderItems to the Order
        order.setOrderItems(orderItems);

        // Save the order to the repository
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with ID: " + orderId));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        return updateOrderStatus(orderId, "PLACED");
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        return updateOrderStatus(orderId, "CONFIRMED");
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return updateOrderStatus(orderId, "SHIPPED");
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        return updateOrderStatus(orderId, "DELIVERED");
    }

    @Override
    public Order cancellledOrder(Long orderId) throws OrderException {
        return updateOrderStatus(orderId, "CANCELLED");
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    private Order updateOrderStatus(Long orderId, String status) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(status);
        if ("DELIVERED".equals(status)) {
            order.setDeliveryDate(LocalDateTime.now());
        }
        return orderRepository.save(order);
    }
}
