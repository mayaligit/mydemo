package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 实体
 */
@ApiModel(value = "GroupPersonnel", description = "")
public class GroupPersonnel extends DataEntity<GroupPersonnel> {

    @ApiModelProperty(value = "考勤人员手机号", example = "考勤人员手机号")
    protected String personnelPhone;
    @ApiModelProperty(value = "考勤人员用户名", example = "考勤人员用户名")
    protected String personnelName;
    @ApiModelProperty(value = "企业id", example = "企业id")
    protected String enterpriseId;
    @ApiModelProperty(value = "企业名称（保留字段)", example = "企业名称（保留字段)")
    protected String enterpriseName;
    @ApiModelProperty(value = "关联对应的考勤组", example = "关联对应的考勤组")
    protected String attendanceGroupId;

    public GroupPersonnel(){

    }
    public GroupPersonnel(String id){
        super(id);
    }

    public String getPersonnelPhone() {
        return personnelPhone;
    }

    public void setPersonnelPhone(String personnelPhone) {
        this.personnelPhone = personnelPhone;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
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

    public String getAttendanceGroupId() {
        return attendanceGroupId;
    }

    public void setAttendanceGroupId(String attendanceGroupId) {
        this.attendanceGroupId = attendanceGroupId;
    }


}