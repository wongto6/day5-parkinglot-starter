package com.parkinglot;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SmartParkingBoy extends StandardParkingBoy {

    public SmartParkingBoy(List<ParkingLot> parkingLots) {
        super(parkingLots);
    }

    @Override
    public List<ParkingLot> getFilteredParkingLots() {
        return parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .sorted(Comparator.comparing(ParkingLot::getAvailablePositions).reversed())
                .collect(Collectors.toList());
    }


}
