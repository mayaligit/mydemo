package com.cmic.attendance.dao;

import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Clazzes;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* 班次表Dao
*/
@Mapper
public interface ClazzesDao extends CrudDao<Clazzes> {
    public Clazzes getClazzesById(@Param("id") String id);

    List<Attendance> checkAttendanceLatterByDay(Map<String, Object> map);

    Clazzes getTotalById(String id);

    int startWork(String attendanceGroup);
    int endWork(String attendanceGroup);

    Clazzes getByGroupName(String attendanceGroup);
}