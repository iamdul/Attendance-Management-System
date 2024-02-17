package com.devdul.attendancemanagement.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardContentFormController {
    public AnchorPane context;
    public TextField txtSearch;
    public Label lblTotal;

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }

    public void clearAllOnAction(ActionEvent actionEvent) {
    }

    public void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/devdul/attendancemanagement/View/" + location + ".fxml"));
        if (loader.getLocation() == null) {
            System.out.println("Resource is null!");
        }
        stage.setScene(new Scene(loader.load()));
        stage.centerOnScreen();
    }
}
