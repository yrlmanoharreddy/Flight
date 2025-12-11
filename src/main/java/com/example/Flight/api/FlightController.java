package com.example.Flight.api;


import com.example.Flight.model.Flight;
import com.example.Flight.service.FlightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Search flights by destination and date
    @GetMapping("/search")
    public List<Flight> searchFlights(
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return flightService.searchFlights(destination, date);
    }

    // List all flights (for debugging/demo)
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }
}

