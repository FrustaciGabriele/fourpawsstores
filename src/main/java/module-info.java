module com.example.fourpawsstores {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;
    requires javafx.web;


    opens com.example.fourpawsstores to javafx.fxml;
    opens com.example.fourpawsstores.view to javafx.fxml;
    exports com.example.fourpawsstores;
}