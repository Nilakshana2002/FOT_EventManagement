package com.fot.eventsystem.controller;

import com.fot.eventsystem.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @GetMapping("/calendar")
    public String calendarPage() {
        return "calendar";
    }

    @GetMapping("/api/calendar/events")
    @ResponseBody
    public List<Map<String, Object>> getCalendarEvents() {
        return calendarService.getCalendarEvents();
    }
}
