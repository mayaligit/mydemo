package com.cmic.attendance.filter;

import com.cmic.attendance.utils.HttpRequestDeviceUtils;
import com.cmic.saas.base.web.RestException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//@WebFilter(urlPatterns = "/*", filterName = "loginFilter")
public class LogFilter implements Filter {

    private static Logger log = Logger.getLogger(LogFilter.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException,RestException{
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        String url = request.getServletPath();
        //判斷是否已登录
        Object current_admin_info = (Object) redisTemplate.boundValueOps("_CURRENT_ADMIN_INFO").get();
        //只拦电脑端
        Object current_admin = request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
        Object attendanceUserVo = request.getSession().getAttribute("attendanceUserVo");

        log.debug(">>>>手机端session："+current_admin);
        log.debug(">>>>电脑端session："+attendanceUserVo);
        if (null !=current_admin){
            //手机端放行，已经登录
            filterChain.doFilter(servletRequest,servletResponse);
        } else if (url.equals("/attendance/info")) {
            //认证用户中心放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else if (url.equals("/attendance/user/login")){
            //电脑端登录放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else if (url.equals("/attendance/user/getCheckCode")){

            //验证码端放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else if(null!=attendanceUserVo){
            //电脑端已经登记放行
            filterChain.doFilter(servletRequest,servletResponse);
        } else if(HttpRequestDeviceUtils.isMobileDevice(request)){
            //放行手机端
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            //进行拦截转发到登录页面接口
                //电脑端转发
            request.getRequestDispatcher("/attendance/user/noLogint").forward(request, response);
            //response.sendRedirect("/attendance/user/noLogint");
        }
    }

    @Override
    public void destroy() {

    }

}
