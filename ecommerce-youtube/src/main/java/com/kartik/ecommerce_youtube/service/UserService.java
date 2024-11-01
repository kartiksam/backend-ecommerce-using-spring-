package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.User;
import org.springframework.stereotype.Service;

@Service
public interface
UserService {

public User findUserById(Long userId) throws UserException;

public User findUserByJwt(String jwt) throws UserException;
}
