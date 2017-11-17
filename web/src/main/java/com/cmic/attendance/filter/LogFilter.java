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
        //只拦电脑端
        Object current_admin = request.getSession().getAttribute("_CURRENT_ADMIN_INFO");

        Object attendanceUserVo = request.getSession().getAttribute("attendanceUserVo");
        log.debug("手机端session"+">>>"+current_admin+"<<<");

        if( null== attendanceUserVo || "".equals(attendanceUserVo)){
            //需要放行的代码
            if (url.equals("/attendance/user/login") ||url.equals("/attandence/user/getCheckCode")
                        ){
                filterChain.doFilter(servletRequest,servletResponse);

            }else if(url.equals("/attendance/info")){
                filterChain.doFilter(servletRequest,servletResponse);
            } else if(current_admin_info!=null){
                //判断是否是手机端访问 如果是则放行

                String requestHeader = request.getHeader("user-agent");
                if(isMobileDevice(requestHeader)){
                    //使用手机端
                    filterChain.doFilter(servletRequest,servletResponse);
                }else{
                    //电脑端
                    response.sendRedirect("http://192.168.185.250:8180/admin_attendance/login.html");
                    /*request.getRequestDispatcher("/admin_attendance/login.html").forward(request,response);*/
                    log.debug("执行了重定向 有reids+ 电脑端重定向项");
                    response.setStatus(302);

                }

            } else {
                log.debug("拦截 URL"+">>>未登录，請重新登录<<<"+url);
                response.sendRedirect("http://192.168.185.250:8180/admin_attendance/login.html");
                /*request.getRequestDispatcher("/admin_attendance/login.html").forward(request,response);*/
                log.debug("执行了重定向");
                response.setStatus(302);
            }

        }else if (null !=attendanceUserVo){
            //已经登录
            log.debug("post URL"+">>>放行<<<"+"服务器session"+">>>"+attendanceUserVo+"<<<");
            filterChain.doFilter(servletRequest,servletResponse);
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
