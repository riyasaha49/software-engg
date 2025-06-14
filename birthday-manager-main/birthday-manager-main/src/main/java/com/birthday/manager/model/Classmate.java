package com.birthday.manager.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Classmate {
    private final IntegerProperty id;
    private final StringProperty name;
    private final ObjectProperty<LocalDate> birthDate;
    private final StringProperty email;
    private final StringProperty phone;
    private final StringProperty notes;
    
    public Classmate() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.birthDate = new SimpleObjectProperty<>();
        this.email = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.notes = new SimpleStringProperty();
    }
    
    public Classmate(int id, String name, LocalDate birthDate, String email, String phone, String notes) {
        this();
        setId(id);
        setName(name);
        setBirthDate(birthDate);
        setEmail(email);
        setPhone(phone);
        setNotes(notes);
    }
    
    // ID property
    public IntegerProperty idProperty() {
        return id;
    }
    
    public int getId() {
        return id.get();
    }
    
    public void setId(int id) {
        this.id.set(id);
    }
    
    // Name property
    public StringProperty nameProperty() {
        return name;
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    // Birth date property
    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }
    
    public LocalDate getBirthDate() {
        return birthDate.get();
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }
    
    // Email property
    public StringProperty emailProperty() {
        return email;
    }
    
    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String email) {
        this.email.set(email);
    }
    
    // Phone property
    public StringProperty phoneProperty() {
        return phone;
    }
    
    public String getPhone() {
        return phone.get();
    }
    
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    
    // Notes property
    public StringProperty notesProperty() {
        return notes;
    }
    
    public String getNotes() {
        return notes.get();
    }
    
    public void setNotes(String notes) {
        this.notes.set(notes);
    }
    
    // Formatted birth date for display
    public String getFormattedBirthDate() {
        if (birthDate.get() != null) {
            return birthDate.get().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return "";
    }
    
    // Get month name in Bengali
    public String getBirthMonth() {
        if (birthDate.get() != null) {
            int month = birthDate.get().getMonthValue();
            String[] bengaliMonths = {
                "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন",
                "জুলাই", "অগাস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"
            };
            return bengaliMonths[month - 1];
        }
        return "";
    }
    
    // Check if birthday is today
    public boolean isBirthdayToday() {
        if (birthDate.get() != null) {
            LocalDate today = LocalDate.now();
            return birthDate.get().getMonthValue() == today.getMonthValue() && 
                   birthDate.get().getDayOfMonth() == today.getDayOfMonth();
        }
        return false;
    }
    
    // Check if birthday is upcoming (within next 30 days)
    public boolean isUpcomingBirthday() {
        if (birthDate.get() != null) {
            LocalDate today = LocalDate.now();
            LocalDate thisBirthday = birthDate.get().withYear(today.getYear());
            
            // If birthday has passed this year, check next year
            if (thisBirthday.isBefore(today)) {
                thisBirthday = thisBirthday.plusYears(1);
            }
            
            return thisBirthday.isAfter(today) && thisBirthday.isBefore(today.plusDays(31));
        }
        return false;
    }
    
    // Get days until birthday
    public long getDaysUntilBirthday() {
        if (birthDate.get() != null) {
            LocalDate today = LocalDate.now();
            LocalDate thisBirthday = birthDate.get().withYear(today.getYear());
            
            // If birthday has passed this year, check next year
            if (thisBirthday.isBefore(today) || thisBirthday.equals(today)) {
                thisBirthday = thisBirthday.plusYears(1);
            }
            
            return java.time.temporal.ChronoUnit.DAYS.between(today, thisBirthday);
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return getName() + " (" + getFormattedBirthDate() + ")";
    }
}