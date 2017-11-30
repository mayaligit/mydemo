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
    protected Integer lateTime;
    @ApiModelProperty(value = "早退次数")
    protected Integer earlyTime;
    @ApiModelProperty(value = "上班时长")
    protected Float officeTime;
    @ApiModelProperty(value = "补填日报次数")
    protected Integer dailyTime;
    @ApiModelProperty(value = "用户名")
    protected String username;
    @ApiModelProperty(value = "统计组")
    protected String attendance_group;
    public Statistics(){

    }
    public Statistics(String id){
        super(id);
    }

    public Integer getLateTime() {
        return lateTime;
    }

    public void setLateTime(Integer lateTime) {
        this.lateTime = lateTime;
    }

    public Integer getEarlyTime() {
        return earlyTime;
    }

    public void setEarlyTime(Integer earlyTime) {
        this.earlyTime = earlyTime;
    }

    public Float getOfficeTime() {
        return officeTime;
    }

    public void setOfficeTime(Float officeTime) {
        this.officeTime = officeTime;
    }

    public Integer getDailyTime() {
        return dailyTime;
    }

    public void setDailyTime(Integer dailyTime) {
        this.dailyTime = dailyTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAttendance_group() {
        return attendance_group;
    }

    public void setAttendance_group(String attendance_group) {
        this.attendance_group = attendance_group;
    }
}