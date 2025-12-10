package com.airline;

public class Reservation {
	private String customerName;
	private Flight flight;
	private int seatsBooked;
	private String reservationId;
	
	public Reservation(String customerName, Flight flight, int seatsBooked) {
	   this.customerName = customerName;
	   this.flight = flight;
	   this.seatsBooked = seatsBooked;
	   this.reservationId = java.util.UUID.randomUUID().toString().substring(0, 8);
	}
	
	public String getCustomerName() { return customerName; }
	public Flight getFlight() { return flight; }
	public int getSeatsBooked() { return seatsBooked; }
	
	@Override
	public String toString() {
	   return String.format("Res#%s: %s booked %d seats on %s", 
	       reservationId, customerName, seatsBooked, flight.getFlightNumber());
	}
}