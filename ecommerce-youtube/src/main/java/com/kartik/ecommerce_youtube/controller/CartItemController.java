package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.exception.CartItemException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.CartItem;
import com.kartik.ecommerce_youtube.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItem createCartItem(@RequestBody CartItem cartItem) {
        return cartItemService.createCartItem(cartItem);
    }

    @PutMapping("/{userId}/{id}")
    public CartItem updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long id,
            @RequestBody CartItem cartItem
    ) throws CartItemException, UserException {
        return cartItemService.updateCartItem(userId, id, cartItem);
    }

    @DeleteMapping("/{userId}/{cartItemId}")
    public void removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId
    ) throws CartItemException, UserException {
        cartItemService.removeCartItem(userId, cartItemId);
    }
}
