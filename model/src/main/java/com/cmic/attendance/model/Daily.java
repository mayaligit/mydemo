package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 日报表实体
 */
@ApiModel(value = "Daily", description = "日报表")
public class Daily extends DataEntity<Daily> {

    @ApiModelProperty(value = "日报所属考勤的id")
    protected String attendanceId;
    @ApiModelProperty(value = "日报描述")
    protected String dailyDesc;
    @ApiModelProperty(value = "日报提交时间")
    protected Date submitTime;
    @ApiModelProperty(value = "日报标题")
    protected String dailyTitle;
    @ApiModelProperty(value = "今日日报")
    protected String todayDaily;
    @ApiModelProperty(value = "提交日报时的地点")
    protected String submitLocation;
    @ApiModelProperty(value = "日报审批人")
    protected String examiner;
    @ApiModelProperty(value = "日报审批时间")
    protected Date examineTime;
    @ApiModelProperty(value = "审批意见状态,0/已阅,1/已处理,2/未处理")
    protected String suggestionStatus;
    @ApiModelProperty(value = "创建时间")
    protected Date createTime;
    @ApiModelProperty(value = "更新时间")
    protected Date updateTime;
    @ApiModelProperty(value = "用户名")
    protected String username;
    @ApiModelProperty(value = "管理所属组")
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

    public String getTodayDaily() {
        return todayDaily;
    }

    public void setTodayDaily(String todayDaily) {
        this.todayDaily = todayDaily;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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