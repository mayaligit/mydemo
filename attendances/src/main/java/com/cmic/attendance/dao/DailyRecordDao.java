package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.DailyRecord;
import org.apache.ibatis.annotations.Mapper;

/**
* 日志表Dao
*/
@Mapper
public interface DailyRecordDao extends CrudDao<DailyRecord> {

}