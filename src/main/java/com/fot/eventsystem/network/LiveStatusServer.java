package com.fot.eventsystem.network;

import com.fot.eventsystem.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


@Component
public class LiveStatusServer {

    @Autowired
    private BookingRepository bookingRepository;

    private static final int PORT = 9090;

    @PostConstruct
    public void startServer() {
        // --- 🔹 THREADING: Run the server in a background thread 🔹 ---
        Thread serverThread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("=========================================");
                System.out.println("NETWORK: Socket Server started on Port " + PORT);
                System.out.println("THREAD: Background monitoring thread is active.");
                System.out.println("=========================================");

                while (true) {
                    // Wait for a client to connect (Network Programming)
                    try (Socket clientSocket = serverSocket.accept();
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                        
                        // Prepare the data to send over the network
                        long count = bookingRepository.count();
                        String message = "FOT Event System Status: Total Bookings = " + count;
                        
                        // Send data to the client
                        out.println(message);
                        System.out.println("NETWORK: Sent stats to a connected client.");
                    } catch (Exception e) {
                        System.err.println("Network Client Error: " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("Could not start Socket Server: " + e.getMessage());
            }
        });

        serverThread.setDaemon(true); // Ensure thread closes when app stops
        serverThread.start();
    }
}
