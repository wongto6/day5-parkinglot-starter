package com.parkinglot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

    @Test
    void should_null_when_fetch_given_used_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingLot.park(car);
        Car fetchCar = parkingLot.fetch(ticket);
        Car fetchCarTwice = parkingLot.fetch(ticket);

        //Then
        assertNull(fetchCarTwice);
    }

    @Test
    void should_return_null_when_park_given_a_car_and_full_capacity() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        parkingLot.updateAvailableSlots(0);
        //When
        Ticket ticket = parkingLot.park(car);
        //Then
        assertNull(ticket);
    }

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_used_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingLot.park(car);
        Car fetchCar = parkingLot.fetch(ticket);
        Car fetchCarTwice = parkingLot.fetch(ticket);

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_wrong_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        //When
        Ticket wrongTicket = new Ticket();
        parkingLot.fetch(wrongTicket);

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_unrecognized_ticket_when_park_given_a_car_and_full_capacity() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        parkingLot.updateAvailableSlots(0);

        Exception error = null;

        //When
        try{
            parkingLot.park(car);
        }catch (Exception e) {
            assertThatIllegalArgumentException();
        }

        //Then
        String expectedOutput = "No available position.";
        assertThat(systemOut()).contains(String.format(expectedOutput));


    }

}
