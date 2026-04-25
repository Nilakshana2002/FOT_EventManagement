package com.fot.eventsystem.controller;

import com.fot.eventsystem.service.BookingService;
import com.fot.eventsystem.service.NewsService;
import com.fot.eventsystem.service.EventService;
import com.fot.eventsystem.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private VenueService venueService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        model.addAttribute("eventList", eventService.getAllEvents());

        if (userDetails != null) {
            model.addAttribute("myBookings", bookingService.getBookingsByUserIdentifier(userDetails.getUsername()));
        }

        return "home";
    }

    @GetMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("venues", venueService.getAllVenues());
        return "about";
    }
}

