package com.key.dwsurvey.mysurvey.controller;

import com.key.common.base.entity.User;
import com.key.common.base.service.AccountManager;
import com.key.common.utils.web.SpringUtils;
import com.key.dwsurvey.mysurvey.entity.SurveyDirectory;
import com.key.dwsurvey.mysurvey.service.SurveyDirectoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 收集入口 action
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */

@Controller
@RequestMapping("/my-collect")
public class MyCollectController {
	
	protected final static String COLLECT1="collect1";
	protected final static String IFRAME="iframe";
	protected final static String SITECOMP="sitecomp";
	protected final static String WEIXIN="weixin";
	protected final static String SHARE="share";
	
	@Autowired
	private SurveyDirectoryManager surveyDirectoryManager;
	@Autowired
	private AccountManager accountManager;

	
	@RequestMapping(value="/collect")
	public String collect(@RequestParam("surveyId")String surveyId) throws Exception {
		HttpServletRequest request= SpringUtils.getRequest();
		String tabId=request.getParameter("tabId");

		String baseUrl = "";
		baseUrl = request.getScheme() +"://" + request.getServerName()  
						+ (request.getServerPort() == 80 ? "" : ":" +request.getServerPort())
                        + request.getContextPath();

		request.setAttribute("baseUrl", baseUrl);

		User user=accountManager.getCurUser();
    	if(user!=null){
    		SurveyDirectory surveyDirectory=surveyDirectoryManager.getSurveyByUser(surveyId, user.getId());
    		if(surveyDirectory!=null){
    			request.setAttribute("survey", surveyDirectory);
				request.setAttribute("surveyId",surveyId);
				if(IFRAME.equals(tabId)){
					return "/page/content/diaowen-collect/collect_iframe";
				}else if(SITECOMP.equals(tabId)){
					return "/page/content/diaowen-collect/collect_website";
				}else if(WEIXIN.equals(tabId)){
					return "/page/content/diaowen-collect/collect_weixin";
				}else if(SHARE.equals(tabId)){
					return "/page/content/diaowen-collect/collect_2";
				}
				return "/page/content/diaowen-collect/collect_1";
			}
    	}
		return null;
	}
	

}
