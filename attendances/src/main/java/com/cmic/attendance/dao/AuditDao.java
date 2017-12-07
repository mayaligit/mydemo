package com.cmic.attendance.dao;

import com.cmic.attendance.model.Audit;
import com.cmic.attendance.model.WorkStatistics;
import com.cmic.saas.base.dao.CrudDao;
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
    List<Audit> getHolidayDays(WorkStatistics workStatistics);

    List<Audit> getHolidayList(WorkStatistics workStatistics);

    List<Audit> getFieldPersonnel(WorkStatistics workStatistics);
    //根据用户名 日期 所属组查询审批是否存在
    Audit getByUserNameDateAndAttendanceGroud(Audit audit);



}