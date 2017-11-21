package com.cmic.attendance.vo;

import java.io.Serializable;

/**
 * 接受打下班参数 V0
 */
public class AttendanceEndVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*当前用户名*/
    private String username;
    /*考勤的日期*/
    private String attendanceMonth;
    /*下班考勤的地址*/
    private String location;
    /*下班的打卡时间*/
    private String offtime;
    /*当前用户*/
    private String phone;
    /*当前用户id*/
    private String attendanceId;
    /*考勤异常*/
    private String attendanceStatus;
    /*考勤异常*/
    private String attendanceDesc;
    /*是否打卡*/
    private String isAttendanceEnd;
    /*打卡状态  0 正常 1/早退  */
    private String endTimeStatus;
    /*日报状态*/
    private String dailyStatus;
    //*返回日期的毫秒值*/
    private String serverTime;
    /*返回日期的年月日*/
    private String date;
    /*判断地点是否异常*/
    private  String distance;
    /*班次表id*/
    private String clazzesId;
    /*考勤组*/
    private String attendanceGroup;
    public String getClazzesId() {
        return clazzesId;
    }

    public void setClazzesId(String clazzesId) {
        this.clazzesId = clazzesId;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getAttendanceMonth() {

        return attendanceMonth;
    }

    public void setAttendanceMonth(String attendanceMonth) {

        this.attendanceMonth = attendanceMonth;
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {

        this.location = location;
    }

    public String getOfftime() {

        return offtime;
    }

    public void setOfftime(String offtime) {

        this.offtime = offtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getIsAttendanceEnd() {
        return isAttendanceEnd;
    }

    public void setIsAttendanceEnd(String isAttendanceEnd) {
        this.isAttendanceEnd = isAttendanceEnd;
    }

    public String getEndTimeStatus() {
        return endTimeStatus;
    }

    public void setEndTimeStatus(String endTimeStatus) {
        this.endTimeStatus = endTimeStatus;
    }

    public String getDailyStatus() {
        return dailyStatus;
    }

    public void setDailyStatus(String dailyStatus) {
        this.dailyStatus = dailyStatus;
    }

    public String getAttendanceDesc() {
        return attendanceDesc;
    }

    public void setAttendanceDesc(String attendanceDesc) {
        this.attendanceDesc = attendanceDesc;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }
}
