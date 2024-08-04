package repository;

import model.Order;
import model.User;
import model.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepository {
    private List<Order> orders = new ArrayList<>();
    private int nextId = 1;

    public void addOrder(Order order) {
        order.setId(nextId++);
        orders.add(order);
    }

    public void updateOrder(Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId() == updatedOrder.getId()) {
                orders.set(i, updatedOrder);
                return;
            }
        }
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public List<Order> findOrdersByUser(User user) {
        return orders.stream()
                .filter(order -> order.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<Order> searchOrders(String clientName, OrderStatus status) {
        return orders.stream()
                .filter(order -> (clientName == null || order.getUser().getUsername().equalsIgnoreCase(clientName)) &&
                        (status == null || order.getStatus() == status))
                .collect(Collectors.toList());
    }

    public Optional<Order> findOrderById(int orderId) {
        return orders.stream()
                .filter(order -> order.getId() == orderId)
                .findFirst();
    }
}
