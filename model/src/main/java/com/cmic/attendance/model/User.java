package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 用户表实体
 */
@ApiModel(value = "User", description = "用户表")
public class User extends DataEntity<User> {

    @ApiModelProperty(value = "账号")
    protected String username;
    @ApiModelProperty(value = "密码")
    protected String password;
    @ApiModelProperty(value = "企业id")
    protected String enterpriseId;
    @ApiModelProperty(value = "企业名称")
    protected String enterpriseName ;
    @ApiModelProperty(value = "账号使用状态,0/正常,1/停用")
    protected String actived;
    @ApiModelProperty(value = "创建时间")
    protected Date createTime;
    @ApiModelProperty(value = "更新时间")
    protected Date updateTime;

    public User(){

    }
    public User(String id){
        super(id);
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