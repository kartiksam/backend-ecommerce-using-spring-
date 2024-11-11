package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.CartItemException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.CartItem;
import com.kartik.ecommerce_youtube.model.Product;

//controller rem,ianing whi se to apids bnegi mapp kekrk node me duirevct ho
// jata tha yha hr ek ka tyoe do ar same sari fielkds do abhiu dekhgunfa
// node me typoe define nhi =seedha  model se
//chk kru nga node bhi kr rakhe h kafi mene
public interface CartItemService {

    public CartItem createCartItem(CartItem cartItem);

    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;

    public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}
