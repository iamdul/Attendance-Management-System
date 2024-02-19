package com.devdul.attendancemanagement.tm;

import javafx.scene.control.Button;

public class AdminTm {
    private String username;
    private String name;
    private String email;
    private Button btn;

    public AdminTm() {
    }

    public AdminTm(String username, String name, String email, Button btn) {
        this.username = username;
        this.name = name;
        this.email = email;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
