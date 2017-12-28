package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.RoleUser;
import org.apache.ibatis.annotations.Mapper;

/**
* 用户角色中间表Dao
*/
@Mapper
public interface RoleUserDao extends CrudDao<RoleUser> {

}