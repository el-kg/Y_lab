import model.Car;
import model.User;
import model.enums.CarCondition;
import model.enums.UserRole;
import repository.CarRepository;
import repository.UserRepository;

import java.util.List;

public class DataInitializer {


    public static void initialize(CarRepository carRepository, UserRepository userRepository) {
        List<Car> cars = List.of(
                new Car("Toyota", "Camry", 2020, 24000, CarCondition.NEW),
                new Car("Honda", "Accord", 2019, 22000, CarCondition.USED),
                new Car("Ford", "Mustang", 2021, 35000, CarCondition.NEW),
                new Car("Chevrolet", "Malibu", 2018, 18000, CarCondition.USED),
                new Car("BMW", "3 Series", 2022, 41000, CarCondition.NEW),
                new Car("Audi", "A4", 2020, 39000, CarCondition.NEW),
                new Car("Mercedes-Benz", "C-Class", 2019, 37000, CarCondition.USED),
                new Car("Tesla", "Model 3", 2021, 45000, CarCondition.NEW),
                new Car("Volkswagen", "Passat", 2018, 20000, CarCondition.USED),
                new Car("Hyundai", "Sonata", 2020, 23000, CarCondition.NEW)
        );

        cars.forEach(carRepository::addCar);

        List<User> users = List.of(
                new User("Bob", "0000", UserRole.CLIENT),
                new User("Alex", "1111", UserRole.CLIENT),
                new User("manager1", "1234", UserRole.MANAGER),
                new User("manager2", "1234", UserRole.MANAGER),
                new User("admin","god",UserRole.ADMIN)
        );

        users.forEach(userRepository::addUser);
    }
}

