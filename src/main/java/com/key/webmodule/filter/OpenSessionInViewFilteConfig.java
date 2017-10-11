package com.key.webmodule.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
/**
 * 注册UrlRewriteFilter
 * @author liangyu
 */
@Configuration
public class OpenSessionInViewFilteConfig {
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
       FilterRegistrationBean registrationBean = new FilterRegistrationBean();
       //直接new所需要注册的filter
       UrlRewriteFilter openSessionInViewFilter =new UrlRewriteFilter();
       registrationBean.setFilter(openSessionInViewFilter);
       List<String> urlPatterns = new ArrayList<String>();
       //需要拦截的路径
       urlPatterns.add("/*");
       registrationBean.setUrlPatterns(urlPatterns);
       return registrationBean;
    }
}
