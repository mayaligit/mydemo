package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 实体
 */
@ApiModel(value = "Clazzes", description = "")
public class Clazzes extends DataEntity<Clazzes> {

    @ApiModelProperty(value = "设定正常打卡地点", example = "设定正常打卡地点")
    protected String nomalAddress;
    @ApiModelProperty(value = "正常上班时间", example = "正常上班时间")
    protected String nomalStartTime;
    @ApiModelProperty(value = "正常下班时间", example = "正常下班时间")
    protected String nomalEndTime;
    @ApiModelProperty(value = "用户所属组ID", example = "用户所属组ID")
    protected String classGroupId;
    @ApiModelProperty(value = "用户所属组名字", example = "用户所属组名字")
    protected String classGroup;
    @ApiModelProperty(value = "用户名", example = "用户名")
    protected String username;
    @ApiModelProperty(value = "打卡应到人数", example = "打卡应到人数")
    protected String total;

    public Clazzes(){

    }
    public Clazzes(String id){
        super(id);
    }

    public String getNomalAddress() {
        return nomalAddress;
    }

    public void setNomalAddress(String nomalAddress) {
        this.nomalAddress = nomalAddress;
    }

    public String getNomalStartTime() {
        return nomalStartTime;
    }

    public void setNomalStartTime(String nomalStartTime) {
        this.nomalStartTime = nomalStartTime;
    }

    public String getNomalEndTime() {
        return nomalEndTime;
    }

    public void setNomalEndTime(String nomalEndTime) {
        this.nomalEndTime = nomalEndTime;
    }

    public String getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(String classGroupId) {
        this.classGroupId = classGroupId;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }


}