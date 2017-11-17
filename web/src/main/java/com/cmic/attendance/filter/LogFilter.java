package com.cmic.attendance.filter;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.web.AttendanceController;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LogFilter implements Filter {

    private static Logger log = Logger.getLogger(LogFilter.class);

    @Autowired
    private RedisTemplate redisTemplate;

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
        Object current_admin_info = (Object) redisTemplate.boundValueOps("_CURRENT_ADMIN_INFO").get();
        Object loginUser = (Object) redisTemplate.boundValueOps("attendanceUser").get();
        //只拦电脑端
        log.debug("手机端session"+">>>"+current_admin_info+"<<<");
        log.debug("服务器session"+">>>"+loginUser+"<<<");
        if(loginUser == null ){
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
