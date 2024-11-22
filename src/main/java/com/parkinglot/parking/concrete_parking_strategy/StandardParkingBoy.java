package com.parkinglot.parking.concrete_parking_strategy;

import com.parkinglot.Car;
import com.parkinglot.ParkingLot;
import com.parkinglot.Ticket;
import com.parkinglot.exception.NoAvailablePositionException;
import com.parkinglot.exception.UnrecognizedParkingTicketException;
import com.parkinglot.parking.ParkingStrategy;

import java.util.List;
import java.util.stream.Collectors;

public class StandardParkingBoy implements ParkingStrategy {

    protected List<ParkingLot> parkingLots;

    public StandardParkingBoy(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    @Override
    public Ticket park(Car car) {
        List<ParkingLot> filteredParkingLots = getFilteredParkingLots();
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

    public List<ParkingLot> getFilteredParkingLots() {
        return parkingLots.stream()
                .filter(ParkingLot::checkAvailableSlotsForPark)
                .collect(Collectors.toList());
    }

    public boolean checkAllAvailableSlotsForPark(List<ParkingLot> filteredParkingLots) {
        return filteredParkingLots
                .isEmpty();
    }

    @Override
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
