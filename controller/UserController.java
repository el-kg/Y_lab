package controller;

import model.User;
import model.enums.UserRole;
import service.impl.UserServiceImpl;

import java.util.Optional;
import java.util.Scanner;

public class UserController {
    private UserServiceImpl userService;
    private Scanner scanner;

    public UserController(UserServiceImpl userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public void register() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Select role (ADMIN, MANAGER, CLIENT):");
        UserRole role = UserRole.valueOf(scanner.nextLine().toUpperCase());

        User user = new User(username, password, role);
        userService.registerUser(user);
    }

    public Optional<User> login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        return userService.authenticate(username, password);
    }
}
