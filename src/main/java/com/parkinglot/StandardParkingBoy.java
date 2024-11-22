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

        List<ParkingLot> filteredParkingLots = parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .collect(Collectors.toList());

        if (checkAllAvailableSlotsForPark(filteredParkingLots)) {
            System.out.println(ParkingLot.NO_AVAILABLE_POSITION);
            throw new NoAvailablePositionException();
        }

        ParkingLot parkingLot = filteredParkingLots
                .getFirst();

        Ticket ticket = new Ticket(car, parkingLot.getParkingRecords().size(), parkingLot);

        parkingLot.getParkingRecords().put(ticket, car);

        parkingLot.updateAvailablePositions(parkingLot.getAvailablePositions() - parkingLot.CAR_TO_PARK);

        return ticket;
    }

    public boolean checkAllAvailableSlotsForPark(List<ParkingLot> filteredParkingLots) {
        return filteredParkingLots
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
