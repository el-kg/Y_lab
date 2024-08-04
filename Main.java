import model.Car;
import model.Order;
import model.User;
import service.AuditService;
import service.CarService;
import service.OrderService;
import service.UserService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static UserService userService = new UserService();
    private static CarService carService = new CarService();
    private static OrderService orderService = new OrderService();
    private static AuditService auditService;

    /**
     * Main method to start the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        try {
            auditService = new AuditService("audit.log");
            try (Scanner scanner = new Scanner(System.in)) {
                User currentUser = null;
                while (true) {
                    try {
                        if (currentUser == null) {
                            currentUser = handleUserAuthentication(scanner);
                        } else {
                            currentUser = handleUserMenu(scanner, currentUser);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Ошибка ввода. Пожалуйста, введите корректное значение.");
                        scanner.next();  // Clear the invalid input
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка инициализации журнала аудита.");
        } finally {
            closeAuditService();
        }
    }

    /**
     * Handles user authentication, including registration and login.
     *
     * @param scanner the scanner object for user input
     * @return the authenticated user or null if the user logs out
     */
    private static User handleUserAuthentication(Scanner scanner) {
        System.out.println("Выберите действие:\n1. Регистрация\n2. Вход\n3. Выход");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                handleRegistration(scanner);
                return null;

            case 2:
                return handleLogin(scanner);

            case 3:
                System.out.println("Выход из системы.");
                return null;

            default:
                System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                return null;
        }
    }

    /**
     * Handles user login.
     *
     * @param scanner the scanner object for user input
     * @return the logged-in user or null if login fails
     */
    private static User handleLogin(Scanner scanner) {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        User user = userService.loginUser(username, password);
        if (user != null) {
            System.out.println("Вход успешен. Роль: " + user.getRole());
            return user;
        } else {
            System.out.println("Ошибка входа. Проверьте имя пользователя и пароль.");
            return null;
        }
    }

    /**
     * Handles the user menu based on their role.
     *
     * @param scanner     the scanner object for user input
     * @param currentUser the currently logged-in user
     * @return the user if they continue; otherwise, null to go back to authentication
     */
    private static User handleUserMenu(Scanner scanner, User currentUser) {
        switch (currentUser.getRole()) {
            case ADMIN:
                handleAdminMenu(scanner);
                break;

            case MANAGER:
                handleManagerMenu(scanner);
                break;

            case CUSTOMER:
                handleCustomerMenu(scanner);
                break;
        }
        return null; // Return null to go back to the authentication menu
    }

    /**
     * Closes the audit service.
     */
    private static void closeAuditService() {
        if (auditService != null) {
            try {
                auditService.close();
            } catch (IOException e) {
                System.out.println("Ошибка закрытия журнала аудита.");
            }
        }
    }

    /**
     * Handles user registration.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleRegistration(Scanner scanner) {
        System.out.println("Введите имя пользователя:");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите роль (ADMIN, MANAGER, CUSTOMER):");
        String roleInput = scanner.nextLine();
        try {
            User.Role role = User.Role.valueOf(roleInput.toUpperCase());
            userService.registerUser(username, password, role);
            auditService.log("Пользователь зарегистрирован: " + username);
            System.out.println("Регистрация успешна.");
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректная роль. Пожалуйста, введите ADMIN, MANAGER или CUSTOMER.");
        }
    }

    /**
     * Handles the admin menu.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("Меню администратора:\n1. Управление автомобилями\n2. Управление пользователями\n3. Просмотр журнала действий\n4. Выход из системы");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleCarManagement(scanner);
                    break;

                case 2:
                    handleUserManagement(scanner);
                    break;

                case 3:
                    handleAuditLog(scanner);
                    break;

                case 4:
                    return; // Return to the main menu

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Handles the manager menu.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleManagerMenu(Scanner scanner) {
        while (true) {
            System.out.println("Меню менеджера:\n1. Управление автомобилями\n2. Управление заказами\n3. Выход из системы");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    handleCarManagement(scanner);
                    break;

                case 2:
                    handleOrderManagement(scanner);
                    break;

                case 3:
                    return; // Return to the main menu

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Handles the customer menu.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleCustomerMenu(Scanner scanner) {
        while (true) {
            System.out.println("Меню клиента:\n1. Просмотр автомобилей\n2. Создать заказ\n3. Выход из системы");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    List<Car> cars = carService.getAllCars();
                    if (cars.isEmpty()) {
                        System.out.println("Автомобили отсутствуют.");
                    } else {
                        cars.forEach(System.out::println);
                    }
                    break;

                case 2:
                    createOrder(scanner);
                    break;

                case 3:
                    return; // Return to the main menu

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Handles car management options.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleCarManagement(Scanner scanner) {
        while (true) {
            System.out.println("Управление автомобилями:\n1. Добавить автомобиль\n2. Редактировать автомобиль\n3. Удалить автомобиль\n4. Просмотреть все автомобили\n5. Поиск автомобилей\n6. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addCar(scanner);
                    break;

                case 2:
                    updateCar(scanner);
                    break;

                case 3:
                    removeCar(scanner);
                    break;

                case 4:
                    List<Car> cars = carService.getAllCars();
                    if (cars.isEmpty()) {
                        System.out.println("Автомобили отсутствуют.");
                    } else {
                        cars.forEach(System.out::println);
                    }
                    break;

                case 5:
                    searchCars(scanner);
                    break;

                case 6:
                    return; // Return to menu

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Prompts the user to input details for a new car and adds it to the inventory.
     * <p>
     * The method requests the user to provide the car's brand, model, year of manufacture, price, and condition.
     * It then creates a new {@link Car} object using the provided details and adds it to the car inventory through
     * the {@link CarService}.
     * </p>
     *
     * @param scanner the {@link Scanner} object used to capture user input
     */
    private static void addCar(Scanner scanner) {
        System.out.println("Введите марку автомобиля:");
        String brand = scanner.nextLine();
        System.out.println("Введите модель автомобиля:");
        String model = scanner.nextLine();
        System.out.println("Введите год выпуска:");
        int year = scanner.nextInt();
        System.out.println("Введите цену:");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.println("Введите состояние:");
        String condition = scanner.nextLine();

        Car newCar = new Car(brand, model, year, price, condition);
        carService.addCar(newCar);
        System.out.println("Автомобиль добавлен.");
    }

    /**
     * Handles user management options.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleUserManagement(Scanner scanner) {
        while (true) {
            System.out.println("Управление пользователями:\n1. Просмотреть пользователей\n2. Добавить пользователя\n3. Редактировать пользователя\n4. Удалить пользователя\n5. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    List<User> users = userService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("Пользователи отсутствуют.");
                    } else {
                        users.forEach(System.out::println);
                    }
                    break;

                case 2:
                    handleRegistration(scanner);
                    break;

                case 3:
                    updateUser(scanner);
                    break;

                case 4:
                    removeUser(scanner);
                    break;

                case 5:
                    return; // Return to menu

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Handles audit log options.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleAuditLog(Scanner scanner) {
        while (true) {
            System.out.println("Журнал действий:\n1. Просмотреть журнал\n2. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    try {
                        List<String> logEntries = auditService.readLog();
                        if (logEntries.isEmpty()) {
                            System.out.println("Журнал пуст.");
                        } else {
                            logEntries.forEach(System.out::println);
                        }
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения журнала.");
                    }
                    break;

                case 2:
                    return;

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Creates a new order.
     *
     * @param scanner the scanner object for user input
     */
    private static void createOrder(Scanner scanner) {
        System.out.println("Введите ID автомобиля для заказа:");
        int carId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Введите имя клиента:");
        String clientName = scanner.nextLine();
        orderService.createOrder(carId, clientName);
        auditService.log("Создан заказ для автомобиля ID: " + carId + " клиент: " + clientName);
        System.out.println("Заказ создан.");
    }

    /**
     * Updates an existing car's details.
     *
     * @param scanner the scanner object for user input
     */
    private static void updateCar(Scanner scanner) {
        System.out.println("Введите ID автомобиля для редактирования:");
        int carId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.println("Введите новые данные автомобиля:");
        System.out.println("Марка:");
        String brand = scanner.nextLine();
        System.out.println("Модель:");
        String model = scanner.nextLine();
        System.out.println("Год выпуска:");
        int year = scanner.nextInt();
        System.out.println("Цена:");
        double price = scanner.nextDouble();
        scanner.nextLine();  // Consume newline
        System.out.println("Состояние:");
        String condition = scanner.nextLine();

        Car updatedCar = new Car(brand, model, year, price, condition);

        try {
            carService.updateCar(carId, updatedCar);
            auditService.log("Обновлен автомобиль ID " + carId + ": " + updatedCar);
            System.out.println("Автомобиль обновлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при обновлении автомобиля: " + e.getMessage());
        }
    }

    /**
     * Removes a car from the inventory.
     *
     * @param scanner the scanner object for user input
     */
    private static void removeCar(Scanner scanner) {
        System.out.println("Введите ID автомобиля для удаления:");
        int carId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        carService.removeCar(carId);
        auditService.log("Удален автомобиль ID: " + carId);
        System.out.println("Автомобиль удален.");
    }

    /**
     * Searches for cars based on given criteria.
     *
     * @param scanner the scanner object for user input
     */
    private static void searchCars(Scanner scanner) {
        System.out.println("Введите критерии поиска (марка, модель, год, цена, состояние):");
        String query = scanner.nextLine();

        List<Car> results = carService.searchCars(query);

        if (results.isEmpty()) {
            System.out.println("Автомобили не найдены.");
        } else {
            results.forEach(System.out::println);
        }
    }

    /**
     * Updates an existing user's details.
     *
     * @param scanner the scanner object for user input
     */
    private static void updateUser(Scanner scanner) {
        System.out.println("Введите имя пользователя для редактирования:");
        String username = scanner.nextLine();

        User existingUser = userService.getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (existingUser == null) {
            System.out.println("Пользователь не найден.");
            return;
        }

        System.out.println("Введите новый пароль:");
        String newPassword = scanner.nextLine();
        System.out.println("Введите новую роль (ADMIN, MANAGER, CUSTOMER):");
        String roleInput = scanner.nextLine();

        User.Role newRole;
        try {
            newRole = User.Role.valueOf(roleInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректная роль. Пожалуйста, введите ADMIN, MANAGER или CUSTOMER.");
            return;
        }

        try {
            userService.updateUser(username, newPassword, newRole);
            System.out.println("Пользователь обновлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при обновлении пользователя: " + e.getMessage());
        }
    }

    /**
     * Removes a user from the system.
     *
     * @param scanner the scanner object for user input
     */
    private static void removeUser(Scanner scanner) {
        System.out.println("Введите имя пользователя для удаления:");
        String username = scanner.nextLine();
        userService.removeUser(username);
        auditService.log("Удален пользователь: " + username);
        System.out.println("Пользователь удален.");
    }

    /**
     * Handles order management options.
     *
     * @param scanner the scanner object for user input
     */
    private static void handleOrderManagement(Scanner scanner) {
        while (true) {
            System.out.println("Управление заказами:\n1. Просмотреть заказы\n2. Создать заказ\n3. Удалить заказ\n4. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    List<Order> orders = orderService.getAllOrders();
                    if (orders.isEmpty()) {
                        System.out.println("Заказы отсутствуют.");
                    } else {
                        orders.forEach(System.out::println);
                    }
                    break;

                case 2:
                    createOrder(scanner);
                    break;

                case 3:
                    cancelOrder(scanner);
                    break;

                case 4:
                    return; // Return to menu

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    /**
     * Cancels an existing order.
     *
     * @param scanner the scanner object for user input
     */
    private static void cancelOrder(Scanner scanner) {
        System.out.println("Введите ID заказа для отмены:");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        orderService.removeOrder(orderId);
        auditService.log("Отменен заказ ID: " + orderId);
        System.out.println("Заказ успешно отменен.");
    }
}
