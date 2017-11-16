package com.cmic.attendance.filter;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.web.AttendanceController;
import com.cmic.saas.base.model.BaseAdminEntity;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LogFilter implements Filter {

    private static Logger log = Logger.getLogger(LogFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        String url = request.getServletPath();
        log.debug("inser URL："+url);
        //判斷是否已登录
        AttendanceUserVo loginUser = (AttendanceUserVo)request.getSession().getAttribute("attendanceUser");
        if(loginUser == null ){
            if (url.equals("/attendance/user/login") ||
                    url.equals("/attendance/info")
                    ||url.equals("/attandence/user/getCheckCode")){
                filterChain.doFilter(servletRequest,servletResponse);

            }else {
                log.debug("post URL"+">>>未登录，請重新登录<<<");
                response.sendRedirect("http://192.168.3.6:80/attendance/login.html");
            }
        }else {
            //已经登录
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
