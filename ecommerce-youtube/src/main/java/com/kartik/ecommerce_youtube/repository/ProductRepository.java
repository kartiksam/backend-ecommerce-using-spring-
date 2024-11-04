package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  ProductRepository extends JpaRepository<Product,Long>{
//here somemethods are byd efaukt avail and some we need to implement

    @Query("SELECT p FROM Product p"+"WHERE (p.category.name=:category OR :category=' ')"+
            "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR (p.discountedPrice BETWEEN :minPrice AND :maxPrice))"+
            "AND (:minDiscount IS NULL OR p.discountPersent>=:minDiscount)"+
            "ORDER BY"+
            "CASE WHEN:SORT='price_low' THEN p.discountedPrice END ASC,"+
            "CASE WHEN:SORT='price_high' THEN p.discountedPrice END DESC,"
    )
    public List<Product> filterProducts(@Param("category") String category,
                                        @Param("minPrice") Integer minPrice,
                                        @Param("maxPrice") Integer maxPrice,
                                        @Param("minDiscount") Integer minDiscount,
                                        @Param("sort") String sort);
}
