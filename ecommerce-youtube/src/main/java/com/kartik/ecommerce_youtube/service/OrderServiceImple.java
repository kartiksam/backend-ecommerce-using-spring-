package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.OrderException;
import com.kartik.ecommerce_youtube.model.*;
import com.kartik.ecommerce_youtube.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImple implements OrderService {

    private  OrderRepository orderRepository;
    private  UserRepository userRepository; // Assuming you have a UserRepository
    private  CartRepository cartRepository;  // Assuming you have a CartReposi
    private AddressRepository addressRepository;
    private OrderItemRepository orderItemRepository;
    private CartSrvice cartSrvice;

    public OrderServiceImple(OrderRepository orderRepository, UserRepository userRepository, CartRepository cartRepository,
                             AddressRepository addressRepository, OrderItemRepository orderItemRepository, CartSrvice cartSrvice) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartSrvice = cartSrvice;
    }

    @Override
    public Order createOrder(User user, Addres shippingAddress) throws OrderException {
        // Fetch the cart for the user

       shippingAddress.setUser(user);
       Addres address=addressRepository.save(shippingAddress);
       user.getAddress().add(address);
       userRepository.save(user);

       Cart cart=cartSrvice.findUserCart(user.getId());
       List<OrderItem> orderItems=new ArrayList<>();

       for(CartItem item:cart.getCartItems()){
           OrderItem orderItem=new OrderItem();

           orderItem.setPrice(item.getPrice());
           orderItem.setProduct(item.getProduct());
           orderItem.setQuantity(item.getQuantity());
           orderItem.setSize(item.getSize());
           orderItem.setUserId(item.getUserId());
           orderItem.setDiscountedPrice(item.getDiscountedPrice());

           OrderItem cretaedOrderItem =orderItemRepository.save(orderItem);
           orderItems.add(cretaedOrderItem);

       }
        // Create a new Order instance
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        order.setTotalPrice(cart.getTotalPrice());
        order.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        order.setDiscount(cart.getDiscount());
        order.getPaymentDetails().setPaymentStatus("PENDING");
        order.setOrderStatus("PLACED");
        order.setTotalItems(cart.getTotalItem());
        order.setOrderDate(LocalDateTime.now());


        // Map CartItems to OrderItems
      Order savedOrder=orderRepository.save(order);

      for(OrderItem item:orderItems){
          item.setOrder(savedOrder);
          orderItemRepository.save(item);
      }

        // Save the order to the repository
        return savedOrder;
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
        List<Order> orders=orderRepository.getUsersOrders(userId);
        return  orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setPaymentStatus("COMPLETED");
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
        orderRepository.deleteById(orderId);
    }

//    @Override
//    public User getUserById(Long userId) {
//        return userRepository.findById(userId).orElse(null);
//    }
}
