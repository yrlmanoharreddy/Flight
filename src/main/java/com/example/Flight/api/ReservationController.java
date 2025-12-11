package com.example.Flight.api;



import com.example.Flight.api.dto.BookingRequest;
import com.example.Flight.model.Reservation;
import com.example.Flight.service.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final FlightService flightService;

    public ReservationController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Book a flight
    @PostMapping
    public ResponseEntity<?> bookFlight(@RequestBody BookingRequest request) {
        try {
            Reservation reservation = flightService.bookFlight(
                    request.getCustomerName(),
                    request.getFlightNumber(),
                    request.getSeats()
            );
            return ResponseEntity.ok(reservation);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Get reservations by customer name
    @GetMapping
    public List<Reservation> getReservations(@RequestParam String customerName) {
        return flightService.getReservationsForCustomer(customerName);
    }
}
