package com.devdul.attendancemanagement.tm;

import javafx.scene.control.Button;

public class DashboardTm {
    private String index;
    private String name;
    private String email;
    private String time;


    public DashboardTm() {
    }

    public DashboardTm(String index, String name, String email, String time) {
        this.index = index;
        this.name = name;
        this.email = email;
        this.time = time;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
