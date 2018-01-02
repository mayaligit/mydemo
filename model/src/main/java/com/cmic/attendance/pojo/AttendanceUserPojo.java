package com.cmic.attendance.pojo;

import com.cmic.attendance.model.AttendanceUser;

import java.io.Serializable;

/**
 * @author 何荣
 * @create 2017-12-29 16:04
 *  封装参数的拓展类 , 用于插入用户信息是维护 招聘的两张表
 **/
public class AttendanceUserPojo extends AttendanceUser implements Serializable {
    private Integer role ;
    private String username;
    private String department ;
    private String email ;
    private String supplierId ;
    private String roleId ;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
