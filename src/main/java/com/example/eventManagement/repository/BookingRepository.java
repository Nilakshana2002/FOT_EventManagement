package com.fot.eventsystem.repository;

import com.fot.eventsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByStatus(String status);
    List<Booking> findByUser(com.fot.eventsystem.model.User user);
    boolean existsByVenueAndBookingDateAndTimeSlot(com.fot.eventsystem.model.Venues venue, java.time.LocalDate bookingDate, String timeSlot);

    // 🔹 HIBERNATE REQUIREMENT: Custom HQL (Hibernate Query Language)
    @org.springframework.data.jpa.repository.Query("SELECT b FROM Booking b WHERE b.status = 'APPROVED'")
    List<Booking> findApprovedBookingsHQL();
}
