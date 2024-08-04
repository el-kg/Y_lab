package src.test.java.service;

import static org.junit.jupiter.api.Assertions.*;

import src.main.java.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.service.UserService;

import java.util.List;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testRegisterUser() {
        userService.registerUser("john_doe", "password123", User.Role.CUSTOMER);

        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
        User registeredUser = users.get(0);
        assertEquals("john_doe", registeredUser.getUsername());
        assertEquals("password123", registeredUser.getPassword());
        assertEquals(User.Role.CUSTOMER, registeredUser.getRole());
    }

    @Test
    void testRegisterDuplicateUser() {
        userService.registerUser("john_doe", "password123", User.Role.CUSTOMER);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("john_doe", "password456", User.Role.MANAGER);
        });
        assertEquals("User already exists.", thrown.getMessage());
    }

    @Test
    void testLoginUserSuccessful() {
        userService.registerUser("john_doe", "password123", User.Role.CUSTOMER);

        User loggedInUser = userService.loginUser("john_doe", "password123");
        assertNotNull(loggedInUser);
        assertEquals("john_doe", loggedInUser.getUsername());
        assertEquals(User.Role.CUSTOMER, loggedInUser.getRole());
    }

    @Test
    void testLoginUserFailed() {
        userService.registerUser("john_doe", "password123", User.Role.CUSTOMER);

        User loggedInUser = userService.loginUser("john_doe", "wrongpassword");
        assertNull(loggedInUser);
    }

    @Test
    void testUpdateUser() {
        userService.registerUser("john_doe", "password123", User.Role.CUSTOMER);

        userService.updateUser("john_doe", "newpassword456", User.Role.MANAGER);

        User updatedUser = userService.getAllUsers().stream()
                .filter(user -> user.getUsername().equals("john_doe"))
                .findFirst()
                .orElse(null);

        assertNotNull(updatedUser);
        assertEquals("newpassword456", updatedUser.getPassword());
        assertEquals(User.Role.MANAGER, updatedUser.getRole());
    }

    @Test
    void testUpdateNonExistentUser() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser("non_existent_user", "newpassword", User.Role.ADMIN);
        });
        assertEquals("User not found.", thrown.getMessage());
    }

    @Test
    void testRemoveUser() {
        userService.registerUser("john_doe", "password123", User.Role.CUSTOMER);
        userService.removeUser("john_doe");

        User removedUser = userService.getAllUsers().stream()
                .filter(user -> user.getUsername().equals("john_doe"))
                .findFirst()
                .orElse(null);

        assertNull(removedUser);
    }
}