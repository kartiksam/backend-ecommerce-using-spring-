package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Rating;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RatingSer {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRatings(Long productId);



}
