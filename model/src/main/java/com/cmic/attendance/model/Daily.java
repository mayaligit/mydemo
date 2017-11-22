package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 实体
 */
@ApiModel(value = "Daily", description = "")
public class Daily extends DataEntity<Daily> {

    @ApiModelProperty(value = "日报所属考勤的id", example = "日报所属考勤的id")
    protected String attendanceId;
    @ApiModelProperty(value = "日报描述", example = "日报描述")
    protected String dailyDesc;
    @ApiModelProperty(value = "日报提交时间", example = "2017-09-09")
    protected Date submitTime;
    @ApiModelProperty(value = "日报标题", example = "日报标题")
    protected String dailyTitle;
    @ApiModelProperty(value = "昨日工作计划", example = "昨日工作计划")
    protected String yesterdayPlan;
    @ApiModelProperty(value = "昨日已完成工作", example = "昨日已完成工作")
    protected String yesterdayFinished;
    @ApiModelProperty(value = "昨日未完成工作", example = "昨日未完成工作")
    protected String yesterdayUnfinished;
    @ApiModelProperty(value = "今日工作计划", example = "今日工作计划")
    protected String todayPlan;
    @ApiModelProperty(value = "提交日报时的地点", example = "提交日报时的地点")
    protected String submitLocation;
    @ApiModelProperty(value = "日报审批人", example = "日报审批人")
    protected String examiner;
    @ApiModelProperty(value = "日报审批时间", example = "2017-09-09")
    protected Date examineTime;
    @ApiModelProperty(value = "审批意见状态,0/已阅,1/已处理,2/未处理", example = "审批意见状态,0/已阅,1/已处理,2/未处理")
    protected String suggestionStatus;
    @ApiModelProperty(value = "用户名", example = "用户名")
    protected String username;
    @ApiModelProperty(value = "所属考勤组名", example = "所属考勤组名")
    protected String attendanceGroup;

    public Daily(){

    }
    public Daily(String id){
        super(id);
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public String getDailyDesc() {
        return dailyDesc;
    }

    public void setDailyDesc(String dailyDesc) {
        this.dailyDesc = dailyDesc;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getDailyTitle() {
        return dailyTitle;
    }

    public void setDailyTitle(String dailyTitle) {
        this.dailyTitle = dailyTitle;
    }

    public String getYesterdayPlan() {
        return yesterdayPlan;
    }

    public void setYesterdayPlan(String yesterdayPlan) {
        this.yesterdayPlan = yesterdayPlan;
    }

    public String getYesterdayFinished() {
        return yesterdayFinished;
    }

    public void setYesterdayFinished(String yesterdayFinished) {
        this.yesterdayFinished = yesterdayFinished;
    }

    public String getYesterdayUnfinished() {
        return yesterdayUnfinished;
    }

    public void setYesterdayUnfinished(String yesterdayUnfinished) {
        this.yesterdayUnfinished = yesterdayUnfinished;
    }

    public String getTodayPlan() {
        return todayPlan;
    }

    public void setTodayPlan(String todayPlan) {
        this.todayPlan = todayPlan;
    }

    public String getSubmitLocation() {
        return submitLocation;
    }

    public void setSubmitLocation(String submitLocation) {
        this.submitLocation = submitLocation;
    }

    public String getExaminer() {
        return examiner;
    }

    public void setExaminer(String examiner) {
        this.examiner = examiner;
    }

    public Date getExamineTime() {
        return examineTime;
    }

    public void setExamineTime(Date examineTime) {
        this.examineTime = examineTime;
    }

    public String getSuggestionStatus() {
        return suggestionStatus;
    }

    public void setSuggestionStatus(String suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }


}