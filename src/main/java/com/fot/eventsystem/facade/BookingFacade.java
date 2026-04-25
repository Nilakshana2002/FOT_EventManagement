package com.fot.eventsystem.facade;

import com.fot.eventsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FACADE DESIGN PATTERN
 * This class provides a simplified interface to the complex booking subsystem.
 * Instead of the Controller handling validation and logic, it just calls the Facade.
 */
@Component
public class BookingFacade {

    @Autowired
    private BookingService bookingService;

    /**
     * Simplified method to handle event registration.
     * It hides the complexity of Date parsing, User lookup, and Event validation.
     */
    public String registerForEvent(Long eventId, String registerno) {
        // Default values for an event registration
        String today = java.time.LocalDate.now().toString();
        String defaultTime = "Event Schedule";
        String note = "System Registration via Facade";

        // The Facade calls the complex service with the right parameters
        return bookingService.processBooking(null, eventId, today, defaultTime, note, registerno);
    }

    /**
     * Simplified method to handle venue booking.
     */
    public String bookVenue(Long venueId, String date, String time, String reason, String registerno) {
        return bookingService.processBooking(venueId, null, date, time, reason, registerno);
    }
}
