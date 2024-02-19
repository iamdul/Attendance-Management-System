package com.devdul.attendancemanagement.tm;

import javafx.scene.control.Button;

public class StudentTm {
    private String index;
    private String name;
    private String email;

    private String password;
    private Button btn;


    public StudentTm() {
    }

    public StudentTm(String index, String name, String email,String password ,Button btn) {
        this.index = index;
        this.name = name;
        this.email = email;
        this.password = password;
        this.btn = btn;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
