package com.parkinglot;

import java.util.Objects;

public class Car {

    private String plateNumber;

    public Car(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(plateNumber, car.plateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(plateNumber);
    }
}
