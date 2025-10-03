package Service.Interfaces;

import Entity.User;

import java.util.List;

public interface UserService {
    User register(User user);
    User login(User user);
    List<User> getAllUsers();


}
