package com.birthday.manager.controller;

import com.birthday.manager.dao.ClassmateDAO;
import com.birthday.manager.model.Classmate;
import com.birthday.manager.notification.NotificationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    
    // FXML Controls
    @FXML private TableView<Classmate> classmateTable;
    @FXML private TableColumn<Classmate, String> nameColumn;
    @FXML private TableColumn<Classmate, String> birthDateColumn;
    @FXML private TableColumn<Classmate, String> emailColumn;
    @FXML private TableColumn<Classmate, String> phoneColumn;
    
    @FXML private TextField nameField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea notesArea;
    
    @FXML private TextField searchNameField;
    @FXML private ComboBox<String> monthComboBox;
    
    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button searchButton;
    @FXML private Button showAllButton;
    @FXML private Button upcomingButton;
    @FXML private Button todayButton;
    
    @FXML private Label statusLabel;
    
    // Data and Services
    private ClassmateDAO classmateDAO;
    private NotificationService notificationService;
    private ObservableList<Classmate> classmateList;
    private Classmate selectedClassmate;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize services
        classmateDAO = new ClassmateDAO();
        notificationService = new NotificationService();
        
        // Initialize table columns
        setupTableColumns();
        
        // Initialize month combo box
        setupMonthComboBox();
        
        // Setup date picker
        setupDatePicker();
        
        // Load initial data
        loadAllClassmates();
        
        // Setup event handlers
        setupEventHandlers();
        
        // Set initial state
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        
        updateStatusLabel("প্রস্তুত");
    }
    
    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        birthDateColumn.setCellValueFactory(cellData -> 
            cellData.getValue().birthDateProperty().asString());
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        // Custom cell factory for birth date formatting
        birthDateColumn.setCellValueFactory(cellData -> {
            Classmate classmate = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(classmate.getFormattedBirthDate());
        });
        
        // Set column widths
        nameColumn.setPrefWidth(200);
        birthDateColumn.setPrefWidth(120);
        emailColumn.setPrefWidth(180);
        phoneColumn.setPrefWidth(120);
    }
    
    private void setupMonthComboBox() {
        String[] bengaliMonths = {
            "সব মাস", "জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন",
            "জুলাই", "অগাস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"
        };
        monthComboBox.setItems(FXCollections.observableArrayList(bengaliMonths));
        monthComboBox.setValue("সব মাস");
    }
    
    private void setupDatePicker() {
        // Set date picker format
        birthDatePicker.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            @Override
            public String toString(LocalDate date) {
                return date != null ? date.format(formatter) : "";
            }
            
            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty() ? LocalDate.parse(string, formatter) : null;
            }
        });
    }
    
    private void setupEventHandlers() {
        // Table selection handler
        classmateTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedClassmate = newSelection;
                populateForm(newSelection);
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            } else {
                selectedClassmate = null;
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        });
        
        // Button handlers
        addButton.setOnAction(e -> addClassmate());
        updateButton.setOnAction(e -> updateClassmate());
        deleteButton.setOnAction(e -> deleteClassmate());
        clearButton.setOnAction(e -> clearForm());
        searchButton.setOnAction(e -> searchClassmates());
        showAllButton.setOnAction(e -> loadAllClassmates());
        upcomingButton.setOnAction(e -> showUpcomingBirthdays());
        todayButton.setOnAction(e -> showTodaysBirthdays());
        
        // Search field enter key handler
        searchNameField.setOnAction(e -> searchClassmates());
    }
    
    @FXML
    private void addClassmate() {
        if (!validateForm()) {
            return;
        }
        
        Classmate classmate = new Classmate();
        classmate.setName(nameField.getText().trim());
        classmate.setBirthDate(birthDatePicker.getValue());
        classmate.setEmail(emailField.getText().trim());
        classmate.setPhone(phoneField.getText().trim());
        classmate.setNotes(notesArea.getText().trim());
        
        if (classmateDAO.addClassmate(classmate)) {
            updateStatusLabel("সহপাঠী সফলভাবে যোগ করা হয়েছে");
            loadAllClassmates();
            clearForm();
            showSuccessAlert("সফল!", "নতুন সহপাঠী সফলভাবে যোগ করা হয়েছে।");
        } else {
            showErrorAlert("ব্যর্থ!", "সহপাঠী যোগ করতে ব্যর্থ হয়েছে।");
        }
    }
    
    @FXML
    private void updateClassmate() {
        if (selectedClassmate == null || !validateForm()) {
            return;
        }
        
        selectedClassmate.setName(nameField.getText().trim());
        selectedClassmate.setBirthDate(birthDatePicker.getValue());
        selectedClassmate.setEmail(emailField.getText().trim());
        selectedClassmate.setPhone(phoneField.getText().trim());
        selectedClassmate.setNotes(notesArea.getText().trim());
        
        if (classmateDAO.updateClassmate(selectedClassmate)) {
            updateStatusLabel("সহপাঠী সফলভাবে আপডেট করা হয়েছে");
            loadAllClassmates();
            clearForm();
            showSuccessAlert("সফল!", "সহপাঠীর তথ্য সফলভাবে আপডেট করা হয়েছে।");
        } else {
            showErrorAlert("ব্যর্থ!", "সহপাঠীর তথ্য আপডেট করতে ব্যর্থ হয়েছে।");
        }
    }
    
    @FXML
    private void deleteClassmate() {
        if (selectedClassmate == null) {
            return;
        }
        
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("নিশ্চিতকরণ");
        confirmAlert.setHeaderText("সহপাঠী মুছে ফেলুন");
        confirmAlert.setContentText("আপনি কি নিশ্চিত যে '" + selectedClassmate.getName() + "' কে মুছে ফেলতে চান?");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (classmateDAO.deleteClassmate(selectedClassmate.getId())) {
                updateStatusLabel("সহপাঠী সফলভাবে মুছে ফেলা হয়েছে");
                loadAllClassmates();
                clearForm();
                showSuccessAlert("সফল!", "সহপাঠী সফলভাবে মুছে ফেলা হয়েছে।");
            } else {
                showErrorAlert("ব্যর্থ!", "সহপাঠী মুছে ফেলতে ব্যর্থ হয়েছে।");
            }
        }
    }
    
    @FXML
    private void clearForm() {
        nameField.clear();
        birthDatePicker.setValue(null);
        emailField.clear();
        phoneField.clear();
        notesArea.clear();
        classmateTable.getSelectionModel().clearSelection();
        selectedClassmate = null;
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
    }
    
    @FXML
    private void searchClassmates() {
        String searchName = searchNameField.getText().trim();
        String selectedMonth = monthComboBox.getValue();
        
        ObservableList<Classmate> searchResults = FXCollections.observableArrayList();
        
        if (!searchName.isEmpty() && !selectedMonth.equals("সব মাস")) {
            // Search by both name and month
            ObservableList<Classmate> nameResults = classmateDAO.searchByName(searchName);
            int monthIndex = monthComboBox.getSelectionModel().getSelectedIndex();
            
            for (Classmate classmate : nameResults) {
                if (classmate.getBirthDate().getMonthValue() == monthIndex) {
                    searchResults.add(classmate);
                }
            }
        } else if (!searchName.isEmpty()) {
            // Search by name only
            searchResults = classmateDAO.searchByName(searchName);
        } else if (!selectedMonth.equals("সব মাস")) {
            // Search by month only
            int monthIndex = monthComboBox.getSelectionModel().getSelectedIndex();
            searchResults = classmateDAO.searchByMonth(monthIndex);
        } else {
            // Show all if no search criteria
            searchResults = classmateDAO.getAllClassmates();
        }
        
        classmateList = FXCollections.observableList(searchResults);
        classmateTable.setItems(classmateList);
        updateStatusLabel("খুঁজে পাওয়া গেছে: " + searchResults.size() + " জন");
    }
    
    @FXML
    private void loadAllClassmates() {
        classmateList = classmateDAO.getAllClassmates();
        classmateTable.setItems(classmateList);
        updateStatusLabel("মোট সহপাঠী: " + classmateList.size() + " জন");
        
        // Clear search fields
        searchNameField.clear();
        monthComboBox.setValue("সব মাস");
    }
    
    @FXML
    private void showUpcomingBirthdays() {
        ObservableList<Classmate> upcomingBirthdays = classmateDAO.getUpcomingBirthdays();
        classmateTable.setItems(upcomingBirthdays);
        updateStatusLabel("আসন্ন জন্মদিন: " + upcomingBirthdays.size() + " জন");
        
        if (upcomingBirthdays.isEmpty()) {
            showInfoAlert("কোন আসন্ন জন্মদিন নেই", "পরবর্তী ৩০ দিনে কোন সহপাঠীর জন্মদিন নেই।");
        }
    }
    
    @FXML
    private void showTodaysBirthdays() {
        notificationService.checkTodaysBirthdays();
    }
    
    private void populateForm(Classmate classmate) {
        nameField.setText(classmate.getName());
        birthDatePicker.setValue(classmate.getBirthDate());
        emailField.setText(classmate.getEmail() != null ? classmate.getEmail() : "");
        phoneField.setText(classmate.getPhone() != null ? classmate.getPhone() : "");
        notesArea.setText(classmate.getNotes() != null ? classmate.getNotes() : "");
    }
    
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showErrorAlert("ভুল তথ্য!", "নাম খালি রাখা যাবে না।");
            nameField.requestFocus();
            return false;
        }
        
        if (birthDatePicker.getValue() == null) {
            showErrorAlert("ভুল তথ্য!", "জন্ম তারিখ দিতে হবে।");
            birthDatePicker.requestFocus();
            return false;
        }
        
        if (birthDatePicker.getValue().isAfter(LocalDate.now())) {
            showErrorAlert("ভুল তথ্য!", "জন্ম তারিখ ভবিষ্যতের হতে পারে না।");
            birthDatePicker.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }
    
    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}