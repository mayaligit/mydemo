package com.cmic.attendance.dao;

import com.cmic.attendance.model.Attendance;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 考勤表Dao
*/
@Mapper
public interface AttendanceDao extends CrudDao<Attendance> {

    Attendance getAttendanceByCreatebyAndCreateTime(@Param("createBy") String createBy,
                                                    @Param("createTime")String createTime);
    void updateByAttendance(@Param("id")Attendance updateByAttendance);
    void updateAttendances(Attendance attendance);
    void updateAttendan(Attendance attendance);

    List<Map> checkAttendanceByDay(String date);

    List<Map> checkAttendanceLatterByDay(Map<String, Object> map);

    int getWorkCount(String date);

    int getLatterCount(Map<String, Object> map);

    int getOutworkCount(String date);

    List<Map> selectAttendances(Attendance attendance);

    List<Attendance> findAttendanceList(Map<String,Object> paramMap);

    List<Attendance> selectExportAttendanceData(Attendance attendance);
}