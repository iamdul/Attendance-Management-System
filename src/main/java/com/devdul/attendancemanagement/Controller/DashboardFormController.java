package com.devdul.attendancemanagement.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {
    public AnchorPane context;
    public void initialize() throws IOException {
        setChildrenUi("DashboardContentForm");
    }
    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        setChildrenUi("DashboardContentForm");
    }
    public void userManagementOnAction(ActionEvent actionEvent) throws IOException {
        setChildrenUi("StudentManagementForm");
    }
    public void adminManagementOnAction(ActionEvent actionEvent) throws IOException {
        setChildrenUi("AdminManagementForm");
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
    private void setChildrenUi(String location) throws IOException{
        context.getChildren().clear();
        context.getChildren().add(FXMLLoader.load(getClass().getResource("/com/devdul/attendancemanagement/View/" + location + ".fxml")));

    }


}
