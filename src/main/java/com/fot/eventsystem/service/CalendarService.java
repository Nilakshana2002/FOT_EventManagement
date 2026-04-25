package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.Event;
import com.fot.eventsystem.repository.BookingRepository;
import com.fot.eventsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CalendarService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Map<String, Object>> getCalendarEvents() {
        List<Map<String, Object>> events = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Add all events to the calendar
        for (Event event : eventRepository.findAll()) {
            if (event.getEventDate() != null) {
                Map<String, Object> ev = new HashMap<>();
                ev.put("id", "event-" + event.getId());
                ev.put("title", event.getTitle());
                ev.put("date", event.getEventDate().format(fmt));
                ev.put("time", event.getTime() != null ? event.getTime() : "");
                ev.put("venue", event.getVenue() != null ? event.getVenue() : "");
                ev.put("description", event.getDescription() != null ? event.getDescription() : "");
                ev.put("type", "EVENT");
                ev.put("image", event.getImageName() != null ? "/images/" + event.getImageName() : "");
                events.add(ev);
            }
        }

        // Add approved bookings to the calendar
        for (Booking booking : bookingRepository.findByStatus("APPROVED")) {
            if (booking.getBookingDate() != null) {
                Map<String, Object> ev = new HashMap<>();
                ev.put("id", "booking-" + booking.getId());

                String title;
                if (booking.getEvent() != null) {
                    title = booking.getEvent().getTitle() + " (Registration)";
                } else if (booking.getVenue() != null) {
                    title = booking.getVenue().getName() + " — Venue Booking";
                } else {
                    title = "Booking #" + booking.getId();
                }
                ev.put("title", title);
                ev.put("date", booking.getBookingDate().format(fmt));
                ev.put("time", booking.getTimeSlot() != null ? booking.getTimeSlot() : "");

                if (booking.getVenue() != null) {
                    ev.put("venue", booking.getVenue().getName());
                } else if (booking.getEvent() != null && booking.getEvent().getVenue() != null) {
                    ev.put("venue", booking.getEvent().getVenue());
                } else {
                    ev.put("venue", "");
                }

                ev.put("description", booking.getMessage() != null ? booking.getMessage() : "");
                ev.put("type", "BOOKING");
                ev.put("image", "");
                events.add(ev);
            }
        }

        return events;
    }
}
