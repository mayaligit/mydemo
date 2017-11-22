package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 实体
 */
@ApiModel(value = "GroupDaily", description = "")
public class GroupDaily extends DataEntity<GroupDaily> {

    @ApiModelProperty(value = "日报模板名字", example = "日报模板名字")
    protected String dailyName;
    @ApiModelProperty(value = "日报模板地址(html地址)", example = "日报模板地址(html地址)")
    protected String dailyAddress;
    @ApiModelProperty(value = "日报内容(注：格式为json格式)", example = "日报内容(注：格式为json格式)")
    protected String dailyContent;
    @ApiModelProperty(value = "日报状态(注0/启用 1/停用)", example = "日报状态(注0/启用 1/停用)")
    protected String dailyStatus;
    @ApiModelProperty(value = "所属考勤组名", example = "所属考勤组名")
    protected String attendanceGroup;

    public GroupDaily(){

    }
    public GroupDaily(String id){
        super(id);
    }

    public String getDailyName() {
        return dailyName;
    }

    public void setDailyName(String dailyName) {
        this.dailyName = dailyName;
    }

    public String getDailyAddress() {
        return dailyAddress;
    }

    public void setDailyAddress(String dailyAddress) {
        this.dailyAddress = dailyAddress;
    }

    public String getDailyContent() {
        return dailyContent;
    }

    public void setDailyContent(String dailyContent) {
        this.dailyContent = dailyContent;
    }

    public String getDailyStatus() {
        return dailyStatus;
    }

    public void setDailyStatus(String dailyStatus) {
        this.dailyStatus = dailyStatus;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }


}