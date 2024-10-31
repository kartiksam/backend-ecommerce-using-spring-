package service;

import exception.UserException;
import jdk.jshell.spi.ExecutionControl;
import model.User;
import org.springframework.stereotype.Service;

@Service
public interface
UserService {

public User findUserById(Long userId) throws UserException;

public User findUserByJwt(String jwt) throws UserException;
}
