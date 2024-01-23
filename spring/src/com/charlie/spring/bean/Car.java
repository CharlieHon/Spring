package com.charlie.spring.bean;

public class Car {
    private Integer carId;
    private String name;
    private Double price;

    public Car() {
    }

    public Car(Integer carId, String name, Double price) {
        this.carId = carId;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
