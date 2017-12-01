package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.dao.WorkStatisticsDao;
import com.cmic.attendance.model.Audit;
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
    AuditDao auditDao;

    public HashMap workStatistics(WorkStatistics workStatistics) {
        int attendanceDays = attendanceDao.getAttendanceDays(workStatistics);//某个月的出勤天数
        int Late = attendanceDao.getLates(workStatistics);//某个月的迟到次数
        int leaveEarly = attendanceDao.getLeaveEarly(workStatistics);//某个月的早退次数
        int fieldPersonnel = auditDao.getFieldPersonnel(workStatistics);//某个月的外勤次数
        int missingCard = attendanceDao.getMissingCard(workStatistics);//某个月的缺卡天数
        double holidayDays = auditDao.getHolidayDays(workStatistics);//某个月的请假时长，天为单位
        List<Audit> holidayList = auditDao.getHolidayList(workStatistics);//某个月的请假审批通过记录
        double overtime = attendanceDao.getOverTime(workStatistics);//某个月总的加班时间，秒为单位。
        //加班时间转换为小时为单位，取2位小数
        double overtime_hour = new BigDecimal(overtime / (60 * 60)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //List<Integer> miss = new ArrayList<>();//没有打卡的日期
        CopyOnWriteArrayList<Integer> miss = new CopyOnWriteArrayList<>();//没有打卡的日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("yyyy-MM");
        //System.out.println("---------今天是几号："+ calendar.get(Calendar.DAY_OF_MONTH));
        int dayOfMonth=0;
        String str=simpleDateFormat2.format(calendar.getTime());
        if(workStatistics.getMonth().equals(str)){//传进来的参数是本月
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//今天是几号
        }else{
            try{
                calendar.setTime(simpleDateFormat2.parse(workStatistics.getMonth()));
            }catch (Exception e){
                e.printStackTrace();
            }
            dayOfMonth=calendar.getActualMaximum(Calendar.DATE);//不是本月的话，看看参数的月份有几天
        }

        //以下为统计某个月的旷工次数
        List<Integer> attendanceList = attendanceDao.getAttendanceDaysList(workStatistics);//打卡的日期
        for (int i = 1; i <= dayOfMonth; i++) {
            if (attendanceList.contains(i)) continue;
            miss.add(i);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm");
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d");
        List<Integer> holidays = new ArrayList<>();//请假的日期集合
        System.out.println("请假开始时间和结束时间：");
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
        System.out.println("------------------------除去工作日和节假日");
        Calendar calendar2=Calendar.getInstance();
        try{
            Date date=simpleDateFormat2.parse(workStatistics.getMonth());
            calendar2.setTime(date);//把时间设为传进来的月份
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(simpleDateFormat.format(calendar2.getTime()));
        //calendar2.set(Calendar.DAY_OF_MONTH,2);
        //除去工作日和节假日
        for (Integer m : miss) {
            calendar2.set(Calendar.DAY_OF_MONTH,m);
            //如果日期m不是工作日,即休息日或者节假日
            try{
                if(!("0".equals(DateUtils.getWorkDays(calendar2.getTime())))){
                    miss.remove(m);//除去休息日和节假日即为旷工日期
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(111);
        //组装 map 返回
        HashMap map=new HashMap();
        map.put("attendanceDays",attendanceDays);//出勤天数
        map.put("Late",Late);//迟到
        map.put("leaveEarly",leaveEarly);//早退
        map.put("fieldPersonnel",fieldPersonnel);//外勤
        map.put("missingCard",missingCard);//缺卡
        map.put("overtime",overtime_hour);//加班时长，小时为单位
        map.put("Absenteeism",miss.size());//旷工天数
        map.put("holidayDays",holidayDays);//某个月的请假时长，天为单位
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