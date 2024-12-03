package com.kartik.ecommerce_youtube.controller;

import com.kartik.ecommerce_youtube.config.JwtProvider;
import com.kartik.ecommerce_youtube.exception.UserException;
import com.kartik.ecommerce_youtube.model.Cart;
import com.kartik.ecommerce_youtube.model.User;
import com.kartik.ecommerce_youtube.service.CartSrvice;
import com.kartik.ecommerce_youtube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.kartik.ecommerce_youtube.repository.UserRepository;
import com.kartik.ecommerce_youtube.request.LoginRequest;
import com.kartik.ecommerce_youtube.response.AuthResponse;
import com.kartik.ecommerce_youtube.service.CustomUserServiceImple;

//because api endpoint use and access in frontend or can do with jsp also

@RestController
@RequestMapping("/auth")
public class AuthController {
@Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserServiceImple customUserServiceImple;
    @Autowired
    private UserService userService;
    @Autowired
    private CartSrvice cartSrvice;

    public AuthController(UserRepository userRepo, JwtProvider jwtProvider,
                          PasswordEncoder passwordEncoder, CustomUserServiceImple customUserServiceImple,
                          UserService userService, CartSrvice cartSrvice) {
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customUserServiceImple = customUserServiceImple;
        this.userService = userService;
        this.cartSrvice = cartSrvice;
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

    Cart cart=cartSrvice.createCart(savedUser);

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

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws UserException {
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }


    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        return ResponseEntity.ok(user);
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
