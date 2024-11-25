package com.parkinglot.parking;

import com.parkinglot.Car;
import com.parkinglot.ParkingLot;
import com.parkinglot.Ticket;
import com.parkinglot.exception.NoAvailablePositionException;
import com.parkinglot.exception.UnrecognizedParkingTicketException;

import java.util.List;
import java.util.stream.Collectors;

public class StandardParkingBoy {

    protected List<ParkingLot> parkingLots;

    private ParkingStrategy parkingStrategy;

    public StandardParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public StandardParkingBoy(List<ParkingLot> parkingLots, ParkingStrategy parkingStrategy) {
        this.parkingLots = parkingLots;
        this.parkingStrategy = parkingStrategy;
    }

    public Ticket park(Car car) {
        List<ParkingLot> filteredParkingLots = getParkingStrategy(parkingLots);
        if (checkAllAvailableSlotsForPark(filteredParkingLots)) {
            System.out.println(ParkingLot.NO_AVAILABLE_POSITION);
            throw new NoAvailablePositionException();
        }
        ParkingLot parkingLot = filteredParkingLots
                .getFirst();
        Ticket ticket = new Ticket(car, parkingLot.getParkingRecords().size(), parkingLot);
        parkingLot.getParkingRecords().put(ticket, car);
        parkingLot.updateAvailablePositions(parkingLot.getAvailablePositions() - ParkingLot.CAR_TO_PARK);
        return ticket;
    }

    public List<ParkingLot> getParkingStrategy(List<ParkingLot> parkingLots) {
        if (parkingStrategy == null) {
            return getFilteredParkingLots();
        }
        return parkingStrategy.getFilteredParkingLots(parkingLots);
    }

    public List<ParkingLot> getFilteredParkingLots() {
        return parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .collect(Collectors.toList());
    }



    public boolean checkAllAvailableSlotsForPark(List<ParkingLot> filteredParkingLots) {
        return filteredParkingLots
                .isEmpty();
    }

    public Car fetch(Ticket ticket) {
        if (isTicketInvalid(ticket)) {
            System.out.println(ParkingLot.UNRECOGNIZED_PARKING_TICKET);
            throw new UnrecognizedParkingTicketException();
        }
        ticket.setTicketUsed();
        return ticket.getParkingLot().getParkingRecords().get(ticket);
    }

    public boolean checkTicketUsage(Ticket ticket) {
        return ticket.getParkingLot().isTicketUsed(ticket);
    }

    public boolean checkParkingRecords(Ticket ticket) {
        return !ticket.getParkingLot().getParkingRecords().containsKey(ticket);
    }

    public boolean isTicketInvalid(Ticket ticket) {
        return ticket.getParkingLot() == null || checkTicketUsage(ticket) || checkParkingRecords(ticket);
    }


}
