package com.cmic.attendance.dao;

import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.WorkStatistics;
import com.cmic.attendance.pojo.AttendancePojo;
import com.cmic.attendance.pojo.AttendanceResultPojo;
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
                                                    @Param("createDate")String createDate);
    void updateByAttendance(@Param("id")Attendance updateByAttendance);
    void updateAttendances(Attendance attendance);
    void updateAttendan(Attendance attendance);

    List<Map> checkAttendanceByDay(AttendancePojo attendancePojo);

    List<Map> checkAttendanceLatterByDay(AttendancePojo attendancePojo);

    int getWorkCount(AttendancePojo attendancePojo);

    int getLatterCount(AttendancePojo attendancePojo);

    int getOutworkCount(AttendancePojo attendancePojo);

    List<Map> selectAttendances(Attendance attendance);

    List<Map> findAttendanceList(Map<String,Object> paramMap);

    List<Attendance> selectExportAttendanceData(Attendance attendance);

    List<Attendance> getAttendanceDays(WorkStatistics workStatistics);

    List<Attendance> getLates(WorkStatistics workStatistics);

    List<Attendance> getLeaveEarly(WorkStatistics workStatistics);

    int getFieldPersonnel(WorkStatistics workStatistics);

    List<Attendance> getMissingCard(WorkStatistics workStatistics);

    List<AttendanceResultPojo> checkAttendanceHardworkingByDay(AttendancePojo attendancePojo);

    List<Map> checkAttendanceHardworkingByMonth(AttendancePojo attendancePojo);

    List<Map> checkAttendanceLatterByMonth(AttendancePojo attendancePojo);

    List<Attendance> getOverTimeList(WorkStatistics workStatistics);

    double getOverTime(WorkStatistics workStatistics);

    List<Integer> getAttendanceDaysList(WorkStatistics workStatistics);

}

