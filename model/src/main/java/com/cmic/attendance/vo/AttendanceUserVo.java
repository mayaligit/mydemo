package com.cmic.attendance.vo;

import java.io.Serializable;

public class AttendanceUserVo implements Serializable {

    protected static final long serialVersionUID = 1L;

    protected  String id;

    protected  String attendanceUsername;

    protected String attendanceGroup;

    protected  String attendancePassword;

    protected  String checkCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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


}
