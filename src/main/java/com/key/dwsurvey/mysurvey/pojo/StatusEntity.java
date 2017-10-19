package com.key.dwsurvey.mysurvey.pojo;

import java.io.Serializable;

public class StatusEntity implements Serializable{
   
    //封装问卷名称
    private String surveyName;
    //返回给用户提示信息
    private String msg;
    //返回的地址
    private String dispatcherUrl;
    //判断是重定向还是返回数据页面
    private int status;
    
    public String getSurveyName() {
        return surveyName;
    }
    
    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public String getDispatcherUrl() {
        return dispatcherUrl;
    }
    
    public void setDispatcherUrl(String dispatcherUrl) {
        this.dispatcherUrl = dispatcherUrl;
    }
    
    public int getStatus() {
        return status;
    }

    
    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
