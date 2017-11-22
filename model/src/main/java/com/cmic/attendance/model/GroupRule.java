package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 实体
 */
@ApiModel(value = "GroupRule", description = "")
public class GroupRule extends DataEntity<GroupRule> {

    @ApiModelProperty(value = "考勤组名", example = "考勤组名")
    protected String groupName;
    @ApiModelProperty(value = "企业id (保留字段)", example = "企业id (保留字段)")
    protected String groupEnterpriseId;
    @ApiModelProperty(value = "企业名称(保留字段)", example = "企业名称(保留字段)")
    protected String groupEnterpriseName;
    @ApiModelProperty(value = "0/启用 1/停用 默认1", example = "0/启用 1/停用 默认1")
    protected String groupStatus;
    @ApiModelProperty(value = "生效时间从-至 至的话默认2099年", example = "2017-09-09")
    protected Date groupDeadline;
    @ApiModelProperty(value = "考勤组启用模板ID", example = "考勤组启用模板ID")
    protected String groupDailyId;
    @ApiModelProperty(value = "考勤方式 0/自由 1/时长", example = "考勤方式 0/自由 1/时长")
    protected String groupAttendanceWay;
    @ApiModelProperty(value = "组考勤上班时间", example = "组考勤上班时间")
    protected String groupAttendanceStart;
    @ApiModelProperty(value = "组考勤下班时间", example = "组考勤下班时间")
    protected String groupAttendanceEnd;
    @ApiModelProperty(value = "考勤的时长（注：单位小时，如8.5小时)", example = "考勤的时长（注：单位小时，如8.5小时)")
    protected String groupAttendanceDuration;
    @ApiModelProperty(value = "组考勤的周", example = "组考勤的周")
    protected String groupAttendanceWeek;
    @ApiModelProperty(value = "组考勤的经度", example = "组考勤的经度")
    protected String groupAttendanceLongitude;
    @ApiModelProperty(value = "组考勤的维度", example = "组考勤的维度")
    protected String groupAttendanceDimension;
    @ApiModelProperty(value = "考勤地址", example = "考勤地址")
    protected String groupAddress;
    @ApiModelProperty(value = "组考勤的范围（注：单位米 如1000米）", example = "组考勤的范围（注：单位米 如1000米）")
    protected String groupAttendanceScope;

    public GroupRule(){

    }
    public GroupRule(String id){
        super(id);
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupEnterpriseId() {
        return groupEnterpriseId;
    }

    public void setGroupEnterpriseId(String groupEnterpriseId) {
        this.groupEnterpriseId = groupEnterpriseId;
    }

    public String getGroupEnterpriseName() {
        return groupEnterpriseName;
    }

    public void setGroupEnterpriseName(String groupEnterpriseName) {
        this.groupEnterpriseName = groupEnterpriseName;
    }

    public String getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(String groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Date getGroupDeadline() {
        return groupDeadline;
    }

    public void setGroupDeadline(Date groupDeadline) {
        this.groupDeadline = groupDeadline;
    }

    public String getGroupDailyId() {
        return groupDailyId;
    }

    public void setGroupDailyId(String groupDailyId) {
        this.groupDailyId = groupDailyId;
    }

    public String getGroupAttendanceWay() {
        return groupAttendanceWay;
    }

    public void setGroupAttendanceWay(String groupAttendanceWay) {
        this.groupAttendanceWay = groupAttendanceWay;
    }

    public String getGroupAttendanceStart() {
        return groupAttendanceStart;
    }

    public void setGroupAttendanceStart(String groupAttendanceStart) {
        this.groupAttendanceStart = groupAttendanceStart;
    }

    public String getGroupAttendanceEnd() {
        return groupAttendanceEnd;
    }

    public void setGroupAttendanceEnd(String groupAttendanceEnd) {
        this.groupAttendanceEnd = groupAttendanceEnd;
    }

    public String getGroupAttendanceDuration() {
        return groupAttendanceDuration;
    }

    public void setGroupAttendanceDuration(String groupAttendanceDuration) {
        this.groupAttendanceDuration = groupAttendanceDuration;
    }

    public String getGroupAttendanceWeek() {
        return groupAttendanceWeek;
    }

    public void setGroupAttendanceWeek(String groupAttendanceWeek) {
        this.groupAttendanceWeek = groupAttendanceWeek;
    }

    public String getGroupAttendanceLongitude() {
        return groupAttendanceLongitude;
    }

    public void setGroupAttendanceLongitude(String groupAttendanceLongitude) {
        this.groupAttendanceLongitude = groupAttendanceLongitude;
    }

    public String getGroupAttendanceDimension() {
        return groupAttendanceDimension;
    }

    public void setGroupAttendanceDimension(String groupAttendanceDimension) {
        this.groupAttendanceDimension = groupAttendanceDimension;
    }

    public String getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress) {
        this.groupAddress = groupAddress;
    }

    public String getGroupAttendanceScope() {
        return groupAttendanceScope;
    }

    public void setGroupAttendanceScope(String groupAttendanceScope) {
        this.groupAttendanceScope = groupAttendanceScope;
    }


}