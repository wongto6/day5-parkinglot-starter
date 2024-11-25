package com.parkinglot.parking.concrete_parking_strategy;

import com.parkinglot.ParkingLot;
import com.parkinglot.parking.ParkingStrategy;
import com.parkinglot.parking.StandardParkingBoy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SmartParkingStrategy implements ParkingStrategy {

    @Override
    public List<ParkingLot> getFilteredParkingLots(List<ParkingLot> parkingLots) {
        return parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .sorted(Comparator.comparing(ParkingLot::getAvailablePositions).reversed())
                .collect(Collectors.toList());
    }


}
