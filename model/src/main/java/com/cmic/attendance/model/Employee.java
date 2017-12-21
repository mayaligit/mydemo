package com.cmic.attendance.model;

import com.cmic.saas.base.model.DataEntity;

import java.util.Date;
/**
 * 入职人员信息表实体
 */
//@ApiModel(value = "Employee", description = "入职人员信息表")
public class Employee extends DataEntity<Employee> {

//    @ApiModelProperty(value = "性别 : 0/女 1男")
    protected Integer sex;
//    @ApiModelProperty(value = "入职人员姓名")
    protected String employeeName;
//    @ApiModelProperty(value = "入职人员电话号码")
    protected String telephone;
//    @ApiModelProperty(value = "所属企业")
    protected String enterName;
//    @ApiModelProperty(value = "所属企业ID")
    protected String enterId;
//    @ApiModelProperty(value = "入职部门/项目组")
    protected String enterpriseDepartment;
//    @ApiModelProperty(value = "所属考勤组名称")
    protected String attendanceName;
//    @ApiModelProperty(value = "个人身份证号码")
    protected String personalId;
//    @ApiModelProperty(value = "最高学历")
    protected String highestEducation;
//    @ApiModelProperty(value = "入职岗位名称")
    protected String jobName;
//    @ApiModelProperty(value = "入职时间")
    protected Date hiredate;

    public Employee(){

    }
    public Employee(String id){
        super(id);
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName;
    }

    public String getEnterId() {
        return enterId;
    }

    public void setEnterId(String enterId) {
        this.enterId = enterId;
    }

    public String getEnterpriseDepartment() {
        return enterpriseDepartment;
    }

    public void setEnterpriseDepartment(String enterpriseDepartment) {
        this.enterpriseDepartment = enterpriseDepartment;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }


}