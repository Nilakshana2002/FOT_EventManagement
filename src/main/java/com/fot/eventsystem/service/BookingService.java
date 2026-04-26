package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.Event;
import com.fot.eventsystem.model.User;
import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.repository.BookingRepository;
import com.fot.eventsystem.repository.EventRepository;
import com.fot.eventsystem.repository.UserRepository;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fot.eventsystem.observer.BookingStatusSubject;
import com.fot.eventsystem.observer.EmailNotificationObserver;
import com.fot.eventsystem.observer.SystemLogObserver;
import jakarta.annotation.PostConstruct;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private BookingStatusSubject bookingStatusSubject;

    @PostConstruct
    public void initObservers() {

        bookingStatusSubject.addObserver(new SystemLogObserver());
        bookingStatusSubject.addObserver(new EmailNotificationObserver());
    }

    public String processBooking(Long venueId, Long eventId, String bookingDate, String timeSlot, String message, String registerno) {
        User user = userRepository.findByRegisterno(registerno);
        if (user == null) {
            return "userNotFound";
        }

        LocalDate date = LocalDate.parse(bookingDate);


        if (date.isBefore(LocalDate.now())) {
            return "pastDate";
        }


        if (venueId != null) {
            Venues venue = venueRepository.findById(venueId).orElse(null);
            if (venue != null) {
                if (bookingRepository.existsByVenueAndBookingDateAndTimeSlot(venue, date, timeSlot)) {
                    return "conflict";
                }
            }
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingDate(date);
        booking.setTimeSlot(timeSlot);
        booking.setMessage(message);
        booking.setStatus("PENDING");

        if (eventId != null) {
            Event event = eventRepository.findById(eventId).orElse(null);
            booking.setEvent(event);
        } else if (venueId != null) {
            Venues venue = venueRepository.findById(venueId).orElse(null);
            booking.setVenue(venue);
        }

        bookingRepository.save(booking);
        return "success";
    }

    public List<Booking> getBookingsByUserIdentifier(String identifier) {
        User user = userRepository.findByRegisterno(identifier);
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getEventBookings() {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getEvent() != null)
                .collect(Collectors.toList());
    }

    public List<Booking> getVenueBookings() {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getEvent() == null)
                .collect(Collectors.toList());
    }

    public Booking saveBooking(Booking booking) {
        booking.setStatus("PENDING");
        return bookingRepository.save(booking);
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> findByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

    public List<Booking> findByUser(com.fot.eventsystem.model.User user) {
        return bookingRepository.findByUser(user);
    }

    public void updateStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            booking.setStatus(status);
            bookingRepository.save(booking);
            

            bookingStatusSubject.notifyObservers(booking);
        }
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
