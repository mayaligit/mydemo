package com.cmic.attendance.dao;

import com.cmic.attendance.model.GroupRule;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
* Dao
*/
@Mapper
public interface GroupRuleDao extends CrudDao<GroupRule> {

    List<Map> findGroupRuleList(Map<String,Object> paramMap);

    GroupRule getByGroupNameAndGroupStatus(@Param("groupName") String groupName,
                                           @Param("groupStatus") int groupStatus);
}