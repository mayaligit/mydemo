package com.cmic.attendance.filter;

import com.cmic.attendance.model.AttendanceUser;
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
        System.out.println("=================方法执行行进行了拦截测试==================");
        log.debug("post URL："+url);
        System.out.println("=================方法执行行进行了拦截测试==================");
        if (!url.equals("")){
            if ("/attendance/user/login".equals(url) || "/attendance/info".equals(url)
                    || "/attandence/user/getCheckCode".equals(url)){
                System.out.println("=================测试方法是否被放行==================");
                filterChain.doFilter(servletRequest,servletResponse);

            }else {
                //判斷是否已登录
                AttendanceUser loginUser = (AttendanceUser)request.getSession().getAttribute("attendanceUser");
                BaseAdminEntity adminEntity=(BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
                if(loginUser == null){
                    //無session則是未登录狀態
                    System.out.println(">>>未登录，請重新登录<<<");
                    response.sendRedirect("http://192.168.3.6:80/attendance/login.html");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
