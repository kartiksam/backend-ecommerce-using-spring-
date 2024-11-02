package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.config.JwtProvider;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kartik.ecommerce_youtube.repository.UserRepository;
import com.kartik.ecommerce_youtube.request.LoginRequest;
import com.kartik.ecommerce_youtube.response.AuthResponse;
import com.kartik.ecommerce_youtube.service.CustomUserServiceImple;

//because api endpoint use and access in frontend or can do with jsp also

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepo;
    private JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImple customUserServiceImple;

    public AuthController(UserRepository userRepo, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, CustomUserServiceImple customUserServiceImple) {
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customUserServiceImple = customUserServiceImple;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{

    String email=user.getEmail();
    String password= user.getPassword();
    String firstString=user.getFirstName();
    String lastNString=user.getLastName();

    User isEmailExist=userRepo.findByEmail(email);

    if(isEmailExist!=null){
  throw new UserException("Email is already Used with Another account");
    }

//    if not then create new userr
//encoding the pass


    User createdUser=new User();
    createdUser.setEmail(email);
    createdUser.setPassword(passwordEncoder.encode(password));
    createdUser.setFirstName(firstString);
    createdUser.setLastName(lastNString);

    User savedUser=userRepo.save(createdUser);

    Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());


    SecurityContextHolder.getContext().setAuthentication(authentication);

    String token= jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setMessage("Signup success");
        authResponse.setJwt(token);
    return  new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

}

@PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail();
        String password= loginRequest.getPassword();

        Authentication authentication=authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtProvider.generateToken(authentication);
    AuthResponse authResponse=new AuthResponse();
        authResponse.setMessage("Signin success");
        authResponse.setJwt(token);
        return  new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String username, String password) {
//        will chk password match or not these kind of stuff

        UserDetails userDetails=customUserServiceImple.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");

        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid passowrd..");
        }



        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
