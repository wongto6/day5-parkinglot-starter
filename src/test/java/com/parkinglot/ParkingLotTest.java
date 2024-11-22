package com.parkinglot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    void should_return_ticket_when_park_given_a_car() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingLot.park(car);
        //Then
        assertNotNull(ticket);
    }

    @Test
    void should_return_car_when_fetch_given_valid_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        Ticket ticket = parkingLot.park(car);
        //When
        Car fetchCar = parkingLot.fetch(ticket);

        //Then
        assertEquals(car, fetchCar);
    }

    @Test
    void should_car_when_fetch_given_two_valid_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        //When
        Car bigCar = new Car("A99999");
        Ticket bigTicket = parkingLot.park(bigCar);
        Car smallCar = new Car("B99999");
        Ticket smallTicket = parkingLot.park(smallCar);

        Car fetchBigCar = parkingLot.fetch(bigTicket);
        Car fetchSmallCar = parkingLot.fetch(smallTicket);

        //Then
        assertEquals(bigCar, fetchBigCar);
        assertEquals(smallCar, fetchSmallCar);
    }

    @Test
    void should_null_when_fetch_given_wrong_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        //When
        Ticket wrongTicket = new Ticket();
        Car fetchBigCar = parkingLot.fetch(wrongTicket);

        //Then
        assertNull(fetchBigCar);
    }



}
