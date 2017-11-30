package com.cmic.attendance.dao;

import com.cmic.attendance.model.GroupAudit;
import com.cmic.saas.base.dao.CrudDao;
import org.apache.ibatis.annotations.Mapper;


/**
* Dao
*/
@Mapper
public interface GroupAuditDao extends CrudDao<GroupAudit> {

    public GroupAudit getGroupAuditBydrId(String groupRuleId);

}