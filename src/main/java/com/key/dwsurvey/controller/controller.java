package com.key.dwsurvey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.key.dwsurvey.entity.SurveyDirectory;
import com.key.dwsurvey.service.SurveyDirectoryManager;

@Controller
public class controller {
    
    @Autowired
    private SurveyDirectoryManager surveyDirectoryManager;
   
    @RequestMapping(value="/aa")
    public String collect(){
        System.out.println("跳转进来了");
        SurveyDirectory survey = surveyDirectoryManager.getSurvey("40288b815ed226e4015ed227b8210000");
        
        return survey.getHtmlPath();
        
    }
}
