package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Review;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.request.ReviewRequest;

import java.util.List;

public interface ReviewSer {

    public Review createReview(ReviewRequest req , User user)throws ProductException;

    public List<Review> getAllReviews(Long productId);

}
