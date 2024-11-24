package com.parkinglot.parking;

import com.parkinglot.Car;
import com.parkinglot.Ticket;

public interface ParkingStrategy {

    public Ticket park(Car car);

    public Car fetch(Ticket ticket);

}