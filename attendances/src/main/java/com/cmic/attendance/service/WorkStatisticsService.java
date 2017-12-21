package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.dao.GroupRuleDao;
import com.cmic.attendance.dao.WorkStatisticsDao;
import com.cmic.attendance.model.Attendance;
import com.cmic.attendance.model.Audit;
import com.cmic.attendance.model.GroupRule;
import com.cmic.attendance.model.WorkStatistics;
import com.cmic.attendance.utils.DateUtils;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Service
 */
@Service
@Transactional(readOnly = true)
public class WorkStatisticsService extends CrudService<WorkStatisticsDao, WorkStatistics> {
    @Autowired
    AttendanceDao attendanceDao;
    @Autowired
    GroupRuleService groupRuleService;

    @Autowired
    AuditDao auditDao;

    @Autowired
    GroupRuleDao groupRuleDao;

    public HashMap workStatistics(WorkStatistics workStatistics) {
        HashMap map = new HashMap();
        map.put("groupName",workStatistics.getGroup());
        map.put("groupStatus",0);
        GroupRule groupRule = groupRuleDao.getByGroupNameAndGroupStatus(map);
        HashMap result = null;
        if ((groupRule!=null)&&(groupRule.getGroupAttendanceWay()==1)){//固定模式
            result = workStatisticsConfirmed(workStatistics);
        }else {//自由模式
            result = workStatisticsFree(workStatistics);
        }
        return result;
    }

