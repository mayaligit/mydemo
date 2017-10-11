package com.key.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@Configuration
public class UrlfilterConfig {
    @Bean("urlFilter")
    public UrlRewriteFilter urlRewriteFilter() {
       UrlRewriteFilter urlFilter =new UrlRewriteFilter();
       return urlFilter;
    }
}
