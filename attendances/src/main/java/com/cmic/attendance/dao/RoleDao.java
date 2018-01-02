package com.cmic.attendance.dao;

import com.cmic.attendance.model.Role;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 角色表Dao
*/
@Mapper
public interface RoleDao extends CrudDao<Role> {
    //查询供应商的id和名字
    List<Map> supplierList();

    //查询角色的集合
    List<Map> getRoleList();
}