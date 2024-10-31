package controller;

import exception.UserException;
import model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;

//because api endpoint use and access in frontend or can do with jsp also

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepo;
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

    User createdUser=new User();
    createdUser.setEmail(email);
    createdUser.setPassword(password);
    createdUser.setFirstName(firstString);
    createdUser.setLastName(lastNString);

    User savedUser=userRepo.save(createdUser);

    Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);


    SecurityContextHolder.getContext().setAuthentication(authentication);

}

}
