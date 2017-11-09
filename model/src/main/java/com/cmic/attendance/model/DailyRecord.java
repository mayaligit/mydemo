package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 日志表实体
 */
@ApiModel(value = "DailyRecord", description = "日志表")
public class DailyRecord extends DataEntity<DailyRecord> {

    @ApiModelProperty(value = "用户名", example = "用户名")
    protected String userName;
    @ApiModelProperty(value = "打卡地点", example = "打卡地点")
    protected String location;
    @ApiModelProperty(value = "创建时间", example = "2017-09-09")
    protected Date createTime;
    @ApiModelProperty(value = "更新时间", example = "2017-09-09")
    protected Date updateTime;

    public DailyRecord(){

    }
    public DailyRecord(String id){
        super(id);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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


}