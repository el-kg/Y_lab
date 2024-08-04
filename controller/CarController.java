package controller;

import model.Car;
import model.enums.CarCondition;
import service.impl.CarServiceImpl;

import java.util.Optional;
import java.util.Scanner;

public class CarController {
    private CarServiceImpl carService;
    private Scanner scanner;

    public CarController(CarServiceImpl carService, Scanner scanner) {
        this.carService = carService;
        this.scanner = scanner;
    }

    public void addCar() {
        System.out.println("Enter car make:");
        String make = scanner.nextLine();
        System.out.println("Enter car model:");
        String model = scanner.nextLine();
        System.out.println("Enter car year:");
        int year = scanner.nextInt();
        System.out.println("Enter car price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        System.out.println("Enter car condition (NEW, USED, REFURBISHED):");
        CarCondition condition = CarCondition.valueOf(scanner.nextLine().toUpperCase());

        Car car = new Car(make, model, year, price, condition);
        carService.addCar(car);
        System.out.println("Car added successfully!");
    }

    public void editCar() {
        System.out.println("Enter car make:");
        String make = scanner.nextLine();
        System.out.println("Enter car model:");
        String model = scanner.nextLine();

        Optional<Car> car = carService.findByMakeAndModel(make, model);
        if (car.isPresent()) {
            Car existingCar = car.get();
            System.out.println("Enter new car year:");
            int year = scanner.nextInt();
            System.out.println("Enter new car price:");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            System.out.println("Enter new car condition (NEW, USED, REFURBISHED):");
            CarCondition condition = CarCondition.valueOf(scanner.nextLine().toUpperCase());

            existingCar.setYear(year);
            existingCar.setPrice(price);
            existingCar.setCarCondition(condition);

            carService.updateCar(existingCar);
            System.out.println("Car information updated successfully!");
        } else {
            System.out.println("Car not found!");
        }
    }
    public void removeCar() {
        System.out.println("Enter car make:");
        String make = scanner.nextLine();
        System.out.println("Enter car model:");
        String model = scanner.nextLine();

        Optional<Car> car = carService.findByMakeAndModel(make, model);
        if (car.isPresent()) {
            carService.removeCar(car.get());
            System.out.println("Car removed successfully!");
        } else {
            System.out.println("Car not found!");
        }
    }

    public void viewAllCars() {
        carService.getAllCars().forEach(System.out::println);
    }

    public void searchCars() {
        System.out.println("Enter car make (or press Enter to skip):");
        String make = scanner.nextLine();
        System.out.println("Enter car model (or press Enter to skip):");
        String model = scanner.nextLine();
        System.out.println("Enter car year (or press Enter to skip):");
        String yearStr = scanner.nextLine();
        int year = yearStr.isEmpty() ? 0 : Integer.parseInt(yearStr);
        System.out.println("Enter maximum price (or press Enter to skip):");
        String priceStr = scanner.nextLine();
        double price = priceStr.isEmpty() ? 0.0 : Double.parseDouble(priceStr);
        System.out.println("Enter car condition (NEW, USED, REFURBISHED) or press Enter to skip:");
        String conditionStr = scanner.nextLine();
        CarCondition condition = conditionStr.isEmpty() ? null : CarCondition.valueOf(conditionStr.toUpperCase());

        carService.searchCars(make, model, year, price, condition).forEach(System.out::println);
    }
}

