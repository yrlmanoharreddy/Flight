package com.example.Flight.repository;

import com.example.Flight.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumberIgnoreCase(String flightNumber);

    List<Flight> findByDestinationIgnoreCaseAndDepartureTimeBetween(
            String destination,
            LocalDateTime startOfDay,
            LocalDateTime endOfDay
    );
}

