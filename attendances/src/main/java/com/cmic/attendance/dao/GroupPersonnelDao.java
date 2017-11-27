package com.cmic.attendance.dao;

import com.cmic.attendance.model.GroupPersonnel;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* Dao
*/
@Mapper
public interface GroupPersonnelDao extends CrudDao<GroupPersonnel> {

    public List<GroupPersonnel> findListByGroupRuleId(String groupRuleId);

}