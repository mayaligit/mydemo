package com.cmic.attendance.model;

import io.swagger.annotations.ApiModel;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 实体
 */
@ApiModel(value = "Audit", description = "")
public class Audit extends DataEntity<Audit> {

    @ApiModelProperty(value = "日报提交时间", example = "2017-09-09")
    protected Date submitTime;
    @ApiModelProperty(value = "申请的审批的内容", example = "申请的审批的内容")
    protected String auditContent;
    @ApiModelProperty(value = "审批人ID", example = "审批人ID")
    protected String auditUserId;
    @ApiModelProperty(value = "审批人名字", example = "审批人名字")
    protected String auditUserName;
    @ApiModelProperty(value = "审批意见状态,0/已阅,1/已处理,2/未处理", example = "审批意见状态,0/已阅,1/已处理,2/未处理")
    protected String auditStatus;
    @ApiModelProperty(value = "审批时间", example = "2017-09-09")
    protected Date auditTime;
    @ApiModelProperty(value = "审批的是补卡类型时,关联对应考勤", example = "审批的是补卡类型时,关联对应考勤")
    protected String attendanceId;
    @ApiModelProperty(value = "0/同意 , 1/拒绝", example = "0/同意 , 1/拒绝")
    protected String auditSuggestion;
    @ApiModelProperty(value = "保留字段,暂定0为补卡新类型, 以后可增加请假等类型", example = "保留字段,暂定0为补卡新类型, 以后可增加请假等类型")
    protected String businessType;
    @ApiModelProperty(value = "审批详情", example = "审批详情")
    protected String auditDetail;
    @ApiModelProperty(value = "")
    protected String username;
    @ApiModelProperty(value = "所属考勤组名", example = "所属考勤组名")
    protected String attendanceGroup;

    public Audit(){

    }
    public Audit(String id){
        super(id);
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

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
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

    public String getAuditSuggestion() {
        return auditSuggestion;
    }

    public void setAuditSuggestion(String auditSuggestion) {
        this.auditSuggestion = auditSuggestion;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getAuditDetail() {
        return auditDetail;
    }

    public void setAuditDetail(String auditDetail) {
        this.auditDetail = auditDetail;
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