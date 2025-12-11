package com.example.Flight.service;



import com.example.Flight.model.Flight;
import com.example.Flight.model.Reservation;
import com.example.Flight.service.impl.FlightServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    private FlightServiceImpl flightService;

    @BeforeEach
    void setUp() {
        flightService = new FlightServiceImpl();
        flightService.seedSampleData();
    }

    @Test
    void testSearchFlightsReturnsMatchesOnDestinationAndDate() {
        LocalDate today = LocalDate.now();
        List<Flight> flights = flightService.searchFlights("New York", today);

        assertFalse(flights.isEmpty());
        assertTrue(flights.stream()
                .allMatch(f -> f.getDestination().equalsIgnoreCase("New York")));
    }

    @Test
    void testBookFlightReducesAvailableSeats() {
        LocalDate today = LocalDate.now();
        Flight flight = flightService.searchFlights("New York", today).get(0);
        int initialSeats = flight.getAvailableSeats();

        Reservation reservation = flightService.bookFlight("Alice", flight.getFlightNumber(), 3);

        assertNotNull(reservation);
        assertEquals(3, reservation.getSeatsBooked());

        Flight updatedFlight = flightService.searchFlights("New York", today).get(0);
        assertEquals(initialSeats - 3, updatedFlight.getAvailableSeats());
    }

    @Test
    void testBookFlightFailsWhenNotEnoughSeats() {
        LocalDate today = LocalDate.now();
        Flight flight = flightService.searchFlights("London", today.plusDays(1)).get(0);

        int available = flight.getAvailableSeats();
        int tooMany = available + 1;

        assertThrows(IllegalStateException.class, () ->
                flightService.bookFlight("Bob", flight.getFlightNumber(), tooMany));
    }

    @Test
    void testGetReservationsForCustomerReturnsOnlyTheirReservations() {
        LocalDate today = LocalDate.now();
        Flight nyFlight = flightService.searchFlights("New York", today).get(0);

        flightService.bookFlight("Charlie", nyFlight.getFlightNumber(), 2);
        flightService.bookFlight("Dana", nyFlight.getFlightNumber(), 1);

        List<Reservation> charlieRes = flightService.getReservationsForCustomer("Charlie");
        assertEquals(1, charlieRes.size());
        assertEquals("Charlie", charlieRes.get(0).getCustomerName());
    }
}
