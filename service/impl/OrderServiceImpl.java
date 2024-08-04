package service.impl;

import model.Order;
import model.User;
import model.enums.OrderStatus;
import service.OrderService;
import repository.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void updateOrder(Order order) {
        orderRepository.updateOrder(order);
    }

    public void cancelOrder(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.updateOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public List<Order> findOrdersByUser(User user) {
        return orderRepository.findOrdersByUser(user);
    }

    public List<Order> searchOrders(String clientName, OrderStatus status) {
        return orderRepository.searchOrders(clientName, status);
    }

    public Optional<Order> findOrderById(int orderId) {
        return orderRepository.getAllOrders().stream()
                .filter(order -> order.getId() == orderId)
                .findFirst();
    }
}


