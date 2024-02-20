package com.devdul.attendancemanagement.Controller;

import com.devdul.attendancemanagement.Model.Record;
import com.devdul.attendancemanagement.Model.Student;
import com.devdul.attendancemanagement.tm.RecordTm;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MarkAttendanceFormController {

    public AnchorPane context;
    public TextField txtEmail;
    public TextField txtIndex;
    public TableView tblAttendance;
    public TableColumn colIndex;
    public TableColumn colName;
    public TableColumn colTime;

    String searchText="";

    public ObservableList<RecordTm> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        colIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        setTableData(searchText);
        tblAttendance.setItems(obList);
    }

    public void setFields(String index,String email){
        txtIndex.setText(index);
        txtEmail.setText(email);
    }
    private void setTableData(String searchText) {
        try{
            String stName="";
            for(Record rc:searchRecord(searchText)) {
                for(Student st:StudentManagementController.searchStudent(searchText)){
                    if(rc.getIndex().equals(st.getIndex())){
                        stName=st.getName();
                        break;
                    }
                }
                RecordTm tm= new RecordTm(
                        rc.getIndex(),
                        stName,
                        rc.getTime()
                );
                obList.add(tm);
            }
            tblAttendance.setItems(obList);
        }catch(ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }
    public RecordTm addOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(formatter);
        String stName="";
        List<Record> list=searchRecord(txtIndex.getText());
        if(list.size() ==0){
            for(Student st:StudentManagementController.searchStudent(searchText)){
                if(txtIndex.getText().equals(st.getIndex())){
                    stName=st.getName();
                    break;
                }
            }
            RecordTm tm = new RecordTm(
                    txtIndex.getText(),
                    stName,
                    formattedTime
            );
            obList.add(tm);
            return tm;
        }else{
            new Alert(Alert.AlertType.INFORMATION, "You have already added the attendance").show();
            return null;
        }
    }
    public void removeOnAction(ActionEvent actionEvent) {
        boolean found = false;
        Iterator<RecordTm> iterator = obList.iterator();
        while (iterator.hasNext()) {
            RecordTm tm = iterator.next();
            if (txtIndex.getText().equals(tm.getIndex())) {
                if (!found) {
                    iterator.remove();
                    found = true;
                } else {
                    new Alert(Alert.AlertType.WARNING, "You are not allowed to remove multiple records").show();
                    break;
                }
            }
        }
    }
    public void addAttendanceOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        RecordTm tm= addOnAction(null);
        if(tm != null){
            Record record=new Record(
                    tm.getIndex(),
                    tm.getTime()
            );
            try {
                if (saveAttendance(record)) {
                    clear();
                    new Alert(Alert.AlertType.INFORMATION, "Attendance added").show();
                    setUi("LoginForm");
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Try Again").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "You have already added the attendance").show();
            setUi("LoginForm");
        }
    }

    public void clear(){
        txtIndex.clear();
        txtEmail.clear();
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

    //==========================================================================================
    private boolean saveAttendance(Record record) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="INSERT INTO records VALUES(?,?)";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1, record.getIndex());
        statement.setString(2, record.getTime());
        return statement.executeUpdate()>0;
    }
    public static List<Record> searchRecord(String text) throws ClassNotFoundException, SQLException {
        text="%"+text+"%";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("SELECT * FROM  records WHERE `index` LIKE ? ");
        statement.setString(1,text);
        ResultSet resultSet=statement.executeQuery();
        List<Record> list=new ArrayList<>();
        while(resultSet.next()){
            list.add(new Record(
                    resultSet.getString(1),
                    resultSet.getString(2)
            ));
        }
        return list;
    }
}
