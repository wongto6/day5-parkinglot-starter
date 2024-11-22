package com.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandardParkingBoy {

    private List<ParkingLot> parkingLots = new ArrayList<ParkingLot>();

    // add a ticket to parkinglot mapping here, the boy would know where to fetch that car

    public StandardParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Ticket park(Car car) {

        if (checkAllAvailableSlotsForPark()) {
            System.out.println(ParkingLot.NO_AVAILABLE_POSITION);
            throw new NoAvailablePositionException();
        }

        ParkingLot parkingLot = parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .collect(Collectors.toList())
                .getFirst();

        Ticket ticket = new Ticket(car, parkingLot.getParkingRecords().size(), parkingLot);

        parkingLot.getParkingRecords().put(ticket, car);

        parkingLot.updateAvailablePositions(parkingLot.getAvailablePositions() - parkingLot.CAR_TO_PARK);

        return ticket;
    }

    public boolean checkAllAvailableSlotsForPark() {
        return parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .collect(Collectors.toList())
                .isEmpty();
    }

    public Car fetch(Ticket ticket) {

        if (ticket.getParkingLot() == null || ticket.getParkingLot().isTicketUsed(ticket) || !ticket.getParkingLot().getParkingRecords().containsKey(ticket)) {
            System.out.println(ticket.getParkingLot().UNRECOGNIZED_PARKING_TICKET);
            throw new UnrecognizedParkingTicketException();
        }

        ticket.setTicketUsed();

        return ticket.getParkingLot().getParkingRecords().get(ticket);
    }


}
