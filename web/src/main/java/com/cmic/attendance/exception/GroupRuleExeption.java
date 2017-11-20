package com.cmic.attendance.exception;

public class GroupRuleExeption extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 发生错误的操作组
     */
    private String errorCode;

    public GroupRuleExeption(String errorCode) {
        super(errorCode);
        this.errorCode=errorCode;
    }
}
