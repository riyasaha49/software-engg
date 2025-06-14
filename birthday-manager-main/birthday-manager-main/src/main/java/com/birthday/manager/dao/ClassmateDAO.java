package com.birthday.manager.dao;

import com.birthday.manager.database.DatabaseManager;
import com.birthday.manager.model.Classmate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClassmateDAO {
    private final DatabaseManager dbManager;
    
    public ClassmateDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    // Add new classmate
    public boolean addClassmate(Classmate classmate) {
        String sql = "INSERT INTO classmates (name, birth_date, email, phone, notes) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, classmate.getName());
            stmt.setDate(2, Date.valueOf(classmate.getBirthDate()));
            stmt.setString(3, classmate.getEmail());
            stmt.setString(4, classmate.getPhone());
            stmt.setString(5, classmate.getNotes());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("সহপাঠী যোগ করতে ব্যর্থ: " + e.getMessage());
            return false;
        }
    }
    
    // Update existing classmate
    public boolean updateClassmate(Classmate classmate) {
        String sql = "UPDATE classmates SET name=?, birth_date=?, email=?, phone=?, notes=? WHERE id=?";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, classmate.getName());
            stmt.setDate(2, Date.valueOf(classmate.getBirthDate()));
            stmt.setString(3, classmate.getEmail());
            stmt.setString(4, classmate.getPhone());
            stmt.setString(5, classmate.getNotes());
            stmt.setInt(6, classmate.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("সহপাঠী আপডেট করতে ব্যর্থ: " + e.getMessage());
            return false;
        }
    }
    
    // Delete classmate
    public boolean deleteClassmate(int id) {
        String sql = "DELETE FROM classmates WHERE id=?";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("সহপাঠী মুছতে ব্যর্থ: " + e.getMessage());
            return false;
        }
    }
    
    // Get all classmates
    public ObservableList<Classmate> getAllClassmates() {
        ObservableList<Classmate> classmates = FXCollections.observableArrayList();
        String sql = "SELECT * FROM classmates ORDER BY name";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Classmate classmate = new Classmate(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDate("birth_date").toLocalDate(),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("notes")
                );
                classmates.add(classmate);
            }
            
        } catch (SQLException e) {
            System.err.println("সহপাঠী তালিকা লোড করতে ব্যর্থ: " + e.getMessage());
        }
        
        return classmates;
    }
    
    // Search classmates by name
    public ObservableList<Classmate> searchByName(String searchTerm) {
        ObservableList<Classmate> classmates = FXCollections.observableArrayList();
        String sql = "SELECT * FROM classmates WHERE name LIKE ? ORDER BY name";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Classmate classmate = new Classmate(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("notes")
                    );
                    classmates.add(classmate);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("নাম দিয়ে খুঁজতে ব্যর্থ: " + e.getMessage());
        }
        
        return classmates;
    }
    
    // Search classmates by month
    public ObservableList<Classmate> searchByMonth(int month) {
        ObservableList<Classmate> classmates = FXCollections.observableArrayList();
        String sql = "SELECT * FROM classmates WHERE MONTH(birth_date) = ? ORDER BY DAY(birth_date)";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, month);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Classmate classmate = new Classmate(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("notes")
                    );
                    classmates.add(classmate);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("মাস দিয়ে খুঁজতে ব্যর্থ: " + e.getMessage());
        }
        
        return classmates;
    }
    
    // Get upcoming birthdays (next 30 days)
    public ObservableList<Classmate> getUpcomingBirthdays() {
        ObservableList<Classmate> allClassmates = getAllClassmates();
        ObservableList<Classmate> upcomingBirthdays = FXCollections.observableArrayList();
        
        for (Classmate classmate : allClassmates) {
            if (classmate.isUpcomingBirthday()) {
                upcomingBirthdays.add(classmate);
            }
        }
        
        // Sort by days until birthday
        upcomingBirthdays.sort((c1, c2) -> Long.compare(c1.getDaysUntilBirthday(), c2.getDaysUntilBirthday()));
        
        return upcomingBirthdays;
    }
    
    // Get today's birthdays
    public List<Classmate> getTodaysBirthdays() {
        List<Classmate> todaysBirthdays = new ArrayList<>();
        String sql = "SELECT * FROM classmates WHERE MONTH(birth_date) = MONTH(CURDATE()) AND DAY(birth_date) = DAY(CURDATE())";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Classmate classmate = new Classmate(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDate("birth_date").toLocalDate(),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("notes")
                );
                todaysBirthdays.add(classmate);
            }
            
        } catch (SQLException e) {
            System.err.println("আজকের জন্মদিন খুঁজতে ব্যর্থ: " + e.getMessage());
        }
        
        return todaysBirthdays;
    }
    
    // Get classmate by ID
    public Classmate getClassmateById(int id) {
        String sql = "SELECT * FROM classmates WHERE id = ?";
        
        try (PreparedStatement stmt = dbManager.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Classmate(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("notes")
                    );
                }
            }
            
        } catch (SQLException e) {
            System.err.println("সহপাঠী খুঁজতে ব্যর্থ: " + e.getMessage());
        }
        
        return null;
    }
}