package com.key.dwsurvey.answersurvey.service;

import com.key.common.service.BaseService;
import com.key.dwsurvey.answersurvey.entity.AnFillblank;
import com.key.dwsurvey.savesurvey.entity.Question;

/**
 * 填空题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface AnFillblankManager extends BaseService<AnFillblank, String>{
	public AnFillblank findAnswer(String belongAnswerId,String quId);

	public void findGroupStats(Question question);
}
