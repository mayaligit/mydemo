package com.cmic.attendance.dao;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.attendance.model.Permission;
import com.cmic.attendance.vo.AttendanceUserVo;
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
    //查询所有项目组名
    List<String> getAttendanceGroupName(AttendanceUserVo attendanceUserVo);

    Integer getCountByAttendanceUsername(String attendanceUsername);

    //后台用户关联角色,查出每个用户对应的角色
    List<Integer> getUserRoleNumber(String attendanceUserId);

    AttendanceUser getAttendanceUser(String telephone);

    Integer getCountByAttendancePhone(String attendancePhone);
}