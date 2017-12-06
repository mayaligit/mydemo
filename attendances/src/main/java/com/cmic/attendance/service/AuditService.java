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

/**
 * 审批表Service
 */
@Service
@Transactional(readOnly = true)
public class AuditService extends CrudService<AuditDao, Audit> {

    @Autowired
    private AttendanceDao attendanceDao;
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
         /*response.setHeader("Access-Control-Allow-Origin", "*");*/

        Map<String, String> map = new HashMap<>();
        //设置用户名
        Object obj = WebUtils.getRequest().getSession().getAttribute("_CURRENT_ADMIN_INFO");
        if (null == obj || !(obj instanceof BaseAdminEntity)) {
            map.put("msg", "登陆超时,请重新登陆");
            return map;
        }
        BaseAdminEntity user = (BaseAdminEntity) obj;
        audit.setUserName(user.getName());

        //audit.setUsername("陈志豪");// 测试数据

        //任何请况下都必须携带的参数
        if (StringUtils.isBlank(audit.getAuditContent()) || StringUtils.isBlank(audit.getAttendanceGroup())) {
            map.put("msg", "请求参数不能为空");
            return map;
        }

        //组规则信息
        GroupRule groupRule = groupRuleService.findGroupNameAndGroupStatus(audit.getAttendanceGroup(), 0);

        double attendanceDuration = 0;
        if (groupRule.getGroupAttendanceWay() == 0) {
            //自由模式时长
            attendanceDuration = (double) groupRule.getGroupAttendanceDuration();
        }
        if (groupRule.getGroupAttendanceWay() == 1) {
            //时长模式时长
            attendanceDuration = (double) groupRule.getGroupAttendanceDuration();
        }

