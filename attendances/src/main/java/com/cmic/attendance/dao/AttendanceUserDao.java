package com.cmic.attendance.dao;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.model.Permission;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import java.util.Map;
import java.util.List;
/**
* 后台y用户表Dao
*/
@Mapper
public interface AttendanceUserDao extends CrudDao<AttendanceUser> {

    AttendanceUser checkUserByName(String userName);
    List<Map> findAttendanceUserList(Map map);

    //登陆成功后返回权限给前端
    List<Permission> givePermission(String attendanceUserId);

    Integer getCountByAttendanceUsername(String attendanceUsername);

    Integer getCountByAttendancePhone(String attendancePhone);
}