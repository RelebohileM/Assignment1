module com.example.relebohile1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.relebohile1 to javafx.fxml;
    exports com.example.relebohile1;
}