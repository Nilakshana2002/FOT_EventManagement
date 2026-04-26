package com.fot.eventsystem.observer;

import com.fot.eventsystem.model.Booking;

/**
 * OBSERVER DESIGN PATTERN: Concrete Observer
 * This observer handles system logging when a booking changes.
 */
public class SystemLogObserver implements BookingObserver {
    
    @Override
    public void update(Booking booking) {
        System.out.println("=========================================");
        System.out.println("OBSERVER PATTERN: SystemLogObserver Notified!");
        System.out.println("Booking ID: " + booking.getId());
        System.out.println("New Status: " + booking.getStatus());
        System.out.println("Target User: " + (booking.getUser() != null ? booking.getUser().getEmail() : "Unknown"));
        System.out.println("=========================================");
    }
}
