package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepo extends JpaRepository<Rating,Long> {

    @Query("select r from Rating r where r.product.id=:productId")
    public List<Rating> getAllProductsRating(@Param("productId")Long productId);

}
