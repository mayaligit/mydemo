package com.key.dwsurvey.mysurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.mysurvey.entity.SurveyDetail;

/**
 * 问卷评情
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface SurveyDetailManager extends BaseService<SurveyDetail, String>{
	
	public SurveyDetail getBySurveyId(String surveyId);
	
}
