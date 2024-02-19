package com.devdul.attendancemanagement.Model;

public class Student {
    private String index;
    private String password;
    private String email;
    private String name;
    public Student() {
    }



    public Student(String index, String password, String email, String name) {
        this.index = index;
        this.password = password;
        this.email = email;
        this.name = name;
    }



    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
