package com.cmic.attendance.dao;

import com.cmic.attendance.model.AttendanceUser;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
* 后台y用户表Dao
*/
@Mapper
public interface AttendanceUserDao extends CrudDao<AttendanceUser> {

    AttendanceUser checkUserByName(String userName);
}