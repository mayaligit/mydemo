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
    private String attendanceGroup;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getAttendanceGroup() {
        return attendanceGroup;
    }

    @Override
    public void setAttendanceGroup(String attendanceGroup) {
        this.attendanceGroup = attendanceGroup;
    }
}
