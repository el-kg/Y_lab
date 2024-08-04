package src.test.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.model.Car;
import src.main.java.model.Order;
import src.main.java.model.User;
import src.main.java.service.OrderService;
import src.main.java.service.CarService;
import src.main.java.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        CarService carService = new CarService() {
            @Override
            public List<Car> getAllCars() {
                List<Car> cars = new ArrayList<>();
                cars.add(new Car(1, "Toyota", "Camry", 2020, 25000.0, "Black"));
                cars.add(new Car(2, "Honda", "Civic", 2019, 22000.0, "White"));
                return cars;
            }
        };

        UserService userService = new UserService() {
            @Override
            public List<User> getAllUsers() {
                List<User> users = new ArrayList<>();
                users.add(new User("john_doe", "password123", User.Role.CUSTOMER));
                users.add(new User("jane_doe", "password456", User.Role.CUSTOMER));
                return users;
            }
        };

        orderService = new OrderService(carService, userService);
    }

    @Test
    void testCreateOrder() {
        orderService.createOrder(1, "john_doe");

        List<Order> orders = orderService.getAllOrders();
        assertEquals(1, orders.size());
        Order order = orders.get(0);
        assertEquals(1, order.getCar().getId());
        assertEquals("john_doe", order.getCustomer().getUsername());
        assertEquals(Order.OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void testCreateOrderCarNotFound() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(99, "john_doe");
        });
        assertEquals("Car with ID 99 not found.", thrown.getMessage());
    }

    @Test
    void testCreateOrderUserNotFound() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(1, "unknown_user");
        });
        assertEquals("User with username unknown_user not found.", thrown.getMessage());
    }

    @Test
    void testUpdateOrderStatus() {
        orderService.createOrder(1, "john_doe");

        orderService.updateOrderStatus(1, Order.OrderStatus.COMPLETED);

        List<Order> orders = orderService.getAllOrders();
        Order order = orders.get(0);
        assertEquals(Order.OrderStatus.COMPLETED, order.getStatus());
    }

    @Test
    void testUpdateOrderStatusOrderNotFound() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderService.updateOrderStatus(99, Order.OrderStatus.COMPLETED);
        });
        assertEquals("Order with ID 99 not found.", thrown.getMessage());
    }

    @Test
    void testRemoveOrder() {
        orderService.createOrder(1, "john_doe");

        orderService.removeOrder(1);

        List<Order> orders = orderService.getAllOrders();
        assertTrue(orders.isEmpty());
    }

    @Test
    void testRemoveOrderNotFound() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            orderService.removeOrder(99);
        });
        assertEquals("Order with ID 99 not found.", thrown.getMessage());
    }
}