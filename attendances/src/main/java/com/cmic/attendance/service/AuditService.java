/*
package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Audit;
import com.cmic.attendance.model.GroupRule;
import com.cmic.attendance.utils.DateUtils;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.saas.base.model.BaseAdminEntity;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * 审批表Service
 *//*

@Service
@Transactional(readOnly = true)
public class AuditService extends CrudService<AuditDao, Audit> {

    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private AuditDao auditDao;
    @Autowired
    private GroupRuleService groupRuleService;

    public Audit get(String id) {
        return super.get(id);
    }

    public List<Audit> findList(Audit audit) {
        return super.findList(audit);
    }

    public PageInfo<Audit> findPage(PageInfo<Audit> page, Audit audit) {
        return super.findPage(page, audit);
    }

    @Transactional(readOnly = false)
    public void save(Audit audit) {
        super.save(audit);
    }

    @Transactional(readOnly = false)
    public Map save(HttpServletResponse response, Audit audit) {
         */
/*response.setHeader("Access-Control-Allow-Origin", "*");*//*

        Map<String, String> map = new HashMap<>();
        //设置用户名
        Object obj = WebUtils.getRequest().getSession().getAttribute("_CURRENT_ADMIN_INFO");
        if (null == obj || !(obj instanceof BaseAdminEntity)) {
            map.put("msg", "登陆超时,请重新登陆");
            return map;
        }
        BaseAdminEntity user = (BaseAdminEntity) obj;
        audit.setUsername(user.getName());


      */
