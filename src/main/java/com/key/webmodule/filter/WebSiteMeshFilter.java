package com.key.webmodule.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter {

  /*  *//** 需要装饰的访问路径 *//*
    @Value("${sitemesh.contentPath}")
    private String contentPath;

    *//** 装饰器页面路径 *//*
    @Value("${sitemesh.decoratorPath}")
    private String decoratorPath;

    *//** 不需要装饰的访问路径,多个之间用英文逗号分隔 *//*
    @Value("${sitemesh.excludedPaths}")
    private String excludedPaths;*/

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // 程序写死 
        builder.addDecoratorPath("/*", "/WEB-INF/page/layouts/default.jsp").addExcludedPath("/js/*")
             .addExcludedPath("/common/*").addExcludedPath("/css/*").addExcludedPath("/design/my-survey-design*").addExcludedPath("/survey*")
             .addExcludedPath("/survey.*").addExcludedPath("/response*").addExcludedPath("/wjHtml*");

        /* 通过配置文件
        builder.addDecoratorPath(contentPath, decoratorPath);
        if (excludedPaths == null) {
            return;
        }
        String[] paths = excludedPaths.split(",");
        for (String path : paths) {
            builder.addExcludedPath(path);
        }*/
    }

}
