package com.parkinglot.exception;

public class NoAvailablePositionException extends RuntimeException {

    public static final String NO_AVAILABLE_POSITION = "No available position.";

    public NoAvailablePositionException() {
        super(NO_AVAILABLE_POSITION);
    }

}
