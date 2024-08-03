package service;

import model.Car;

import java.util.ArrayList;
import java.util.List;

public class CarService {
    private List<Car> cars = new ArrayList<>();
    private int nextId = 1;

    public void addCar(Car car) {
        car = new Car(nextId++, car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getCondition());
        cars.add(car);
    }

    public void updateCar(int carId, Car updatedCar) {
        for (Car car : cars) {
            if (car.getId() == carId) {
                car.setBrand(updatedCar.getBrand());
                car.setModel(updatedCar.getModel());
                car.setYear(updatedCar.getYear());
                car.setPrice(updatedCar.getPrice());
                car.setCondition(updatedCar.getCondition());
                return;
            }
        }
        throw new IllegalArgumentException("Car with ID " + carId + " not found.");
    }

    public void removeCar(int carId) {
        cars.removeIf(car -> car.getId() == carId);
    }

    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    public List<Car> searchCars(String criteria) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getBrand().contains(criteria) || car.getModel().contains(criteria) ||
                    String.valueOf(car.getYear()).contains(criteria) ||
                    String.valueOf(car.getPrice()).contains(criteria) ||
                    car.getCondition().contains(criteria)) {
                result.add(car);
            }
        }
        return result;
    }
}
