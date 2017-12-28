package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 角色权限中间表实体
 */
@ApiModel(value = "RolePermission", description = "角色权限中间表")
public class RolePermission extends DataEntity<RolePermission> {

    @ApiModelProperty(value = "权限表主键ID", example = "权限表主键ID")
    protected String permissionId;
    @ApiModelProperty(value = "角色表主键ID", example = "角色表主键ID")
    protected String roleId;

    public RolePermission(){

    }
    public RolePermission(String id){
        super(id);
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }


}