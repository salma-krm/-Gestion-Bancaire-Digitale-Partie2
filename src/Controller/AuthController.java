package Controller;

import Entity.User;
import Repository.Interfaces.UserRepository;
import Service.Interfaces.UserService;
import org.postgresql.core.Utils;
import utils.validatedd;

;import java.util.List;

import static java.util.Optional.empty;

public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    public  User register(User user) {

        if (user != null && validatedd.validate(user)) {
             return userService.register(user);
        } else {
            return null;
        }
    }
    public User login (User user ){
        if(user != null){
            return userService.login(user);
        }else{
            return null;
        }

    }
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }



}
