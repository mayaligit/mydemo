package com.cmic.attendance.vo;

import com.cmic.attendance.model.Employee;

/**
 * 入职人员信息表包装类
 */
//@ApiModel(value = "Employee", description = "入职人员信息表")
public class EmployeeVo extends Employee {

    //日期
    private String date;
    //1/打卡，0/未打卡
    private String attFlag;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttFlag() {
        return attFlag;
    }

    public void setAttFlag(String attFlag) {
        this.attFlag = attFlag;
    }
}