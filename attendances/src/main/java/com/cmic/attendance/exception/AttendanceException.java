package com.cmic.attendance.exception;

/**
 * Created by issuser on 2017/11/24.
 */
public class AttendanceException extends Exception{

    private static final long serialVersionUID = 1L;

    /**
     * 发生错误的操作组
     */
    private String errorCode;

    public AttendanceException(String errorCode){
        super(errorCode);
        this.errorCode=errorCode;
    }
}
