package com.example.Flight.repository;

import com.example.Flight.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerNameIgnoreCase(String customerName);
}
