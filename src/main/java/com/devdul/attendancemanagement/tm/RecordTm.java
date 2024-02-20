package com.devdul.attendancemanagement.tm;

import java.sql.Time;

public class RecordTm {
    private String index;
    private String name;
    private String time;

    public RecordTm() {
    }

    public RecordTm(String index, String name, String time) {
        this.index = index;
        this.name = name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
