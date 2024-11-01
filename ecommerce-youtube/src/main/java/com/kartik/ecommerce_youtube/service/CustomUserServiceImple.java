package com.kartik.ecommerce_youtube.service;

import com.kartik.ecommerce_youtube.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.kartik.ecommerce_youtube.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

//why need this to remove spring by def configuration

@Service
public class CustomUserServiceImple implements UserDetailsService {

    private UserRepository userRepo;

    public CustomUserServiceImple(UserRepository userRepo){
        this.userRepo=userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

User user=userRepo.findByEmail(username);
if(user==null){
    throw new UsernameNotFoundException("user not found with given email"+username);

}

        List<GrantedAuthority> authorities=new ArrayList<>();
return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);


    }
}
