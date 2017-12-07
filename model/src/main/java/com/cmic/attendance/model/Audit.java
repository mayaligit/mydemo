package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 审批实体
 */
@ApiModel(value = "Audit", description = "日报")
public class Audit extends DataEntity<Audit> {

    @ApiModelProperty(value = "提交审批用户名")
    protected String userName;
    @ApiModelProperty(value = "审批提交时间")
    protected Date submitTime;
    @ApiModelProperty(value = "申请的审批的内容")
    protected String auditContent;
    @ApiModelProperty(value = "审批人ID")
    protected String auditUserId;
    @ApiModelProperty(value = "审批人名字")
    protected String auditUserName;
    @ApiModelProperty(value = "审批意见状态,0/已阅,1/已处理,2/未处理")
    protected Integer auditStatus;
    @ApiModelProperty(value = "审批时间")
    protected Date auditTime;
    @ApiModelProperty(value = "审批的是补卡类型时,关联对应考勤")
    protected String attendanceId;
    @ApiModelProperty(value = "0/同意 , 1/拒绝")
    protected Integer auditSuggestion;
    @ApiModelProperty(value = "0/请假,1/外勤,2/缺卡")
    protected Integer businessType;
    @ApiModelProperty(value = "审批意见备注")
    protected String suggestionRemarksvarchar;
    @ApiModelProperty(value = "提交审批所属组")
    protected String attendanceGroup;
    @ApiModelProperty(value = "请假开始时间,外勤开始时间")
    protected Date startDate;
    @ApiModelProperty(value = "请假结束时间,外勤结束时间")
    protected Date endDate;
    @ApiModelProperty(value = "请假或外勤时长 1/1天 0.5/半天")
    protected Double holidayDays;
    @ApiModelProperty(value = "申请人手机号码")
    protected String phoneNumber;
    protected String dateStr;
    protected String date;

    public Audit() {

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Integer getAuditSuggestion() {
        return auditSuggestion;
    }

    public void setAuditSuggestion(Integer auditSuggestion) {
        this.auditSuggestion = auditSuggestion;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getSuggestionRemarksvarchar() {
        return suggestionRemarksvarchar;
    }

    public void setSuggestionRemarksvarchar(String suggestionRemarksvarchar) {
        this.suggestionRemarksvarchar = suggestionRemarksvarchar;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(Double holidayDays) {
        this.holidayDays = holidayDays;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}