package com.cmic.attendance.model;

/**
 * Created by HT on 2018/1/5.
 */
public class HolidayAndAttendance {
    String number;
    String name;
    String sumattendanceDay;
    String sumholiday;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSumattendanceDay() {
        return sumattendanceDay;
    }

    public void setSumattendanceDay(String sumattendanceDay) {
        this.sumattendanceDay = sumattendanceDay;
    }

    public String getSumholiday() {
        return sumholiday;
    }

    public void setSumholiday(String sumholiday) {
        this.sumholiday = sumholiday;
    }
}
