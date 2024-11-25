package com.parkinglot.parking;

import com.parkinglot.Car;
import com.parkinglot.ParkingLot;
import com.parkinglot.Ticket;

import java.util.List;

public interface ParkingStrategy {

    List<ParkingLot> getFilteredParkingLots(List<ParkingLot> parkingLots);

}