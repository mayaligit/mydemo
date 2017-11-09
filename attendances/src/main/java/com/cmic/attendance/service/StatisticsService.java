package com.cmic.attendance.service;

import com.cmic.attendance.dao.StatisticsDao;
import com.cmic.attendance.model.Statistics;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Map<String, Object> checkAttendanceHardworkingByDay(String date,PageInfo page) {

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        PageHelper.startPage(pageNum, pageSize,"workHour DESC");

        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceHardworkingByDay(date);
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());
        ;
        return map;
    }

    public Map<String, Object> checkAttendanceHardworkingByMonth(String date, PageInfo page) {

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize(),"workHour DESC");

        int i = date.indexOf("-");
        String substring = date.substring(i + 1, i + 3);
        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceHardworkingByMonth(substring);

        Map<String, Object> map = new HashMap<>();
        Page pi = (Page)pageInfo;
        long total = pi.getTotal();
        map.put("pageInfo",pageInfo);
        map.put("total",total);
        map.put("pageCount",pi.getPages());

        return map;

    }

    public Map<String, Object> checkAttendanceLatterByMonth(String date, PageInfo page) {

        if(page.getPageNum() <= 0) {
            page.setPageNum(1);
        }
        if(page.getPageSize() <= 0) {
            page.setPageSize(10);
        }
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        PageHelper.startPage(pageNum, pageSize,"lateTime DESC");

        int i = date.indexOf("-");
        String substring = date.substring(i + 1, i + 3);
        List<Map> pageInfo = (List<Map>)this.dao.checkAttendanceLatterByMonth(substring);
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