package com.cmic.attendance.service;

import com.cmic.attendance.dao.StatisticsDao;
import com.cmic.attendance.model.Statistics;
import com.cmic.attendance.pojo.StatisticsPojo;
import com.cmic.attendance.vo.AttendanceUserVo;
import com.cmic.attendance.vo.QueryStatisticsVo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.cmic.saas.utils.WebUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* 统计表Service
*/
@Service
@Transactional(readOnly = true)
public class StatisticsService extends CrudService<StatisticsDao, Statistics> {

    public Statistics get(String id) {
        return super.get(id);
    }

    public List<Statistics> findList(Statistics statistics) {
        return super.findList(statistics);
    }

    public PageInfo<Statistics> findPage(PageInfo<Statistics> page, Statistics statistics) {
        return super.findPage(page, statistics);
    }

    @Transactional(readOnly = false)
    public void save(Statistics statistics) {
        super.save(statistics);
    }

    @Transactional(readOnly = false)
    public void dynamicUpdate(Statistics statistics) {
        super.dynamicUpdate(statistics);
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        //判断是否存在
        Statistics statistics = get(id);
        if(statistics==null|| StringUtils.isEmpty(statistics.getId())){
            throw new RestException("删除失败，统计表不存在");
        }
        super.delete(id);
        logger.info("删除统计表：" + statistics.toJSONString());
    }


    /**
     * @author 何家来
     * @return
     * 考勤统计,按日统计勤奋榜
     */
    public Map<String, Object> checkAttendanceHardworkingByDay(QueryStatisticsVo queryStatisticsVo) {


        String date = queryStatisticsVo.getDate();
        PageInfo page = queryStatisticsVo.getPageInfo();

        HttpServletRequest request = WebUtils.getRequest();
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)request.getSession().getAttribute("attendanceUserVo");
        //测试使用，写死
        if(null == attendanceUserVo){
            attendanceUserVo = new AttendanceUserVo();
            attendanceUserVo.setAttendanceGroup("odc");
        }
        String attendanceGroup = attendanceUserVo.getAttendanceGroup();

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize(),"workHour DESC");

        StatisticsPojo statisticsPojo = new StatisticsPojo();
        statisticsPojo.setDate(date);
        statisticsPojo.setAttendanceGroup(attendanceGroup);

        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceHardworkingByDay(statisticsPojo);
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());

        return map;
    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按月统计勤奋榜
     */
    public Map<String, Object> checkAttendanceHardworkingByMonth(QueryStatisticsVo queryStatisticsVo) {

        String date = queryStatisticsVo.getDate();
        PageInfo page = queryStatisticsVo.getPageInfo();

        HttpServletRequest request = WebUtils.getRequest();
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)request.getSession().getAttribute("attendanceUserVo");
        //测试使用，写死
        if(null == attendanceUserVo){
            attendanceUserVo = new AttendanceUserVo();
            attendanceUserVo.setAttendanceGroup("odc");
        }
        String attendanceGroup = attendanceUserVo.getAttendanceGroup();

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize(),"workHour DESC");

        int i = date.lastIndexOf("-");
        String substring = date.substring(0, i);

        StatisticsPojo statisticsPojo = new StatisticsPojo();
        statisticsPojo.setDate(substring);
        statisticsPojo.setAttendanceGroup(attendanceGroup);

        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceHardworkingByMonth(statisticsPojo);

        Map<String, Object> map = new HashMap<>();
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());

        return map;

    }

    /**
     * @author 何家来
     * @return
     * 考勤统计,按月统计迟到榜
     */
    public Map<String, Object> checkAttendanceLatterByMonth(QueryStatisticsVo queryStatisticsVo) {

        String date = queryStatisticsVo.getDate();
        PageInfo page = queryStatisticsVo.getPageInfo();

        HttpServletRequest request = WebUtils.getRequest();
        AttendanceUserVo attendanceUserVo = (AttendanceUserVo)request.getSession().getAttribute("attendanceUserVo");
        //测试使用，写死
        if(null == attendanceUserVo){
            attendanceUserVo = new AttendanceUserVo();
            attendanceUserVo.setAttendanceGroup("odc");
        }
        String attendanceGroup = attendanceUserVo.getAttendanceGroup();

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize(),"lateTime DESC");

        int i = date.lastIndexOf("-");
        String substring = date.substring(0, i);

        StatisticsPojo statisticsPojo = new StatisticsPojo();
        statisticsPojo.setDate(substring);
        statisticsPojo.setAttendanceGroup(attendanceGroup);

        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceLatterByMonth(statisticsPojo);
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());

        return map;
    }

    @Transactional(readOnly = false)
    public Statistics checkAttendanceByCreateByAndCreateTime(String createBy,String createTime){
        return this.dao.checkAttendanceByCreateByAndCreateTime(createBy,createTime);
    }
}