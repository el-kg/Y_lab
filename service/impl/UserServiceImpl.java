package service.impl;

import model.User;
import service.UserService;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.addUser(user);
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findUserByUsername(username)
                .filter(user -> user.getPassword().equals(password));
    }

    public void addUser(User user) {userRepository.addUser(user);}

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}

