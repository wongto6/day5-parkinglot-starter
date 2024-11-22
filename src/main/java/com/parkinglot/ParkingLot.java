package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private Map<Ticket, Car> parkingRecords= new HashMap<Ticket, Car>();

    public static final int CAR_TO_PARK = 1;
    private Integer availableSlots = 10;

    public Ticket park(Car car) {

        if(!checkAvailableSlotsForPark()){
            return null;
        }

        Ticket ticket = new Ticket(car);

        parkingRecords.put(ticket, car);

        return ticket;
    }

    public boolean checkAvailableSlotsForPark() {
        return availableSlots - CAR_TO_PARK > 0;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void updateAvailableSlots(Integer updatedAvailableSlots) {
        this.availableSlots = updatedAvailableSlots;
    }

    public Car fetch(Ticket ticket){
        return parkingRecords.get(ticket);
    }

}
