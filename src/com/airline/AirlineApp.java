package com.airline;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class AirlineApp {
    private static FlightService service = new FlightService();
    private static Scanner scanner = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        seedData();
        
        boolean running = true;
        System.out.println("=== Welcome to SkyHigh Airlines ===");

        while (running) {
            System.out.println("\n1. Search Flights");
            System.out.println("2. Book a Flight");
            System.out.println("3. View My Reservations");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": handleSearch(); break;
                case "2": handleBooking(); break;
                case "3": handleViewReservations(); break;
                case "4": running = false; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void handleSearch() {
        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();
        System.out.print("Enter Date (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();

        try {
            LocalDateTime date = LocalDateTime.parse(dateStr + " 00:00", formatter);
            List<Flight> results = service.searchFlights(dest, date);

            if (results.isEmpty()) {
                System.out.println("No flights found.");
            } else {
                System.out.println("--- Available Flights ---");
                for (int i = 0; i < results.size(); i++) {
                    System.out.println((i + 1) + ". " + results.get(i));
                }
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    private static void handleBooking() {
        System.out.println("To book, first search for a flight.");
        handleSearch();
        
        System.out.print("Enter the Destination again to confirm selection context: ");
        String dest = scanner.nextLine(); 
        
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        
        Flight flightToBook = service.getAllFlights().stream()
                .filter(f -> f.getDestination().equalsIgnoreCase(dest))
                .findFirst()
                .orElse(null);

        if (flightToBook == null) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.print("How many seats? ");
        try {
            int seats = Integer.parseInt(scanner.nextLine());
            service.bookFlight(name, flightToBook, seats);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleViewReservations() {
        System.out.print("Enter your Name: ");
        String name = scanner.nextLine();
        List<Reservation> resList = service.getUserReservations(name);
        if (resList.isEmpty()) System.out.println("No reservations found.");
        else resList.forEach(System.out::println);
    }

    private static void seedData() {
        service.addFlight(new Flight("FL101", "New York", LocalDateTime.now().plusDays(1), 10));
        service.addFlight(new Flight("FL102", "London", LocalDateTime.now().plusDays(2), 5));
        service.addFlight(new Flight("FL103", "Paris", LocalDateTime.now().plusDays(1), 2));
    }
}