package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 角色表实体
 */
@ApiModel(value = "Role", description = "角色表")
public class Role extends DataEntity<Role> {

    @ApiModelProperty(value = "角色: 1/超级管理员 2/招聘宝需求方 3/招聘宝供应商 4/考勤组管理员 5/部门管理员", example = "角色: 1/超级管理员 2/招聘宝需求方 3/招聘宝供应商 4/考勤组管理员 5/部门管理员")
    protected Integer role;
    @ApiModelProperty(value = "状态: 1/使用中 2/暂停使用 ", example = "状态: 1/使用中 2/暂停使用 ")
    protected Integer status;

    public Role(){

    }
    public Role(String id){
        super(id);
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}