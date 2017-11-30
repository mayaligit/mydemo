package com.cmic.attendance.dao;

import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.GroupAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
* 考勤地址表Dao
*/
@Mapper
public interface GroupAddressDao extends CrudDao<GroupAddress> {

    public List<GroupAddress> findListByGroupRuleId(String attendanceGroupId);

    public void updateGroupAddressById(Map<String,Object> paramMap);
}