package model;

/**
 * Represents an order for a car made by a customer.
 */
public class Order {
    private int id;
    private Car car;
    private User customer;
    private OrderStatus status;

    /**
     * Enum representing the status of the order.
     */
    public enum OrderStatus {
        PENDING, COMPLETED, CANCELED
    }

    /**
     * Constructs a new Order with the specified id, car, customer, and status.
     *
     * @param id       the unique identifier of the order
     * @param car      the car associated with the order
     * @param customer the customer who made the order
     * @param status   the status of the order
     */
    public Order(int id, Car car, User customer, OrderStatus status) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.status = status;
    }

    /**
     * Returns the unique identifier of the order.
     *
     * @return the id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the car associated with the order.
     *
     * @return the car associated with the order
     */
    public Car getCar() {
        return car;
    }

    /**
     * Returns the customer who made the order.
     *
     * @return the customer who made the order
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Returns the status of the order.
     *
     * @return the status of the order
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status the new status of the order
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the order.
     *
     * @return a string representation of the order
     */
    @Override
    public String toString() {
        return String.format("Order ID: %d, Car: [%s], Customer: [%s], Status: %s",
                id, car, customer.getUsername(), status);
    }
}

