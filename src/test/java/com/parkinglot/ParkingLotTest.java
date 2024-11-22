package com.parkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    void should_return_ticket_for_first_parkinglot_when_park_given_a_car_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();


        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));

        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingBoy.park(car);
        //Then
        assertNotNull(ticket);
    }

    @Test
    void should_return_car_for_first_parkinglot_when_fetch_given_valid_ticket_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(car, fetchCar);
    }

    @Test
    void should_return_car_for_second_parkinglot_when_fetch_given_valid_ticket_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        firstParkingLot.updateAvailablePositions(0);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(car, fetchCar);
    }

    @Test
    void should_car_when_fetch_given_two_valid_ticket_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));

        //When
        Car bigCar = new Car("A99999");
        Ticket bigTicket = parkingBoy.park(bigCar);
        firstParkingLot.updateAvailablePositions(0);
        Car smallCar = new Car("B99999");
        Ticket smallTicket = parkingBoy.park(smallCar);

        Car fetchBigCar = parkingBoy.fetch(bigTicket);
        Car fetchSmallCar = parkingBoy.fetch(smallTicket);

        //Then
        assertEquals(bigCar, fetchBigCar);
        assertEquals(smallCar, fetchSmallCar);
    }








}
