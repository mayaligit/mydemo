package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 统计表实体
 */
@ApiModel(value = "Statistics", description = "统计表")
public class Statistics extends DataEntity<Statistics> {

    @ApiModelProperty(value = "迟到次数")
    protected int lateTime;
    @ApiModelProperty(value = "早退次数")
    protected int earlyTime;
    @ApiModelProperty(value = "上班时长")
    protected int officeTime;
    @ApiModelProperty(value = "补填日报次数")
    protected String dailyTime;
    @ApiModelProperty(value = "创建时间")
    protected Date createTime;
    @ApiModelProperty(value = "更新时间")
    protected Date updateTime;
    @ApiModelProperty(value = "用户名")
    protected String username;
    @ApiModelProperty(value = "所属考勤组名")
    protected String attendanceGroup;
    public Statistics(){

    }
    public Statistics(String id){
        super(id);
    }

    public int getLateTime() {
        return lateTime;
    }

    public void setLateTime(int lateTime) {
        this.lateTime = lateTime;
    }

    public int getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(int earlyTime) {
        this.earlyTime = earlyTime;
    }

    public int getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(int officeTime) {
        this.officeTime = officeTime;
    }

    public String getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(String dailyTime) {
        this.dailyTime = dailyTime;
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