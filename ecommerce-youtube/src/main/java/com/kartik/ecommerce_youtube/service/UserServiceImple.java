package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.config.JwtProvider;
import com.kartik.ecommerce_youtube.exception.ProductException;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.Product;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImple implements UserService{

    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImple(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }



    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> opt=userRepository.findById(userId);

        if(opt.isPresent()){
            return opt.get();
        }

        throw new UserException("User not found with id "+userId);
    }


    @Override
    public User findUserByJwt(String jwt) throws UserException {
    String email=jwtProvider.getEmailFromToken(jwt);

    User user=userRepository.findByEmail(email);
    if(user==null){
        throw new UserException("User not found with this email"+email);

    }
    return user;
    }
}
