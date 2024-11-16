package com.kartik.ecommerce_youtube.request;

import com.kartik.ecommerce_youtube.model.Addres;

public class OrderRequest {

    private Long userId;
    private Addres shippingAddress;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Addres getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Addres shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
