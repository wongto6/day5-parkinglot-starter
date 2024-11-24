package com.parkinglot;

public class ParkingContext {

    private ParkingStrategy parkingStrategy;

    public ParkingContext(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public void setSortingStrategy(ParkingStrategy parkingStrategy) {
        this.parkingStrategy = parkingStrategy;
    }

    public Ticket performPark(Car car) {
        return parkingStrategy.park(car);
    }

}
