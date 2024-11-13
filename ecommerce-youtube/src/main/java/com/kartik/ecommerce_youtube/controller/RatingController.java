package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.model.Rating;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.request.RatingRequest;
import com.kartik.ecommerce_youtube.service.RatingSer;
import com.kartik.ecommerce_youtube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingSer ratingSer;
    private final UserService userService;

    @Autowired
    public RatingController(RatingSer ratingSer, UserService userService) {
        this.ratingSer = ratingSer;
        this.userService = userService;
    }

    // Rate a product
    @PostMapping("/rate")
    public ResponseEntity<String> rateProduct(@RequestBody RatingRequest request, @RequestHeader("Authorization") String token) {
        try {
            // Assuming the JWT is passed in the Authorization header
            String jwt = token.substring(7); // Remove "Bearer " prefix
            User user = userService.findUserByJwt(jwt);
            ratingSer.createRating(request, user);
            return ResponseEntity.ok("Product rated successfully");
        } catch (ProductException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    // Get all ratings for a product
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRatings(@PathVariable Long productId) {
        List<Rating> ratings = ratingSer.getProductsRatings(productId);
        return ResponseEntity.ok(ratings);
    }
}
