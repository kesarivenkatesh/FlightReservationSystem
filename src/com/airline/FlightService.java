package com.airline;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private List<Flight> flights = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getAllFlights() {
        return flights;
    }

    public List<Flight> searchFlights(String destination, LocalDateTime date) {
        return flights.stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(destination))
                .filter(f -> f.getDepartureTime().toLocalDate().equals(date.toLocalDate()))
                .filter(f -> f.getAvailableSeats() > 0) // Optional: Only show flights with seats
                .collect(Collectors.toList());
    }

    public Reservation bookFlight(String customerName, Flight flight, int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Booking seat count must be positive.");
        }
        
        if (flight.getAvailableSeats() < seats) {
            throw new IllegalStateException("Booking failed: Not enough available seats.");
        }

        flight.reduceSeats(seats);

        Reservation reservation = new Reservation(customerName, flight, seats);
        reservations.add(reservation);
        
        System.out.println("Booking Confirmed for " + customerName);
        return reservation;
    }

    public List<Reservation> getUserReservations(String customerName) {
        return reservations.stream()
                .filter(r -> r.getCustomerName().equalsIgnoreCase(customerName))
                .collect(Collectors.toList());
    }
}
