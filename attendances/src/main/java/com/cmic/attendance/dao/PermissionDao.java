package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
* 角色权限中间表Dao
*/
@Mapper
public interface PermissionDao extends CrudDao<Permission> {

}