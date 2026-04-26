package com.fot.eventsystem.observer;

import com.fot.eventsystem.model.Booking;

/**
 * OBSERVER DESIGN PATTERN: Observer Interface
 */
public interface BookingObserver {
    void update(Booking booking);
}
