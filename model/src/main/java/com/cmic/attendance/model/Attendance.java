package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 实体
 */
@ApiModel(value = "Attendance", description = "")
public class Attendance extends DataEntity<Attendance> {

    @ApiModelProperty(value = "当前打卡用户")
    protected String attendanceUser;
    @ApiModelProperty(value = "上班打卡时间")
    protected Date startTime;
    @ApiModelProperty(value = "下班打卡时间")
    protected Date endTime;
    @ApiModelProperty(value = "0/正常,1/异常 2/外勤")
    protected String attendanceStatus;
    @ApiModelProperty(value = "考勤状态正常的话考勤描述为空，异常的话考勤状态需要显示是否已提审批，未审批显示异常")
    protected String attendanceDesc;
    @ApiModelProperty(value = "考勤月份")
    protected String attendanceMonth;
    @ApiModelProperty(value = "上班打卡地点")
    protected String startLocation;
    @ApiModelProperty(value = "下班打卡地点")
    protected String endLocation;
    @ApiModelProperty(value = "上班打卡使用的信号源,如wifi名,移动4G等保留字段")
    protected String startSignal;
    @ApiModelProperty(value = "下班打卡使用的信号源,如wifi名,移动4G等保留字段")
    protected String endSignal;
    @ApiModelProperty(value = "日报状态,0/未完成,1/已完成")
    protected Integer dailyStatus;
    @ApiModelProperty(value = "备用字段,用户所属考勤组")
    protected String attendanceGroup;
    @ApiModelProperty(value = "0/下班正常,1/下班早退")
    protected String endTimeStatus;
    @ApiModelProperty(value = "0/上班正常,1/上班迟到")
    protected String startTimeStatus;
    @ApiModelProperty(value = "0/正常，1/缺上班卡，2/缺下班卡")
    protected String attendanceCardStatus;
    @ApiModelProperty(value = "考勤打卡的经度")
    protected String attendanceLongitude;
    @ApiModelProperty(value = "考勤打卡的维度")
    protected String attendanceDimension;

    public Attendance(){

    }
    public Attendance(String id){
        super(id);
    }

    public String getAttendanceUser() {
        return attendanceUser;
    }

    public void setAttendanceUser(String attendanceUser) {
        this.attendanceUser = attendanceUser;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getAttendanceDesc() {
        return attendanceDesc;
    }

    public void setAttendanceDesc(String attendanceDesc) {
        this.attendanceDesc = attendanceDesc;
    }

    public String getAttendanceMonth() {
        return attendanceMonth;
    }

    public void setAttendanceMonth(String attendanceMonth) {
        this.attendanceMonth = attendanceMonth;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartSignal() {
        return startSignal;
    }

    public void setStartSignal(String startSignal) {
        this.startSignal = startSignal;
    }

    public String getEndSignal() {
        return endSignal;
    }

    public void setEndSignal(String endSignal) {
        this.endSignal = endSignal;
    }

    public Integer getDailyStatus() {
        return dailyStatus;
    }

    public void setDailyStatus(Integer dailyStatus) {
        this.dailyStatus = dailyStatus;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }

    public String getEndTimeStatus() {
        return endTimeStatus;
    }

    public void setEndTimeStatus(String endTimeStatus) {
        this.endTimeStatus = endTimeStatus;
    }

    public String getStartTimeStatus() {
        return startTimeStatus;
    }

    public void setStartTimeStatus(String startTimeStatus) {
        this.startTimeStatus = startTimeStatus;
    }

    public String getAttendanceCardStatus() {
        return attendanceCardStatus;
    }

    public void setAttendanceCardStatus(String attendanceCardStatus) {
        this.attendanceCardStatus = attendanceCardStatus;
    }

    public String getAttendanceLongitude() {
        return attendanceLongitude;
    }

    public void setAttendanceLongitude(String attendanceLongitude) {
        this.attendanceLongitude = attendanceLongitude;
    }

    public String getAttendanceDimension() {
        return attendanceDimension;
    }

    public void setAttendanceDimension(String attendanceDimension) {
        this.attendanceDimension = attendanceDimension;
    }


}