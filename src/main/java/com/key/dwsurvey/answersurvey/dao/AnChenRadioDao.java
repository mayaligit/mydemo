package com.key.dwsurvey.answersurvey.dao;

import com.key.common.dao.BaseDao;
import com.key.dwsurvey.savesurvey.entity.Question;
import com.key.dwsurvey.answersurvey.entity.AnChenRadio;

/**
 * 矩陈单选题数据 interface
 * @author KeYuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 *
 */
public interface AnChenRadioDao extends BaseDao<AnChenRadio, String>{

	public void findGroupStats(Question question);

}
