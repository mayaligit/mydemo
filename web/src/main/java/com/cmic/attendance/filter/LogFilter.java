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
@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
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
        log.debug("请求 URL："+url);
        //判斷是否已登录
        Object current_admin_info = request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
        AttendanceUserVo loginUser = (AttendanceUserVo)request.getSession().getAttribute("attendanceUser");
        if(loginUser == null && current_admin_info==null){
            if (url.equals("/attendance/user/login") ||
                    url.equals("/attendance/info")
                    ||url.equals("/attandence/user/getCheckCode")){
                filterChain.doFilter(servletRequest,servletResponse);

            }else {
                log.debug("post URL"+">>>未登录，請重新登录<<<");
                response.sendRedirect("http://192.168.185.250:8180/admin_attendance/login.html");
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
