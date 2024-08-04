package menuService;

import model.Car;
import model.Order;
import model.ServiceRequest;
import model.User;
import model.enums.CarCondition;
import model.enums.OrderStatus;
import model.enums.ServiceRequestStatus;
import service.impl.CarServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ServiceRequestServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenuService extends BaseMenuService {

    public ClientMenuService(CarServiceImpl carService, OrderServiceImpl orderService,
                             ServiceRequestServiceImpl serviceRequestService, Scanner scanner) {
        super(null, carService, orderService, serviceRequestService, scanner);
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Client Menu ---");
            System.out.println("1. View Available Cars");
            System.out.println("2. Search Cars");
            System.out.println("3. Create Car Purchase Order");
            System.out.println("4. View and Manage Orders");
            System.out.println("5. Create Service Request");
            System.out.println("6. View Service Request Status");
            System.out.println("7. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewCars();
                    break;
                case 2:
                    searchCars();
                    break;
                case 3:
                    createCarPurchaseOrder();
                    break;
                case 4:
                    viewAndManageOrders();
                    break;
                case 5:
                    createServiceRequest();
                    break;
                case 6:
                    viewServiceRequestStatus();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    return; // Exit the menu and return to the login screen
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchCars() {
        System.out.print("Enter make (or press Enter to skip): ");
        String make = scanner.nextLine();
        System.out.print("Enter model (or press Enter to skip): ");
        String model = scanner.nextLine();
        System.out.print("Enter year (or press Enter to skip): ");
        String yearInput = scanner.nextLine();
        System.out.print("Enter max price (or press Enter to skip): ");
        String priceInput = scanner.nextLine();
        System.out.print("Enter condition (NEW, USED or press Enter to skip): ");
        String conditionInput = scanner.nextLine();

        int year = yearInput.isEmpty() ? 0 : Integer.parseInt(yearInput);
        double price = priceInput.isEmpty() ? 0.0 : Double.parseDouble(priceInput);
        CarCondition condition = conditionInput.isEmpty() ? null : CarCondition.valueOf(conditionInput.toUpperCase());

        List<Car> cars = carService.searchCars(make, model, year, price, condition);
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private void createCarPurchaseOrder() {
        System.out.print("Enter car make: ");
        String make = scanner.nextLine();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine();

        Optional<Car> optionalCar = carService.findByMakeAndModel(make, model);
        if (!optionalCar.isPresent()) {
            System.out.println("Car not found.");
            return;
        }
        Car car = optionalCar.get();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        Order order = new Order(user, car, OrderStatus.PENDING);
        orderService.createOrder(order);
        System.out.println("Order created successfully: " + order);
    }


    private void viewAndManageOrders() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<Order> orders = orderService.findOrdersByUser(user);
        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.print("Enter order ID to update status or press Enter to return: ");
        String orderIdInput = scanner.nextLine();
        if (orderIdInput.isEmpty()) {
            return;
        }

        int orderId = Integer.parseInt(orderIdInput);
        Order order = orderService.findOrderById(orderId).orElse(null);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        System.out.print("Enter new status (PENDING, COMPLETED, CANCELLED): ");
        String statusInput = scanner.nextLine();
        OrderStatus newStatus = OrderStatus.valueOf(statusInput.toUpperCase());
        order.setStatus(newStatus);
        orderService.updateOrder(order);
        System.out.println("Order status updated successfully.");
    }

    private void createServiceRequest() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter car make: ");
        String make = scanner.nextLine();
        System.out.print("Enter car model: ");
        String model = scanner.nextLine();

        Optional<Car> optionalCar = carService.findByMakeAndModel(make, model);
        if (!optionalCar.isPresent()) {
            System.out.println("Car not found.");
            return;
        }
        Car car = optionalCar.get();

        System.out.print("Enter service description: ");
        String description = scanner.nextLine();

        ServiceRequest serviceRequest = new ServiceRequest(user, car, description, ServiceRequestStatus.PENDING);
        serviceRequestService.createServiceRequest(serviceRequest);
        System.out.println("Service request created successfully: " + serviceRequest);
    }

    private void viewServiceRequestStatus() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        User user = userService.findUserByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        List<ServiceRequest> requests = serviceRequestService.findServiceRequestsByUser(user);
        for (ServiceRequest request : requests) {
            System.out.println(request);
        }
    }
}
