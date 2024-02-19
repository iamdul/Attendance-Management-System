module com.devdul.attendancemanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.devdul.attendancemanagement to javafx.fxml;
    exports com.devdul.attendancemanagement;
    exports com.devdul.attendancemanagement.Controller;
    opens com.devdul.attendancemanagement.Controller to javafx.fxml;
    opens com.devdul.attendancemanagement.tm;
}