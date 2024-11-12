package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.model.Review;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.repository.ProductRepository;
import com.kartik.ecommerce_youtube.repository.ReviewRepos;
import com.kartik.ecommerce_youtube.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewSerImpl implements ReviewSer {

    private ReviewRepos reviewRepos;
    private ProductService productService;
    private ProductRepository productRepository;

    public ReviewSerImpl(ReviewRepos reviewRepos, ProductService productService,
                         ProductRepository productRepository) {
        this.reviewRepos = reviewRepos;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {

        Product product=productService.findProductById(req.getProductId());
        Review review=new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());
return reviewRepos.save(review);
    }

    @Override
    public List<Review> getAllReviews(Long productId) {
        return reviewRepos.getAllProductsReview(productId);
    }
}
