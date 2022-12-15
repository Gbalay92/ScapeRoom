module com.example.randomnumber {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.randomnumber to javafx.fxml;
    exports com.example.randomnumber;
}