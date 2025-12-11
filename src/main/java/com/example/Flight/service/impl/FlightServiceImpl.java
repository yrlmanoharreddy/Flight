
package com.example.Flight.service.impl;

import com.example.Flight.model.Flight;
import com.example.Flight.model.Reservation;
import com.example.Flight.repository.FlightRepository;
import com.example.Flight.repository.ReservationRepository;
import com.example.Flight.service.FlightService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;

    public FlightServiceImpl(FlightRepository flightRepository,
                             ReservationRepository reservationRepository) {
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostConstruct
    @Override
    public void seedSampleData() {
        if (flightRepository.count() > 0) {
            return;
        }

        LocalDate today = LocalDate.now();

        flightRepository.save(new Flight(
                "AA101", "New York",
                LocalDateTime.of(today, LocalTime.of(9, 0)), 50));

        flightRepository.save(new Flight(
                "AA202", "New York",
                LocalDateTime.of(today, LocalTime.of(15, 30)), 20));

        flightRepository.save(new Flight(
                "BA303", "London",
                LocalDateTime.of(today.plusDays(1), LocalTime.of(11, 15)), 10));
    }

    @Override
    public List<Flight> searchFlights(String destination, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return flightRepository
                .findByDestinationIgnoreCaseAndDepartureTimeBetween(
                        destination, startOfDay, endOfDay)
                .stream()
                .filter(f -> f.getAvailableSeats() > 0)
                .toList();
    }

    @Override
    public Reservation bookFlight(String customerName, String flightNumber, int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Seats must be positive");
        }

        Flight flight = flightRepository
                .findByFlightNumberIgnoreCase(flightNumber)
                .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightNumber));

        if (flight.getAvailableSeats() < seats) {
            throw new IllegalStateException("Not enough seats available");
        }

        // update seats and save
        flight.reduceAvailableSeats(seats);
        flightRepository.save(flight);

        Reservation reservation = new Reservation(customerName, flight, seats);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsForCustomer(String customerName) {
        return reservationRepository.findByCustomerNameIgnoreCase(customerName);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
}
