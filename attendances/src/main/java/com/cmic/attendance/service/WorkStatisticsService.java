package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.dao.WorkStatisticsDao;
import com.cmic.attendance.model.Audit;
import com.cmic.attendance.model.WorkStatistics;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


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

    public WorkStatistics workStatistics(WorkStatistics workStatistics) {
        int attendanceDays = attendanceDao.getAttendanceDays(workStatistics);//某个月的出勤天数
        int Late = attendanceDao.getLates(workStatistics);//某个月的迟到次数
        int leaveEarly = attendanceDao.getLeaveEarly(workStatistics);//某个月的早退次数
        int fieldPersonnel = attendanceDao.getFieldPersonnel(workStatistics);//某个月的外勤次数
        int missingCard = attendanceDao.getMissingCard(workStatistics);//某个月的缺卡天数
        //double holidayDays = auditDao.getHolidayDays(workStatistics);//某个月的请假天数
        List<Audit> holidayList = auditDao.getHolidayList(workStatistics);//某个月的请假审批通过记录
        double overtime = attendanceDao.getOverTime(workStatistics);//某个月总的加班时间，秒为单位。
        //加班时间转换为小时为单位，取2位小数
        Calendar calendar = Calendar.getInstance();
        //System.out.println("---------今天是几号："+ calendar.get(Calendar.DAY_OF_MONTH));
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);//今天是几号
        double overtime_hour = new BigDecimal(overtime / (60 * 60)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        List<Integer> miss=new ArrayList<>();//没有打卡的日期
        //以下为统计某个月的旷工次数
        List<Integer> attendanceList = attendanceDao.getAttendanceDaysList(workStatistics);//打卡的日期
        for(int i=0;i<dayOfMonth;i++){
            if(attendanceList.contains(i))continue;
            miss.add(i);
        }
        for(Audit audit:holidayList){

        }
        System.out.println(111);
        return null;
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