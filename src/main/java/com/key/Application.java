package com.key;

import java.util.ArrayList;
import java.util.List;

import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;

import com.key.common.plugs.xss.XssFilter;

import javax.annotation.Resources;


@SpringBootApplication
@ServletComponentScan(basePackages="com.key.common.utils")
@ImportResource(locations= {
        "classpath*:conf/applicationContext.xml",
        "classpath*:conf/applicationContext-dwsurvey.xml",
        "classpath*:conf/jcaptcha/applicationContext-jcaptcha-1.xml"
        })
public class Application {

   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }*/
    //注册SessionInViewFilter 
   @Bean
    public FilterRegistrationBean filterRegistrationBean() {
       FilterRegistrationBean registrationBean = new FilterRegistrationBean();
       OpenSessionInViewFilter openSessionInViewFilter =new OpenSessionInViewFilter();
       registrationBean.setFilter(openSessionInViewFilter);
       List<String> urlPatterns = new ArrayList<String>();
       urlPatterns.add("/*");
       registrationBean.setUrlPatterns(urlPatterns);
       return registrationBean;
    }
    
    /**
     * 注册hibernate工厂类
     * @return SessionFactoryBean
     */
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
