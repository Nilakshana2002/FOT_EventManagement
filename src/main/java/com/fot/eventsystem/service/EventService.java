package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Event;
import com.fot.eventsystem.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private FileService fileService;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void saveEvent(String title, String description, String eventDate, String time, String venue, MultipartFile imageFile) throws IOException {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setTime(time);
        event.setVenue(venue);

        if (eventDate != null && !eventDate.isEmpty()) {
            event.setEventDate(LocalDate.parse(eventDate));
        }

        String fileName = fileService.saveFile(imageFile);
        event.setImageName(fileName);

        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id).orElse(null);
        if (event != null) {
            fileService.deleteFile(event.getImageName());
            eventRepository.delete(event);
        }
    }
}
