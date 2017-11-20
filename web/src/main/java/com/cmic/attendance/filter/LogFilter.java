package com.cmic.attendance.filter;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.web.AttendanceController;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.web.RestException;
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
            //手机端放行
            filterChain.doFilter(servletRequest,servletResponse);
        } else if (url.equals("/attendance/info")) {
            //认证用户中心放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else if (url.equals("/attendance/user/login")){
            //电脑端登录放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else if (url.equals("/attandence/user/getCheckCode")){
            //验证码端放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else if(null!=attendanceUserVo){
            //电脑端已经登记放行
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            //进行拦截转发到登录页面接口
            request.getRequestDispatcher("/attendance/user/noLogint").forward(request, response);
           /* response.sendRedirect("/attendance/user/noLogint");*/

        }

    }

    @Override
    public void destroy() {

    }

    public static boolean  isMobileDevice(String requestHeader){
        /**
         * android : 所有android设备
         * mac os : iphone ipad
         * windows phone:Nokia等windows系统的手机
         */
        String[] deviceArray = new String[]{"android","mac os","windows phone"};
        if(requestHeader == null)
            return false;
        requestHeader = requestHeader.toLowerCase();
        for(int i=0;i<deviceArray.length;i++){
            if(requestHeader.indexOf(deviceArray[i])>0){
                return true;
            }
        }
        return false;
    }
}
