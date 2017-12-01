package com.cmic.attendance.service;

import com.cmic.attendance.dao.StatisticsDao;
import com.cmic.attendance.model.Statistics;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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




    @Transactional(readOnly = false)
    public Statistics checkAttendanceByCreateByAndCreateTime(String createBy,String createTime){
        return this.dao.checkAttendanceByCreateByAndCreateTime(createBy,createTime);
    }
}