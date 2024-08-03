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

    public static void main(String[] args) {
        try {
            auditService = new AuditService("audit.log");
        } catch (IOException e) {
            System.out.println("Ошибка инициализации журнала аудита.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        User currentUser = null;

        while (true) {
            try {
                if (currentUser == null) {
                    System.out.println("Выберите действие:\n1. Регистрация\n2. Вход\n3. Выход");
                    int choice = scanner.nextInt();
                    scanner.nextLine();  // consume newline

                    switch (choice) {
                        case 1: // Регистрация
                            handleRegistration(scanner);
                            break;

                        case 2: // Вход
                            currentUser = handleLogin(scanner);
                            break;

                        case 3: // Выход
                         //   auditService.close();
                            System.out.println("Выход из системы.");
                            return;

                        default:
                            System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                            break;
                    }
                } else {
                    // Пользователь вошел в систему
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
                    currentUser = null; // Вернуться к выбору действия после выхода из меню
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите корректное значение.");
                scanner.next();  // clear the invalid input
            }
        }
    }

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

    private static void handleAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("Меню администратора:\n1. Управление автомобилями\n2. Управление пользователями\n3. Просмотр журнала действий\n4. Выход из системы");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

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
                    return; // Возврат в главное меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void handleManagerMenu(Scanner scanner) {
        while (true) {
            System.out.println("Меню менеджера:\n1. Управление автомобилями\n2. Управление заказами\n3. Выход из системы");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    handleCarManagement(scanner);
                    break;

                case 2:
                    handleOrderManagement(scanner);
                    break;

                case 3:
                    return; // Возврат в главное меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void handleCustomerMenu(Scanner scanner) {
        while (true) {
            System.out.println("Меню клиента:\n1. Просмотр автомобилей\n2. Создать заказ\n3. Выход из системы");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    List<Car> cars = carService.getAllCars();
                    if (cars.isEmpty()) {
                        System.out.println("Автомобили отсутствуют.");
                    } else {
                        cars.forEach(car -> System.out.println(car));
                    }
                    break;

                case 2:
                    createOrder(scanner);
                    break;

                case 3:
                    return; // Возврат в главное меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void handleCarManagement(Scanner scanner) {
        while (true) {
            System.out.println("Управление автомобилями:\n1. Добавить автомобиль\n2. Редактировать автомобиль\n3. Удалить автомобиль\n4. Просмотреть все автомобили\n5. Поиск автомобилей\n6. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

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
                        cars.forEach(car -> System.out.println(car));
                    }
                    break;

                case 5:
                    searchCars(scanner);
                    break;

                case 6:
                    return; // Возврат в меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void handleUserManagement(Scanner scanner) {
        while (true) {
            System.out.println("Управление пользователями:\n1. Просмотреть пользователей\n2. Добавить пользователя\n3. Редактировать пользователя\n4. Удалить пользователя\n5. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    List<User> users = userService.getAllUsers();
                    if (users.isEmpty()) {
                        System.out.println("Пользователи отсутствуют.");
                    } else {
                        users.forEach(user -> System.out.println(user));
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
                    return; // Возврат в меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void handleAuditLog(Scanner scanner) {
        while (true) {
            System.out.println("Журнал действий:\n1. Просмотреть журнал\n2. Фильтровать журнал\n3. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    try {
                        List<String> logs = auditService.readLog();
                        logs.forEach(System.out::println);
                    } catch (IOException e) {
                        System.out.println("Ошибка чтения журнала.");
                    }
                    break;

                case 2:
                    System.out.println("Введите ключевое слово для фильтрации:");
                    String keyword = scanner.nextLine();
                    try {
                        List<String> filteredLogs = auditService.filterLog(keyword);
                        filteredLogs.forEach(System.out::println);
                    } catch (IOException e) {
                        System.out.println("Ошибка фильтрации журнала.");
                    }
                    break;

                case 3:
                    return; // Возврат в меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void handleOrderManagement(Scanner scanner) {
        while (true) {
            System.out.println("Управление заказами:\n1. Создать заказ\n2. Просмотреть заказы\n3. Изменить статус заказа\n4. Вернуться в меню");
            int choice = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (choice) {
                case 1:
                    createOrder(scanner);
                    break;

                case 2:
                    viewOrders(scanner);
                    break;

                case 3:
                    updateOrderStatus(scanner);
                    break;

                case 4:
                    return; // Возврат в меню

                default:
                    System.out.println("Некорректный выбор. Пожалуйста, попробуйте снова.");
                    break;
            }
        }
    }

    private static void createOrder(Scanner scanner) {
        System.out.println("Введите ID автомобиля:");
        int carId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.println("Введите имя пользователя:");
        String customerUsername = scanner.nextLine();
        try {
            orderService.createOrder(carId, customerUsername);
            auditService.log("Создан заказ для автомобиля ID " + carId + " клиент " + customerUsername);
            System.out.println("Заказ создан.");
        } catch (Exception e) {
            System.out.println("Ошибка при создании заказа: " + e.getMessage());
        }
    }

    private static void addCar(Scanner scanner) {
        System.out.println("Введите марку автомобиля:");
        String make = scanner.nextLine();
        System.out.println("Введите модель автомобиля:");
        String model = scanner.nextLine();
        System.out.println("Введите год выпуска:");
        int year = scanner.nextInt();
        System.out.println("Введите цену:");
        double price = scanner.nextDouble();
        scanner.nextLine();  // consume newline
        System.out.println("Введите состояние автомобиля:");
        String condition = scanner.nextLine();
        Car car = new Car(make, model, year, price, condition);
        carService.addCar(car);
        auditService.log("Добавлен новый автомобиль: " + car);
        System.out.println("Автомобиль добавлен.");
    }

    private static void updateCar(Scanner scanner) {
        System.out.println("Введите ID автомобиля для редактирования:");
        int carId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.println("Введите новые данные автомобиля:");
        System.out.println("Марка:");
        String make = scanner.nextLine();
        System.out.println("Модель:");
        String model = scanner.nextLine();
        System.out.println("Год выпуска:");
        int year = scanner.nextInt();
        System.out.println("Цена:");
        double price = scanner.nextDouble();
        scanner.nextLine();  // consume newline
        System.out.println("Состояние:");
        String condition = scanner.nextLine();
        Car car = new Car(make, model, year, price, condition);
        carService.updateCar(carId, car);
        auditService.log("Обновлен автомобиль ID " + carId + ": " + car);
        System.out.println("Автомобиль обновлен.");
    }

    private static void removeCar(Scanner scanner) {
        System.out.println("Введите ID автомобиля для удаления:");
        int carId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        carService.removeCar(carId);
        auditService.log("Удален автомобиль ID " + carId);
        System.out.println("Автомобиль удален.");
    }

    private static void searchCars(Scanner scanner) {
        System.out.println("Введите критерии поиска (марка, модель, год, цена, состояние):");
        String searchCriteria = scanner.nextLine();
        List<Car> cars = carService.searchCars(searchCriteria);
        if (cars.isEmpty()) {
            System.out.println("Автомобили не найдены.");
        } else {
            cars.forEach(System.out::println);
        }
    }

    private static void viewOrders(Scanner scanner) {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Заказы отсутствуют.");
        } else {
            orders.forEach(System.out::println);
        }
    }

    private static void updateOrderStatus(Scanner scanner) {
        System.out.println("Введите ID заказа для изменения статуса:");
        int orderId = scanner.nextInt();
        scanner.nextLine();  // consume newline
        System.out.println("Введите новый статус заказа:");
        String status = scanner.nextLine();
        orderService.updateOrderStatus(orderId, status);
        auditService.log("Обновлен статус заказа ID " + orderId + " на " + status);
        System.out.println("Статус заказа обновлен.");
    }

    private static void updateUser(Scanner scanner) {
        System.out.println("Введите имя пользователя для редактирования:");
        String username = scanner.nextLine();
        System.out.println("Введите новый пароль:");
        String password = scanner.nextLine();
        System.out.println("Введите новую роль (ADMIN, MANAGER, CUSTOMER):");
        String roleInput = scanner.nextLine();
        try {
            User.Role role = User.Role.valueOf(roleInput.toUpperCase());
            userService.updateUser(username, password, role);
            auditService.log("Обновлен пользователь: " + username);
            System.out.println("Пользователь обновлен.");
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректная роль. Пожалуйста, введите ADMIN, MANAGER или CUSTOMER.");
        }
    }

    private static void removeUser(Scanner scanner) {
        System.out.println("Введите имя пользователя для удаления:");
        String username = scanner.nextLine();
        userService.removeUser(username);
        auditService.log("Удален пользователь: " + username);
        System.out.println("Пользователь удален.");
    }
}
