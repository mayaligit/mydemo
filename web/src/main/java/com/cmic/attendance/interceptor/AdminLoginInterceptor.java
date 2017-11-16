package com.cmic.attendance.interceptor;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.model.User;
import com.cmic.attendance.web.AttendanceController;
import com.cmic.saas.base.model.BaseAdminEntity;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = Logger.getLogger(AttendanceController.class);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setCharacterEncoding("UTF-8");
        String url = request.getServletPath();
        System.out.println("=================方法执行行进行了拦截测试==================");
        log.debug("post URL："+url);
        System.out.println("=================方法执行行进行了拦截测试==================");
        if (url.equals("/attendance/user/login")){
            //放行登录连接
            log.debug("放行登录连接");
            return true;
        }
        if(!url.equals("")){
            //判斷是否已登录
            AttendanceUser loginUser = (AttendanceUser)request.getSession().getAttribute("attendanceUser");
            BaseAdminEntity  adminEntity=(BaseAdminEntity)request.getSession().getAttribute("_CURRENT_ADMIN_INFO");
            if(loginUser == null){
                //無session則是未登录狀態
                System.out.println(">>>未登录，請重新登录<<<");
                response.sendRedirect("http://192.168.156.200/layui-admin/login");
                return false;
            }
        }
        return true;
    }
    /*private boolean isPassUrl(String url){
        if(!url.endsWith("/login/login")
                && !url.endsWith("/login/chnagePassEntry")
                && !url.endsWith("/login/change_Login")
                && !url.endsWith("/API/Service")
                && !url.endsWith("/API/Service2")
                && !url.endsWith("/province.txt")
                && !url.endsWith("/city.txt")
                && !url.endsWith("/area.txt")
                && !url.endsWith(".xml")
                && !url.endsWith(".js")
                && !url.endsWith(".css") && !url.endsWith(".png")
                && !url.endsWith(".CSS") && !url.endsWith(".CSS")
                && !url.endsWith(".jpg") && !url.endsWith(".gif")
                && !url.endsWith(".JPG") && !url.endsWith(".GIF")){
            return true;
        }

        return false;
    }*/
}
