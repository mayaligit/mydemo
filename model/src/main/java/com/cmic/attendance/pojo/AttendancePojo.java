package com.cmic.attendance.pojo;/**
 * Created by pc on 2017/10/30.
 */

import com.cmic.attendance.model.Attendance;

import java.io.Serializable;

/**
 * @author 何家来
 * @create 2017-10-30 19:04
 **/
public class AttendancePojo extends Attendance implements Serializable {

    private String date;
    private String workStartTime;
    private String workEndTime;
    private float workHour;
    private String userType;
    private String telephone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getWorkHour() {
        return workHour;
    }

    public String getWorkStartTime() {
        return workStartTime;
    }

    public void setWorkStartTime(String workStartTime) {
        this.workStartTime = workStartTime;
    }

    public String getWorkEndTime() {
        return workEndTime;
    }

    public void setWorkEndTime(String workEndTime) {
        this.workEndTime = workEndTime;
    }

    public void setWorkHour(float workHour) {
        this.workHour = workHour;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
