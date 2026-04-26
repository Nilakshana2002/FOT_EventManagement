package com.fot.eventsystem.observer;

import com.fot.eventsystem.model.Booking;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * OBSERVER DESIGN PATTERN: The Subject (Observable)
 */
@Component
public class BookingStatusSubject {
    
    private final List<BookingObserver> observers = new ArrayList<>();

    public void addObserver(BookingObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(Booking booking) {
        for (BookingObserver observer : observers) {
            observer.update(booking);
        }
    }
}
