package model;

public class Order {
    private int id;
    private Car car;
    private User customer;
    private OrderStatus status;

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELED
    }

    public Order(int id, Car car, User customer, OrderStatus status) {
        this.id = id;
        this.car = car;
        this.customer = customer;
        this.status = status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Car getCar() {
        return car;
    }

    public User getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Order ID: " + id + ", Car: [" + car + "], Customer: [" + customer.getUsername() + "], Status: " + status;
    }
}

