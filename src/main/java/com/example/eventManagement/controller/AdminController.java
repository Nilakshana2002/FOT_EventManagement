package com.fot.eventsystem.controller;

import com.fot.eventsystem.model.Booking;
import com.fot.eventsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



import com.fot.eventsystem.util.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // --- 🔹 DEMONSTRATION OF SINGLETON & JDBC PATTERN 🔹 ---
        try {
            // We call our Singleton instance here
            java.sql.Connection conn = DatabaseConnection.INSTANCE.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT VERSION()");
            if(rs.next()) {
                String dbVersion = rs.getString(1);
                model.addAttribute("dbVersion", dbVersion);
                System.out.println(">>> Singleton Pattern Test: DB Version fetched is " + dbVersion);
            }
        } catch (Exception e) {
            System.err.println("Singleton Demo Error: " + e.getMessage());
        }
        // -----------------------------------------------------

        model.addAttribute("eventBookings", bookingService.getEventBookings());
        model.addAttribute("venueBookings", bookingService.getVenueBookings());
        return "admin/dashboard";
    }

    @GetMapping("/bookings/approve/{id}")
    public String approveBooking(@PathVariable Long id) {
        bookingService.updateStatus(id, "APPROVED");
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/bookings/reject/{id}")
    public String rejectBooking(@PathVariable Long id) {
        bookingService.updateStatus(id, "REJECTED");
        return "redirect:/admin/dashboard";
    }

    // 🔹 SERIALIZATION DEMONSTRATION: Export Data Backup
    @GetMapping("/backup/export")
    public String exportBackup() {
        java.util.List<Booking> allBookings = bookingService.findAll();
        com.fot.eventsystem.util.DataBackupUtility.saveBackup(allBookings);
        return "redirect:/admin/dashboard?backupSuccess=true";
    }

    // 🔹 DESERIALIZATION DEMONSTRATION: Validate and Read Backup File
    @GetMapping("/backup/validate")
    public String validateBackup() {
        java.util.List<Booking> loadedData = com.fot.eventsystem.util.DataBackupUtility.loadBackup();
        if (loadedData != null) {
            System.out.println("--- 🔹 DESERIALIZED DATA REPORT 🔹 ---");
            for (Booking b : loadedData) {
                System.out.println("Found Booking ID: " + b.getId() + " | Status: " + b.getStatus());
            }
            System.out.println("--------------------------------------");
            return "redirect:/admin/dashboard?validationSuccess=true&count=" + loadedData.size();
        }
        return "redirect:/admin/dashboard?error=true";
    }
}