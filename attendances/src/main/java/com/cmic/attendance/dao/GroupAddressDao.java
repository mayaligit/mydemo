package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.GroupAddress;
import org.apache.ibatis.annotations.Mapper;

/**
* 考勤地址表Dao
*/
@Mapper
public interface GroupAddressDao extends CrudDao<GroupAddress> {

}