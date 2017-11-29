package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 实体
 */
@ApiModel(value = "WorkStatistics", description = "")
public class WorkStatistics extends DataEntity<WorkStatistics> {

    @ApiModelProperty(value = "手机号", example = "手机号")
    protected String phone;
    @ApiModelProperty(value = "用户名", example = "用户名")
    protected String userName;
    @ApiModelProperty(value = "出勤天数", example = "出勤天数")
    protected String attendanceDays;
    @ApiModelProperty(value = "请假天数", example = "请假天数")
    protected String holidayDays;
    @ApiModelProperty(value = "迟到次数", example = "迟到次数")
    protected String late;
    @ApiModelProperty(value = "早退次数", example = "早退次数")
    protected String leaveEarly;
    @ApiModelProperty(value = "缺卡。0为正常", example = "缺卡。0为正常")
    protected String missingCard;
    @ApiModelProperty(value = "旷工次数", example = "旷工次数")
    protected String absenteeism;
    @ApiModelProperty(value = "外勤次数", example = "外勤次数")
    protected String fieldPersonnel;
    @ApiModelProperty(value = "加班", example = "加班")
    protected String overtime;
    @ApiModelProperty(value = "月份（字符串）。例如：2017-11", example = "月份（字符串）。例如：2017-11")
    protected String month;

    public WorkStatistics(){

    }
    public WorkStatistics(String id){
        super(id);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(String attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public String getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(String holidayDays) {
        this.holidayDays = holidayDays;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
    }

    public String getLeaveEarly() {
        return leaveEarly;
    }

    public void setLeaveEarly(String leaveEarly) {
        this.leaveEarly = leaveEarly;
    }

    public String getMissingCard() {
        return missingCard;
    }

    public void setMissingCard(String missingCard) {
        this.missingCard = missingCard;
    }

    public String getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(String absenteeism) {
        this.absenteeism = absenteeism;
    }

    public String getFieldPersonnel() {
        return fieldPersonnel;
    }

    public void setFieldPersonnel(String fieldPersonnel) {
        this.fieldPersonnel = fieldPersonnel;
    }

    public String getOvertime() {
        return overtime;
    }

    public void setOvertime(String overtime) {
        this.overtime = overtime;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}