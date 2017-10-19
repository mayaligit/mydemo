package com.key.dwsurvey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

@Configuration
public class UrlfilterConfig {
    @Bean("urlFilter")
    public UrlRewriteFilter urlRewriteFilter() {
       UrlRewriteFilter urlFilter =new UrlRewriteFilter();
       return urlFilter;
    }
}
