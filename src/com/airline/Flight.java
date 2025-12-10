package com.airline;

import java.time.LocalDateTime;

public class Flight {
	 private String flightNumber;
	 private String destination;
	 private LocalDateTime departureTime;
	 private int availableSeats;
	
	 public Flight(String flightNumber, String destination, LocalDateTime departureTime, int availableSeats) {
	     this.flightNumber = flightNumber;
	     this.destination = destination;
	     this.departureTime = departureTime;
	     this.availableSeats = availableSeats;
	 }
	
	 public String getFlightNumber() { return flightNumber; }
	 public String getDestination() { return destination; }
	 public LocalDateTime getDepartureTime() { return departureTime; }
	 public int getAvailableSeats() { return availableSeats; }
	
	 public void reduceSeats(int count) {
	     if (count > availableSeats) {
	         throw new IllegalArgumentException("Insufficient seats available.");
	     }
	     this.availableSeats -= count;
	 }
	
	 @Override
	 public String toString() {
	     return String.format("Flight[%s] to %s on %s | Seats: %d", 
	         flightNumber, destination, departureTime, availableSeats);
	 }
}