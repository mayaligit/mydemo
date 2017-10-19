package com.key.dwsurvey.answersurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.answersurvey.entity.AnOrder;
import com.key.dwsurvey.savesurvey.entity.Question;

/**
 * 排序题 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnOrderDao extends BaseDao<AnOrder, String>{

	public void findGroupStats(Question question);
	
}
