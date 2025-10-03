package Repository.Interfaces;

import Entity.User;
import java.util.List;

public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
    List<User> getAllUsers(); // <-- nouveau
}
