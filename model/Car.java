package model;

/**
 * Represents a car with various attributes such as brand, model, year, price, and condition.
 */
public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private String condition;

    /**
     * Constructs a new Car with the specified id, brand, model, year, price, and condition.
     *
     * @param id        the unique identifier of the car
     * @param brand     the brand of the car
     * @param model     the model of the car
     * @param year      the manufacturing year of the car
     * @param price     the price of the car
     * @param condition the condition of the car (e.g., new, used)
     */
    public Car(int id, String brand, String model, int year, double price, String condition) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    /**
     * Constructs a new Car with the specified brand, model, year, price, and condition.
     * The id needs to be set later using the setId method.
     *
     * @param brand     the brand of the car
     * @param model     the model of the car
     * @param year      the manufacturing year of the car
     * @param price     the price of the car
     * @param condition the condition of the car (e.g., new, used)
     */
    public Car(String brand, String model, int year, double price, String condition) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.condition = condition;
    }

    /**
     * Returns the unique identifier of the car.
     *
     * @return the id of the car
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the car.
     *
     * @param id the new id of the car
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the brand of the car.
     *
     * @return the brand of the car
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand of the car.
     *
     * @param brand the new brand of the car
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Returns the model of the car.
     *
     * @return the model of the car
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     *
     * @param model the new model of the car
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the manufacturing year of the car.
     *
     * @return the year of the car
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the manufacturing year of the car.
     *
     * @param year the new year of the car
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns the price of the car.
     *
     * @return the price of the car
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the car.
     *
     * @param price the new price of the car
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the condition of the car.
     *
     * @return the condition of the car
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the condition of the car.
     *
     * @param condition the new condition of the car
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Returns a string representation of the car in the specified format.
     *
     * @return a string representation of the car
     */
    @Override
    public String toString() {
        return String.format("Car{id=%d, brand='%s', model='%s', year=%d, price=%.2f, condition='%s'}",
                id, brand, model, year, price, condition);
    }
}