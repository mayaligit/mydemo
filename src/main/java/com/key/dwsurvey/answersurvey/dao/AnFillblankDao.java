package com.key.dwsurvey.answersurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.answersurvey.entity.AnFillblank;
import com.key.dwsurvey.savesurvey.entity.Question;

/**
 * 填空题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnFillblankDao extends BaseDao<AnFillblank, String>{

	public void findGroupStats(Question question);

}
