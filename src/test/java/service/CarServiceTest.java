package src.test.java.service;

import static org.junit.jupiter.api.Assertions.*;


import src.main.java.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.service.CarService;

import java.util.List;


class CarServiceTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService();
    }

    @Test
    void testAddCar() {
        Car car = new Car("Toyota", "Corolla", 2020, 20000, "New");
        carService.addCar(car);

        List<Car> cars = carService.getAllCars();
        assertEquals(1, cars.size());
        Car addedCar = cars.get(0);
        assertNotNull(addedCar.getId());
        assertEquals("Toyota", addedCar.getBrand());
        assertEquals("Corolla", addedCar.getModel());
        assertEquals(2020, addedCar.getYear());
        assertEquals(20000, addedCar.getPrice());
        assertEquals("New", addedCar.getCondition());
    }

    @Test
    void testUpdateCar() {
        Car car = new Car("Toyota", "Corolla", 2020, 20000, "New");
        carService.addCar(car);

        int carId = carService.getAllCars().stream()
                .filter(c -> "Toyota".equals(c.getBrand()) && "Corolla".equals(c.getModel()) && 2020 == c.getYear())
                .map(Car::getId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Car ID not found"));


        Car updatedCar = new Car("Honda", "Civic", 2022, 22000, "Used");


        carService.updateCar(carId, updatedCar);

        Car carAfterUpdate = carService.getAllCars().stream()
                .filter(c -> c.getId() == carId)
                .findFirst()
                .orElse(null);

        assertNotNull(carAfterUpdate);
        assertEquals("Honda", carAfterUpdate.getBrand());
        assertEquals("Civic", carAfterUpdate.getModel());
        assertEquals(2022, carAfterUpdate.getYear());
        assertEquals(22000, carAfterUpdate.getPrice());
        assertEquals("Used", carAfterUpdate.getCondition());
    }

    @Test
    void testRemoveCar() {
        Car car = new Car("Toyota", "Corolla", 2020, 20000, "New");
        carService.addCar(car);
        int carId = car.getId();

        carService.removeCar(carId);

        Car removedCar = carService.getAllCars().stream()
                .filter(c -> c.getId() == carId)
                .findFirst()
                .orElse(null);

        assertNull(removedCar);
    }

    @Test
    void testSearchCars() {
        carService.addCar(new Car("Toyota", "Corolla", 2020, 20000, "New"));
        carService.addCar(new Car("Honda", "Civic", 2021, 21000, "Used"));

        List<Car> results = carService.searchCars("Toyota");
        assertEquals(1, results.size());
        assertEquals("Toyota", results.get(0).getBrand());

        results = carService.searchCars("2021");
        assertEquals(1, results.size());
        assertEquals(2021, results.get(0).getYear());

        results = carService.searchCars("Used");
        assertEquals(1, results.size());
        assertEquals("Used", results.get(0).getCondition());
    }
}