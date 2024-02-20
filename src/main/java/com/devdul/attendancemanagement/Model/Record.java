package com.devdul.attendancemanagement.Model;

public class Record {
    private String index;
    private String time;

    public Record() {
    }

    public Record(String index, String time) {
        this.index = index;
        this.time = time;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
