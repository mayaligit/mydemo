package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
/**
 * 雇员分数实体
 */
@ApiModel(value = "EmployeeScore", description = "雇员分数")
public class EmployeeScore extends DataEntity<EmployeeScore> {

    @ApiModelProperty(value = "姓名", example = "姓名")
    protected String username;
    @ApiModelProperty(value = "手机号", example = "手机号")
    protected String phone;
    @ApiModelProperty(value = "月份的字符串", example = "月份的字符串")
    protected String month;
    @ApiModelProperty(value = "总分", example = "总分")
    protected Integer total;
    @ApiModelProperty(value = "时效性", example = "时效性")
    protected Integer timeliness;
    @ApiModelProperty(value = "规范", example = "规范")
    protected Integer specification;
    @ApiModelProperty(value = "编写质量", example = "编写质量")
    protected Integer codeQuality;
    @ApiModelProperty(value = "工作量", example = "工作量")
    protected Integer workload;
    @ApiModelProperty(value = "质量管理", example = "质量管理")
    protected Integer qualityManagement;
    @ApiModelProperty(value = "出勤率", example = "出勤率")
    protected Integer attendance;
    @ApiModelProperty(value = "责任感和积极性", example = "责任感和积极性")
    protected Integer responsibility;

    public EmployeeScore(){

    }
    public EmployeeScore(String id){
        super(id);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTimeliness() {
        return timeliness;
    }

    public void setTimeliness(Integer timeliness) {
        this.timeliness = timeliness;
    }

    public Integer getSpecification() {
        return specification;
    }

    public void setSpecification(Integer specification) {
        this.specification = specification;
    }

    public Integer getCodeQuality() {
        return codeQuality;
    }

    public void setCodeQuality(Integer codeQuality) {
        this.codeQuality = codeQuality;
    }

    public Integer getWorkload() {
        return workload;
    }

    public void setWorkload(Integer workload) {
        this.workload = workload;
    }

    public Integer getQualityManagement() {
        return qualityManagement;
    }

    public void setQualityManagement(Integer qualityManagement) {
        this.qualityManagement = qualityManagement;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Integer responsibility) {
        this.responsibility = responsibility;
    }


}