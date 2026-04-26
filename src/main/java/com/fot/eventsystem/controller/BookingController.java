package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.model.Event;
import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.repository.EventRepository;
import com.fot.eventsystem.repository.VenueRepository;
import com.fot.eventsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private com.fot.eventsystem.facade.BookingFacade bookingFacade;

    @Autowired
    private VenueRepository venueRepository;


    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/booking")
    public String showBookingPage(@RequestParam(required = false) Long eventId, Model model) {
        if (eventId != null) {
            eventRepository.findById(eventId).ifPresent(event -> model.addAttribute("selectedEvent", event));
        }
        model.addAttribute("venues", venueRepository.findAll());
        return "booking";
    }

    @PostMapping("/booking/save")
    public String saveBooking(@RequestParam(required = false) Long venueId,
                              @RequestParam(required = false) Long eventId,
                              @RequestParam String bookingDate,
                              @RequestParam String timeSlot,
                              @RequestParam(required = false) String message,
                              @AuthenticationPrincipal UserDetails userDetails) {


        String result;
        if (eventId != null) {
            result = bookingFacade.registerForEvent(eventId, userDetails.getUsername());
        } else {
            result = bookingFacade.bookVenue(venueId, bookingDate, timeSlot, message, userDetails.getUsername());
        }


        if ("pastDate".equals(result)) {
            return "redirect:/booking?error=pastDate" + (eventId != null ? "&eventId=" + eventId : "");
        } else if ("conflict".equals(result)) {
            return "redirect:/booking?error=conflict" + (eventId != null ? "&eventId=" + eventId : "");
        } else if ("success".equals(result)) {
            return "redirect:/?bookingSuccess=true";
        } else {
            return "redirect:/booking?error=true" + (eventId != null ? "&eventId=" + eventId : "");
        }
    }

    @GetMapping("/my-registrations")
    public String myRegistrations(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Booking> myBookings = bookingService.getBookingsByUserIdentifier(userDetails.getUsername());
        model.addAttribute("bookings", myBookings);
        return "my-registrations";
    }
}
