package com.example.Flight.service.impl;



import com.example.Flight.model.Flight;
import com.example.Flight.model.Reservation;
import com.example.Flight.service.FlightService;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    // In-memory data storage
    private final List<Flight> flights = Collections.synchronizedList(new ArrayList<>());
    private final List<Reservation> reservations = Collections.synchronizedList(new ArrayList<>());

    @PostConstruct
    @Override
    public void seedSampleData() {
        if (!flights.isEmpty()) {
            return;
        }
        LocalDate today = LocalDate.now();
        flights.add(new Flight("AA101", "New York",
                LocalDateTime.of(today, LocalTime.of(9, 0)), 50));
        flights.add(new Flight("AA202", "New York",
                LocalDateTime.of(today, LocalTime.of(15, 30)), 20));
        flights.add(new Flight("BA303", "London",
                LocalDateTime.of(today.plusDays(1), LocalTime.of(11, 15)), 10));
    }

    @Override
    public List<Flight> searchFlights(String destination, LocalDate date) {
        LocalDate target = date;
        synchronized (flights) {
            return flights.stream()
                    .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                    .filter(f -> f.getDepartureTime().toLocalDate().equals(target))
                    .filter(f -> f.getAvailableSeats() > 0)
                    .toList();
        }
    }

    @Override
    public Reservation bookFlight(String customerName, String flightNumber, int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Seats must be positive");
        }

        Flight flight;
        synchronized (flights) {
            Optional<Flight> opt = flights.stream()
                    .filter(f -> f.getFlightNumber().equalsIgnoreCase(flightNumber))
                    .findFirst();

            if (opt.isEmpty()) {
                throw new IllegalArgumentException("Flight not found: " + flightNumber);
            }
            flight = opt.get();

            if (flight.getAvailableSeats() < seats) {
                throw new IllegalStateException("Not enough seats available");
            }

            // Update seats atomically inside synchronized block
            flight.reduceAvailableSeats(seats);
        }

        Reservation reservation = new Reservation(customerName, flight, seats);

        synchronized (reservations) {
            reservations.add(reservation);
        }

        return reservation;
    }

    @Override
    public List<Reservation> getReservationsForCustomer(String customerName) {
        synchronized (reservations) {
            return reservations.stream()
                    .filter(r -> r.getCustomerName().equalsIgnoreCase(customerName))
                    .toList();
        }
    }

    @Override
    public List<Flight> getAllFlights() {
        synchronized (flights) {
            return new ArrayList<>(flights);
        }
    }
}
