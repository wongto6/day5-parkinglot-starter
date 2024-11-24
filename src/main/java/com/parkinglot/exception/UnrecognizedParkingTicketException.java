package com.parkinglot.exception;

public class UnrecognizedParkingTicketException extends RuntimeException {

    public static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket";

    public UnrecognizedParkingTicketException() {
        super(UNRECOGNIZED_PARKING_TICKET);
    }

}
