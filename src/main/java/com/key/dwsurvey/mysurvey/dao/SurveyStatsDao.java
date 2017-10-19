package com.key.dwsurvey.mysurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.savesurvey.entity.Question;
import com.key.dwsurvey.mysurvey.entity.SurveyStats;

public interface SurveyStatsDao extends BaseDao<SurveyStats, String>{

	public void findStatsDataCross(Question rowQuestion, Question colQuestion);

}
