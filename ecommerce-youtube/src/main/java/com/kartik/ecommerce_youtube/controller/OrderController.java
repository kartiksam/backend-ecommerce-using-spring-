package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.exception.OrderException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.Addres;
import com.kartik.ecommerce_youtube.model.Order;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.service.OrderService;
import com.kartik.ecommerce_youtube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Addres shippingAddress, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        Order order = orderService.createOrder(user, shippingAddress); // Pass user and address to service
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // Get all orders for a user
    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUserOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        List<Order> orders = orderService.usersOrderHistory(user.getId()); // Fetch orders for the user
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Update order status to 'PLACED' for the user's order
    @PutMapping("/status/placed/{orderId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        Order order = orderService.findOrderById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("Unauthorized to modify this order.");
        }
        Order updatedOrder = orderService.placedOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    // Update order status to 'CONFIRMED' for the user's order
    @PutMapping("/status/confirmed/{orderId}")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        Order order = orderService.findOrderById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("Unauthorized to modify this order.");
        }
        Order updatedOrder = orderService.confirmedOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    // Update order status to 'SHIPPED'
    @PutMapping("/status/shipped/{orderId}")
    public ResponseEntity<Order> shipOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        Order order = orderService.findOrderById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("Unauthorized to modify this order.");
        }
        Order updatedOrder = orderService.shippedOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    // Update order status to 'DELIVERED'
    @PutMapping("/status/delivered/{orderId}")
    public ResponseEntity<Order> deliverOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        Order order = orderService.findOrderById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("Unauthorized to modify this order.");
        }
        Order updatedOrder = orderService.deliveredOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }

    // Cancel an order
    @PutMapping("/status/cancel/{orderId}")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {
        User user = userService.findUserByJwt(jwt); // Extract user from JWT
        Order order = orderService.findOrderById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new OrderException("Unauthorized to modify this order.");
        }
        Order updatedOrder = orderService.cancellledOrder(orderId);
        return ResponseEntity.ok(updatedOrder);
    }
}
