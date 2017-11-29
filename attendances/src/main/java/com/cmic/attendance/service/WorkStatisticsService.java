package com.cmic.attendance.service;

import com.cmic.attendance.dao.WorkStatisticsDao;
import com.cmic.attendance.model.WorkStatistics;
import com.github.pagehelper.PageInfo;
import com.cmic.saas.base.service.CrudService;
import com.cmic.saas.base.web.RestException;
import com.cmic.saas.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Service
 */
@Service
@Transactional(readOnly = true)
public class WorkStatisticsService extends CrudService<WorkStatisticsDao, WorkStatistics> {

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
        if(workStatistics==null|| StringUtils.isEmpty(workStatistics.getId())){
            throw new RestException("删除失败，不存在");
        }
        super.delete(id);
        logger.info("删除：" + workStatistics.toJSONString());
    }

}