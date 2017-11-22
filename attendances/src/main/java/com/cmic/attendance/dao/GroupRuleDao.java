package com.cmic.attendance.dao;

import com.cmic.attendance.model.GroupRule;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

/**
* Dao
*/
@Mapper
public interface GroupRuleDao extends CrudDao<GroupRule> {

}