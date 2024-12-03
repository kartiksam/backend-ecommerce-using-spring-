package com.kartik.ecommerce_youtube.repository;

import com.kartik.ecommerce_youtube.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
