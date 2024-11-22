package com.parkinglot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParkingLotTest {

    @Test
    void should_return_ticket_when_park_given_a_car(){
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingLot.park(car);
        //Then
        assertNotNull(ticket);
    }

    @Test
    void should_return_car_when_fetch_given_valid_ticket(){
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        Ticket ticket = parkingLot.park(car);
        //When
        Car fetchCar = parkingLot.fetch(ticket);

        //Then
        assertEquals(car, fetchCar);
    }

}
