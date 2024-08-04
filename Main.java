
import controller.UserController;
import menuService.AdminMenuService;
import menuService.ClientMenuService;
import menuService.ManagerMenuService;
import menuService.MenuService;
import model.User;

import model.enums.UserRole;
import repository.ServiceRequestRepository;
import service.impl.CarServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ServiceRequestServiceImpl;
import service.impl.UserServiceImpl;
import repository.CarRepository;
import repository.OrderRepository;
import repository.UserRepository;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CarRepository carRepository = new CarRepository();
        UserRepository userRepository = new UserRepository();
        OrderRepository orderRepository = new OrderRepository();
        ServiceRequestRepository serviceRequestRepository = new ServiceRequestRepository();


        DataInitializer.initialize(carRepository, userRepository);

        CarServiceImpl carService = new CarServiceImpl(carRepository);
        UserServiceImpl userService = new UserServiceImpl(userRepository);
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository);
        ServiceRequestServiceImpl serviceRequestService = new ServiceRequestServiceImpl(serviceRequestRepository);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the dealership management system.");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                UserController userController = new UserController(userService, scanner);
                Optional<User> optionalUser = userController.login();

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    MenuService menuService = getMenuServiceForRole(user.getRole(), carService, orderService, serviceRequestService, userService, scanner);
                    menuService.showMenu();
                } else {
                    System.out.println("Invalid credentials. Please try again.");
                }
            } else if (choice == 2) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static MenuService getMenuServiceForRole(UserRole role, CarServiceImpl carService,
                                                     OrderServiceImpl orderService,
                                                     ServiceRequestServiceImpl serviceRequestService,
                                                     UserServiceImpl userService, Scanner scanner) {
        switch (role) {
            case ADMIN:
                return new AdminMenuService(userService, carService, orderService, serviceRequestService, scanner);
            case MANAGER:
                return new ManagerMenuService(carService, orderService, serviceRequestService, scanner);
            case CLIENT:
                return new ClientMenuService(carService, orderService, serviceRequestService, scanner);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}



