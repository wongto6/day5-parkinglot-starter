package com.parkinglot;

public class StandardParkingBoy {

    private ParkingLot parkingLot;

    public StandardParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Ticket park(Car car) {

        if (!parkingLot.checkAvailableSlotsForPark()) {
            System.out.println(parkingLot.NO_AVAILABLE_POSITION);
            throw new NoAvailablePositionException();
        }

        Ticket ticket = new Ticket(car, parkingLot.getParkingRecords().size());

        parkingLot.getParkingRecords().put(ticket, car);

        parkingLot.updateAvailablePositions(parkingLot.getAvailablePositions() - parkingLot.CAR_TO_PARK);

        return ticket;
    }

    public Car fetch(Ticket ticket) {

        if (parkingLot.isTicketUsed(ticket) || !parkingLot.getParkingRecords().containsKey(ticket)) {
            System.out.println(parkingLot.UNRECOGNIZED_PARKING_TICKET);
            throw new UnrecognizedParkingTicketException();
        }

        ticket.setTicketUsed();

        return parkingLot.getParkingRecords().get(ticket);
    }


}
