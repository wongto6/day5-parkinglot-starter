package com.parkinglot.parking;

import com.parkinglot.Car;
import com.parkinglot.Ticket;

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

    public Car performFetch(Ticket ticket) {
        return parkingStrategy.fetch(ticket);
    }

}
