package com.fot.eventsystem.facade;

import com.fot.eventsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class BookingFacade {

    @Autowired
    private BookingService bookingService;


    public String registerForEvent(Long eventId, String registerno) {

        String today = java.time.LocalDate.now().toString();
        String defaultTime = "Event Schedule";
        String note = "System Registration via Facade";


        return bookingService.processBooking(null, eventId, today, defaultTime, note, registerno);
    }


    public String bookVenue(Long venueId, String date, String time, String reason, String registerno) {
        return bookingService.processBooking(venueId, null, date, time, reason, registerno);
    }
}
