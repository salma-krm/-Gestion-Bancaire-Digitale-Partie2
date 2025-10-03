package Service.Inpement;

import Entity.User;
import Repository.Interfaces.UserRepository;
import Service.Interfaces.UserService;
import utils.validatedd;

import java.util.List;

public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        if (user == null) return null;

        if (userRepository.findByEmail(user.getEmail()) != null) {
            System.out.println("Email déjà utilisé.");
            return null;
        }

        return userRepository.save(user);
    }

    @Override
    public User login(User user) {
        if (user == null || user.getEmail() == null || user.getPassword() == null) {
            System.out.println("Email ou mot de passe manquant.");
            return null;
        }

        User existing = userRepository.findByEmail(user.getEmail());

        if (existing == null) {
            System.out.println("Aucun utilisateur trouvé.");
            return null;
        }

        if (!existing.getPassword().equals(user.getPassword())) {
            System.out.println("Mot de passe incorrect.");
            return null;
        }

        return existing;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }


}
