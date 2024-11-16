package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.OrderException;
import com.kartik.ecommerce_youtube.model.*;
import com.kartik.ecommerce_youtube.repository.OrderRepository;
import com.kartik.ecommerce_youtube.repository.UserRepository;
import com.kartik.ecommerce_youtube.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImple implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository; // Assuming you have a UserRepository
    private final CartRepository cartRepository;  // Assuming you have a CartRepository

    public OrderServiceImple(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public Order createOrder(User user, Addres shippingAddress) throws OrderException {
        // Fetch the cart for the user
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new OrderException("Cart is empty or not found for the user.");
        }

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
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderException("Order not found for ID: " + orderId);
        }
        return order.get();
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        return orderRepository.save(order);
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order cancellledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELLED");
        return orderRepository.save(order);
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

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
