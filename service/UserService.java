package service;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing a list of users.
 */
public class UserService {
    private List<User> users = new ArrayList<>();

    /**
     * Registers a new user with the specified username, password, and role.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @param role     the role of the new user
     * @throws IllegalArgumentException if a user with the specified username already exists
     */
    public void registerUser(String username, String password, User.Role role) {
        if (getUserByUsername(username).isPresent()) {
            throw new IllegalArgumentException("User already exists.");
        }
        users.add(new User(username, password, role));
    }

    /**
     * Logs in a user with the specified username and password.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the user if login is successful, or null if login fails
     */
    public User loginUser(String username, String password) {
        Optional<User> user = getUserByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
        return null;
    }

    /**
     * Returns a list of all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    /**
     * Updates the password and role of an existing user.
     *
     * @param username    the username of the user to be updated
     * @param newPassword the new password of the user
     * @param newRole     the new role of the user
     * @throws IllegalArgumentException if a user with the specified username is not found
     */
    public void updateUser(String username, String newPassword, User.Role newRole) {
        User user = getUserByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setPassword(newPassword);
        user.setRole(newRole);
    }

    /**
     * Removes a user with the specified username.
     *
     * @param username the username of the user to be removed
     */
    public void removeUser(String username) {
        users.removeIf(user -> user.getUsername().equals(username));
    }

    /**
     * Returns an Optional containing the user with the specified username, if such a user exists.
     *
     * @param username the username of the user
     * @return an Optional containing the user, if found
     */
    private Optional<User> getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
