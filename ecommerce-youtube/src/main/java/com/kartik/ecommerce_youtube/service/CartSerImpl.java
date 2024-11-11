package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.repository.CartRepository;
import com.kartik.ecommerce_youtube.request.AddItemRequest;
import org.springframework.stereotype.Service;

//business logic
@Service
public class CartSerImpl implements CartSrvice {

    private CartRepository cartRepository;
    private CartItemService cartItemService;



    @Override
    public Cart createCart(User user) {
        return null;
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        return "";
    }

    @Override
    public Cart findUserCart(Long userId) {
        return null;
    }
}
