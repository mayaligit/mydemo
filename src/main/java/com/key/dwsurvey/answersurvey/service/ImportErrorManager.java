package com.key.dwsurvey.answersurvey.service;

import com.key.dwsurvey.answersurvey.entity.ImportError;

/**
 * 导入错误记录
 * @author keyuan(keyuan258@gmail.com)
 *
 * https://github.com/wkeyuan/DWSurvey
 * http://dwsurvey.net
 */
public interface ImportErrorManager {

    void save(ImportError importError);

}
