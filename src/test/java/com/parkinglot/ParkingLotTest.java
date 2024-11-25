package com.parkinglot;

import com.parkinglot.exception.NoAvailablePositionException;
import com.parkinglot.exception.UnrecognizedParkingTicketException;
import com.parkinglot.parking.concrete_parking_strategy.DefaultParkingStrategy;
import com.parkinglot.parking.concrete_parking_strategy.SmartParkingStrategy;
import com.parkinglot.parking.StandardParkingBoy;
import com.parkinglot.parking.concrete_parking_strategy.SuperParkingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void should_print_unrecognized_ticket_when_fetch_given_used_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingLot.park(car);
        Car fetchCar = parkingLot.fetch(ticket);
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingLot.fetch(ticket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_wrong_ticket() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Ticket wrongTicket = new Ticket();

        //When
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingLot.fetch(wrongTicket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_no_available_position_when_park_given_a_car_and_full_capacity() {
        //Given
        ParkingLot parkingLot = new ParkingLot();
        Car car = new Car("A99999");
        parkingLot.updateAvailablePositions(0);

        //When
        assertThrows(NoAvailablePositionException.class, () -> parkingLot.park(car));

        //Then
        String expectedOutput = "No available position.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
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
        assertEquals(secondParkingLot, ticket.getParkingLot());
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

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_wrong_ticket_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));
        Ticket wrongTicket = new Ticket();

        //When
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingBoy.fetch(wrongTicket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_no_available_position_when_park_given_a_car_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));
        Car car = new Car("A99999");
        firstParkingLot.updateAvailablePositions(0);
        secondParkingLot.updateAvailablePositions(0);

        //When
        assertThrows(NoAvailablePositionException.class, () -> parkingBoy.park(car));

        //Then
        String expectedOutput = "No available position.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_used_ticket_and_a_standard_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()));
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(ticket);
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingBoy.fetch(ticket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_return_ticket_for_first_parkinglot_when_park_given_a_car_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());

        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingBoy.park(car);
        //Then
        assertNotNull(ticket);
    }

    @Test
    void should_return_car_for_first_parkinglot_when_fetch_given_valid_ticket_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(car, fetchCar);
    }

    @Test
    void should_return_car_for_second_parkinglot_when_fetch_given_valid_ticket_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        firstParkingLot.updateAvailablePositions(2);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(secondParkingLot, ticket.getParkingLot());
        assertEquals(car, fetchCar);
    }

    @Test
    void should_return_car_for_first_parkinglot_and_first_equals_second_fetch_when_given_valid_ticket_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        firstParkingLot.updateAvailablePositions(1);
        secondParkingLot.updateAvailablePositions(1);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(firstParkingLot, ticket.getParkingLot());
        assertEquals(car, fetchCar);
    }

    @Test
    void should_car_when_fetch_given_two_valid_ticket_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());

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

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_wrong_ticket_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());
        Ticket wrongTicket = new Ticket();

        //When
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingBoy.fetch(wrongTicket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_no_available_position_when_park_given_a_car_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());
        Car car = new Car("A99999");
        firstParkingLot.updateAvailablePositions(0);
        secondParkingLot.updateAvailablePositions(0);

        //When
        assertThrows(NoAvailablePositionException.class, () -> parkingBoy.park(car));

        //Then
        String expectedOutput = "No available position.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_used_ticket_and_a_smart_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SmartParkingStrategy());
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(ticket);
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingBoy.fetch(ticket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_return_ticket_for_first_parkinglot_when_park_given_a_car_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();


        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());

        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingBoy.park(car);
        //Then
        assertNotNull(ticket);
    }

    @Test
    void should_return_car_for_first_parkinglot_when_fetch_given_valid_ticket_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(car, fetchCar);
    }

    @Test
    void should_return_car_for_second_parkinglot_when_fetch_given_valid_ticket_and_a_super_parking_boy_second_ratio_lower_than_first() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot(10);
        ParkingLot secondParkingLot = new ParkingLot(100);

        firstParkingLot.updateAvailablePositions(2);
        secondParkingLot.updateAvailablePositions(2);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(secondParkingLot, ticket.getParkingLot());
        assertEquals(car, fetchCar);
    }

    @Test
    void should_return_car_for_first_parkinglot_and_first_equals_second_fetch_when_given_valid_ticket_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();

        firstParkingLot.updateAvailablePositions(1);
        secondParkingLot.updateAvailablePositions(1);

        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());

        Car car = new Car("A99999");
        Ticket ticket = parkingBoy.park(car);
        //When
        Car fetchCar = parkingBoy.fetch(ticket);

        //Then
        assertEquals(firstParkingLot, ticket.getParkingLot());
        assertEquals(car, fetchCar);
    }

    @Test
    void should_car_when_fetch_given_two_valid_ticket_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());

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

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_wrong_ticket_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());
        Ticket wrongTicket = new Ticket();

        //When
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingBoy.fetch(wrongTicket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_no_available_position_when_park_given_a_car_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());
        Car car = new Car("A99999");
        firstParkingLot.updateAvailablePositions(0);
        secondParkingLot.updateAvailablePositions(0);

        //When
        assertThrows(NoAvailablePositionException.class, () -> parkingBoy.park(car));

        //Then
        String expectedOutput = "No available position.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }

    @Test
    void should_print_unrecognized_ticket_when_fetch_given_used_ticket_and_a_super_parking_boy() {
        //Given
        ParkingLot firstParkingLot = new ParkingLot();
        ParkingLot secondParkingLot = new ParkingLot();
        StandardParkingBoy parkingBoy = new StandardParkingBoy(Arrays
                .stream((new ParkingLot[]{firstParkingLot, secondParkingLot}))
                .collect(Collectors.toList()), new SuperParkingStrategy());
        Car car = new Car("A99999");
        //When
        Ticket ticket = parkingBoy.park(car);
        Car fetchCar = parkingBoy.fetch(ticket);
        assertThrows(UnrecognizedParkingTicketException.class, () -> parkingBoy.fetch(ticket));

        //Then
        String expectedOutput = "Unrecognized parking ticket.";
        assertThat(systemOut()).contains(String.format(expectedOutput));
    }


}
