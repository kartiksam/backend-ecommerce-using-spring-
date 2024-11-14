package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.request.AddItemRequest;
import com.kartik.ecommerce_youtube.service.CartSrvice;
import com.kartik.ecommerce_youtube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartSrvice cartService;
    private final UserService userService;

    @Autowired
    public CartController(CartSrvice cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    // Endpoint to create a cart for a given user
    @PostMapping("/create/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable Long userId) {
        try {
            // Retrieve User by userId
            User user = userService.findUserById(userId);
            Cart cart = cartService.createCart(user);
            return new ResponseEntity<>(cart, HttpStatus.CREATED);
        } catch (UserException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to add an item to the cart
    @PostMapping("/addItem/{userId}")
    public ResponseEntity<String> addCartItem(
            @PathVariable Long userId,
            @RequestBody AddItemRequest request
    ) {
        try {
            String response = cartService.addCartItem(userId, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get the cart details of a user
    @GetMapping("/{userId}")
    public ResponseEntity<Cart> findUserCart(@PathVariable Long userId) {
        Cart cart = cartService.findUserCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
