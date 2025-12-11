package com.example.Flight.service;

import com.example.Flight.model.Flight;
import com.example.Flight.model.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {

    List<Flight> searchFlights(String destination, LocalDate date);

    Reservation bookFlight(String customerName, String flightNumber, int seats);

    List<Reservation> getReservationsForCustomer(String customerName);

    List<Flight> getAllFlights();

    void seedSampleData();
}

