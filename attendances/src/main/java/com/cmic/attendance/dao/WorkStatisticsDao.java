package com.cmic.attendance.dao;

import com.cmic.attendance.model.WorkStatistics;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * Dao
 */
@Mapper
public interface WorkStatisticsDao extends CrudDao<WorkStatistics> {

}