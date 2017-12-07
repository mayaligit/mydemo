package com.cmic.attendance.vo;/**
 * Created by pc on 2017/10/30.
 */

import com.cmic.attendance.model.Daily;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;

/**
 * @author 何家来
 * @create 2017-10-30 19:04
 **/
public class DailyVo extends Daily implements Serializable {


    private String startDate;
    private String endDate;

    private String date;
    private PageInfo pageInfo;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
