package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.model.Order;
import com.kartik.ecommerce_youtube.request.OrderRequest;

import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.service.OrderService;
import com.kartik.ecommerce_youtube.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Fetch the user from the database using the userId
            User user = orderService.getUserById(orderRequest.getUserId());
            if (user == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            // Create the order
            Order order = orderService.createOrder(user, orderRequest.getShippingAddress());
            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Get order details by order ID
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.findOrderById(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Get all orders for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.usersOrderHistory(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Update order status to "PLACED"
    @PutMapping("/{orderId}/placed")
    public ResponseEntity<Order> updateToPlaced(@PathVariable Long orderId) {
        try {
            Order order = orderService.placedOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update order status to "CONFIRMED"
    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> updateToConfirmed(@PathVariable Long orderId) {
        try {
            Order order = orderService.confirmedOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update order status to "SHIPPED"
    @PutMapping("/{orderId}/shipped")
    public ResponseEntity<Order> updateToShipped(@PathVariable Long orderId) {
        try {
            Order order = orderService.shippedOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update order status to "DELIVERED"
    @PutMapping("/{orderId}/delivered")
    public ResponseEntity<Order> updateToDelivered(@PathVariable Long orderId) {
        try {
            Order order = orderService.deliveredOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Update order status to "CANCELLED"
    @PutMapping("/{orderId}/cancelled")
    public ResponseEntity<Order> updateToCancelled(@PathVariable Long orderId) {
        try {
            Order order = orderService.cancellledOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (OrderException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
