package com.cmic.attendance.bo;

import java.io.Serializable;

/**
 * 传递到统计表的下班打卡信息
 * Bo业务层bean
 */
public class InsetEndStaticBo implements Serializable {
    /*用户手机号*/
    private String CreateBy;
    /*当天的年月日时间*/
    private String createTime;
    /*用户名*/
    private String userName;
    /*打卡时间*/
    private String startTime;
    /*下班时间*/
    private String offtime;
    /*上班标准时间*/
    private String standardTime;

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOfftime() {
        return offtime;
    }

    public void setOfftime(String offtime) {
        this.offtime = offtime;
    }

    public String getStandardTime() {
        return standardTime;
    }

    public void setStandardTime(String standardTime) {
        this.standardTime = standardTime;
    }
}
