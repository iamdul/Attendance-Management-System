package com.devdul.attendancemanagement.Controller;

import com.devdul.attendancemanagement.Model.Admin;
import com.devdul.attendancemanagement.Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.*;

public class LoginFormController {

    @FXML
    public TextField txtUsername;

    @FXML
    public AnchorPane context;

    @FXML
    public PasswordField txtPassword;



    public void studentOnAction(ActionEvent actionEvent) throws IOException,ClassNotFoundException {
        String index=txtUsername.getText().toLowerCase();
        String password=txtPassword.getText().trim();
        try{
            Student selectedStudent=studentLogin(index);
            if (selectedStudent != null) {
                if(password.equals(selectedStudent.getPassword())){

                    new Alert(Alert.AlertType.INFORMATION, "Login Successful..!").show();
                    setUiWithParams("MarkAttendanceForm",index,selectedStudent.getEmail());
                }else{
                    new Alert(Alert.AlertType.ERROR, "Wrong Password.!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Student Not Found").show();
            }
        }catch(ClassNotFoundException|SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }

    public void adminOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String username=txtUsername.getText().toLowerCase();
        String password=txtPassword.getText().trim();
        try{
            Admin selectedAdmin=adminLogin(username);
            if (selectedAdmin != null) {
                if(password.equals(selectedAdmin.getPassword())){
                    new Alert(Alert.AlertType.INFORMATION, "Login Successful..!").show();
                    setUi("DashboardForm");
                }else{
                    new Alert(Alert.AlertType.ERROR, "Wrong Password.!").show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Admin Not Found").show();
            }
        }catch(ClassNotFoundException|SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }


    public void setUi(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/devdul/attendancemanagement/View/" + location + ".fxml"));
        if (loader.getLocation() == null) {
            System.out.println("Resource is null!");
        }
        stage.setScene(new Scene(loader.load()));
        stage.centerOnScreen();
    }
    public void setUiWithParams(String location,String index,String email) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/com/devdul/attendancemanagement/View/" + location + ".fxml"));
        if (loader.getLocation() == null) {
            System.out.println("Resource is null!");
        }
        AnchorPane root = loader.load();
        MarkAttendanceFormController markAttendanceFormController = loader.getController();
        markAttendanceFormController.setFields(index, email);
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }

    //============================================================
    private Admin adminLogin(String username) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
        DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="SELECT * FROM admin WHERE username=? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,username);
        ResultSet resultSet= statement.executeQuery();
        if(resultSet.next()){
             Admin admin= new Admin(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("name")
            );
            return admin;
        }
        return null;
    }

    private Student studentLogin(String index) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="SELECT * FROM students WHERE `index`=? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,index);
        ResultSet resultSet= statement.executeQuery();
        if(resultSet.next()){
            Student student= new Student(
                    resultSet.getString("index"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("name")
            );
            return student;
        }
        return null;
    }
}