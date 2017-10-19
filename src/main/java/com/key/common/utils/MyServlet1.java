package com.key.common.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
/**
                <from>/resp/(.*)/(.*)/(.*)/(.*).html</from>
                <to>/nosm/response!input.action?year=$1&amp;month=$2&amp;day=$3&amp;parentId=$4</to>
            </rule>
            <rule>
                <from>/wenjuan/(.*).html</from>
                <to>/response.action?sid=$1</to>
         <  /rule>
 *

@WebServlet(urlPatterns = "/wenjuan/*") */
public class MyServlet1 extends HttpServlet {
    
    
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        /*String url = new String(request.getRequestURL());
        SurveyDirectory survey = surveyDirectoryManager.getSurvey("40288b815ed226e4015ed227b8210000");
        String htmlPath = survey.getHtmlPath();
        //
        StringBuffer path = SpringUtils.getRequest().getRequestURL();
        String paths= path.toString().split("wenjuan/")[0];
        System.out.println("切割后的地址"+paths);
        paths= paths+survey.getHtmlPath();
        request.getRequestDispatcher(htmlPath).forward(request, response); */
        System.out.println("进入myServlet");
        response.sendRedirect("/response.action?sid=aba1qjt");
    }
 
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,getServletContext());
        System.out.println("myServlet初始化成功");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
