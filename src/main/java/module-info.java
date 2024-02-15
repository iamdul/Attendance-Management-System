module com.devdul.attendancemanagement {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.devdul.attendancemanagement to javafx.fxml;
    exports com.devdul.attendancemanagement;
    exports com.devdul.attendancemanagement.Controller;
    opens com.devdul.attendancemanagement.Controller to javafx.fxml;
}