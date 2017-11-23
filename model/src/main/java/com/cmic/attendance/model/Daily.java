package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 日报实体
 */
@ApiModel(value = "Daily", description = "日报")
public class Daily extends DataEntity<Daily> {

    @ApiModelProperty(value = "日报提交用户名")
    protected String username;
    @ApiModelProperty(value = "日报所属考勤的id")
    protected String attendanceId;
    @ApiModelProperty(value = "日报描述")
    protected String dailyDesc;
    @ApiModelProperty(value = "日报提交时间")
    protected Date submitTime;
    @ApiModelProperty(value = "日报标题")
    protected String dailyTitle;
    @ApiModelProperty(value = "已完成工作")
    protected String finishedWork;
    @ApiModelProperty(value = "未完成工作")
    protected String unfinishedWork;
    @ApiModelProperty(value = "提交日报时的地点")
    protected String submitLocation;
    @ApiModelProperty(value = "日报审批人")
    protected String examiner;
    @ApiModelProperty(value = "日报审批时间")
    protected Date examineTime;
    @ApiModelProperty(value = "审批意见状态,0/已阅,1/已处理,2/未处理")
    protected Integer suggestionStatus;
    @ApiModelProperty(value = "提交审批所属组")
    protected String attendanceGroup;

    public Daily(){

    }
    public Daily(String id){
        super(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFinishedWork() {
        return finishedWork;
    }

    public void setFinishedWork(String finishedWork) {
        this.finishedWork = finishedWork;
    }

    public String getUnfinishedWork() {
        return unfinishedWork;
    }

    public void setUnfinishedWork(String unfinishedWork) {
        this.unfinishedWork = unfinishedWork;
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

    public Integer getSuggestionStatus() {
        return suggestionStatus;
    }

    public void setSuggestionStatus(Integer suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }


}