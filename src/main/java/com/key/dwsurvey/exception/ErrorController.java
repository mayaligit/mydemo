package com.key.dwsurvey.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ErrorController implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println(">>>MyInterceptor1>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");  
		return true;// 只有返回true才会继续向下执行，返回false取消当前请求  
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView modelAndView)
			throws Exception {
		if(response.getStatus()==500){  
            modelAndView.setViewName("/page/common/500");  
        }else if(response.getStatus()==404){  
            modelAndView.setViewName("/page/common/404");  
        }
		
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		 		
	}


}
