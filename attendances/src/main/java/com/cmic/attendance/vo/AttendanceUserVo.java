package com.cmic.attendance.vo;

import java.io.Serializable;

public class AttendanceUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String attendanceUsername;

    private  String attendancePassword;

    private  String checkCode;

    public String getAttendanceUsername() {
        return attendanceUsername;
    }

    public void setAttendanceUsername(String attendanceUsername) {
        this.attendanceUsername = attendanceUsername;
    }

    public String getAttendancePassword() {
        return attendancePassword;
    }

    public void setAttendancePassword(String attendancePassword) {
        this.attendancePassword = attendancePassword;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
