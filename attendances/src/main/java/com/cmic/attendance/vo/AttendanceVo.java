package com.cmic.attendance.vo;

import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.GroupAddress;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 打卡系统的vo
 * 传递数据的javabean
 */
public class AttendanceVo{

    /*打卡uuid*/
    private String attendanceId;
    /*打卡用户名*/
    @NotNull(message = "用户名为空")
    private String username;
    /*打卡手机号*/
    @NotNull(message = "手机号为空")
    @Length(max=11)
    private String phone;
    /*地点异常状态*/
    private String attendanceStatus;
    /*打卡描述*/
    private String attendanceDesc;
    /*日报状态*/
    private String dailyStatus;
    /*上班打卡地点*/
    @NotNull(message = "打卡地址为空")
    private String location;
    /*上班打卡状态*/
    private String startTimeStatus;
    /*考勤的月份*/
    @NotNull(message = "考勤月份为空")
    private String attendanceMonth;
    /*考勤的地址状态*/
    private String locationStatus;
    /*考勤早上数据*/
    @NotNull(message = "打卡时间为空")
    private String attendanceHour;
    /*是否上班已经打卡*/
    private String isAttendanceStart;
    /*服务器时间 毫秒值*/
    private  String serverTime;
    /*服务器日期*/
    private  String date;
    /*地址是否异常*/
    private  String distance;
    /*班次表id*/
    private String clazzesId;
    /*下班时间*/
    private String offtime;
    /*下班打卡状态*/
    private String endTimeStatus;
    /*是否打下班卡*/
    private String isAttendanceEnd;
    /*下班卡地址*/
    private String endLocation;
    //返回多地址数据
    private ArrayList<GroupAddressVo> addressList;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public String getAttendanceDesc() {
        return attendanceDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public void setAttendanceDesc(String attendanceDesc) {
        this.attendanceDesc = attendanceDesc;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    public String getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(String attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(String locationStatus) {
        this.locationStatus = locationStatus;
    }

    public String getAttendanceHour() {
        return this.attendanceHour=attendanceHour;
    }

    public void setAttendanceHour(String attendanceHour) {
        this.attendanceHour = attendanceHour;
    }

    public String getIsAttendanceStart() {
        return isAttendanceStart;
    }

    public void setIsAttendanceStart(String isAttendanceStart) {
        this.isAttendanceStart = isAttendanceStart;
    }

    public String getStartTimeStatus() {
        return startTimeStatus;
    }

    public void setStartTimeStatus(String startTimeStatus) {
        this.startTimeStatus = startTimeStatus;
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

    public String getDailyStatus() {
        return dailyStatus;
    }

    public void setDailyStatus(String dailyStatus) {
        this.dailyStatus = dailyStatus;
    }

    public String getDistance() {
        return distance;
    }
    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getClazzesId() {
        return clazzesId;
    }

    public void setClazzesId(String clazzesId) {
        this.clazzesId = clazzesId;
    }

    public String getOfftime() {
        return offtime;
    }

    public void setOfftime(String offtime) {
        this.offtime = offtime;
    }

    public String getEndTimeStatus() {
        return endTimeStatus;
    }

    public void setEndTimeStatus(String endTimeStatus) {
        this.endTimeStatus = endTimeStatus;
    }

    public String getIsAttendanceEnd() {
        return isAttendanceEnd;
    }

    public void setIsAttendanceEnd(String isAttendanceEnd) {
        this.isAttendanceEnd = isAttendanceEnd;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public ArrayList<GroupAddressVo> getAddressList() {
        return addressList;
    }

    public void setAddressList(ArrayList<GroupAddressVo> addressList) {
        this.addressList = addressList;
    }
}
