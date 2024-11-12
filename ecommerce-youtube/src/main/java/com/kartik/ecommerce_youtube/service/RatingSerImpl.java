package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.model.Rating;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.repository.RatingRepo;
import com.kartik.ecommerce_youtube.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingSerImpl implements RatingSer{

    private RatingRepo ratingRepo;
    private ProductService productService;

    public RatingSerImpl(RatingRepo ratingRepo, ProductService productService) {
        this.ratingRepo = ratingRepo;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getProductsRatings(Long productId) {



        return ratingRepo.getAllProductsRating(productId);
    }









}
