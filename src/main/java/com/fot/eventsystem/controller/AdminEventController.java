package com.fot.eventsystem.controller;

import com.fot.eventsystem.service.EventService;
import com.fot.eventsystem.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/events")
public class AdminEventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private VenueService venueService;

    @GetMapping
    public String showEventsPage(Model model) {
        model.addAttribute("eventList", eventService.getAllEvents());
        model.addAttribute("venues", venueService.getAllVenues());
        return "admin/manage-events";
    }

    @PostMapping("/save")
    public String saveEvent(@RequestParam String title,
                             @RequestParam String description,
                             @RequestParam String eventDate,
                             @RequestParam String time,
                             @RequestParam String venue,
                             @RequestParam("imageFile") MultipartFile file) {
        try {
            eventService.saveEvent(title, description, eventDate, time, venue, file);
            return "redirect:/admin/events?success=true";
        } catch (Exception e) {
            System.err.println("Error saving event: " + e.getMessage());
            return "redirect:/admin/events?error=true";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }
}
