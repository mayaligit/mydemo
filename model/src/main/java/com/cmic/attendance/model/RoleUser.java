package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 用户角色中间表实体
 */
@ApiModel(value = "RoleUser", description = "用户角色中间表")
public class RoleUser extends DataEntity<RoleUser> {

    @ApiModelProperty(value = "用户ID", example = "用户ID")
    protected String userId;
    @ApiModelProperty(value = "角色ID", example = "角色ID")
    protected String roleId;

    public RoleUser(){

    }
    public RoleUser(String id){
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


}