package com.cmic.attendance.dao;

import com.cmic.attendance.model.GroupDailyRule;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;


/**
* Dao
*/
@Mapper
public interface GroupDailyRuleDao extends CrudDao<GroupDailyRule> {

}