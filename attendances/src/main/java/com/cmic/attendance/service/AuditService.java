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
    private AttendanceService attendanceService;
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

        //audit.setUserName("梁永燊");// 测试数据

        //查询数据库中申请了的请假或外勤时间是否存在
        Audit DBAudit = null;
        if(audit.getAttendanceGroup()!=null && audit.getUserName()!=null&& audit.getStartDate()!=null){
            audit.setDateStr(DateUtils.getDateToYearMonthDay(audit.getStartDate()));
            DBAudit=dao.getByUserNameDateAndAttendanceGroud(audit);
        }

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
                    if (null == audit.getStartDate() || null == audit.getEndDate()) {
                        map.put("msg", "缺卡时间不能为空");
                        return map;
                    }
                    startDateSecond = audit.getStartDate().getTime();
                    endDateSecond = audit.getEndDate().getTime();
                    if (startDateSecond > endDateSecond) {
                        map.put("msg", "请正确选择开始和结束时间");
                        return map;
                    }
                    break;
            }
        }
        audit.setSubmitTime(new Date());
        audit.setUpdateDate(new Date());
        if (DBAudit==null){
            audit.setAuditStatus(1);  //设置审批状态为未处理
            audit.setCreateDate(new Date());
            super.save(audit);
            map.put("msg", "提交成功");
        }else if(DBAudit.getAuditStatus()==1){
            //未处理的审批可以更新
            audit.setId(DBAudit.getId());
            dao.dynamicUpdate(audit);
            map.put("msg", "更新成功");
        }else if(DBAudit.getAuditSuggestion()==1){
            //处理后拒绝的审批可以更新
            audit.setAuditStatus(1);
            audit.setId(DBAudit.getId());
            dao.dynamicUpdate(audit);
            map.put("msg", "更新成功");
        }else {
            //处理后同意的审批不可以更新
            map.put("msg", "该时间段已经被申请了,这次申请不成功,请联系考勤组管理员");
        }
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
    //处理审批,更新审批表,维护考勤表
    @Transactional(readOnly = false)
    public void updateAudit(Audit audit, HttpServletRequest request) {
        Attendance attendance = new Attendance();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        //获取审批人信息,更新审批表
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo) WebUtils.getSession().getAttribute("attendanceUserVo");
        if(attendanceUserVo==null){
            throw new RestException("登陆超时,请重新登陆");
        }
        paraMap.put("updateBy", attendanceUserVo.getAttendanceUsername());
        paraMap.put("updateDate", new Date());
        paraMap.put("auditTime", DateUtils.getDateToStrings(new Date()));
        paraMap.put("auditStatus", "0"); //设置审批意见状态为 已处理
        paraMap.put("auditUserId", attendanceUserVo.getId());//审批人ID
        paraMap.put("auditUsername", attendanceUserVo.getAttendanceUsername());
        paraMap.put("auditSuggestion", audit.getAuditSuggestion());
        paraMap.put("suggestionRemarksvarchar", audit.getSuggestionRemarksvarchar());
        paraMap.put("id", audit.getId());
        dao.updateAudit(paraMap);

       /* // 测试数据
        paraMap.put("updateBy", "陈华龙");// 测试数据
        paraMap.put("auditUserId", "666");// 测试数据
        paraMap.put("auditUsername", "陈华龙");// 测试数据
        dao.updateAudit(paraMap);// 测试数据*/

        //获取考勤规则
        GroupRule groupRule = groupRuleService.findGroupNameAndGroupStatus(audit.getAttendanceGroup(), 0);
        //获取缺卡,外出时间
        String startHHSSMM = DateUtils.getDateToHourMinuteS(audit.getStartDate()) ;
        String endHHSSMM = DateUtils.getDateToHourMinuteS(audit.getEndDate());

        //算出每人上班的时长,用下班时间减去上班时间
        float startTimeSeconds = attendanceService.getSeconds(startHHSSMM);
        float endTimeSeconds = attendanceService.getSeconds(endHHSSMM);
        float differTime = endTimeSeconds - startTimeSeconds;
        //如果审批同意 ,维护考勤表;如果不同意,无操作
        if (audit.getAuditSuggestion() == 1) {
            return;
        }

        //获取数据库当天打卡数据
        String phoneNumber = audit.getPhoneNumber();
        Date  startDate= audit.getStartDate();
        String StartTime = DateUtils.getDateToYearMonthDay(startDate);
        Attendance DBattendance = attendanceDao.getAttendanceByCreatebyAndCreateTime(phoneNumber, StartTime);

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
                    attendance.setAttendanceMonth(DateUtils.getMonth(audit.getStartDate()));
                    attendance.setDailyStatus(0);
                    attendance.setAttendanceGroup(audit.getAttendanceGroup());
                    attendance.setEndTimeStatus("0");
                    attendance.setStartTimeStatus("0");
                    attendance.setCreateDate(new Date());
                    attendance.setUpdateDate(new Date());
                    attendance.setStartTime(audit.getStartDate());
                    attendance.setEndTime(audit.getEndDate());
                    attendance.setAttendanceWorkTime(differTime);
                    if(audit.getBusinessType()==1){
                        attendance.setAttendanceStatus("2");
                    }else {
                        attendance.setAttendanceStatus("0");
                    }
                    attendanceDao.insert(attendance);
                } else {
                    attendance.setId(DBattendance.getId());
                    attendance.preUpdate();
                    attendance.setUpdateDate(new Date());
                    attendance.setStartTimeStatus("0");
                    attendance.setEndTimeStatus("0");
                    attendance.setStartTime(audit.getStartDate());
                    attendance.setEndTime(audit.getEndDate());
                    attendance.setAttendanceWorkTime(differTime);
                    if(audit.getBusinessType()==1){
                        attendance.setAttendanceStatus("2");
                    }else {
                        attendance.setAttendanceStatus("0");
                    }
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
                    attendance.setStartTime(audit.getStartDate());
                    attendance.setEndTime(audit.getEndDate());
                    attendance.setAttendanceMonth(DateUtils.getMonth(audit.getStartDate()));
                    attendance.setDailyStatus(0);
                    attendance.setAttendanceGroup(audit.getAttendanceGroup());
                    attendance.setEndTimeStatus("0");
                    attendance.setStartTimeStatus("0");
                    attendance.setAttendanceWorkTime(differTime);
                    attendance.setCreateDate(new Date());
                    attendance.setUpdateDate(new Date());
                    if(audit.getBusinessType()==1){
                        attendance.setAttendanceStatus("2");
                        attendance.setStartLocation("审批维护数据");
                        attendance.setEndLocation("审批维护数据");
                    }else {
                        attendance.setAttendanceStatus("0");
                        attendance.setStartLocation(groupRule.getGroupAddress());
                        attendance.setEndLocation(groupRule.getGroupAddress());
                    }
                    attendanceDao.insert(attendance);
                } else {
                    attendance.setId(DBattendance.getId());
                    attendance.preUpdate();
                    attendance.setStartTime(audit.getStartDate());
                    attendance.setEndTime(audit.getEndDate());
                    attendance.setStartTimeStatus("0");
                    attendance.setEndTimeStatus("0");
                    attendance.setAttendanceWorkTime(differTime);
                    attendance.setUpdateDate(new Date());
                    if(audit.getBusinessType()==1){
                        attendance.setAttendanceStatus("2");
                        attendance.setStartLocation(groupRule.getGroupAddress());
                        attendance.setEndLocation(groupRule.getGroupAddress());
                    }else {
                        attendance.setAttendanceStatus("0");
                        attendance.setStartLocation(groupRule.getGroupAddress());
                        attendance.setEndLocation(groupRule.getGroupAddress());
                    }
                    attendanceDao.dynamicUpdate(attendance);
                }
            }
        }
    }

    public Map<String, Object> findAuditList(PageInfo<Audit> page, Audit audit) {
        //创建封装数据
        Map<String, Object> dataMap = new HashMap<>();
        //验证登陆信息
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)WebUtils.getRequest().getSession().getAttribute("attendanceUserVo");
        if (null == attendanceUserVo) {
            dataMap.put("flag", "1");
            return dataMap;
        } else {
            dataMap.put("flag", "0");
        }

        //分配不同权限,查看不同东西内容
        List<Integer> roleList =(List<Integer> ) WebUtils.getRequest().getSession().getAttribute("roleList");
        if(roleList.contains(1)){
            audit.setAttendanceGroup(null);
        }else {
            audit.setAttendanceGroup(attendanceUserVo.getAttendanceGroup());
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