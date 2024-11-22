package com.parkinglot;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket.";
    public static final String NO_AVAILABLE_POSITION = "No available position.";
    private Map<Ticket, Car> parkingRecords = new HashMap<Ticket, Car>();

    // only standardParkingBoy
    // status: working when in a park/fetch, back to available after transaction completed
    // move all action type methods of parkingLot to standardParkingBoy Class
    // standardParkingBoy is a setter to parkingLot

    public static final int CAR_TO_PARK = 1;
    private Integer availablePositions = 10;

    public Ticket park(Car car) {

        if (!checkAvailableSlotsForPark()) {
            System.out.println(NO_AVAILABLE_POSITION);
            throw new NoAvailablePositionException();
        }

        Ticket ticket = new Ticket(car, parkingRecords.size());

        parkingRecords.put(ticket, car);

        updateAvailablePositions(getAvailablePositions() - CAR_TO_PARK);

        return ticket;
    }

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

    public Car fetch(Ticket ticket) {

        if (isTicketUsed(ticket) || !parkingRecords.containsKey(ticket)) {
            System.out.println(UNRECOGNIZED_PARKING_TICKET);
            throw new UnrecognizedParkingTicketException();
        }

        ticket.setTicketUsed();

        return parkingRecords.get(ticket);
    }


}