/*  // 测试数据
        audit.setUsername("陈华龙");
        audit.setAttendanceGroup("odc");*//*


        //任何请况下都必须携带的参数
        if (StringUtils.isBlank(audit.getAuditContent()) || StringUtils.isBlank(audit.getAttendanceGroup())) {
            map.put("msg", "请求参数不能为空");
            return map;
        }

        //组规则信息
        // GroupRule groupRule=groupRuleService.findGroupNameAndGroupStatus(audit.getAttendanceGroup(), 0);
        // Float attendanceDuration=groupRule.getGroupAttendanceDuration();//考勤时长

        if (StringUtils.isBlank(String.valueOf(audit.getBusinessType()))) {
            map.put("msg", "审批类型不能为空");
            return map;
        } else {
            switch (audit.getBusinessType()) {
                case 0:
                    //请假情况 startDate endDate holiday_days 不为空
                    if (null == audit.getStartDate() || null == audit.getEndDate() || StringUtils.isBlank(String.valueOf(audit.getHolidayDays()))) {
                        map.put("msg", "请假时间不能为空");
                        return map;
                    }
                    Long startDateSecond = audit.getStartDate().getTime();
                    Long endDateSecond = audit.getEndDate().getTime();
                    if (startDateSecond > endDateSecond) {
                        map.put("msg", "请正确选择开始和结束时间");
                        return map;
                    }
                    break;
                case 1:
                    //外勤情况 startDate endDate Field_personnel_days 不为空
                    if (null == audit.getStartDate() || null == audit.getEndDate() || StringUtils.isBlank(String.valueOf(audit.getFieldPersonnelDays()))) {
                        map.put("msg", "外勤时间不能为空");
                        return map;
                    }
                    startDateSecond = audit.getStartDate().getTime();
                    endDateSecond = audit.getEndDate().getTime();
                    if (startDateSecond > endDateSecond) {
                        map.put("msg", "请正确选择开始和结束时间");
                        return map;
                    }
                    //设置外勤次数为1
                    audit.setFieldPersonnel(1);
                    break;
                case 2:
                    //缺卡 uditContent已经判断了
                    break;
            }
        }
        audit.setSubmitTime(new Date());
        audit.setAuditStatus(1);  //设置审批状态为未处理

        audit.setCreateDate(audit.getSubmitTime());
        audit.setUpdateDate(audit.getSubmitTime());

        super.save(audit);

        map.put("msg", "申请提交成功");
        return map;
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Audit audit) {
        super.dynamicUpdate(audit);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Audit audit = get(id);
        if (audit == null || StringUtils.isEmpty(audit.getId())) {
            throw new RestException("删除失败，审批表不存在");
        }
        super.delete(id);
        logger.info("删除审批表：" + audit.toJSONString());
    }

    @Transactional(readOnly = false)
    public Audit getAuditById(String auditId) {
        Audit audit = dao.getAuditById(auditId);
        return audit;
    }

    @Transactional(readOnly = false)
    public void updateAudit(Audit audit, HttpServletRequest request) {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        //获取审批人信息,更新审批表
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo) WebUtils.getSession().getAttribute("attendanceUserVo");
        paraMap.put("updateBy", attendanceUserVo.getAttendanceUsername());
        paraMap.put("updateTime", new Date());
        paraMap.put("auditTime", DateUtils.getDateToStrings(new Date()));
        paraMap.put("auditStatus", "0"); //设置审批意见状态为 已处理
        paraMap.put("auditId", "");//审批人ID 老铁会提供 在attendanceUserVo中获取
        paraMap.put("auditSuggestion", audit.getAuditSuggestion());
        //  paraMap.put("suggestionRemarks",audit.getSuggestionRemarks());        //更新 审批状态为 已处理
        dao.updateAudit(paraMap);

        // 如果审批同意 ,维护考勤表;如果不同意,无操作
        if (audit.getAuditSuggestion() == 1) {
            return;
        }
        if (audit.getAuditSuggestion() == 0) {
            //如果是请假 0 同意了 0  更新审批表  维护考勤表  上班时长为 0
            // 如果是外勤 1 同意了 0  更新审批表  维护考情表  上下班按照默认值
            // 如果是缺卡 2 同意了 0  更新审批表  维护考勤表 上下班按照默认值
            //获取当天数据库的打卡数据
            String phone = audit.getCreateBy().getId();
            Date submitTime = audit.getSubmitTime();
            String createTime = DateUtils.getDateToYearMonthDay(submitTime);
            Attendance DBattendance = attendanceDao.getAttendanceByCreatebyAndCreateTime(phone, createTime);
            switch (audit.getBusinessType()) {
                //分别按照自由模式和时长模式维护数据表 自由/groupAttendanceWay =0  时长/groupAttendanceWay=1
                case 0:
                    break;
                case 1:
                    //获取考勤规则
                     GroupRule groupRule=groupRuleService.findGroupNameAndGroupStatus(audit.getAttendanceGroup(), 0);
                    //自由模式
                    groupRule.get
                    break;
                case 2:
                    break;
            }
        }

        //审批的是补卡类型时,关联对应考勤

        //将手机号码放到session中
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId(phone);
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO", adminEntity);

        Attendance attendance = new Attendance();
        String businessType = audit.getBusinessType().toString();
        if (null == DBattendance) { //没有打卡数据
            attendance.preInsert();
            attendance.setAttendanceUser(audit.getUsername());
            attendance.setCreateDate(new Date());
            attendance.setUpdateDate(new Date());
            attendance.setAttendanceMonth(createTime);
            attendance.setAttendanceGroup("ODC");

            if (businessType.trim().equals("0")) {  //处理补上班卡
                Date startTime = DateUtils.getStringsToDates(createTime + " " + "9:00:00");
                attendance.setStartTime(startTime);
                attendance.setStartTimeStatus("0");
                attendance.setStartLocation("南方基地");
                attendance.setAttendanceStatus("0");
                attendance.setDailyStatus(0);

            } else if (businessType.trim().equals("1")) {  //处理补下班卡
                Date endTime = DateUtils.getStringsToDates(createTime + " " + "18:00:00");
                attendance.setEndTime(endTime);
                attendance.setEndLocation("南方基地");
                attendance.setEndTimeStatus("0");
                attendance.setDailyStatus(0);
                attendance.setAttendanceStatus("1");

            }
            attendanceDao.insert(attendance);

        } else { //有打卡数据
            attendance.setId(DBattendance.getId());
            attendance.preUpdate();
            attendance.setUpdateDate(new Date());

            if (businessType.trim().equals("0")) {  //处理补上班卡
                Date startTime = DateUtils.getStringsToDates(createTime + " " + "9:00:00");
                attendance.setStartTime(startTime);
                attendance.setStartTimeStatus("0");
                attendance.setStartLocation("南方基地");

            } else if (businessType.trim().equals("1")) {  //处理补下班卡
                Date endTime = DateUtils.getStringsToDates(createTime + " " + "18:00:00");
                attendance.setEndTime(endTime);
                attendance.setEndLocation("南方基地");
                attendance.setEndTimeStatus("0");
            }
            attendanceDao.dynamicUpdate(attendance);
        }
    }

    public Map<String, Object> findAuditList(PageInfo<Audit> page, Audit audit) {
        if (page.getPageNum() == 0) {
            page.setPageNum(1);
        }

        if (page.getPageSize() == 0) {
            page.setPageSize(10);
        }

        if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_time desc");
        }

        PageHelper.startPage(page.getPageNum(), page.getPageSize() > 0 ? page.getPageSize() : 10, page.getOrderBy());

        PageInfo<Map> result = new PageInfo(dao.findAuditList(audit));

        //创建封装数据
        Map<String, Object> dataMap = new HashMap<>();
        //考勤数据
        dataMap.put("auditList", result.getList());
        //总页数
        dataMap.put("pageCount", result.getPages());
        //总记录数
        dataMap.put("pageTotal", result.getTotal());
        return dataMap;
    }

}*/
