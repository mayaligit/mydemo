package com.key.dwsurvey.mysurvey.controller;

import java.net.URLDecoder;

import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 创建问卷
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */


@Controller
@RequestMapping("/design")
public class MySurveyCreateController{

	@Autowired
	private SurveyDirectoryManager directoryManager;


	@RequestMapping(value="/my-survey-create!save")
	public String save(@RequestParam("surveyName")String surveyName){
		//String surveyName=request.getParameter("surveyName");
		SurveyDirectory survey = new SurveyDirectory();
		String surveyId = null;
	    try{
	    	survey.setDirType(2);
	    	if(surveyName==null || "".equals(surveyName.trim())){
	    		surveyName="请输入问卷标题";
	    	}else{
	    		surveyName=URLDecoder.decode(surveyName,"utf-8");
	    	}
	 	    survey.setSurveyName(surveyName);
	 	    directoryManager.save(survey);
	 	    surveyId = survey.getId();
	 	    //request.setAttribute("surveyId",surveyId);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return "redirect:/design/my-survey-design?surveyId="+surveyId;
	}

	
}
