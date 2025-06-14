package com.birthday.manager.database;

import java.sql.*;
import java.util.Properties;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;
    
    // Database configuration
    private static final String DB_URL = "jdbc:mysql://localhost:3306/birthday_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "dg001";
    
    private DatabaseManager() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Set connection properties
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");
            props.setProperty("serverTimezone", "Asia/Dhaka");
            props.setProperty("characterEncoding", "UTF-8");
            props.setProperty("useUnicode", "true");
            
            // Create database if not exists
            createDatabaseIfNotExists();
            
            // Connect to database
            connection = DriverManager.getConnection(DB_URL, props);
            System.out.println("ডেটাবেস সংযোগ সফল!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL ড্রাইভার খুঁজে পাওয়া যায়নি: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("ডেটাবেস সংযোগ ব্যর্থ: " + e.getMessage());
        }
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void createDatabaseIfNotExists() {
        try {
            String baseUrl = "jdbc:mysql://localhost:3306/";
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("useSSL", "false");
            props.setProperty("allowPublicKeyRetrieval", "true");
            props.setProperty("serverTimezone", "Asia/Dhaka");
            
            Connection tempConnection = DriverManager.getConnection(baseUrl, props);
            Statement stmt = tempConnection.createStatement();
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS birthday_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            stmt.close();
            tempConnection.close();
            
        } catch (SQLException e) {
            System.err.println("ডেটাবেস তৈরি করতে ব্যর্থ: " + e.getMessage());
        }
    }
    
    public void initializeDatabase() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS classmates (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(255) NOT NULL, " +
                    "birth_date DATE NOT NULL, " +
                    "email VARCHAR(255), " +
                    "phone VARCHAR(20), " +
                    "notes TEXT, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci";
            
            PreparedStatement stmt = connection.prepareStatement(createTableSQL);
            stmt.executeUpdate();
            stmt.close();
            
            System.out.println("ডেটাবেস টেবিল প্রস্তুত!");
            
        } catch (SQLException e) {
            System.err.println("টেবিল তৈরি করতে ব্যর্থ: " + e.getMessage());
        }
    }
    
    public Connection getConnection()  throws SQLException {
        try {
            // Check if connection is still valid
            if (connection == null || connection.isClosed()) {
                // Reconnect
                Properties props = new Properties();
                props.setProperty("user", DB_USER);
                props.setProperty("password", DB_PASSWORD);
                props.setProperty("useSSL", "false");
                props.setProperty("allowPublicKeyRetrieval", "true");
                props.setProperty("serverTimezone", "Asia/Dhaka");
                props.setProperty("characterEncoding", "UTF-8");
                props.setProperty("useUnicode", "true");
                
                connection = DriverManager.getConnection(DB_URL, props);
            }
        } catch (SQLException e) {
            System.err.println("সংযোগ পুনরুদ্ধার ব্যর্থ: " + e.getMessage());
        }
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("ডেটাবেস সংযোগ বন্ধ!");
            }
        } catch (SQLException e) {
            System.err.println("সংযোগ বন্ধ করতে ব্যর্থ: " + e.getMessage());
        }
    }
}