package com.cmic.attendance.vo;/**
 * Created by pc on 2017/10/30.
 */

import com.cmic.attendance.model.Daily;

import java.io.Serializable;

/**
 * @author 何家来
 * @create 2017-10-30 19:04
 **/
public class DailyVo extends Daily implements Serializable {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
