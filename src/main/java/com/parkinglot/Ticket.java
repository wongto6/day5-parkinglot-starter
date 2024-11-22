package com.parkinglot;

import java.util.Objects;

public class Ticket {

    private Integer ticketId;
    private String plateNumber;
    private boolean used;
    private ParkingLot parkingLot;

    public Ticket(Car car, Integer ticketId, ParkingLot parkingLot){
        this.plateNumber = car.getPlateNumber();
        this.ticketId = ticketId;
        this.parkingLot = parkingLot;
    }

    public Ticket(){

    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setTicketUsed() {
        this.used = true;
    }

    public boolean getTicketUsed() {
        return this.used;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(ticketId, ticket.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ticketId);
    }
}
