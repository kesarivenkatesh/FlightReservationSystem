package com.airline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FlightServiceTest {

    private FlightService flightService;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        flightService = new FlightService();
        
        testFlight = new Flight("TEST01", "Berlin", LocalDateTime.now().plusDays(1), 5);
        flightService.addFlight(testFlight);
    }

    @Test
    void testSearchFlights_Found() {
        List<Flight> results = flightService.searchFlights("Berlin", LocalDateTime.now().plusDays(1));
        assertEquals(1, results.size());
        assertEquals("TEST01", results.get(0).getFlightNumber());
    }

    @Test
    void testSearchFlights_NotFound_WrongDate() {
        List<Flight> results = flightService.searchFlights("Berlin", LocalDateTime.now().plusDays(2));
        assertTrue(results.isEmpty());
    }

    @Test
    void testBookFlight_Success() {
        Reservation res = flightService.bookFlight("John Doe", testFlight, 2);
        
        assertNotNull(res);
        assertEquals(3, testFlight.getAvailableSeats(), "Seats should be reduced by 2");
        assertEquals("John Doe", res.getCustomerName());
    }

    @Test
    void testBookFlight_Failure_NotEnoughSeats() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            flightService.bookFlight("Jane Doe", testFlight, 6);
        });

        assertEquals("Booking failed: Not enough available seats.", exception.getMessage());
        assertEquals(5, testFlight.getAvailableSeats(), "Seat count should remain unchanged on failure");
    }
    
    @Test
    void testBookFlight_Failure_ZeroOrNegative() {
         assertThrows(IllegalArgumentException.class, () -> {
            flightService.bookFlight("Jane Doe", testFlight, 0);
        });
    }
}