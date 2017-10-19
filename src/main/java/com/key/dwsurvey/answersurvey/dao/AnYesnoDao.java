package com.key.dwsurvey.answersurvey.dao;

import java.util.List;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.answersurvey.entity.AnYesno;
import com.key.dwsurvey.answersurvey.entity.DataCross;
import com.key.dwsurvey.savesurvey.entity.Question;

/**
 * 是非题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnYesnoDao extends BaseDao<AnYesno, String>{

	public void findGroupStats(Question question);

	public List<DataCross> findStatsDataCross(Question rowQuestion, Question colQuestion);

	public List<DataCross> findStatsDataChart(Question question);

}
