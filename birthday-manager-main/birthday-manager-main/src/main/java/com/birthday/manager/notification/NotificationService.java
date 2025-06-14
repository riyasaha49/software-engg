package com.birthday.manager.notification;

import com.birthday.manager.dao.ClassmateDAO;
import com.birthday.manager.model.Classmate;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationService {
    private final ClassmateDAO classmateDAO;
    private final ScheduledExecutorService scheduler;
    
    public NotificationService() {
        this.classmateDAO = new ClassmateDAO();
        this.scheduler = Executors.newScheduledThreadPool(1);
    }
    
    // Check for today's birthdays
    public void checkTodaysBirthdays() {
        List<Classmate> todaysBirthdays = classmateDAO.getTodaysBirthdays();
        
        if (!todaysBirthdays.isEmpty()) {
            Platform.runLater(() -> showBirthdayNotification(todaysBirthdays));
        }
        
        // Schedule daily check at 9 AM
        scheduleDaily();
    }
    
    // Show birthday notification dialog
    private void showBirthdayNotification(List<Classmate> birthdays) {
        StringBuilder message = new StringBuilder();
        message.append("🎉 আজ জন্মদিন! 🎉\n\n");
        
        for (Classmate classmate : birthdays) {
            message.append("🎂 ").append(classmate.getName());
            if (classmate.getPhone() != null && !classmate.getPhone().isEmpty()) {
                message.append(" (").append(classmate.getPhone()).append(")");
            }
            message.append("\n");
        }
        
        message.append("\nতাদের জন্মদিনের শুভেচ্ছা জানান! 🎈");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("জন্মদিনের অভিনন্দন");
        alert.setHeaderText("আজকের জন্মদিন");
        alert.setContentText(message.toString());
        
        // Customize the alert
        alert.getDialogPane().setPrefWidth(400);
        alert.getDialogPane().setPrefHeight(300);
        
        // Show and wait for user response
        alert.showAndWait();
    }
    
    // Schedule daily birthday check
    private void scheduleDaily() {
        scheduler.scheduleAtFixedRate(() -> {
            List<Classmate> todaysBirthdays = classmateDAO.getTodaysBirthdays();
            if (!todaysBirthdays.isEmpty()) {
                Platform.runLater(() -> showBirthdayNotification(todaysBirthdays));
            }
        }, 24, 24, TimeUnit.HOURS); // Check every 24 hours
    }
    
    // Show upcoming birthdays notification
    public void showUpcomingBirthdaysNotification() {
        List<Classmate> upcomingBirthdays = classmateDAO.getUpcomingBirthdays();
        
        if (upcomingBirthdays.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("আসন্ন জন্মদিন");
            alert.setHeaderText("কোন আসন্ন জন্মদিন নেই");
            alert.setContentText("পরবর্তী ৩০ দিনে কোন সহপাঠীর জন্মদিন নেই।");
            alert.showAndWait();
            return;
        }
        
        StringBuilder message = new StringBuilder();
        message.append("📅 আসন্ন জন্মদিনসমূহ (পরবর্তী ৩০ দিন):\n\n");
        
        for (Classmate classmate : upcomingBirthdays) {
            long daysUntil = classmate.getDaysUntilBirthday();
            message.append("🎂 ").append(classmate.getName())
                   .append(" - ").append(classmate.getFormattedBirthDate())
                   .append(" (").append(daysUntil).append(" দিন বাকি)\n");
        }
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("আসন্ন জন্মদিন");
        alert.setHeaderText("পরবর্তী ৩০ দিনের জন্মদিন");
        alert.setContentText(message.toString());
        alert.getDialogPane().setPrefWidth(500);
        alert.getDialogPane().setPrefHeight(400);
        alert.showAndWait();
    }
    
    // Stop the scheduler
    public void shutdown() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}