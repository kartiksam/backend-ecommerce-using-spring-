package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.CartItemException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.CartItem;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.repository.CartItemRepository;
import com.kartik.ecommerce_youtube.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemSerImpl implements CartItemService {


    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public CartItemSerImpl(CartItemRepository cartItemRepository, UserService userService,
                           CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {

       cartItem.setQuantity(1);
       cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
       cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

       CartItem createdCartitem =cartItemRepository.save(cartItem);

       return createdCartitem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item=findCartItemById(id);
        User user=userService.findUserById(item.getUserId());

//        security so that whose account only who can update
        if (user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * item.getQuantity());
        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
//   no custom method in cartRepositry for this need top craete for it there then we can use that here
       CartItem cartItem=cartItemRepository.isCartItemExist(cart,product,size,userId);
       return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
CartItem cartItem=findCartItemById(cartItemId);
User user=userService.findUserById(cartItem.getUserId());

User reqUser=userService.findUserById(userId);
//like autheitcation
        if(user.getId().equals(reqUser.getId())){
            cartRepository.deleteById(cartItemId);
        }
else{
    throw new UserException("you can't remove another user item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);

        if(opt.isPresent()){
            return opt.get();
        }
throw new CartItemException("Cart item not found with "+cartItemId);





    }
}
