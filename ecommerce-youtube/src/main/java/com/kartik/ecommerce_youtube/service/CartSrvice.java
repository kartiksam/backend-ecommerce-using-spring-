package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.request.AddItemRequest;

public interface CartSrvice {

   public Cart createCart(User user);

   public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

   public Cart findUserCart(Long userId);



}
