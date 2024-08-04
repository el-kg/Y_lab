package model;

import model.enums.ServiceRequestStatus;

import java.util.UUID;

public class ServiceRequest {
    private int id;
    private User user;
    private Car car;
    private String description;
    private ServiceRequestStatus status;

    // Конструктор для двух параметров (для примера)
    public ServiceRequest(User user, Car car) {
        this.user = user;
        this.car = car;
        // Значения по умолчанию
        this.description = "";
        this.status = ServiceRequestStatus.PENDING;
    }

    // Полный конструктор, если нужны все параметры
    public ServiceRequest(User user, Car car, String description, ServiceRequestStatus status) {
        this.user = user;
        this.car = car;
        this.description = description;
        this.status = status;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ServiceRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceRequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceRequest{" +
                "id=" + id +
                ", user=" + user +
                ", car=" + car +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
