package model;

import model.enums.OrderStatus;


public class Order {
    private int id;
    private User user;
    private Car car;
    private OrderStatus status;

    // Конструктор с дефолтным статусом
    public Order(User user, Car car) {
        this.user = user;
        this.car = car;
        this.status = OrderStatus.NEW; // По умолчанию новый заказ
    }

    // Конструктор с заданным статусом
    public Order(User user, Car car, OrderStatus status) {
        this.user = user;
        this.car = car;
        this.status = status;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", status=" + status +
                '}';
    }
}
