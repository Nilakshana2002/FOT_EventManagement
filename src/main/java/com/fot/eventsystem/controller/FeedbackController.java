package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Feedback;
import com.fot.eventsystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }


    @PostMapping("/contact/save")
    public String saveFeedback(Feedback feedback) {
        feedbackService.saveFeedback(feedback);
        return "redirect:/contact?success";
    }


    @GetMapping("/admin/feedback")
    public String viewFeedback(Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllFeedback());
        return "admin/feedback-list";
    }
}