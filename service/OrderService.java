package service;

import model.Car;
import model.Order;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing orders.
 */
public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;
    private CarService carService = new CarService();
    private UserService userService = new UserService();

    /**
     * Creates a new order for the specified car and customer.
     *
     * @param carId           the ID of the car to be ordered
     * @param customerUsername the username of the customer placing the order
     * @throws IllegalArgumentException if the car or user is not found
     */
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

    /**
     * Returns a list of all orders.
     *
     * @return a list of all orders
     */
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    /**
     * Updates the status of an existing order.
     *
     * @param orderId the ID of the order to be updated
     * @param status  the new status of the order
     * @throws IllegalArgumentException if the status is invalid or the order is not found
     */
    public void updateOrderStatus(int orderId, Order.OrderStatus status) {
        Order order = orders.stream().filter(o -> o.getId() == orderId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " not found."));
        order.setStatus(status);
    }

    /**
     * Removes an order by its ID.
     *
     * @param orderId the ID of the order to be removed
     * @throws IllegalArgumentException if the order is not found
     */
    public void removeOrder(int orderId) {
        boolean removed = orders.removeIf(o -> o.getId() == orderId);
        if (!removed) {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
    }
}
