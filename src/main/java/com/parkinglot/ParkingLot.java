package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";
    public static final String NO_AVAILABLE_POSITION = "No available position.";
    private Map<Ticket, Car> parkingRecords = new HashMap<Ticket, Car>();

    public static final int CAR_TO_PARK = 1;
    private Integer availableSlots = 10;

    public Ticket park(Car car) {

        if (!checkAvailableSlotsForPark()) {
            System.out.println(NO_AVAILABLE_POSITION);
            return null;
        }

        Ticket ticket = new Ticket(car);

        parkingRecords.put(ticket, car);

        updateAvailableSlots(getAvailableSlots() - CAR_TO_PARK);

        return ticket;
    }

    public boolean checkAvailableSlotsForPark() {
        return getAvailableSlots() - CAR_TO_PARK > 0;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void updateAvailableSlots(Integer updatedAvailableSlots) {
        this.availableSlots = updatedAvailableSlots;
    }

    public boolean isTicketUsed(Ticket ticket) {
        return ticket.getTicketUsed();
    }

    public Car fetch(Ticket ticket) {

        if (isTicketUsed(ticket) || !parkingRecords.containsKey(ticket)) {
            System.out.println(UNRECOGNIZED_PARKING_TICKET);
            return null;
        }

        ticket.setTicketUsed();

        return parkingRecords.get(ticket);
    }


}
