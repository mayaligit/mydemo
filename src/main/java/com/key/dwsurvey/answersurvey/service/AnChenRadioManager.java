package com.key.dwsurvey.answersurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.savesurvey.entity.Question;
import com.key.dwsurvey.answersurvey.entity.AnChenRadio;
import com.key.dwsurvey.answersurvey.entity.DataCross;

/**
 * 矩陈单选题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */

public interface AnChenRadioManager extends BaseService<AnChenRadio, String>{
	public List<AnChenRadio> findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);

	public List<DataCross> findStatsDataChart(Question question);
}
