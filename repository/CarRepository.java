package repository;

import model.Car;
import model.enums.CarCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarRepository {
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
    }

    public void updateCar(Car car) {
        // Update car details
    }

    public void removeCar(Car car) {
        cars.remove(car);
    }

    public List<Car> getAllCars() {
        return cars;
    }

    public List<Car> searchCars(String make, String model, int year, double price, CarCondition condition) {
        return cars.stream()
                .filter(car -> (make == null || car.getMake().equalsIgnoreCase(make)) &&
                        (model == null || car.getModel().equalsIgnoreCase(model)) &&
                        (year == 0 || car.getYear() == year) &&
                        (price == 0.0 || car.getPrice() <= price) &&
                        (condition == null || car.getCarCondition() == condition)) // Используйте getCarCondition()
                .collect(Collectors.toList());
    }
    public Optional<Car> findByMakeAndModel(String make, String model) {
        return cars.stream()
                .filter(car -> car.getMake().equalsIgnoreCase(make) && car.getModel().equalsIgnoreCase(model))
                .findFirst();
    }
}

