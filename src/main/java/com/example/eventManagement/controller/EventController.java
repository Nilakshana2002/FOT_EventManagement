package com.example.eventManagement.controller;

import com.example.eventManagement.model.Event;
import com.example.eventManagement.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "index";
    }

    @GetMapping("/event/new")
    public String showNewEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "new_event";
    }

    @PostMapping("/event")
    public String saveEvent(@ModelAttribute("event") Event event) {
        eventService.saveEvent(event);
        return "redirect:/";
    }

    @GetMapping("/event/edit/{id}")
    public String showEditEventForm(@PathVariable String id, Model model) {
        model.addAttribute("event", eventService.getEventById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id)));
        return "edit_event";
    }

    @PostMapping("/event/{id}")
    public String updateEvent(@PathVariable String id, @ModelAttribute("event") Event event) {
        event.setId(id);
        eventService.saveEvent(event);
        return "redirect:/";
    }

    @GetMapping("/event/delete/{id}")
    public String deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return "redirect:/";
    }
}

