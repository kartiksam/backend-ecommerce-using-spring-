package com.kartik.ecommerce_youtube.response;

public class AuthResponse {

private String jwt;
private String message;

    public AuthResponse(String jwt, String message) {
    super();
        this.jwt = jwt;
        this.message = message;
    }

}