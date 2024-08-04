package model;

/**
 * Represents a user with a username, password, and role.
 */
public class User {
    private String username;
    private String password;
    private Role role;

    /**
     * Enum representing the role of the user.
     */
    public enum Role {
        ADMIN, MANAGER, CUSTOMER
    }

    /**
     * Constructs a new User with the specified username, password, and role.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @param role     the role of the user
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the new role of the user
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return String.format("User{username='%s', password='%s', role=%s}",
                username, password, role);
    }
}
