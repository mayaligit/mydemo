package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 角色权限中间表实体
 */
@ApiModel(value = "Permission", description = "角色权限中间表")
public class Permission extends DataEntity<Permission> {

    @ApiModelProperty(value = "权限名称", example = "权限名称")
    protected String permissionName;
    @ApiModelProperty(value = "父极菜单ID", example = "父极菜单ID")
    protected String parentId;
    @ApiModelProperty(value = "菜单分类；1/目录 2/菜单 3/按钮", example = "菜单分类；1/目录 2/菜单 3/按钮")
    protected Integer groupMenu;
    @ApiModelProperty(value = "资源访问路径", example = "资源访问路径")
    protected String url;
    @ApiModelProperty(value = "状态标记: 0/使用 1/不使用", example = "状态标记: 0/使用 1/不使用")
    protected Integer status;
    @ApiModelProperty(value = "权限标识代码", example = "权限标识代码")
    protected String pcode;

    public Permission(){

    }
    public Permission(String id){
        super(id);
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(Integer groupMenu) {
        this.groupMenu = groupMenu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }


}