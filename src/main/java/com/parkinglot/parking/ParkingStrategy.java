package com.parkinglot.parking;

import com.parkinglot.Car;
import com.parkinglot.Ticket;

public interface ParkingStrategy {

    Ticket park(Car car);

    Car fetch(Ticket ticket);

}