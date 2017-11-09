package com.cmic.attendance.Constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 何荣
 * @create 2017-10-25 10:47
 **/
@Component
public class Constant {

    public static String LOGIN_SESSION_KEY = "login_user";

    //认证的地址
    public static String certifyServicePath;
    @Value("${certify.server.path}")
    public void setVersion(String certifyServicePath) {
        this.certifyServicePath = certifyServicePath;
    }

    public static String userINfo;
    @Value("${certify.server.userInfo}")
    public void setUserINfo(String userINfo) {
        this.userINfo = userINfo;
    }

}
