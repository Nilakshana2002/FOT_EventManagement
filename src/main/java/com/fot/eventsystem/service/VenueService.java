package com.fot.eventsystem.service;

import com.fot.eventsystem.model.Venues;
import com.fot.eventsystem.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private FileService fileService;

    public List<Venues> getAllVenues() {
        return venueRepository.findAll();
    }

    public void saveVenue(String name, int capacity, double price, MultipartFile imageFile) throws IOException {
        Venues venue = new Venues();
        venue.setName(name);
        venue.setCapacity(capacity);
        venue.setPrice(price);

        String fileName = fileService.saveFile(imageFile);
        venue.setImageName(fileName);

        venueRepository.save(venue);
    }

    public void deleteVenue(Long id) {
        Venues venue = venueRepository.findById(id).orElse(null);
        if (venue != null) {
            fileService.deleteFile(venue.getImageName());
            venueRepository.delete(venue);
        }
    }
}
