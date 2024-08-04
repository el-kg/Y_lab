package menuService;

import service.impl.CarServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ServiceRequestServiceImpl;
import service.impl.UserServiceImpl;

import java.util.Scanner;

public class AdminMenuService extends BaseMenuService {

    public AdminMenuService(UserServiceImpl userService, CarServiceImpl carService, OrderServiceImpl orderService,
                            ServiceRequestServiceImpl serviceRequestService, Scanner scanner) {
        super(userService, carService, orderService, serviceRequestService, scanner);
    }

    @Override
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Cars");
            System.out.println("3. Manage Orders");
            System.out.println("4. Manage Service Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageUsers();
                    break;
                case 2:
                    manageCars();
                    break;
                case 3:
                    manageOrders();
                    break;
                case 4:
                    manageServiceRequests();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
