package com.devdul.attendancemanagement.Controller;

import com.devdul.attendancemanagement.Model.Student;
import com.devdul.attendancemanagement.tm.StudentTm;
import com.mysql.cj.xdevapi.JsonParser;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class StudentManagementController {
    public AnchorPane context;
    public TextField txtIndex;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtPassword;
    public TextField txtSearch;
    public TableView tblStudent;
    public TableColumn colIndex;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn colOption;
    public Button btn;

    String searchText="";

    public void initialize() throws SQLException, ClassNotFoundException {
        colIndex.setCellValueFactory(new PropertyValueFactory<>("index"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        setIndex();
        setTableData(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            setTableData(searchText);
        });

        tblStudent.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(null!=newValue){
                        setData((StudentTm) newValue);
                    }

                }
        );


    }

    private void setData(StudentTm tm) {
        txtIndex.setText(tm.getIndex());
        txtName.setText(tm.getName());
        txtEmail.setText(tm.getEmail());
        txtPassword.setText(tm.getPassword());
        btn.setText("Update Student");
    }

    private void setIndex() {
        try{
            String lastIndex=getLastIndex();
            if(null!=lastIndex) {
                String splitData[] = lastIndex.split("-");
                String lastIdIntegerAsAString = splitData[1];
                int lastIdIntegerAsAnInt = Integer.parseInt(lastIdIntegerAsAString);
                lastIdIntegerAsAnInt++;
                String generatedStudentIndex = "240-" + lastIdIntegerAsAnInt;
                txtIndex.setText(generatedStudentIndex);

            }else{
                txtIndex.setText("240-1");
            }
        }catch(ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }

    private void setTableData(String searchText) {
        ObservableList<StudentTm> oblist= FXCollections.observableArrayList();
        try{
            for(Student st:searchStudent(searchText)) {
                Button btn = new Button("Delete");
                StudentTm tm = new StudentTm(
                        st.getIndex(),
                        st.getName(),
                        st.getEmail(),
                        st.getPassword(),
                        btn
                );

                btn.setOnAction((e) -> {
                    Alert conf = new Alert(Alert.AlertType.CONFIRMATION,
                            "Are you sure?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = conf.showAndWait();
                    if (buttonType.get() == ButtonType.YES) {
                        try{
                            if(deleteStudent(st.getIndex())){
                                new Alert(Alert.AlertType.INFORMATION,"Deleted.!").show();
                                setTableData(searchText);
                                setIndex();
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
            tblStudent.setItems(oblist);

        }catch(ClassNotFoundException | SQLException e){
            new Alert(Alert.AlertType.ERROR,e.toString()).show();
        }
    }


    public void saveStudentOnAction(ActionEvent actionEvent) {
        Student student=new Student(
                txtIndex.getText(),
                txtPassword.getText(),
                txtEmail.getText(),
                txtName.getText()
        );
        if(btn.getText().equalsIgnoreCase("Save Student")) {
            try {
                if (saveStudent(student)) {
                    System.out.println(student.getIndex());
                    setIndex();
                    System.out.println(txtIndex.getText());
                    clear();
                    setTableData(searchText);
                    new Alert(Alert.AlertType.INFORMATION, "Student saved").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Try Again").show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.toString()).show();
            }
        }else{
            try{
                if(updateStudent(student)){
                    setIndex();
                    clear();
                    setTableData(searchText);
                    btn.setText("Save Student");
                    new Alert(Alert.AlertType.INFORMATION,"Student Updated").show();
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Try Again").show();
                }

            }catch (ClassNotFoundException|SQLException e){
                new Alert(Alert.AlertType.ERROR,e.toString()).show();
            }
        }

    }

    private void clear(){
        txtPassword.clear();
        txtName.clear();
        txtEmail.clear();
    }

    public void addNewStudentOnAction(ActionEvent actionEvent) {
        clear();
        setIndex();
        btn.setText("Save Student");
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


    //=========================connect with database==========================================
    private boolean saveStudent(Student student) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="INSERT INTO students VALUES(?,?,?,?)";
        PreparedStatement statement=connection.prepareStatement(sql);
        statement.setString(1, student.getIndex());
        statement.setString(2, student.getPassword());
        statement.setObject(3,student.getEmail());
        statement.setString(4,student.getName());
        return statement.executeUpdate()>0;
    }
    private String getLastIndex()throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("SELECT `index` FROM students ORDER BY CAST(SUBSTRING(`index`,5)AS UNSIGNED )DESC LIMIT 1");
        ResultSet resultSet=statement.executeQuery();
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return  null;
    }

    public static List<Student> searchStudent(String text) throws ClassNotFoundException, SQLException {
        text="%"+text+"%";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("SELECT * FROM  students WHERE name LIKE ? ");
        statement.setString(1,text);
        ResultSet resultSet=statement.executeQuery();
        List<Student> list=new ArrayList<>();
        while(resultSet.next()){
            list.add(new Student(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));
        }
        return list;

    }

    private boolean deleteStudent(String index)throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        PreparedStatement statement=connection.prepareStatement("DELETE FROM students WHERE `index`=?");
        statement.setString(1,index);
        return statement.executeUpdate()>0;

    }

    private boolean updateStudent(Student student) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection=
                DriverManager.getConnection("jdbc:mysql://localhost:3306/attendance","root","1234");
        String sql="UPDATE students SET password=?,email=?,name=? WHERE `index`=?";
        PreparedStatement statement=connection.prepareStatement(sql);

        statement.setString(1, student.getPassword());
        statement.setObject(2,student.getEmail());
        statement.setString(3,student.getName());
        statement.setString(4, student.getIndex());
        return statement.executeUpdate()>0;
    }
}
