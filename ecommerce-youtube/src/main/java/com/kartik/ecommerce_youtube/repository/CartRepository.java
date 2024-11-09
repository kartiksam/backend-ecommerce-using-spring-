package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {



}
