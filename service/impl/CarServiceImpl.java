package service.impl;

import model.Car;
import model.enums.CarCondition;
import repository.CarRepository;
import service.CarService;

import java.util.List;
import java.util.Optional;

public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(Car car) {
        carRepository.addCar(car);
    }

    public void updateCar(Car car) {
        carRepository.updateCar(car);
    }

    public void removeCar(Car car) {
        carRepository.removeCar(car);
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }

    public List<Car> searchCars(String make, String model, int year, double price, CarCondition condition) {
        return carRepository.searchCars(make, model, year, price, condition);
    }

    public Optional<Car> findByMakeAndModel(String make, String model) {
        return carRepository.findByMakeAndModel(make, model);
    }
}

