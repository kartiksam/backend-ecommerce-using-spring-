package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.request.ReviewRequest;
import com.kartik.ecommerce_youtube.service.ReviewSer;
import com.kartik.ecommerce_youtube.model.Review;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewSer reviewSer;
    private UserService userService;

    @Autowired
    public ReviewController(ReviewSer reviewSer) {
        this.reviewSer = reviewSer;
    }

    // Create a review for a product
    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody ReviewRequest request, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserByJwt(jwt);  // Assume the logic to fetch user from JWT
        reviewSer.createReview(request, user);
        return ResponseEntity.ok("Review created successfully");
    }

    // Get all reviews for a product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviews(@PathVariable Long productId) {
        List<Review> reviews = reviewSer.getAllReviews(productId);
        return ResponseEntity.ok(reviews);
    }
}
