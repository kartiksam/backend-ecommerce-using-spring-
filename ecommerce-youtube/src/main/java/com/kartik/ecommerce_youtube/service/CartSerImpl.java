package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.CartItem;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.repository.CartRepository;
import com.kartik.ecommerce_youtube.request.AddItemRequest;
import org.springframework.stereotype.Service;

//business logic
@Service
public class CartSerImpl implements CartSrvice {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartSerImpl(CartRepository cartRepository, CartItemService cartItemService,
                       ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart=new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
//      first i have to find the cart
        Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());

//        chk cartiem is there ina db or not if yes then update that and if no then create
        CartItem isPresentCart=cartItemService.isCartItemExist(cart,product, req.getSize(), userId);

if(isPresentCart==null){
    CartItem cartItem=new CartItem();
    cartItem.setProduct(product);
    cartItem.setCart(cart);
    cartItem.setQuantity(req.getQuantity());
    cartItem.setUserId(userId);

    int price=req.getQuantity()*product.getDiscountedPrice();
    cartItem.setPrice(price);
    cartItem.setSize(req.getSize());

    CartItem createdCartItem=cartItemService.createCartItem(cartItem);
    cart.getCartItems().add(createdCartItem);

}



       return "Item add to cart";
    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart=cartRepository.findByUserId(userId);

        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;

        for(CartItem cartItem:cart.getCartItems()){
            totalPrice=totalPrice+cartItem.getPrice();
            totalPrice=totalPrice+cartItem.getDiscountedPrice();
            totalItem=totalItem+cartItem.getQuantity();

        }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
    return  cartRepository.save(cart);
    }

}
