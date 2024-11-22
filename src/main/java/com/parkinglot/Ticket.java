package com.parkinglot;

import java.util.Objects;

public class Ticket {

    private String ticketId;
    private String plateNumber;
    private boolean used;

    public Ticket(Car car){
        this.plateNumber = car.getPlateNumber();
    }

    public Ticket(){

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

    public void setTicketUsed() {
        this.used = true;
    }

    public boolean getTicketUsed() {
        return this.used;
    }

}
