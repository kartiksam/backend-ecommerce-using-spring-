package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.Rating;
import com.kartik.ecommerce_youtube.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepos extends JpaRepository<Review,Long> {


    @Query("select r from Review r where r.product.id=:productId")
    public List<Review> getAllProductsReview(@Param("productId")Long productId);

}
