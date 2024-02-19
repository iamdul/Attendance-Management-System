package com.devdul.attendancemanagement.Controller;

import com.devdul.attendancemanagement.Model.Admin;
import com.devdul.attendancemanagement.Model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminManagementController {


    public AnchorPane context;
    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtPassword;
    public Button btn;
    public TextField txtSearch;
    public TableView tblAdmin;
    public TableColumn colUsername;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colOption;



    public void addNewAdminOnAction(ActionEvent actionEvent) {
    }

    public void saveAdminOnAction(ActionEvent actionEvent) {
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

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
    }


    //=========================connect with database==========================================
    private boolean saveAdmin(Admin admin) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="INSERT INTO students VALUES(?,?,?,?)";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1, admin.getUsername());
        statement.setString(2, admin.getPassword());
        statement.setObject(3,admin.getEmail());
        statement.setString(4,admin.getName());
        return statement.executeUpdate()>0;
    }

    public static List<Admin> searchAdmin(String text) throws ClassNotFoundException, SQLException {
        text="%"+text+"%";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("SELECT * FROM  admin WHERE name LIKE ? ");
        statement.setString(1,text);
        ResultSet resultSet=statement.executeQuery();
        List<Admin> list=new ArrayList<>();
        while(resultSet.next()){
            list.add(new Admin(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return list;

    }

    private boolean deleteAdmin(String username)throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("DELETE FROM admin WHERE username=?");
        statement.setString(1,username);
        return statement.executeUpdate()>0;

    }

    private boolean updateAdmin(Admin admin) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="UPDATE student SET password=?,email=?,name=? WHERE username=?";
        PreparedStatement statement=connection.prepareStatement(sql);

        statement.setString(1, admin.getPassword());
        statement.setObject(2,admin.getEmail());
        statement.setString(3,admin.getName());
        statement.setString(4, admin.getUsername());
        return statement.executeUpdate()>0;
    }


}
