package com.cmic.attendance.exception;

public class LoginExeptions extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * 发生错误的操作组
     */
    private String errorCode;

    public LoginExeptions(String errorCode) {
        super(errorCode);
        this.errorCode=errorCode;
    }
}
