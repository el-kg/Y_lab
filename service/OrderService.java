package service;

import model.Car;
import model.Order;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;
    private CarService carService = new CarService();
    private UserService userService = new UserService();

    public void createOrder(int carId, String customerUsername) {
        Optional<Car> car = carService.getAllCars().stream().filter(c -> c.getId() == carId).findFirst();
        Optional<User> user = userService.getAllUsers().stream().filter(u -> u.getUsername().equals(customerUsername)).findFirst();

        if (car.isEmpty()) {
            throw new IllegalArgumentException("Car with ID " + carId + " not found.");
        }
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with username " + customerUsername + " not found.");
        }

        orders.add(new Order(nextId++, car.get(), user.get(), Order.OrderStatus.PENDING));
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public void updateOrderStatus(int orderId, String status) {
        Order.OrderStatus newStatus;
        try {
            newStatus = Order.OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value.");
        }

        Order order = orders.stream().filter(o -> o.getId() == orderId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found."));
        order.setStatus(newStatus);
    }
}
