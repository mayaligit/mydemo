package com.cmic.attendance.service;

import com.cmic.attendance.dao.AttendanceDao;
import com.cmic.attendance.dao.AuditDao;
import com.cmic.attendance.dao.WorkStatisticsDao;
import com.cmic.attendance.model.WorkStatistics;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        double holidayDays = auditDao.getHolidayDays(workStatistics);//某个月的请假天数
        double overtime = attendanceDao.getOverTime(workStatistics);//某个月总的加班时间，秒为单位。
        //加班时间转换为小时为单位，取2位小数
        double overtime_hour=  new BigDecimal(overtime/(60*60)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        //以下为统计某个月的旷工次数
        List<Integer> missDays=attendanceDao.getAttendanceDaysList(workStatistics);
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