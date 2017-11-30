package com.cmic.attendance.dao;

import com.cmic.attendance.model.Statistics;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* 统计表Dao
*/
@Mapper
public interface StatisticsDao extends CrudDao<Statistics> {

    Statistics  checkAttendanceByCreateByAndCreateTime(@Param("createBy") String createBy,
                                                       @Param("createTime")String createTime);
}