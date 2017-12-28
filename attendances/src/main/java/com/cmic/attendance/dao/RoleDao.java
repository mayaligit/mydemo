package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.Role;
import org.apache.ibatis.annotations.Mapper;

/**
* 角色表Dao
*/
@Mapper
public interface RoleDao extends CrudDao<Role> {

}