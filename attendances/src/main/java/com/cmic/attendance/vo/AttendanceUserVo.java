package com.cmic.attendance.vo;

import java.io.Serializable;

public class AttendanceUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private  String attendanceUsername;

    protected String attendanceGroup;

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

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }

    @Override
    public String toString() {
        return "AttendanceUserVo{" +
                "attendanceUsername='" + attendanceUsername + '\'' +
                ", attendancePassword='" + attendancePassword + '\'' +
                ", checkCode='" + checkCode + '\'' +
                '}';
    }
}
