package com.fot.eventsystem.observer;

import com.fot.eventsystem.model.Booking;

/**
 * OBSERVER DESIGN PATTERN: Concrete Observer
 * This observer simulates sending an email notification.
 */
public class EmailNotificationObserver implements BookingObserver {
    
    @Override
    public void update(Booking booking) {
        String email = (booking.getUser() != null) ? booking.getUser().getEmail() : "user";
        System.out.println(">>> EMAIL SIMULATION: Sending notification to " + email);
        System.out.println(">>> Message: Your booking status for " + 
            (booking.getEvent() != null ? booking.getEvent().getTitle() : "Venue") + 
            " has been updated to: " + booking.getStatus());
    }
}
