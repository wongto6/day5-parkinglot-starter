package com.parkinglot;

public class Ticket {

    private String ticketId;
    private String plateNumber;

    public Ticket(Car car){
        this.plateNumber = car.getPlateNumber();
    }

}
