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

    public void setTicketUsed() {
        this.used = true;
    }

    public boolean getTicketUsed() {
        return this.used;
    }

}
