package com.cmic.attendance.dao;

import com.cmic.attendance.model.WorkStatistics;
import com.cmic.saas.base.dao.CrudDao;
import com.cmic.attendance.model.Audit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 审批表Dao
 */
@Mapper
public interface AuditDao extends CrudDao<Audit> {

    /**
     *  获取审批记录的详细信息
     * @param auditId 审批记录主键
     * @return
     */
    Audit getAuditById(@Param("auditId") String auditId);

    /**
     * 审批后跟新 审批记录的信息
     * @param paraMap 封装参数
     * @return
     */
    void updateAudit(Map<String,Object> paraMap);

    List<Map> findAuditList(Audit audit);

    //某个月的请假天数
    double getHolidayDays(WorkStatistics workStatistics);

    List<Audit> getHolidayList(WorkStatistics workStatistics);

}