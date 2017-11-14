package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 后台y用户表实体
 */
@ApiModel(value = "AttendanceUser", description = "后台y用户表")
public class AttendanceUser extends DataEntity<AttendanceUser> {

    @ApiModelProperty(value = "账号")
    protected String username;
    @ApiModelProperty(value = "密码")
    protected String password;
    @ApiModelProperty(value = "企业id")
    protected String enterpriseId;
    @ApiModelProperty(value = "企业名称")
    protected String enterpriseName;
    @ApiModelProperty(value = "0/正常,1/停用")
    protected String actived;

    public AttendanceUser(){

    }
    public AttendanceUser(String id){
        super(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getActived() {
        return actived;
    }

    public void setActived(String actived) {
        this.actived = actived;
    }


}