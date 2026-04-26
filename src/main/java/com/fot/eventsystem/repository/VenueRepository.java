package com.fot.eventsystem.repository;

import com.fot.eventsystem.model.Venues;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venues, Long> {

}