package com.key.config;

import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.key.dwsurvey.shiro.FormAuthenticationWithLockFilter;
import com.key.dwsurvey.shiro.ShiroDbRealm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * @author liangyu
 * @email sunlightcs@gmail.com
 * @date 2017-04-20 18:33
 */
@Configuration
public class ShiroConfig {
    
    @Bean("shiroDbRealm")
    public ShiroDbRealm authRealm(){
        ShiroDbRealm shiroDbRealm = new ShiroDbRealm();
        return shiroDbRealm;
    }
    
    @Bean("sessionManager")
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
       /* sessionManager.setSessionIdUrlRewritingEnabled(false);*/
        //sessionManager.setSessionIdCookieEnabled(false);
        return sessionManager;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(ShiroDbRealm shiroDbRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroDbRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }
    
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager,FormAuthenticationWithLockFilter formAuthenticationWithLockFilter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //登录的地址
        shiroFilter.setLoginUrl("/login.jsp");
        //检验成功跳转的地址
        shiroFilter.setSuccessUrl("/design/my-survey.action");
        //没有授权的地址
        shiroFilter.setUnauthorizedUrl("/login.jsp?una=0");
        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", formAuthenticationWithLockFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/login.jsp", "authc");
        filterMap.put("/ic/**", "user");
        filterMap.put("/design/**", "user");
        filterMap.put("/da/**", "user");
        filterMap.put("/sy/**", "roles[admin]");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }
    
    @Bean("formAuthenticationWithLockFilter")
    public FormAuthenticationWithLockFilter formAuthenticationWithLockFilter() {
        FormAuthenticationWithLockFilter formAuthenticationWithLockFilter =new FormAuthenticationWithLockFilter();
        formAuthenticationWithLockFilter.setMaxLoginAttempts(100);
        formAuthenticationWithLockFilter.setSuccessAdminUrl("/main.action?menu=3");
        formAuthenticationWithLockFilter.setSuccessAdminRole("admin");
        formAuthenticationWithLockFilter.setRememberMeParam("rememberMe");
        return formAuthenticationWithLockFilter;
        
    }
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean("cacheManager")
    public MemoryConstrainedCacheManager memoryConstrainedCacheManager() {
        return new MemoryConstrainedCacheManager();
    }
    
    // <!-- 使用记住我功能 -->
    //<!-- 会话Cookie模板 -->
   /* @Bean("sessionIdCookie")
    public SimpleCookie simpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }*/
    @Bean("rememberMeCookie")
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setName("shiro.sesssion");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }
   // <!-- rememberMe管理器 -->
    @Bean("rememberMeManager")
    public CookieRememberMeManager cookieRememberMeManager(SimpleCookie simpleCookie ) {
        CookieRememberMeManager bean=new CookieRememberMeManager();
        byte[] decode = org.apache.shiro.codec.Base64.decode("4AvVhmFLUs0KTA3Kprsdag==");
        bean.setCipherKey(decode);
        bean.setCookie(getSimpleCookie());
        return bean;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
