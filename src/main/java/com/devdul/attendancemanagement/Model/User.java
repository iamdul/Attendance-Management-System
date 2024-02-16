package com.devdul.attendancemanagement.Model;

public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private Boolean isAdmin;

    public User() {
    }

    public User(String username, String password, String email, String name, Boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
