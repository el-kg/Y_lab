package service;
import model.User;

import java.util.*;


public class UserService {
    private List<User> users = new ArrayList<>();

    public void registerUser(String username, String password, User.Role role) {
        if (getUserByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }
        users.add(new User(username, password, role));
    }

    public User loginUser(String username, String password) {
        Optional<User> user = getUserByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public void updateUser(String username, String newPassword, User.Role newRole) {
        User user = getUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setPassword(newPassword);
        user = new User(username, newPassword, newRole); // Update the user with new role
    }

    public void removeUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
    }

    private Optional<User> getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
