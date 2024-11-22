package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";
    public static final String NO_AVAILABLE_POSITION = "No available position.";
    private Map<Ticket, Car> parkingRecords = new HashMap<Ticket, Car>();


    public static final int CAR_TO_PARK = 1;
    private Integer availablePositions = 10;

    public Map<Ticket, Car> getParkingRecords() {
        return parkingRecords;
    }

    public boolean checkAvailableSlotsForPark() {
        return getAvailablePositions() - CAR_TO_PARK > 0;
    }

    public Integer getAvailablePositions() {
        return availablePositions;
    }

    public void updateAvailablePositions(Integer updatedAvailableSlots) {
        this.availablePositions = updatedAvailableSlots;
    }

    public boolean isTicketUsed(Ticket ticket) {
        return ticket.getTicketUsed();
    }

}
