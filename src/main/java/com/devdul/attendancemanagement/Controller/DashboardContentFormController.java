package com.devdul.attendancemanagement.Controller;

import com.devdul.attendancemanagement.Model.Record;
import com.devdul.attendancemanagement.Model.Student;
import com.devdul.attendancemanagement.tm.DashboardTm;
import com.devdul.attendancemanagement.tm.RecordTm;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DashboardContentFormController {
    public AnchorPane context;
    public TextField txtSearch;
    public Label lblTotal;
    public TableView tblDashboard;
    public TableColumn colIndex;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colTime;


    public ObservableList<DashboardTm> obList= FXCollections.observableArrayList();
    public Button btnClearAll;

    String searchText="";
    public void initialize() throws SQLException, ClassNotFoundException {
        colIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setTableData(searchText);




        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            setTableData(searchText);
        });

    }

    private void setTableData(String searchText) {
        obList.clear();

        try {
            for (Student st : StudentManagementController.searchStudent(searchText)) {
                String stTime = "";
                for (Record rc : MarkAttendanceFormController.searchRecord(st.getIndex())) {
                    if (st.getIndex().equals(rc.getIndex()) && !rc.getTime().isEmpty()) {
                        stTime = rc.getTime();
                        break;
                    }
                }
                if(stTime != "") {
                    DashboardTm tm = new DashboardTm(
                            st.getIndex(),
                            st.getName(),
                            st.getEmail(),
                            stTime
                    );
                    obList.add(tm);
                }


            }
            tblDashboard.setItems(obList);
            int total = obList.size();
            lblTotal.setText(Integer.toString(total));
        } catch (ClassNotFoundException | SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
        }
    }



    public void clearAllOnAction(ActionEvent actionEvent) {
        btnClearAll.setOnAction((e) -> {
            Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure want to delete all records?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = conf.showAndWait();
            if(buttonType.get() == ButtonType.YES){
                try{
                    if(deleteRecords()){
                        new Alert(Alert.AlertType.INFORMATION,"All records are deleted!").show();
                        obList.clear();
                        lblTotal.setText("");
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Try Again").show();
                    }


                }catch (ClassNotFoundException | SQLException e1){
                    new Alert(Alert.AlertType.ERROR, e1.toString()).show();
                }
            }
        });

    }
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");
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

    //===============================================================================
    private boolean deleteRecords()throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("DELETE FROM records");
        return statement.executeUpdate()>0;
    }
}
