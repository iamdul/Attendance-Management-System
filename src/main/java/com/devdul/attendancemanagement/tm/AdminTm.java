package com.devdul.attendancemanagement.tm;

import javafx.scene.control.Button;

public class AdminTm {
    private String username;
    private String name;
    private String email;

    private String password;
    private Button btn;

    public AdminTm() {
    }

    public AdminTm(String username, String name, String email,String password ,Button btn) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password=password;
        this.btn = btn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
