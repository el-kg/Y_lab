package model;


import model.enums.UserRole;

public class User {
    private String username;
    private String password;
    private UserRole userRole;

    public User(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return userRole;
    }

}
