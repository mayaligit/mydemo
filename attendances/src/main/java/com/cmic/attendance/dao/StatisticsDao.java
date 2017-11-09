package com.cmic.attendance.dao;

import com.cmic.attendance.model.Statistics;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 统计表Dao
*/
@Mapper
public interface StatisticsDao extends CrudDao<Statistics> {

    List<Map> checkAttendanceHardworkingByDay(String date);

    List<Map> checkAttendanceHardworkingByMonth(String date);

    List<Map> checkAttendanceLatterByMonth(String date);

    Statistics  checkAttendanceByCreateByAndCreateTime(@Param("createBy") String createBy, @Param("createTime")String createTime);
}