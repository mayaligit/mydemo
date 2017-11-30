package com.cmic.attendance.vo;

import com.cmic.attendance.model.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.acl.Group;
import java.util.List;

/**
 * 接收WEB页面参数的vo
 * 数据格式为：json
 */
public class GroupRuleVo implements Serializable{

    private static final long serialVersionUID = 1L;

    /*规则表数据*/
    private GroupRule groupRule;

    /*考勤人员表数据*/
    private GroupPersonnel groupPersonnel;

    /*考勤地址信息表*/
    private GroupAddress groupAddress;

    /*日报人员规则*/
    private GroupDailyRule groupDailyRule;

    /*日报人员规则*/
    private GroupAudit groupAudit;

    private Integer pageNum;
    private Integer pageSize;
    private String groupName;

    public GroupRule getGroupRule() {
        return groupRule;
    }

    public void setGroupRule(GroupRule groupRule) {
        this.groupRule = groupRule;
    }

    public GroupPersonnel getGroupPersonnel() {
        return groupPersonnel;
    }

    public void setGroupPersonnel(GroupPersonnel groupPersonnel) {
        this.groupPersonnel = groupPersonnel;
    }

    public GroupDailyRule getGroupDailyRule() {
        return groupDailyRule;
    }

    public void setGroupDailyRule(GroupDailyRule groupDailyRule) {
        this.groupDailyRule = groupDailyRule;
    }

    public GroupAudit getGroupAudit() {
        return groupAudit;
    }

    public void setGroupAudit(GroupAudit groupAudit) {
        this.groupAudit = groupAudit;
    }

    public GroupAddress getGroupAddress() {
        return groupAddress;
    }

    public void setGroupAddress(GroupAddress groupAddress) {
        this.groupAddress = groupAddress;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
