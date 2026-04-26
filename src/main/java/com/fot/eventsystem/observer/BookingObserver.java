package com.fot.eventsystem.observer;

import com.fot.eventsystem.model.Booking;


public interface BookingObserver {
    void update(Booking booking);
}
