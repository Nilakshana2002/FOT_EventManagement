package com.fot.eventsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public enum DatabaseConnection {
    INSTANCE;

    private Connection connection;

  
    DatabaseConnection() {
        try {
          
            String url = "jdbc:mysql://localhost:3306/fot_event";
            String user = "root";
            String password = "Kali00@#12";

          
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, user, password);
            
            System.out.println("=========================================");
            System.out.println("DESIGN PATTERN: Singleton Enum instance created.");
            System.out.println("JDBC: Manual Database connection established.");
            System.out.println("=========================================");
            
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database Connection Failed: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
        
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("JDBC: Connection closed safely.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
