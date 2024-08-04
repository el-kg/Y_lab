package src.main.java.service;

import src.main.java.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing a list of cars.
 */
public class CarService {
    private List<Car> cars = new ArrayList<>();
    private int nextId = 1;

    /**
     * Adds a new car to the list. The car will be assigned a unique ID.
     *
     * @param car the car to be added
     */
    public void addCar(Car car) {
        car = new Car(nextId++, car.getBrand(), car.getModel(), car.getYear(), car.getPrice(), car.getCondition());
        cars.add(car);
    }

    /**
     * Updates the details of an existing car.
     *
     * @param carId      the ID of the car to be updated
     * @param updatedCar the car object containing updated details
     * @throws IllegalArgumentException if a car with the specified ID is not found
     */
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

    /**
     * Removes a car from the list by its ID.
     *
     * @param carId the ID of the car to be removed
     */
    public void removeCar(int carId) {
        cars.removeIf(car -> car.getId() == carId);
    }

    /**
     * Returns a list of all cars.
     *
     * @return a list of all cars
     */
    public List<Car> getAllCars() {
        return new ArrayList<>(cars);
    }

    /**
     * Searches for cars that match the given criteria.
     * The search is case-insensitive and checks the brand, model, year, price, and condition.
     *
     * @param criteria the search criteria
     * @return a list of cars that match the search criteria
     */
    public List<Car> searchCars(String criteria) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getBrand().toLowerCase().contains(criteria.toLowerCase()) ||
                    car.getModel().toLowerCase().contains(criteria.toLowerCase()) ||
                    String.valueOf(car.getYear()).contains(criteria) ||
                    String.valueOf(car.getPrice()).contains(criteria) ||
                    car.getCondition().toLowerCase().contains(criteria.toLowerCase())) {
                result.add(car);
            }
        }
        return result;
    }
}
