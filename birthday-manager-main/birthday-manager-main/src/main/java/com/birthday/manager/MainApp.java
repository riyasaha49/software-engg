package com.birthday.manager;

import com.birthday.manager.database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize database
        DatabaseManager.getInstance().initializeDatabase();

        // Load FXML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/birthday/manager/fxml/main.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 700);

        // Set CSS
        scene.getStylesheets().add(getClass().getResource("/com/birthday/manager/css/styles.css").toExternalForm());

        primaryStage.setTitle("জন্মদিন পরিচালনা সিস্টেম");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        DatabaseManager.getInstance().closeConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}