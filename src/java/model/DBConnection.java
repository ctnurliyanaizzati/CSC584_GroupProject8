package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    // Default connection details for Java DB (Derby) in NetBeans
    private static final String URL = "jdbc:derby://localhost:1527/FYPTracker";
    private static final String USER = "app";
    private static final String PASS = "app";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load the Derby Client Driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // Establish the connection
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Database Connected Successfully!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}