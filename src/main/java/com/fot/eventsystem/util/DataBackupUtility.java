package com.fot.eventsystem.util;

import com.fot.eventsystem.model.Booking;
import java.io.*;
import java.util.List;

 
public class DataBackupUtility {

    private static final String BACKUP_FILE = "system_backup.ser";

    
    public static void saveBackup(List<Booking> bookings) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BACKUP_FILE))) {
            out.writeObject(bookings);
            System.out.println("=========================================");
            System.out.println("SERIALIZATION SUCCESS!");
            System.out.println("Data saved to: " + new File(BACKUP_FILE).getAbsolutePath());
            System.out.println("=========================================");
        } catch (IOException e) {
            System.err.println("Serialization Error: " + e.getMessage());
        }
    }

     
    @SuppressWarnings("unchecked")
    public static List<Booking> loadBackup() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BACKUP_FILE))) {
            List<Booking> bookings = (List<Booking>) in.readObject();
            System.out.println("=========================================");
            System.out.println("DESERIALIZATION SUCCESS!");
            System.out.println("Loaded " + bookings.size() + " items from backup file.");
            System.out.println("=========================================");
            return bookings;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Deserialization Error: " + e.getMessage());
            return null;
        }
    }
}
