package com.example.Flight.model;

import jakarta.persistence.*;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "seats_booked", nullable = false)
    private int seatsBooked;

    protected Reservation() {
        // JPA only
    }

    public Reservation(String customerName, Flight flight, int seatsBooked) {
        this.customerName = customerName;
        this.flight = flight;
        this.seatsBooked = seatsBooked;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }
}
