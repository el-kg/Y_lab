package menuService;

import model.Car;
import model.Order;
import model.ServiceRequest;
import model.User;
import model.enums.CarCondition;
import model.enums.UserRole;
import service.impl.CarServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ServiceRequestServiceImpl;
import service.impl.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public abstract class BaseMenuService implements MenuService {
    protected UserServiceImpl userService;
    protected CarServiceImpl carService;
    protected OrderServiceImpl orderService;
    protected ServiceRequestServiceImpl serviceRequestService;
    protected Scanner scanner;

    public BaseMenuService(UserServiceImpl userService, CarServiceImpl carService, OrderServiceImpl orderService,
                           ServiceRequestServiceImpl serviceRequestService, Scanner scanner) {
        this.userService = userService;
        this.carService = carService;
        this.orderService = orderService;
        this.serviceRequestService = serviceRequestService;
        this.scanner = scanner;
    }

    protected void manageUsers() {
        System.out.println("\n--- Manage Users ---");
        System.out.println("1. View Users");
        System.out.println("2. Add User");
        System.out.println("3. Edit User");
        System.out.println("4. Delete User");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                viewUsers();
                break;
            case 2:
                addUser();
                break;
            case 3:
                editUser();
                break;
            case 4:
                deleteUser();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    protected void viewUsers() {
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    protected void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (ADMIN, MANAGER, CLIENT): ");
        String role = scanner.nextLine();

        User user = new User(username, password, UserRole.valueOf(role.toUpperCase()));
        userService.addUser(user);
        System.out.println("User added successfully.");
    }

    protected void editUser() {
        // Implementation for editing user
    }

    protected void deleteUser() {
        // Implementation for deleting user
    }

    protected void manageCars() {
        System.out.println("\n--- Manage Cars ---");
        System.out.println("1. View Cars");
        System.out.println("2. Add Car");
        System.out.println("3. Edit Car");
        System.out.println("4. Delete Car");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                viewCars();
                break;
            case 2:
                addCar();
                break;
            case 3:
                editCar();
                break;
            case 4:
                deleteCar();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    protected void viewCars() {
        List<Car> cars = carService.getAllCars();
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    protected void addCar() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter condition (NEW, USED): ");
        String condition = scanner.nextLine();

        Car car = new Car(make, model, year, price, CarCondition.valueOf(condition.toUpperCase()));
        carService.addCar(car);
        System.out.println("Car added successfully.");
    }

    protected void editCar() {
        // Implementation for editing car
    }

    protected void deleteCar() {
        // Implementation for deleting car
    }

    protected void manageOrders() {
        System.out.println("\n--- Manage Orders ---");
        System.out.println("1. View Orders");
        System.out.println("2. Edit Order");
        System.out.println("3. Delete Order");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                viewOrders();
                break;
            case 2:
                editOrder();
                break;
            case 3:
                deleteOrder();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    protected void viewOrders() {
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    protected void editOrder() {
        // Implementation for editing order
    }

    protected void deleteOrder() {
        // Implementation for deleting order
    }

    protected void manageServiceRequests() {
        System.out.println("\n--- Manage Service Requests ---");
        System.out.println("1. View Service Requests");
        System.out.println("2. Edit Service Request");
        System.out.println("3. Delete Service Request");
        System.out.print("Enter your choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                viewServiceRequests();
                break;
            case 2:
                editServiceRequest();
                break;
            case 3:
                deleteServiceRequest();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    protected void viewServiceRequests() {
        List<ServiceRequest> serviceRequests = serviceRequestService.getAllServiceRequests();
        for (ServiceRequest request : serviceRequests) {
            System.out.println(request);
        }
    }

    protected void editServiceRequest() {
        // Implementation for editing service request
    }

    protected void deleteServiceRequest() {
        // Implementation for deleting service request
    }
}
