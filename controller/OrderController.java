package controller;

import model.Car;
import model.Order;
import model.User;
import model.enums.OrderStatus;
import service.impl.CarServiceImpl;
import service.impl.OrderServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OrderController {
    private OrderServiceImpl orderService;
    private CarServiceImpl carService;
    private Scanner scanner;

    public OrderController(OrderServiceImpl orderService, CarServiceImpl carService, Scanner scanner) {
        this.orderService = orderService;
        this.carService = carService;
        this.scanner = scanner;
    }

    public void createOrder(User client) {
        System.out.println("Enter car make:");
        String make = scanner.nextLine();
        System.out.println("Enter car model:");
        String model = scanner.nextLine();

        Optional<Car> car = carService.findByMakeAndModel(make, model);
        if (car.isPresent()) {
            Order order = new Order(client, car.get());
            orderService.createOrder(order);
            System.out.println("Order created successfully!");
        } else {
            System.out.println("Car not found!");
        }
    }

    public void viewAndManageOrders() {
        List<Order> orders = orderService.getAllOrders();
        orders.forEach(System.out::println);

        System.out.println("Enter order ID to manage:");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Optional<Order> orderOpt = orderService.findOrderById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            System.out.println("1. Change status");
            System.out.println("2. Cancel order");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter new status (NEW, PROCESSING, COMPLETED, CANCELLED):");
                    OrderStatus status = OrderStatus.valueOf(scanner.nextLine().toUpperCase());
                    order.setStatus(status);
                    orderService.updateOrder(order);
                    System.out.println("Order status updated successfully!");
                    break;
                case 2:
                    orderService.cancelOrder(order);
                    System.out.println("Order cancelled successfully!");
                    break;
            }
        } else {
            System.out.println("Order not found!");
        }
    }

    public void searchOrders() {
        System.out.println("Enter client name (or press Enter to skip):");
        String clientName = scanner.nextLine();
        System.out.println("Enter order status (NEW, PROCESSING, COMPLETED, CANCELLED) or press Enter to skip:");
        String statusStr = scanner.nextLine();
        OrderStatus status = statusStr.isEmpty() ? null : OrderStatus.valueOf(statusStr.toUpperCase());

        orderService.searchOrders(clientName, status).forEach(System.out::println);
    }

    public void viewOrders(User client) {
        List<Order> orders = orderService.findOrdersByUser(client);
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
