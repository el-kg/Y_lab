package menuService;

import model.Car;
import model.Order;
import model.User;
import model.enums.OrderStatus;
import service.impl.CarServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ServiceRequestServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ManagerMenuService extends BaseMenuService {

    public ManagerMenuService(CarServiceImpl carService, OrderServiceImpl orderService,
                              ServiceRequestServiceImpl serviceRequestService, Scanner scanner) {
        super(null, carService, orderService, serviceRequestService, scanner);
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Manage Cars");
            System.out.println("2. View Orders");
            System.out.println("3. Search Orders");
            System.out.println("4. Manage Service Requests");
            System.out.println("5. Create Car Purchase Order");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageCars();
                    break;
                case 2:
                    viewOrders();
                    break;
                case 3:
                    searchOrders();
                    break;
                case 4:
                    manageServiceRequests();
                    break;
                case 5:
                    createCarPurchaseOrder();
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return; // Exit the menu and return to the login screen
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchOrders() {
        System.out.println("Enter search criteria:");
        System.out.print("Enter client name (or press Enter to skip): ");
        String clientName = scanner.nextLine().trim();
        System.out.print("Enter order status (or press Enter to skip): ");
        String statusInput = scanner.nextLine().trim();
        OrderStatus status = statusInput.isEmpty() ? null : OrderStatus.valueOf(statusInput.toUpperCase());

        List<Order> orders = orderService.searchOrders(clientName, status);

        if (orders.isEmpty()) {
            System.out.println("No orders found matching the criteria.");
        } else {
            System.out.println("Orders found:");
            for (Order order : orders) {
                System.out.println(order);
            }
        }
    }
    private void createCarPurchaseOrder() {
        System.out.print("Enter car make: ");
        String make = scanner.nextLine().trim();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine().trim();

        Optional<Car> optionalCar = carService.findByMakeAndModel(make, model);
        if (!optionalCar.isPresent()) {
            System.out.println("Car not found.");
            return;
        }
        Car car = optionalCar.get();

        System.out.print("Enter username of the customer: ");
        String username = scanner.nextLine().trim();
        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Order order = new Order(user, car, OrderStatus.PENDING);
        orderService.createOrder(order);
        System.out.println("Order created successfully: " + order);
    }
}

