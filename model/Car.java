package model;
import model.enums.CarCondition;
public class Car {
    private String make;
    private String model;
    private int year;
    private double price;
    private CarCondition carCondition;

    public Car(String make, String model, int year, double price, CarCondition carCondition) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.carCondition = carCondition;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CarCondition getCarCondition() {
        return carCondition;
    }

    public void setCarCondition(CarCondition carCondition) {
        this.carCondition = carCondition;
    }

    @Override
    public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", carCondition=" + carCondition +
                '}';
    }
}
