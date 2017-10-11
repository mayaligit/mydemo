package com.key.webmodule.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import com.key.common.plugs.xss.XssFilter;

/**
 * 配置Java Xss保护过滤器
 * @author liangyu
 *
 */
@Configuration
public class XssFilterConfig {
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
       FilterRegistrationBean registrationBean = new FilterRegistrationBean();
       //直接new所需要注册的filter
       XssFilter openSessionInViewFilter =new XssFilter();
       registrationBean.setFilter(openSessionInViewFilter);
       List<String> urlPatterns = new ArrayList<String>();
       //需要拦截的路径
       urlPatterns.add("*.action");
       registrationBean.setUrlPatterns(urlPatterns);
       return registrationBean;
    }
}
