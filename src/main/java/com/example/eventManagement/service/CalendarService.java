package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.Poster;
import com.fot.eventsystem.repository.BookingRepository;
import com.fot.eventsystem.repository.PosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CalendarService {

    @Autowired
    private PosterRepository posterRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Map<String, Object>> getCalendarEvents() {
        List<Map<String, Object>> events = new ArrayList<>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Add all posters (events) to the calendar
        for (Poster poster : posterRepository.findAll()) {
            if (poster.getEventDate() != null) {
                Map<String, Object> ev = new HashMap<>();
                ev.put("id", "event-" + poster.getId());
                ev.put("title", poster.getTitle());
                ev.put("date", poster.getEventDate().format(fmt));
                ev.put("time", poster.getTime() != null ? poster.getTime() : "");
                ev.put("venue", poster.getVenue() != null ? poster.getVenue() : "");
                ev.put("description", poster.getDescription() != null ? poster.getDescription() : "");
                ev.put("type", "EVENT");
                ev.put("image", poster.getImageName() != null ? "/images/" + poster.getImageName() : "");
                events.add(ev);
            }
        }

        // Add approved bookings to the calendar
        for (Booking booking : bookingRepository.findByStatus("APPROVED")) {
            if (booking.getBookingDate() != null) {
                Map<String, Object> ev = new HashMap<>();
                ev.put("id", "booking-" + booking.getId());

                String title;
                if (booking.getPoster() != null) {
                    title = booking.getPoster().getTitle() + " (Registration)";
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
                } else if (booking.getPoster() != null && booking.getPoster().getVenue() != null) {
                    ev.put("venue", booking.getPoster().getVenue());
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
