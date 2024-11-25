package com.parkinglot.parking.concrete_parking_strategy;

import com.parkinglot.ParkingLot;
import com.parkinglot.parking.ParkingStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultParkingStrategy implements ParkingStrategy {

    @Override
    public List<ParkingLot> getFilteredParkingLots(List<ParkingLot> parkingLots){
        return parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .collect(Collectors.toList());
    }

}
