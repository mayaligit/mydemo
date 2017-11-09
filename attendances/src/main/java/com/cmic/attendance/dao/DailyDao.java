package com.cmic.attendance.dao;

import com.cmic.attendance.model.Daily;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 日报表Dao
*/
@Mapper
public interface DailyDao extends CrudDao<Daily> {

    Daily getDailyByAttendanceId(String attendanceId);

    Daily getDailyById(@Param("dailyId") String dailyId);

    void updateDailyAuditById(Map<String,Object> paramMap);

    List<Map> findDailyList(Daily daily);
}