package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 后台管理员实体
 */
@ApiModel(value = "AttendanceUser", description = "后台管理员")
public class AttendanceUser extends DataEntity<AttendanceUser> {

    @ApiModelProperty(value = "账号")
    protected String attendanceUsername;
    @ApiModelProperty(value = "密码")
    protected String attendancePassword;
    @ApiModelProperty(value = "管理所属组")
    protected String attendanceGroup;
    @ApiModelProperty(value = "企业id-备用字段")
    protected String enterpriseId;
    @ApiModelProperty(value = "企业名称-备用字段")
    protected String enterpriseName;
    @ApiModelProperty(value = "0/超级管理员,1/考勤组管理员")
    protected String userType;
    @ApiModelProperty(value = "0/正常,1/停用")
    protected String actived;

    public AttendanceUser(){

    }
    public AttendanceUser(String id){
        super(id);
    }

    public String getAttendanceUsername() {
        return attendanceUsername;
    }

    public void setAttendanceUsername(String attendanceUsername) {
        this.attendanceUsername = attendanceUsername;
    }

    public String getAttendancePassword() {
        return attendancePassword;
    }

    public void setAttendancePassword(String attendancePassword) {
        this.attendancePassword = attendancePassword;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getActived() {
        return actived;
    }

    public void setActived(String actived) {
        this.actived = actived;
    }


}