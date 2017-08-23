package com.key.dwsurvey.service;

import java.util.List;

import com.key.common.service.BaseService;
import com.key.dwsurvey.entity.QuScore;

/**
 * 评分题
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface QuScoreManager extends BaseService<QuScore, String>{

	public List<QuScore> findByQuId(String id);
	
	public QuScore upOptionName(String quId,String quItemId, String optionName);

	public List<QuScore> saveManyOptions(String quId,List<QuScore> quScores);

	public void ajaxDelete(String quItemId);

	public void saveAttr(String quItemId);
}