    public HashMap workStatisticsFree(WorkStatistics workStatistics) {//自由模式
        SimpleDateFormat strdate = new SimpleDateFormat("E", Locale.SIMPLIFIED_CHINESE);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd ", Locale.SIMPLIFIED_CHINESE);
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm");
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(" HH:mm");

        List<Attendance> attendanceDays = attendanceDao.getAttendanceDaysFree(workStatistics);//某个月的出勤
        List<Attendance> leaveEarly = attendanceDao.getLeaveEarlyFree(workStatistics);//某个月的早退次数
        List<Audit> fieldPersonnel = auditDao.getFieldPersonnel(workStatistics);//某个月的外勤次数
        List<Attendance> missingCard = attendanceDao.getMissingCard(workStatistics);//某个月的缺卡天数
        List<Audit> holidayDays = auditDao.getHolidayDays(workStatistics);//某个月的请假时长，小时为单位
        List<Audit> holidayList = auditDao.getHolidayList(workStatistics);//某个月的请假审批和外勤通过记录
        Calendar calendar = Calendar.getInstance();
        int dayOfMonth = 0;
        String str = simpleDateFormat2.format(calendar.getTime());
        if (workStatistics.getMonth().equals(str)) {//传进来的参数是本月
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//今天是几号
        } else {
            try {
                calendar.setTime(simpleDateFormat2.parse(workStatistics.getMonth()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//不是本月的话，看看参数的月份有几天
        }
        //以下为统计某个月的旷工次数
        CopyOnWriteArrayList<Integer> miss = new CopyOnWriteArrayList<>();//没有打卡的日期
        List<String> AbsenteeismData = new ArrayList<>();//旷工详情
        List<Integer> attendanceList = attendanceDao.getAttendanceDaysList(workStatistics);//打卡的日期
        for (int i = 1; i <= dayOfMonth; i++) {
            if (attendanceList.contains(i)) continue;
            miss.add(i);
        }
        List<Integer> holidays = new ArrayList<>();//请假的日期集合
        //System.out.println("请假开始时间和结束时间：");
        for (Audit audit : holidayList) {
            System.out.println(audit.getStartDate().getDate());
            System.out.println(audit.getEndDate().getDate());
            int start = audit.getStartDate().getDate();
            int end = audit.getEndDate().getDate();
            for (int j = start; j <= end; j++) {
                holidays.add(j);//请假的日期  集合
            }
        }
        for (Integer t : holidays) {
            if (miss.contains(t)) {
                miss.remove(t);//除去请假的日期
            }
        }
        //System.out.println("------------------------除去工作日和节假日");
        Calendar calendar2 = Calendar.getInstance();
        try {
            Date date = simpleDateFormat2.parse(workStatistics.getMonth());
            calendar2.setTime(date);//把时间设为传进来的月份
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(simpleDateFormat.format(calendar2.getTime()));
        //除去工作日和节假日
        for (Integer m : miss) {
            calendar2.set(Calendar.DAY_OF_MONTH, m);
            //如果日期m不是工作日,即休息日或者节假日
            try {
                String check=DateUtils.getWorkDays(calendar2.getTime());
                if((check!=null)&&(!check.contains("0"))){
                    miss.remove(m);//除去休息日和节假日即为旷工日期
                }
                /*if (!("0/r/n".equals(DateUtils.getWorkDays(calendar2.getTime())))) {
                    miss.remove(m);//除去休息日和节假日即为旷工日期
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Integer m : miss) {//封装旷工详情
            calendar2.set(Calendar.DAY_OF_MONTH, m);
            AbsenteeismData.add(simpleDateFormat3.format(calendar2.getTime()) + strdate.format(calendar2.getTime()));
        }
        //组装 map 返回
        double holidayDays_sum = 0;//请假总时长
        for (Audit audit : holidayDays) {
            holidayDays_sum += audit.getHolidayDays();
        }
        HashMap map = new HashMap();
        List<String> attendanceDaysData = new ArrayList<>();//出勤天数详情
        map.put("attendanceDays", attendanceDays.size());//出勤天数
        for (Attendance attendance : attendanceDays) {
            attendanceDaysData.add(simpleDateFormat3.format(attendance.getStartTime()) + strdate.format(attendance.getStartTime()));
        }
        List<String> leaveEarlyData = new ArrayList<>();//早退详情
        for (Attendance attendance : leaveEarly) {
            leaveEarlyData.add(simpleDateFormat3.format(attendance.getEndTime()) + strdate.format(attendance.getEndTime()) + simpleDateFormat4.format(attendance.getEndTime()));
        }
        List<String> fieldPersonnelData = new ArrayList<>();//外勤详情
        for (Audit audit : fieldPersonnel) {
            fieldPersonnelData.add(simpleDateFormat3.format(audit.getSubmitTime()) + strdate.format(audit.getSubmitTime()));
        }
        List<String> missingCardData = new ArrayList<>();//缺卡详情
        for (Attendance attendance : missingCard) {
            missingCardData.add(simpleDateFormat3.format(attendance.getStartTime()) + strdate.format(attendance.getStartTime()));
        }
        List<String> holidayDaysData = new ArrayList<>();//请假详情
        for (Audit audit : holidayDays) {
            holidayDaysData.add(simpleDateFormat3.format(audit.getStartDate()) + strdate.format(audit.getStartDate())+" "+audit.getHolidayDays()+"小时");
        }
        map.put("Late", 0);//迟到
        map.put("leaveEarly", leaveEarly.size());//早退
        map.put("fieldPersonnel", fieldPersonnel.size());//外勤
        map.put("missingCard", missingCard.size());//缺卡
        map.put("overtime", 0);//加班时长，小时为单位
        map.put("Absenteeism", miss.size());//旷工天数
        map.put("holidayDays", holidayDays.size());//某个月的请假时长，小时为单位
        //以下为详情的数据
        map.put("attendanceDaysData", attendanceDaysData);//出勤详情
        map.put("LateData", new ArrayList());//迟到详情
        map.put("leaveEarlyData", leaveEarlyData);//早退详情
        map.put("fieldPersonnelData", fieldPersonnelData);//外勤详情
        map.put("missingCardData", missingCardData);//缺卡详情
        map.put("holidayDaysData", holidayDaysData);//请假详情
        map.put("overtimeData", new ArrayList());//加班详情
        map.put("AbsenteeismData", AbsenteeismData);//旷工详情
        return map;
    }

    public HashMap workStatisticsConfirmed(WorkStatistics workStatistics) {//固定模式
        //获取用户所属组的上班规则
        /*GroupRule groupRule = groupRuleService.findGroupNameAndGroupStatus(workStatistics.getGroup(), 0);
        double groupAttendanceDuration = 8;//默认每天上班时长为8小时
        if (!groupRule.equals(null)) {
            groupAttendanceDuration = groupRule.getGroupAttendanceDuration();//规则非空，重置上班时长
        }*/
        SimpleDateFormat strdate = new SimpleDateFormat("E", Locale.SIMPLIFIED_CHINESE);
        //SimpleDateFormat strdate = new SimpleDateFormat("EEEE");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy-MM-dd ");
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm");
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat(" HH:mm");

        List<Attendance> attendanceDays = attendanceDao.getAttendanceDays(workStatistics);//某个月的出勤
        //int Late = attendanceDao.getLates(workStatistics);//某个月的迟到次数
        List<Attendance> Late = attendanceDao.getLates(workStatistics);//某个月的迟到次数
        List<Attendance> leaveEarly = attendanceDao.getLeaveEarly(workStatistics);//某个月的早退次数
        List<Audit> fieldPersonnel = auditDao.getFieldPersonnel(workStatistics);//某个月的外勤次数
        List<Attendance> missingCard = attendanceDao.getMissingCard(workStatistics);//某个月的缺卡天数
        List<Audit> holidayDays = auditDao.getHolidayDays(workStatistics);//某个月的请假时长，小时为单位
        List<Audit> holidayList = auditDao.getHolidayList(workStatistics);//某个月的请假审批和外勤通过记录
        //List<Attendance> overtime = attendanceDao.getOverTimeList(workStatistics);//某个月总的加班。
        //double overtime_second = attendanceDao.getOverTime(workStatistics);//某个月总的加班时间，秒为单位。
        //加班时间转换为小时为单位，取2位小数
        //double overtime_hour = new BigDecimal(overtime_second / (60 * 60)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //List<Integer> miss = new ArrayList<>();//没有打卡的日期
        CopyOnWriteArrayList<Integer> miss = new CopyOnWriteArrayList<>();//没有打卡的日期
        Calendar calendar = Calendar.getInstance();

        int dayOfMonth = 0;
        String str = simpleDateFormat2.format(calendar.getTime());
        if (workStatistics.getMonth().equals(str)) {//传进来的参数是本月
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//今天是几号
        } else {
            try {
                calendar.setTime(simpleDateFormat2.parse(workStatistics.getMonth()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dayOfMonth = calendar.getActualMaximum(Calendar.DATE);//不是本月的话，看看参数的月份有几天
        }

        //以下为统计某个月的旷工次数
        List<String> AbsenteeismData = new ArrayList<>();//旷工详情
        List<Integer> attendanceList = attendanceDao.getAttendanceDaysList(workStatistics);//打卡的日期
        for (int i = 1; i <= dayOfMonth; i++) {
            if (attendanceList.contains(i)) continue;
            miss.add(i);
        }

        List<Integer> holidays = new ArrayList<>();//请假的日期集合
        //System.out.println("请假开始时间和结束时间：");
        for (Audit audit : holidayList) {
            System.out.println(audit.getStartDate().getDate());
            System.out.println(audit.getEndDate().getDate());
            int start = audit.getStartDate().getDate();
            int end = audit.getEndDate().getDate();
            for (int j = start; j <= end; j++) {
                holidays.add(j);//请假的日期  集合
            }
        }
        for (Integer t : holidays) {
            if (miss.contains(t)) {
                miss.remove(t);//除去请假的日期
            }
        }
        //System.out.println("------------------------除去工作日和节假日");
        Calendar calendar2 = Calendar.getInstance();
        try {
            Date date = simpleDateFormat2.parse(workStatistics.getMonth());
            calendar2.setTime(date);//把时间设为传进来的月份
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(simpleDateFormat.format(calendar2.getTime()));
        //除去工作日和节假日
        for (Integer m : miss) {
            calendar2.set(Calendar.DAY_OF_MONTH, m);
            //如果日期m不是工作日,即休息日或者节假日
            try {
                String check=DateUtils.getWorkDays(calendar2.getTime());
                if((check!=null)&&(!check.contains("0"))){
                    miss.remove(m);//除去休息日和节假日即为旷工日期
                }
                /*if (!("0/r/n".equals(DateUtils.getWorkDays(calendar2.getTime())))) {
                    miss.remove(m);//除去休息日和节假日即为旷工日期
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Integer m : miss) {//封装旷工详情
            calendar2.set(Calendar.DAY_OF_MONTH, m);
            AbsenteeismData.add(simpleDateFormat3.format(calendar2.getTime()) + strdate.format(calendar2.getTime()));
        }
        //组装 map 返回
        double holidayDays_sum = 0;//请假总时长
        for (Audit audit : holidayDays) {
            holidayDays_sum += audit.getHolidayDays();
        }
        HashMap map = new HashMap();
        List<String> attendanceDaysData = new ArrayList<>();//出勤天数详情
        map.put("attendanceDays", attendanceDays.size());//出勤天数
        for (Attendance attendance : attendanceDays) {
            attendanceDaysData.add(simpleDateFormat3.format(attendance.getStartTime()) + strdate.format(attendance.getStartTime()));
        }
        List<String> LateData = new ArrayList<>();//迟到详情
        for (Attendance attendance : Late) {
            LateData.add(simpleDateFormat3.format(attendance.getStartTime()) + strdate.format(attendance.getStartTime()) + simpleDateFormat4.format(attendance.getStartTime()));
        }
        List<String> leaveEarlyData = new ArrayList<>();//早退详情
        for (Attendance attendance : leaveEarly) {
            leaveEarlyData.add(simpleDateFormat3.format(attendance.getEndTime()) + strdate.format(attendance.getEndTime()) + simpleDateFormat4.format(attendance.getEndTime()));
        }
        List<String> fieldPersonnelData = new ArrayList<>();//外勤详情
        for (Audit audit : fieldPersonnel) {
            fieldPersonnelData.add(simpleDateFormat3.format(audit.getSubmitTime()) + strdate.format(audit.getSubmitTime()));
        }
        List<String> missingCardData = new ArrayList<>();//缺卡详情
        for (Attendance attendance : missingCard) {
            missingCardData.add(simpleDateFormat3.format(attendance.getStartTime()) + strdate.format(attendance.getStartTime()));
        }
        List<String> holidayDaysData = new ArrayList<>();//请假详情
        for (Audit audit : holidayDays) {
            holidayDaysData.add(simpleDateFormat3.format(audit.getStartDate()) + strdate.format(audit.getStartDate())+" "+audit.getHolidayDays()+"小时");
        }
        holidayDaysData.add("总时长:"+holidayDays_sum+"小时");
        List<String> overtimeData = new ArrayList<>();//加班详情
        /*for (Attendance attendance : overtime) {
            overtimeData.add(simpleDateFormat3.format(attendance.getStartTime()) + strdate.format(attendance.getStartTime()));
        }*/
        map.put("Late", Late.size());//迟到
        map.put("leaveEarly", leaveEarly.size());//早退
        map.put("fieldPersonnel", fieldPersonnel.size());//外勤
        map.put("missingCard", missingCard.size());//缺卡
        map.put("overtime", 0/*overtime_hour*/);//加班时长，小时为单位
        map.put("Absenteeism", miss.size());//旷工天数
        map.put("holidayDays", holidayDays.size());//某个月的请假时长，小时为单位
        //以下为详情的数据
        map.put("attendanceDaysData", attendanceDaysData);//出勤详情
        map.put("LateData", LateData);//迟到详情
        map.put("leaveEarlyData", leaveEarlyData);//早退详情
        map.put("fieldPersonnelData", fieldPersonnelData);//外勤详情
        map.put("missingCardData", missingCardData);//缺卡详情
        map.put("holidayDaysData", holidayDaysData);//请假详情
        map.put("overtimeData", overtimeData);//加班详情
        map.put("AbsenteeismData", AbsenteeismData);//旷工详情
        return map;
    }

    public WorkStatistics get(String id) {
        return super.get(id);
    }

    public List<WorkStatistics> findList(WorkStatistics workStatistics) {
        return super.findList(workStatistics);
    }

    public PageInfo<WorkStatistics> findPage(PageInfo<WorkStatistics> page, WorkStatistics workStatistics) {
        return super.findPage(page, workStatistics);
    }

    @Transactional(readOnly = false)
    public void save(WorkStatistics workStatistics) {
        super.save(workStatistics);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(WorkStatistics workStatistics) {
        super.dynamicUpdate(workStatistics);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        WorkStatistics workStatistics = get(id);
        if (workStatistics == null || StringUtils.isEmpty(workStatistics.getId())) {
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + workStatistics.toJSONString());
    }

}