        //判断审批参数
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
                    //如果请假时间大于一天的工作时间,请假时间为工作时间
                    if (attendanceDuration < audit.getHolidayDays()) {
                        audit.setHolidayDays(attendanceDuration);
                    }
                    break;
                case 1:
                    //外勤情况 startDate endDate holidayDays 不为空
                    if (null == audit.getStartDate() || null == audit.getEndDate() || StringUtils.isBlank(String.valueOf(audit.getHolidayDays()))) {
                        map.put("msg", "外勤时间不能为空");
                        return map;
                    }
                    startDateSecond = audit.getStartDate().getTime();
                    endDateSecond = audit.getEndDate().getTime();
                    if (startDateSecond > endDateSecond) {
                        map.put("msg", "请正确选择开始和结束时间");
                        return map;
                    }
                    //如果外勤时间大于一天的工作时间,外勤时间为工作时间
                    if (attendanceDuration < audit.getHolidayDays()) {
                        audit.setHolidayDays(attendanceDuration);
                    }
                    break;
                case 2:
                    //缺卡
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
        Attendance attendance = new Attendance();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        //获取审批人信息,更新审批表
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo) WebUtils.getSession().getAttribute("attendanceUserVo");
        paraMap.put("updateBy", attendanceUserVo.getAttendanceUsername());
        paraMap.put("updateDate", new Date());
        paraMap.put("auditTime", DateUtils.getDateToStrings(new Date()));
        paraMap.put("auditStatus", "0"); //设置审批意见状态为 已处理
        paraMap.put("auditUserId", attendanceUserVo.getId());//审批人ID
        paraMap.put("auditUsername", attendanceUserVo.getAttendanceUsername());
        paraMap.put("auditSuggestion", audit.getAuditSuggestion());
        paraMap.put("suggestionRemarksvarchar",audit.getSuggestionRemarksvarchar());
        paraMap.put("id", audit.getId());
        dao.updateAudit(paraMap);

       /* // 测试数据
        paraMap.put("updateBy", "陈华龙");// 测试数据
        paraMap.put("auditUserId", "666");// 测试数据
        paraMap.put("auditUsername", "陈华龙");// 测试数据
        dao.updateAudit(paraMap);// 测试数据*/

        //获取考勤规则
        GroupRule groupRule = groupRuleService.findGroupNameAndGroupStatus(audit.getAttendanceGroup(), 0);
        String startTime = DateUtils.getDateToYearMonthDay(new Date()) + " " + groupRule.getGroupAttendanceStart() + ":00";
        String endTime = DateUtils.getDateToYearMonthDay(new Date()) + " " + groupRule.getGroupAttendanceEnd() + ":00";

        //如果审批同意 ,维护考勤表;如果不同意,无操作
        if (audit.getAuditSuggestion() == 1) {
            return;
        }

        //获取数据库当天打卡数据
        String phoneNumber = audit.getPhoneNumber();
        Date submitTime = audit.getSubmitTime();
        String createTime = DateUtils.getDateToYearMonthDay(submitTime);
        Attendance DBattendance = attendanceDao.getAttendanceByCreatebyAndCreateTime(phoneNumber, createTime);

        //将手机号码放到session中
        BaseAdminEntity adminEntity = new BaseAdminEntity();
        adminEntity.setId(phoneNumber);
        request.getSession().setAttribute("_CURRENT_ADMIN_INFO", adminEntity);


     /* 审批同意 按自由模式和时长模式维护考勤表
         如果是请假 0   更新审批表(已经处理)  不维护考勤表
         如果是外勤 1   更新审批表(已经处理)  维护考情表  上下班按照默认值
         如果是缺卡 2   更新审批表(已经处理)  维护考勤表 上下班按照默认值  */
        //自由模式
        if (groupRule.getGroupAttendanceWay() == 0) {
            if (audit.getBusinessType() == 0) {
                //请假 不做任何处理
                return;
            } else if (audit.getBusinessType() == 1 || audit.getBusinessType() == 2) {
                //外勤  如果提交多次外勤,则除了第一次提交,其他都按照更新处理
                //缺卡 分为有打卡和没有打卡情况   所以外勤和缺卡是一样的
                if (DBattendance == null) {
                    attendance.preInsert();
                    attendance.setAttendanceUser(audit.getUserName());
                    attendance.setAttendanceStatus("0");
                    attendance.setAttendanceMonth(DateUtils.getCurrMonth());
                    attendance.setDailyStatus(0);
                    attendance.setAttendanceGroup(audit.getAttendanceGroup());
                    attendance.setEndTimeStatus("0");
                    attendance.setStartTimeStatus("0");
                    attendance.setCreateDate(new Date());
                    attendance.setUpdateDate(new Date());
                    attendanceDao.insert(attendance);
                } else {
                    attendance.setId(DBattendance.getId());
                    attendance.preUpdate();
                    attendance.setAttendanceStatus("0");
                    attendance.setUpdateDate(new Date());
                    attendance.setStartTimeStatus("0");
                    attendance.setEndTimeStatus("0");
                    attendanceDao.dynamicUpdate(attendance);
                }
            }
        }
        //时长模式
        if (groupRule.getGroupAttendanceWay() == 1) {
            if (audit.getBusinessType() == 0) {
                //请假 不做任何处理
                return;
            } else if (audit.getBusinessType() == 1 || audit.getBusinessType() == 2) {
                //外勤  如果提交多次外勤,则除了第一次提交,其他都按照更新处理
                //缺卡 分为有打卡和没有打卡情况   所以外勤和缺卡是一样的
                if (DBattendance == null) {
                    attendance.preInsert();
                    attendance.setAttendanceUser(audit.getUserName());
                    attendance.setStartTime(DateUtils.getStringsToDates(startTime));
                    attendance.setEndTime(DateUtils.getStringsToDates(endTime));
                    attendance.setAttendanceStatus("0");
                    attendance.setAttendanceMonth(DateUtils.getCurrMonth());
                    attendance.setStartLocation(groupRule.getGroupAddress());
                    attendance.setEndLocation(groupRule.getGroupAddress());
                    attendance.setDailyStatus(0);
                    attendance.setAttendanceGroup(audit.getAttendanceGroup());
                    attendance.setEndTimeStatus("0");
                    attendance.setStartTimeStatus("0");
                    attendance.setAttendanceWorkTime(groupRule.getGroupAttendanceDuration());
                    attendance.setCreateDate(new Date());
                    attendance.setUpdateDate(new Date());
                    attendanceDao.insert(attendance);
                } else {
                    attendance.setId(DBattendance.getId());
                    attendance.preUpdate();
                    attendance.setStartTime(DateUtils.getStringsToDates(startTime));
                    attendance.setEndTime(DateUtils.getStringsToDates(endTime));
                    attendance.setAttendanceStatus("0");
                    attendance.setStartLocation(groupRule.getGroupAddress());
                    attendance.setEndLocation(groupRule.getGroupAddress());
                    attendance.setStartTimeStatus("0");
                    attendance.setEndTimeStatus("0");
                    attendance.setAttendanceWorkTime(groupRule.getGroupAttendanceDuration());
                    attendance.setUpdateDate(new Date());
                    attendanceDao.dynamicUpdate(attendance);
                }
            }
        }
    }

    public Map<String, Object> findAuditList(PageInfo<Audit> page, Audit audit) {
        //创建封装数据
        Map<String, Object> dataMap = new HashMap<>();
        //验证登陆信息
        Object obj = WebUtils.getRequest().getSession().getAttribute("attendanceUserVo");
        if (null == obj ) {
            dataMap.put("flag", "1");
            return dataMap;
        } else {
            dataMap.put("flag", "0");
        }

        if (page.getPageNum() == 0) {
            page.setPageNum(1);
        }

        if (page.getPageSize() == 0) {
            page.setPageSize(10);
        }

        if (org.apache.commons.lang3.StringUtils.isEmpty(page.getOrderBy())) {
            page.setOrderBy("a.create_date desc");
        }

        PageHelper.startPage(page.getPageNum(), page.getPageSize() > 0 ? page.getPageSize() : 10, page.getOrderBy());

        PageInfo<Map> result = new PageInfo(dao.findAuditList(audit));

        //考勤数据
        dataMap.put("auditList", result.getList());
        //总页数
        dataMap.put("pageCount", result.getPages());
        //总记录数
        dataMap.put("pageTotal", result.getTotal());
        return dataMap;
    }

}