package com.cmic.attendance.interceptor;

import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by huguoju on 2016/12/30.
 */
@Configuration
//@ComponentScan(basePackages = "com.cmic.attendance")
public class MvcConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(MvcConfig.class);
    /**
     * 拦截器配置
     */
    @Bean
    AdminLoginInterceptor  myInterceptor(){
        return new AdminLoginInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("注册成功");
        // 注册监控拦截器
        registry.addInterceptor(myInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/attendance/user/login").
                excludePathPatterns("/attendance/info");
        super.addInterceptors(registry);

    }

}