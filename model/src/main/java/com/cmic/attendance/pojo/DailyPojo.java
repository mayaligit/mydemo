package com.cmic.attendance.pojo;/**
 * Created by pc on 2017/10/30.
 */

import com.cmic.attendance.model.Daily;

import java.io.Serializable;

/**
 * @author 何家来
 * @create 2017-10-30 19:04
 **/
public class DailyPojo extends Daily implements Serializable {

    private String date;
    private String attendanceGroup;
    private String phone;
    private String startDate;
    private String endDate;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
