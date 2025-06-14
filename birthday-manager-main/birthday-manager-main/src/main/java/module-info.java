module com.birthday.manager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.java;

    opens com.birthday.manager to javafx.fxml;
    opens com.birthday.manager.controller to javafx.fxml;
    exports com.birthday.manager;
}