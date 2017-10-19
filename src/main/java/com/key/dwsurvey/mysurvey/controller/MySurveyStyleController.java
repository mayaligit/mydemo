package com.key.dwsurvey.mysurvey.controller;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.mysurvey.entity.SurveyDetail;
import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import com.key.dwsurvey.mysurvey.entity.SurveyStyle;
import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
import com.key.dwsurvey.mysurvey.service.SurveyStyleManager;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 问卷样式
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */

@Controller
@RequestMapping("/design")
public class MySurveyStyleController {
	
	@Autowired
	private SurveyStyleManager surveyStyleManager;
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	@RequestMapping(value = "/my-survey-style!save")
	@ResponseBody
	public String save(@RequestParam("surveyId")String surveyId) throws Exception  {
	    HttpServletRequest request= SpringUtils.getRequest();
		HttpServletResponse response=SpringUtils.getResponse();
		//保存收集规则 
		String effective=request.getParameter("effective");
		String effectiveIp=request.getParameter("effectiveIp");
		String rule=request.getParameter("rule");
		String ruleCode=request.getParameter("ruleCode");
		String mailOnly=request.getParameter("mailOnly");
		String ynEndNum=request.getParameter("ynEndNum");
		String ynEndTime=request.getParameter("ynEndTime");
		String endTime=request.getParameter("endTime");
		String endNum=request.getParameter("endNum");
		String showShareSurvey=request.getParameter("showShareSurvey");
		String showAnswerDa=request.getParameter("showAnswerDa");
		String refresh=request.getParameter("refresh"); 
		//保存属性
		
		try{
			User user=accountManager.getCurUser();
	    	if(user!=null){
	    		SurveyDirectory survey=surveyDirectoryManager.getSurvey(surveyId);
	    		if(survey!=null && user.getId().equals(survey.getUserId())){
	    			
	    			SurveyDetail surveyDetail=survey.getSurveyDetail();
	    			if(effective!=null && !"".equals(effective)){
	    			    surveyDetail.setEffective(Integer.parseInt(effective));
	    			}
	    			if(effectiveIp!=null && !"".equals(effectiveIp)){
	    			    surveyDetail.setEffectiveIp(Integer.parseInt(effectiveIp));
	    			}
	    			if(rule!=null && !"".equals(rule)){
	    			    surveyDetail.setRule(Integer.parseInt(rule));
	    			    surveyDetail.setRuleCode(ruleCode);
	    			}
	    			if(refresh!=null && !"".equals(refresh)){
	    			    surveyDetail.setRefresh(Integer.parseInt(refresh));
	    			}
	    			if(mailOnly!=null && !"".equals(mailOnly)){
	    			    surveyDetail.setMailOnly(Integer.parseInt(mailOnly));
	    			}
	    			if(ynEndNum!=null && !"".equals(ynEndNum)){
	    			    surveyDetail.setYnEndNum(Integer.parseInt(ynEndNum));
	    			    if(endNum!=null && endNum.matches("\\d*")){
	    			    	surveyDetail.setEndNum(Integer.parseInt(endNum));			
	    			    }
	    			}
	    			if(ynEndTime!=null && !"".equals(ynEndTime)){
	    			    surveyDetail.setYnEndTime(Integer.parseInt(ynEndTime));
//	    				surveyDetail.setEndTime(endTime);
	    			    surveyDetail.setEndTime(new Date());
	    			}
	    			if(showShareSurvey!=null && !"".equals(showShareSurvey)){
	    			    surveyDetail.setShowShareSurvey(Integer.parseInt(showShareSurvey));
	    			    survey.setIsShare(Integer.parseInt(showShareSurvey));
	    			}
	    			if(showAnswerDa!=null && !"".equals(showAnswerDa)){
	    			    surveyDetail.setShowAnswerDa(Integer.parseInt(showAnswerDa));
	    			    survey.setViewAnswer(Integer.parseInt(showAnswerDa));
	    			}
	    			surveyDirectoryManager.save(survey);

	    			//保存样式
					SurveyStyle entity = new SurveyStyle();
					String bodyBgColor = request.getParameter("bodyBgColor");
					String bodyBgImage = request.getParameter("bodyBgImage");
					String showBodyBi = request.getParameter("showBodyBi");
					entity.setSurveyStyleType(0);
					entity.setBodyBgColor(bodyBgColor);
					entity.setBodyBgImage(bodyBgImage);
					entity.setShowBodyBi(Integer.parseInt(showBodyBi));
	    			surveyStyleManager.save(entity);

	    			//响应
	    			//response.getWriter().write("true");
	    			//response.getWriter().close();
					return "true";
	    		}
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "none";
	}

	@RequestMapping(value = "/my-survey-style!ajaxGetStyle")
	@ResponseBody
	public String ajaxGetStyle(@RequestParam("id")String styleModelId) throws Exception {
	    try{
			HttpServletResponse response=SpringUtils.getResponse();
			SurveyStyle surveyStyle=surveyStyleManager.get(styleModelId);
			String jsonObj= JSONObject.fromObject(surveyStyle).toString();
			/*response.getWriter().write(jsonObj);*/
			return jsonObj;
		 }catch (Exception e) {
			e.printStackTrace();
		 }
	    return null;
	}
	

}
