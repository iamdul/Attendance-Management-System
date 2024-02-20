package com.devdul.attendancemanagement.Controller;

import com.devdul.attendancemanagement.Model.Admin;
import com.devdul.attendancemanagement.Model.Student;
import com.devdul.attendancemanagement.tm.AdminTm;
import com.devdul.attendancemanagement.tm.StudentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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



    String searchText="";

    public void initialize(){
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            setTableData(searchText);
        });

        tblAdmin.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(null!=newValue){
                        setData((AdminTm) newValue);
                    }

                }
        );
    }

    private void setData(AdminTm tm) {
        txtUsername.setText(tm.getUsername());
        txtName.setText(tm.getName());
        txtEmail.setText(tm.getEmail());
        txtPassword.setText(tm.getPassword());
        btn.setText("Update Student");
    }

    private void setTableData(String searchText) {
        ObservableList<AdminTm> oblist= FXCollections.observableArrayList();
        try{
            for(Admin ad:searchAdmin(searchText)) {
                Button btn = new Button("Delete");
                AdminTm tm = new AdminTm(
                        ad.getUsername(),
                        ad.getName(),
                        ad.getEmail(),
                        ad.getPassword(),
                        btn
                );

                btn.setOnAction((e) -> {
                    Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = conf.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        try{
                            if(deleteAdmin(ad.getUsername())){
                                new Alert(Alert.AlertType.INFORMATION,"Deleted.!").show();
                                setTableData(searchText);
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Try Again").show();
                            }
                        }catch (ClassNotFoundException | SQLException e1) {
                            new Alert(Alert.AlertType.ERROR, e1.toString()).show();
                        }

                    }
                });
                oblist.add(tm);
            }
            tblAdmin.setItems(oblist);

        }catch(ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }



    public void saveAdminOnAction(ActionEvent actionEvent) {
        Admin admin=new Admin(
                txtUsername.getText(),
                txtPassword.getText(),
                txtEmail.getText(),
                txtName.getText()
        );
        if(btn.getText().equalsIgnoreCase("Save Admin")) {
            try {
                if (saveAdmin(admin)) {
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Admin saved").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Try Again").show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }
        }else{
            try{
                if(updateAdmin(admin)){
                    clear();
                    setTableData(searchText);
                    btn.setText("Save Admin");
                    new Alert(Alert.AlertType.INFORMATION,"Admin Updated").show();
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Try Again").show();
                }

            }catch (ClassNotFoundException|SQLException e){
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }
        }

    }

    private void clear(){
        txtUsername.clear();
        txtPassword.clear();
        txtName.clear();
        txtEmail.clear();
    }

    public void addNewAdminOnAction(ActionEvent actionEvent) {
        clear();
        btn.setText("Save Student");
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
        String sql="INSERT INTO admin VALUES(?,?,?,?)";
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
        String sql="UPDATE admin SET password=?,email=?,name=? WHERE username=?";
        PreparedStatement statement=connection.prepareStatement(sql);

        statement.setString(1, admin.getPassword());
        statement.setObject(2,admin.getEmail());
        statement.setString(3,admin.getName());
        statement.setString(4, admin.getUsername());
        return statement.executeUpdate()>0;
    }


}
