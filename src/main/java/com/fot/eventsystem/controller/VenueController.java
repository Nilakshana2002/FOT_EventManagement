package com.fot.eventsystem.controller;

import com.fot.eventsystem.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @GetMapping
    public String showVenues(Model model) {
        model.addAttribute("venues", venueService.getAllVenues());
        return "admin/manage-venues";
    }

    @PostMapping("/save")
    public String saveVenue(@RequestParam String name,
            @RequestParam int capacity,
            @RequestParam double price,
            @RequestParam MultipartFile imageFile) {
        try {
            venueService.saveVenue(name, capacity, price, imageFile);
            return "redirect:/admin/venues";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/admin/venues?error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
        return "redirect:/admin/venues";
    }
}